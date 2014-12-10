package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.util.Date;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
import org.apache.shiro.SecurityUtils;
import org.crusoe.entity.workflow.governmentInformationDisclosure.AttachmentEntity;
import org.crusoe.entity.workflow.governmentInformationDisclosure.Datum;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.DatumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatumService {
	@Autowired
	private DatumDao datumDao;
	@Autowired
	private TaskService taskService;

	public Datum create(String title, String substance, String attachmentList) {
		Datum datum = new Datum();
		datum.setTitle(title);
		datum.setSubstance(substance);
		datum.setCreateTime(new Date());
		datum.setAuthor(SecurityUtils.getSubject().getPrincipal().toString());
		String[] attachmentIds = attachmentList.split(",");
		for (String id : attachmentIds) {
			Attachment attachment = taskService.getAttachment(id);
			if (attachment != null) {
				AttachmentEntity ae = new AttachmentEntity();
				ae.setId(attachment.getId());
				ae.setName(attachment.getName());
				datum.getAttachments().add(ae);
			}

		}
		return datum;
	}
}
