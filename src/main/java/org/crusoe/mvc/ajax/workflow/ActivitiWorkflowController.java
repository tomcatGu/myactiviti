package org.crusoe.mvc.ajax.workflow;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;

//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.node.ObjectNode;
import org.crusoe.dto.repository.ModelDTO;
import org.crusoe.dynamic.DynamicDeployBeans2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping(value = "/workflow")
public class ActivitiWorkflowController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;



	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String getIndexForm(Model model) {


		return "/workflow/index";
	}

	@RequiresUser
	@RequestMapping(value = "listModels", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> listTasks(@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size)
			throws IllegalAccessException, InvocationTargetException {

		List<org.activiti.engine.repository.Model> list = repositoryService
				.createModelQuery().orderByCreateTime().desc()
				.listPage(start, size);
		List<ModelDTO> todoList = new ArrayList<ModelDTO>();
		HashMap<String, Object> rets = new HashMap<String, Object>();
		for (org.activiti.engine.repository.Model model : list) {
			ModelDTO modelDTO = new ModelDTO();
			BeanUtils.copyProperties(modelDTO, model);
			todoList.add(modelDTO);

		}

		long count = repositoryService.createModelQuery().count();
		rets.put("count", count);
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", todoList);

		return rets;

	}

	@RequestMapping(value = "deploy", method = RequestMethod.GET)
	public String getDeployForm(Model model) {

		return "/workflow/deployForm";
	}

	@RequestMapping(value = "process/start", method = RequestMethod.GET)
	public String startProcessInstance(
			@RequestParam("processKey") String processKey) {

		runtimeService.startProcessInstanceByKey(processKey);

		return "/task/index";

	}

	@RequestMapping(value = "resource/{packageName}/{resourceName}", method = RequestMethod.GET)
	public String getResource(@PathVariable("packageName") String packageName,
			@PathVariable("resourceName") String resourceName) {

		return packageName + "/" + resourceName;

	}

	@RequestMapping(value = "create")
	public void create(@RequestParam("name") String name,
			@RequestParam("key") String key,
			@RequestParam("description") String description,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace",
					"http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			org.activiti.engine.repository.Model modelData = repositoryService
					.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));

			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(),
					editorNode.toString().getBytes("UTF-8"));
			response.setCharacterEncoding("UTF-8");
			response.sendRedirect(request.getContextPath()
					+ "/modeler/service/editor?id=" + modelData.getId());

		} catch (Exception e) {

		}
	}

	/**
	 * 根据Model部署流程
	 */
	@RequestMapping(value = "deploy/{modelId}")
	public String deploy(@PathVariable("modelId") String modelId,
			RedirectAttributes redirectAttributes) {
		try {
			org.activiti.engine.repository.Model modelData = repositoryService
					.getModel(modelId);
			JsonNode modelNode = (JsonNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData
							.getId()));

			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter()
					.convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);

			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment()
					.name(modelData.getName())
					.addString(processName, new String(bpmnBytes)).deploy();
			redirectAttributes.addFlashAttribute("message", "部署成功，部署ID="
					+ deployment.getId());
		} catch (Exception e) {
			// logger.error("根据模型部署流程失败：modelId={}", modelId, e);
		}
		return "redirect:/workflow/index";
	}

	@RequestMapping(value = "/deployments", method = RequestMethod.POST)
	public String deploy(
			@RequestParam(value = "file", required = false) MultipartFile file) {

		String fileName = file.getOriginalFilename();

		try {
			InputStream fileInputStream = file.getInputStream();

			String extension = FilenameUtils.getExtension(fileName);
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(
						new BufferedInputStream(fileInputStream));
				repositoryService.createDeployment().addZipInputStream(zip)
						.deploy();

			} else if (extension.equals("png")) {
				repositoryService.createDeployment()
						.addInputStream(fileName, fileInputStream).deploy();
			} else if (extension.indexOf("bpmn20.xml") != -1) {
				repositoryService.createDeployment()
						.addInputStream(fileName, fileInputStream).deploy();
			} else if (extension.equals("bpmn")) {
				/*
				 * bpmn扩展名特殊处理，转换为bpmn20.xml
				 */
				String baseName = FilenameUtils.getBaseName(fileName);
				repositoryService
						.createDeployment()
						.addInputStream(baseName + ".bpmn20.xml",
								fileInputStream).deploy();
			} else {
				throw new ActivitiException("no support file type of "
						+ extension);
			}
		} catch (Exception e) {
			// logger.error("error on deploy process, because of file input stream");
		}

		return "redirect:/workflow/index";
	}

	@RequestMapping(value = "deployments", method = RequestMethod.GET)
	public String getDeployments(@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) {

		List<Object[]> objects = new ArrayList<Object[]>();

		List<ProcessDefinition> processDefinitionList = repositoryService
				.createProcessDefinitionQuery().listPage(start, size);
		for (ProcessDefinition processDefinition : processDefinitionList) {
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery()
					.deploymentId(deploymentId).singleResult();
			objects.add(new Object[] { processDefinition, deployment });

		}
		long total = repositoryService.createProcessDefinitionQuery().count();
		model.addAttribute("processes", objects);
		return "/workflow/processList";
	}

	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param deploymentId
	 *            流程部署的ID
	 * @param resourceName
	 *            资源名称(foo.xml|foo.png)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "deployment", method = RequestMethod.GET)
	public void loadByDeployment(
			@RequestParam("deploymentId") String deploymentId,
			@RequestParam("resourceName") String resourceName,
			HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = repositoryService.getResourceAsStream(
				deploymentId, resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public ModelAndView submitForm(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = null;
		Map<String, String[]> map1 = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = map1.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}

		formService.submitStartFormData("value", map);
		return new ModelAndView();
	}
}
