package org.crusoe.mvc.ajax.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
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

}