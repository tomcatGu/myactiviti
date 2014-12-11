package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.crusoe.entity.workflow.governmentInformationDisclosure.AttachmentEntity;
import org.crusoe.entity.workflow.governmentInformationDisclosure.Datum;
import org.crusoe.entity.workflow.governmentInformationDisclosure.DatumAttachmentEntity;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.DatumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class DatumService {
	@Autowired
	private DatumDao datumDao;
	@Autowired
	private TaskService taskService;

	public Datum save(DelegateExecution execution, String title,
			String substance, String attachmentList) {
		Datum datum = new Datum();
		datum.setTitle(title);
		datum.setSubstance(substance);
		datum.setCreateTime(new Date());
		datum.setAuthor(SecurityUtils.getSubject().getPrincipal().toString());
		Task task = taskService.createTaskQuery()
				.executionId(execution.getId()).singleResult();
		datum.setTaskId(task.getId());
		String[] attachmentIds = attachmentList.split(",");
		for (String id : attachmentIds) {
			Attachment attachment = taskService.getAttachment(id);
			if (attachment != null) {
				DatumAttachmentEntity ae = new DatumAttachmentEntity();
				ae.setId(attachment.getId());
				ae.setName(attachment.getName());
				datum.getAttachments().add(ae);
			}

		}
		datumDao.save(datum);
		return datum;
	}

	public Page<Datum> findAll(PageRequest pageRequest) {
		// TODO Auto-generated method stub

		return datumDao.findAll(pageRequest);

	}

	public long count() {
		// TODO Auto-generated method stub
		return datumDao.count();
	}
}
