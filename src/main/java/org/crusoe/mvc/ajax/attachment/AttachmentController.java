package org.crusoe.mvc.ajax.attachment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.IOUtils;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/runtime/attachment")
public class AttachmentController {
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
	protected AccountService accountService;

	@RequestMapping(value = "{attachmentId}")
	public ResponseEntity<byte[]> getAttachmentAsStream(
			@PathVariable String attachmentId, HttpServletResponse response)
			throws IOException {
		Attachment attachment = taskService.getAttachment(attachmentId);
		InputStream is = taskService.getAttachmentContent(attachmentId);
		byte[] bb = IOUtils.toByteArray(is);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment",
				attachment.getName());
		headers.setContentLength(bb.length);

		headers.setCacheControl("no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
		headers.setPragma("no-cache");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		// response.setContentType("application/octet-stream;charset=UTF-8");
		return new ResponseEntity<byte[]>(bb, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "{attachmentId}", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, ? extends Object> deleteAttachment(
			@PathVariable String attachmentId) {
		
		
		taskService.deleteAttachment(attachmentId);

		Map<String, String> msg = new HashMap<String, String>();
		msg.put("msg", "删除附件成功");
		return msg;

	}
	
	@RequestMapping(value = "uploadAttachment/{taskId}", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> uploadAttachment(
			@PathVariable("taskId") String taskId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		HashMap<String, Object> rets = new HashMap<String, Object>();
		String fileName = file.getOriginalFilename();

		try {
			InputStream fileInputStream = file.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(fileInputStream);
			String processInstanceId = taskService.createTaskQuery()
					.taskId(taskId).singleResult().getProcessInstanceId();
			Attachment attachment=taskService.createAttachment(FilenameUtils.getExtension(fileName),
					taskId, processInstanceId, fileName, "description", bis);
			rets.put("msg", "OK");
			rets.put("attachmentId", attachment.getId());
			rets.put("filename", fileName);
			rets.put("size", file.getSize());
		} catch (Exception e) {
			rets.put("msg", "upload failed.");
		}

		return rets;

	}

}