package org.crusoe.mvc.ajax.statistical;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.crusoe.dto.governmentInformationDisclosure.DatumDTO;
import org.crusoe.dto.governmentInformationDisclosure.GovernmentInformationDisclosureDTO;
import org.crusoe.entity.User;
import org.crusoe.entity.workflow.governmentInformationDisclosure.Datum;
import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.crusoe.entity.workflow.governmentInformationDisclosure.StatisticalSheet;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.GovernmentInformationDisclosureDao;
import org.crusoe.service.AccountService;
import org.crusoe.service.workflow.governmentInformationDisclosure.DatumService;
import org.crusoe.service.workflow.governmentInformationDisclosure.StatisticalSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/statistical")
public class StatisticalController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;

	@Autowired
	private GovernmentInformationDisclosureDao gidDao;

	@Autowired
	protected AccountService accountService;
	@Autowired
	protected StatisticalSheetService statisticalSheetService;
	@Autowired
	protected DatumService datumService;

	@RequestMapping(value = "counter/{processDefinitionId}", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> counter(@PathVariable String processDefinitionId,
			@RequestParam("statisticalRange") String statisticalRange,
			@RequestParam("separatorChars") String separatorChars,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> rets = new HashMap<String, Object>();
		List<HistoricProcessInstance> processes = historyService
				.createHistoricProcessInstanceQuery()
				.processDefinitionId(processDefinitionId).list();
		HashMap<String, Integer> statisticalResult = new HashMap<String, Integer>();
		for (HistoricProcessInstance process : processes) {
			List<HistoricVariableInstance> variables = historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(process.getId()).list();
			for (HistoricVariableInstance variable : variables) {
				if (variable.getVariableName().equals(statisticalRange)) {
					String val = (String) variable.getValue();
					String[] keys = StringUtils.split(val, separatorChars);
					for (String key : keys) {
						if (statisticalResult.containsKey(key)) {
							statisticalResult.put(key,
									statisticalResult.get(key) + 1);
						} else {
							statisticalResult.put(key, 1);
						}

					}
				}

			}
		}
		rets.put("err", false);
		rets.put("statisticalResult", statisticalResult);
		return rets;
	}

	@RequestMapping(value = "countergid", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> counterGovernmentInformationDisclosure(
			@RequestParam("startTime") final String startTime,
			@RequestParam("endTime") final String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> rets = new HashMap<String, Object>();

		List<GovernmentInformationDisclosure> gids = gidDao
				.findAll(new Specification<GovernmentInformationDisclosure>() {
					// Date startTime;
					// Date endTime;

					@Override
					public Predicate toPredicate(
							Root<GovernmentInformationDisclosure> root,
							CriteriaQuery<?> query, CriteriaBuilder builder) {
						// TODO Auto-generated method stub
						SimpleDateFormat dateformat1 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Predicate predicate = builder.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();

						try {
							expressions.add(builder.between(
									root.<Date> get("createTime"),
									dateformat1.parse(startTime),
									dateformat1.parse(endTime)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return predicate;
					}
				});
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Integer> statisticalResult;
		for (GovernmentInformationDisclosure gid : gids) {
			String username = gid.getCreateUser();
			// username =
			// accountService.findUserByLoginName(username).getName();
			User user = accountService.findUserByLoginName(gid.getCreateUser());

			String organizationName = user.getOrganization().getName();
			if (organizationName == null)
				break;
			if (result.containsKey(organizationName)) {
				statisticalResult = (HashMap<String, Integer>) result
						.get(organizationName);
			} else {
				statisticalResult = new HashMap<String, Integer>();
				result.put(organizationName, statisticalResult);
			}
			String fod = gid.getFormOfDisclosure();
			if (statisticalResult.containsKey(fod)) {
				statisticalResult.put(fod, statisticalResult.get(fod) + 1);
			} else {
				if (fod != null)
					statisticalResult.put(fod, 1);

			}

			String formOfResponse = gid.getFormOfResponse();
			if (statisticalResult.containsKey(formOfResponse)) {
				statisticalResult.put(formOfResponse,
						statisticalResult.get(formOfResponse) + 1);
			} else {
				if (formOfResponse != null)
					statisticalResult.put(formOfResponse, 1);

			}

		}

		rets.put("err", false);
		rets.put("statisticalResult", result);
		return rets;
	}

	@RequestMapping(value = "analyseByApplicant", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> analyseByApplicant(
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam("applicantName") final String applicantName,
			@RequestParam("startTime") final String startTime,
			@RequestParam("endTime") final String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> rets = new HashMap<String, Object>();
		Sort sortRequest = "desc".equals(order.toLowerCase()) ? new Sort(
				Direction.DESC, new String[] { sort }) : new Sort(
				Direction.ASC, new String[] { sort });
		PageRequest pageRequest = new PageRequest(start / size, size,
				sortRequest);
		Specification<GovernmentInformationDisclosure> spec = new Specification<GovernmentInformationDisclosure>() {
			// Date startTime;
			// Date endTime;

			@Override
			public Predicate toPredicate(
					Root<GovernmentInformationDisclosure> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				// TODO Auto-generated method stub
				SimpleDateFormat dateformat1 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Predicate predicate = builder.conjunction();
				List<Expression<Boolean>> expressions = predicate
						.getExpressions();

				try {
					if (!startTime.equals(endTime)) {
						expressions.add(builder.between(
								root.<Date> get("createTime"),
								dateformat1.parse(startTime),
								dateformat1.parse(endTime)));
					}
					if (!applicantName.isEmpty()) {
						expressions
								.add(builder.equal(
										root.<String> get("citizenName"),
										applicantName));
					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return predicate;
			}
		};

		Page<GovernmentInformationDisclosure> gids = gidDao.findAll(spec,
				pageRequest);
		List<GovernmentInformationDisclosureDTO> result = Lists.newArrayList();

		for (GovernmentInformationDisclosure gid : gids) {
			GovernmentInformationDisclosureDTO gidDTO = new GovernmentInformationDisclosureDTO();
			try {
				PropertyUtils.copyProperties(gidDTO, gid);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.add(gidDTO);

		}

		rets.put("err", false);
		rets.put("records", result);
		rets.put("count", gidDao.findAll(spec).size());
		rets.put("start", start);
		rets.put("size", size);
		return rets;
	}

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> importFromExcel(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		HashMap<String, Object> rets = new HashMap<String, Object>();
		try {
			InputStream fileInputStream = file.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(fileInputStream);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(bis);

			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {

				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				Iterator<Row> iter = hssfSheet.iterator();
				while (iter.hasNext()) {
					HSSFRow hssfRow = (HSSFRow) iter.next();
					// HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}

					GovernmentInformationDisclosure gid = new GovernmentInformationDisclosure();
					// 循环列Cell
					// 0申请人 1提交部门 2申请事项 3处理结果
					// for (int cellNum = 0; cellNum <=4; cellNum++) {
					HSSFCell citizenName = hssfRow.getCell(0);
					if (citizenName == null) {
						continue;
					}
					gid.setCitizenName(citizenName.getStringCellValue());

					HSSFCell departmentName = hssfRow.getCell(1);
					if (departmentName == null) {
						continue;
					}
					gid.setSubmitDepartment(departmentName.getStringCellValue());

					HSSFCell applicationName = hssfRow.getCell(2);
					if (applicationName == null) {
						continue;
					}
					gid.setApplicationName(applicationName.getStringCellValue());

					HSSFCell formOfResponse = hssfRow.getCell(3);
					if (formOfResponse == null) {
						continue;
					}
					gid.setFormOfResponse(formOfResponse.getStringCellValue());
					gidDao.save(gid);

				}

			}

			rets.put("msg", "OK");

		} catch (Exception e) {
			rets.put("msg", "upload failed.");
		}

		return rets;

	}

	@RequestMapping(value = "index")
	public String getIndexForm() {
		return "statistical/index";
	}

	@RequestMapping(value = "isExists/{annual}/{username}")
	public @ResponseBody
	boolean isExists(@PathVariable("annual") String annual,
			@PathVariable("username") String username) {
		return statisticalSheetService.isExists(annual, username);

	}

	@RequestMapping(value = "total/{annual}/{status}")
	public String countAnnualStatistical(@PathVariable("annual") String annual,
			@PathVariable("status") String status, Model model) {
		model.addAttribute("result",
				statisticalSheetService.total(annual, status));

		return "governmentInformationDisclosure/statisticalSheet.readonly";
	}

	@RequestMapping(value = "viewSheet/{id}")
	public String countAnnualStatistical(@PathVariable("id") String id,
			Model model) {
		model.addAttribute("result",
				statisticalSheetService.findById(Long.parseLong(id)));

		return "governmentInformationDisclosure/statisticalSheet.readonly";
	}

	@RequestMapping(value = "listSheet/{annual}/{status}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> listSheet(@PathVariable("annual") String annual,
			@PathVariable("status") String status,
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) {
		HashMap<String, Object> sheets = new HashMap<String, Object>();

		Sort sortRequest = "desc".equals(order.toLowerCase()) ? new Sort(
				Direction.DESC, new String[] { sort }) : new Sort(
				Direction.ASC, new String[] { sort });
		PageRequest pageRequest = new PageRequest(start / size, size,
				sortRequest);
		Iterator<StatisticalSheet> sheetList = statisticalSheetService
				.findByAnnualAndStatus(annual, status, pageRequest);
		while (sheetList.hasNext()) {
			StatisticalSheet ss = sheetList.next();
			HashMap<String, Object> aSheet = new HashMap<String, Object>();
			aSheet.put("id", ss.getId());
			aSheet.put("name", ss.getLoginName());
			aSheet.put("fillingDate", ss.getFillingDate());
			aSheet.put("state", ss.getStatus());
			sheets.put(ss.getId().toString(), aSheet);

		}
		return sheets;
	}

	@RequestMapping(value = "listDatum", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> listDatum(@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			Model model) {

		List<Object> result = Lists.newArrayList();
		Sort sortRequest = "desc".equals(order.toLowerCase()) ? new Sort(
				Direction.DESC, new String[] { sort }) : new Sort(
				Direction.ASC, new String[] { sort });
		PageRequest pageRequest = new PageRequest(start / size, size,
				sortRequest);
		Iterator iter = datumService.findAll(pageRequest).iterator();
		while (iter.hasNext()) {
			Datum datum = (Datum) iter.next();
			DatumDTO datumDTO = new DatumDTO();
			datumDTO.setId(datum.getId());
			datumDTO.setTitle(datum.getTitle());
			datumDTO.setContent(datum.getSubstance());
			datumDTO.setAuthor(datum.getAuthor());
			HistoricTaskInstance task = historyService
					.createHistoricTaskInstanceQuery()
					.taskId(datum.getTaskId()).singleResult();
			datumDTO.setTaskId(task.getId());
			datumDTO.setProcessDefinitionId(task.getProcessDefinitionId());
			datumDTO.setTaskDefinitionKey(task.getTaskDefinitionKey());
			datumDTO.setCreateTime(datum.getCreateTime());
			result.add(datumDTO);

		}

		HashMap<String, Object> rets = new HashMap<String, Object>();
		rets.put("count", datumService.count());
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", result);
		return rets;

	}

	@RequestMapping(value = "datums", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, ? extends Object> batchDelete(
			@RequestParam(value = "items[]", required = false) String[] items)
			throws Exception {

		for (int i = 0; i < items.length; i++) {
			datumService.delete(Long.parseLong(items[i]));
		}
		Map<String, String> msgs = new HashMap<String, String>();

		msgs.put("msg", "删除成功");
		return msgs;
	}

}
