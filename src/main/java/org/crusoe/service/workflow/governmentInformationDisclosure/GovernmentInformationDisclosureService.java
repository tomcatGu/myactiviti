package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.apache.lucene.document.Field;
import org.apache.shiro.SecurityUtils;
import org.crusoe.dto.FieldDTO;
import org.crusoe.dto.HistoricProcessInstanceDTO;
import org.crusoe.dto.fulltextSearch.SearchResultDTO;
import org.crusoe.dto.task.TaskDTO;
import org.crusoe.entity.User;
import org.crusoe.entity.workflow.governmentInformationDisclosure.Application;
import org.crusoe.entity.workflow.governmentInformationDisclosure.AttachmentEntity;
import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.crusoe.entity.workflow.governmentInformationDisclosure.Reply;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.ApplicationDao;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.GovernmentInformationDisclosureDao;
import org.crusoe.service.AccountService;
import org.crusoe.util.LuceneIKUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * 流程业务类
 * 
 * @author gwx
 */
// Spring Service Bean的标识.
@Service
@Transactional(readOnly = true)
public class GovernmentInformationDisclosureService {
	@Autowired
	private GovernmentInformationDisclosureDao gidDao;
	@Autowired
	private ApplicationDao applicationDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private LuceneIKUtil ikUtil;

	public GovernmentInformationDisclosure save(DelegateExecution execution,
			String applicationName, String citizenName, String citizenWorkunit,
			String citizenCertificate, String citizenCertificateID,
			String citizenTelphone, String citizenZipcode,
			String citizenAddress, String citizenFax, String citizenEmail,
			String groupName, String groupID, String groupDelegate,
			String groupDelegateName, String groupDelegateTelphone,
			String groupDelegateFax, String groupDelegateAddress,
			String groupDelegateEmail, String applicationTime,
			String submitDepartment, String description, String purpose,
			String reply, String mode, String obtainMode,
			String formOfDisclosure) {

		GovernmentInformationDisclosure gid = new GovernmentInformationDisclosure();

		gid.setApplicationName(applicationName);
		gid.setCitizenName(citizenName);
		gid.setCitizenWorkunit(citizenWorkunit);
		gid.setCitizenCertificate(citizenCertificate);
		gid.setCitizenCertificateID(citizenCertificateID);
		gid.setCitizenTelphone(citizenTelphone);
		gid.setCitizenZipcode(citizenZipcode);
		gid.setCitizenAddress(citizenAddress);
		gid.setCitizenFax(citizenFax);
		gid.setCitizenEmail(citizenEmail);

		gid.setGroupName(groupName);
		gid.setGroupID(groupID);
		gid.setGroupDelegate(groupDelegate);
		gid.setGroupDelegateName(groupDelegateName);
		gid.setGroupDelegateTelphone(groupDelegateTelphone);
		gid.setGroupDelegateFax(groupDelegateFax);
		gid.setGroupDelegateAddress(groupDelegateAddress);
		gid.setGroupDelegateEmail(groupDelegateEmail);
		gid.setFormOfDisclosure(formOfDisclosure);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			gid.setApplicationTime(formatter.parse(applicationTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gid.setSubmitDepartment(submitDepartment);
		gid.setDescription(description);
		gid.setPurpose(purpose);

		Reply replyEntity = new Reply();
		replyEntity.setReply(reply);
		replyEntity.setReplyTime(new Date());
		replyEntity.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		replyEntity
				.setUsername(accountService.findUserByLoginName(
						SecurityUtils.getSubject().getPrincipal().toString())
						.getName());
		gid.getReplies().add(replyEntity);

		gid.setMode(mode);
		gid.setObtainMode(obtainMode);
		gidDao.save(gid);
		// LuceneIKUtil ik = new LuceneIKUtil("/IK");
		// execution.
		List<FieldDTO> fields = Lists.newArrayList();
		FieldDTO field = new FieldDTO("id", gid.getId().toString(),
				Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("processInstanceId",
				execution.getProcessInstanceId(), Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("applicationName", gid.getApplicationName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("description", gid.getDescription(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("citizenName", gid.getCitizenName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("groupName", gid.getGroupName(), Field.Store.YES,
				false);
		fields.add(field);
		field = new FieldDTO("review", "", Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("reply", reply, Field.Store.YES, false);
		fields.add(field);

		ikUtil.addIndex(fields);

		// ikUtil.addIndex(execution.getProcessInstanceId(), gid.getId(),
		// gid.getApplicationName(), gid.getDescription());

		return gid;

	}

	public GovernmentInformationDisclosure save(DelegateExecution execution,
			String applicationName, String citizenName, String citizenWorkunit,
			String citizenCertificate, String citizenCertificateID,
			String citizenTelphone, String citizenZipcode,
			String citizenAddress, String citizenFax, String citizenEmail,
			String groupName, String groupID, String groupDelegate,
			String groupDelegateName, String groupDelegateTelphone,
			String groupDelegateFax, String groupDelegateAddress,
			String groupDelegateEmail, String applicationTime,
			String submitDepartment, String description, String purpose,
			String reply, String mode, String obtainMode,
			String formOfDisclosure, String attachmentList) {

		GovernmentInformationDisclosure gid = new GovernmentInformationDisclosure();

		gid.setApplicationName(applicationName);
		gid.setCitizenName(citizenName);
		gid.setCitizenWorkunit(citizenWorkunit);
		gid.setCitizenCertificate(citizenCertificate);
		gid.setCitizenCertificateID(citizenCertificateID);
		gid.setCitizenTelphone(citizenTelphone);
		gid.setCitizenZipcode(citizenZipcode);
		gid.setCitizenAddress(citizenAddress);
		gid.setCitizenFax(citizenFax);
		gid.setCitizenEmail(citizenEmail);

		gid.setGroupName(groupName);
		gid.setGroupID(groupID);
		gid.setGroupDelegate(groupDelegate);
		gid.setGroupDelegateName(groupDelegateName);
		gid.setGroupDelegateTelphone(groupDelegateTelphone);
		gid.setGroupDelegateFax(groupDelegateFax);
		gid.setGroupDelegateAddress(groupDelegateAddress);
		gid.setGroupDelegateEmail(groupDelegateEmail);
		gid.setFormOfDisclosure(formOfDisclosure);
		gid.setCreateTime(new Date());
		gid.setCreateUser(SecurityUtils.getSubject().getPrincipal().toString());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			gid.setApplicationTime(formatter.parse(applicationTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gid.setSubmitDepartment(submitDepartment);
		gid.setDescription(description);
		gid.setPurpose(purpose);

		Reply replyEntity = new Reply();
		replyEntity.setReply(reply);

		replyEntity.setReplyTime(new Date());

		replyEntity.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		replyEntity
				.setUsername(accountService.findUserByLoginName(
						SecurityUtils.getSubject().getPrincipal().toString())
						.getName());

		String[] attachmentIds = attachmentList.split(",");
		for (String id : attachmentIds) {
			Attachment attachment = taskService.getAttachment(id);
			if (attachment != null) {
				AttachmentEntity ae = new AttachmentEntity();
				ae.setId(attachment.getId());
				ae.setName(attachment.getName());
				replyEntity.getAttachments().add(ae);
			}

		}

		gid.getReplies().add(replyEntity);

		gid.setMode(mode);
		gid.setObtainMode(obtainMode);
		gidDao.save(gid);
		// LuceneIKUtil ik = new LuceneIKUtil("/IK");
		// execution.
		List<FieldDTO> fields = Lists.newArrayList();
		FieldDTO field = new FieldDTO("id", gid.getId().toString(),
				Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("processInstanceId",
				execution.getProcessInstanceId(), Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("applicationName", gid.getApplicationName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("description", gid.getDescription(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("citizenName", gid.getCitizenName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("groupName", gid.getGroupName(), Field.Store.YES,
				false);
		fields.add(field);
		field = new FieldDTO("review", "", Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("reply", reply, Field.Store.YES, false);
		fields.add(field);

		ikUtil.addIndex(fields);

		// ikUtil.addIndex(execution.getProcessInstanceId(), gid.getId(),
		// gid.getApplicationName(), gid.getDescription());

		return gid;

	}

	public GovernmentInformationDisclosure save(DelegateExecution execution,
			String applicationName, String citizenName, String citizenWorkunit,
			String citizenCertificate, String citizenCertificateID,
			String citizenTelphone, String citizenZipcode,
			String citizenAddress, String citizenFax, String citizenEmail,
			String groupName, String groupID, String groupDelegate,
			String groupDelegateName, String groupDelegateTelphone,
			String groupDelegateFax, String groupDelegateAddress,
			String groupDelegateEmail, String applicationTime,
			String submitDepartment, String description, String purpose,
			String reply, String mode, String obtainMode,
			String formOfDisclosure, String formOfResponse,
			String attachmentList) {

		GovernmentInformationDisclosure gid = new GovernmentInformationDisclosure();

		gid.setApplicationName(applicationName);
		gid.setCitizenName(citizenName);
		gid.setCitizenWorkunit(citizenWorkunit);
		gid.setCitizenCertificate(citizenCertificate);
		gid.setCitizenCertificateID(citizenCertificateID);
		gid.setCitizenTelphone(citizenTelphone);
		gid.setCitizenZipcode(citizenZipcode);
		gid.setCitizenAddress(citizenAddress);
		gid.setCitizenFax(citizenFax);
		gid.setCitizenEmail(citizenEmail);

		gid.setGroupName(groupName);
		gid.setGroupID(groupID);
		gid.setGroupDelegate(groupDelegate);
		gid.setGroupDelegateName(groupDelegateName);
		gid.setGroupDelegateTelphone(groupDelegateTelphone);
		gid.setGroupDelegateFax(groupDelegateFax);
		gid.setGroupDelegateAddress(groupDelegateAddress);
		gid.setGroupDelegateEmail(groupDelegateEmail);
		gid.setFormOfDisclosure(formOfDisclosure);
		gid.setCreateTime(new Date());
		gid.setCreateUser(SecurityUtils.getSubject().getPrincipal().toString());
		gid.setFormOfResponse(formOfResponse);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			gid.setApplicationTime(formatter.parse(applicationTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gid.setSubmitDepartment(submitDepartment);
		gid.setDescription(description);
		gid.setPurpose(purpose);

		Reply replyEntity = new Reply();
		replyEntity.setReply(reply);

		replyEntity.setReplyTime(new Date());

		replyEntity.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		replyEntity
				.setUsername(accountService.findUserByLoginName(
						SecurityUtils.getSubject().getPrincipal().toString())
						.getName());

		String[] attachmentIds = attachmentList.split(",");
		for (String id : attachmentIds) {
			Attachment attachment = taskService.getAttachment(id);
			if (attachment != null) {
				AttachmentEntity ae = new AttachmentEntity();
				ae.setId(attachment.getId());
				ae.setName(attachment.getName());
				replyEntity.getAttachments().add(ae);
			}

		}

		gid.getReplies().add(replyEntity);

		gid.setMode(mode);
		gid.setObtainMode(obtainMode);
		gidDao.save(gid);
		// LuceneIKUtil ik = new LuceneIKUtil("/IK");
		// execution.
		List<FieldDTO> fields = Lists.newArrayList();
		FieldDTO field = new FieldDTO("id", gid.getId().toString(),
				Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("processInstanceId",
				execution.getProcessInstanceId(), Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("applicationName", gid.getApplicationName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("description", gid.getDescription(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("citizenName", gid.getCitizenName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("groupName", gid.getGroupName(), Field.Store.YES,
				false);
		fields.add(field);
		field = new FieldDTO("review", "", Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("reply", reply, Field.Store.YES, false);
		fields.add(field);

		ikUtil.addIndex(fields);

		// ikUtil.addIndex(execution.getProcessInstanceId(), gid.getId(),
		// gid.getApplicationName(), gid.getDescription());
		System.out.println("submitTaskForm stop at:" + new Date());
		return gid;

	}

	public GovernmentInformationDisclosure addReplyAsReview(
			DelegateExecution execution, GovernmentInformationDisclosure gid,
			String reply, String formOfResponse) {
		Reply replyEntity = new Reply();
		replyEntity.setReply(reply);

		replyEntity.setReplyTime(new Date());

		replyEntity.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		replyEntity
				.setUsername(accountService.findUserByLoginName(
						SecurityUtils.getSubject().getPrincipal().toString())
						.getName());
		gid.getReplies().add(replyEntity);
		gid.setFormOfResponse(formOfResponse);
		String replyContent = "";
		for (Reply aReply : gid.getReplies()) {

			replyContent += aReply.getReply() + " ";

		}

		List<FieldDTO> fields = Lists.newArrayList();
		FieldDTO field = new FieldDTO("id", gid.getId().toString(),
				Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("processInstanceId",
				execution.getProcessInstanceId(), Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("applicationName", gid.getApplicationName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("description", gid.getDescription(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("citizenName", gid.getCitizenName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("groupName", gid.getGroupName(), Field.Store.YES,
				false);
		fields.add(field);
		if (gid.getReview() != null) {
			field = new FieldDTO("review", gid.getReview(), Field.Store.YES,
					false);
			fields.add(field);
		}
		field = new FieldDTO("reply", replyContent, Field.Store.YES, false);
		fields.add(field);
		ikUtil.updateIndex(fields);
		return gidDao.save(gid);

	}

	public GovernmentInformationDisclosure addReply(
			DelegateExecution execution, GovernmentInformationDisclosure gid,
			String reply, String attachmentList) {
		Reply replyEntity = new Reply();
		replyEntity.setReply(reply);

		replyEntity.setReplyTime(new Date());

		replyEntity.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		replyEntity
				.setUsername(accountService.findUserByLoginName(
						SecurityUtils.getSubject().getPrincipal().toString())
						.getName());

		String[] attachmentIds = attachmentList.split(",");
		for (String id : attachmentIds) {
			Attachment attachment = taskService.getAttachment(id);
			if (attachment != null) {
				AttachmentEntity ae = new AttachmentEntity();
				ae.setId(attachment.getId());
				ae.setName(attachment.getName());
				replyEntity.getAttachments().add(ae);
			}

		}

		gid.getReplies().add(replyEntity);

		String replyContent = "";
		for (Reply aReply : gid.getReplies()) {

			replyContent += aReply.getReply() + " ";
		}

		List<FieldDTO> fields = Lists.newArrayList();
		FieldDTO field = new FieldDTO("id", gid.getId().toString(),
				Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("processInstanceId",
				execution.getProcessInstanceId(), Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("applicationName", gid.getApplicationName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("description", gid.getDescription(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("citizenName", gid.getCitizenName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("groupName", gid.getGroupName(), Field.Store.YES,
				false);
		fields.add(field);
		if (gid.getReview() != null) {
			field = new FieldDTO("review", gid.getReview(), Field.Store.YES,
					false);
			fields.add(field);
		}
		field = new FieldDTO("reply", replyContent, Field.Store.YES, false);
		fields.add(field);
		ikUtil.updateIndex(fields);
		return gidDao.save(gid);

	}

	public GovernmentInformationDisclosure saveReview(
			DelegateExecution execution, GovernmentInformationDisclosure gid,
			String review) {
		String replyContent = "";
		for (Reply aReply : gid.getReplies()) {

			replyContent += aReply.getReply() + " ";

		}
		gid.setReview(review);

		List<FieldDTO> fields = Lists.newArrayList();
		FieldDTO field = new FieldDTO("id", gid.getId().toString(),
				Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("processInstanceId",
				execution.getProcessInstanceId(), Field.Store.YES, true);
		fields.add(field);
		field = new FieldDTO("applicationName", gid.getApplicationName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("description", gid.getDescription(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("citizenName", gid.getCitizenName(),
				Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("groupName", gid.getGroupName(), Field.Store.YES,
				false);
		fields.add(field);
		field = new FieldDTO("review", review, Field.Store.YES, false);
		fields.add(field);
		field = new FieldDTO("reply", replyContent, Field.Store.YES, false);
		fields.add(field);
		ikUtil.updateIndex(fields);

		return gidDao.save(gid);

	}

	public HashMap<String, Object> search(String[] fields, String keyword,
			int start, int size) {

		// LuceneIKUtil ik = new LuceneIKUtil("/IK");
		HashMap<String, Object> rets = ikUtil.search(fields, keyword, start,
				size);
		List<TaskDTO> taskList = Lists.newArrayList();
		List<SearchResultDTO> result = (List<SearchResultDTO>) rets
				.get("result");
		Iterator<SearchResultDTO> iter = result.iterator();
		int count = 0;
		while (iter.hasNext()) {
			SearchResultDTO srDTO = iter.next();
			List<HistoricTaskInstance> historictaskList = historyService
					.createHistoricTaskInstanceQuery()
					.processInstanceId(srDTO.getProcessInstanceId()).list();

			for (HistoricTaskInstance task : historictaskList) {
				TaskDTO taskDTO = new TaskDTO();
				taskDTO.setId(task.getId());
				taskDTO.setName(task.getName());
				taskDTO.setAssignee(task.getAssignee());
				taskDTO.setEndTime(task.getEndTime());
				taskDTO.setStartTime(task.getStartTime());
				taskDTO.setTaskDefinitionKey(task.getTaskDefinitionKey());
				taskDTO.setProcessDefinitionId(task.getProcessDefinitionId());
				if (!taskList.contains(taskDTO)) {
					taskList.add(taskDTO);
					count++;
				}
			}

		}
		rets.clear();
		rets.put("start", start);
		rets.put("size", size);
		rets.put("count", count);
		rets.put("records", taskList);
		return rets;

	}

	public HashMap<String, Object> searchProcessInstance(String[] fields,
			String keyword, int start, int size) {

		// LuceneIKUtil ik = new LuceneIKUtil("/IK");
		HashMap<String, Object> rets = ikUtil.search(fields, keyword, start,
				size);
		List<TaskDTO> taskList = Lists.newArrayList();
		List<SearchResultDTO> result = (List<SearchResultDTO>) rets
				.get("result");
		Iterator<SearchResultDTO> iter = result.iterator();
		List<Object> objects = new ArrayList<Object>();
		int count = 0;
		while (iter.hasNext()) {
			SearchResultDTO srDTO = iter.next();
			List<HistoricProcessInstance> historicProcessInstanceList = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(srDTO.getProcessInstanceId()).list();

			for (HistoricProcessInstance instance : historicProcessInstanceList) {
				HistoricProcessInstanceDTO piDTO = new HistoricProcessInstanceDTO();
				piDTO.setBusinessKey(instance.getBusinessKey());
				piDTO.setId(instance.getId());
				piDTO.setProcessDefinitionId(instance.getProcessDefinitionId());
				piDTO.setStartTime(instance.getStartTime());
				piDTO.setEndTime(instance.getEndTime());
				piDTO.setStartUserId(instance.getStartUserId());
				piDTO.setUsername(accountService.findUserByLoginName(
						piDTO.getStartUserId()).getName());
				if (piDTO.getEndTime() == null)
					piDTO.setStatus("activited");
				else
					piDTO.setStatus("finished");

				if (!objects.contains(piDTO)) {
					objects.add(piDTO);
					count++;
				}
			}

		}
		rets.clear();
		rets.put("start", start);
		rets.put("size", size);
		rets.put("count", count);
		rets.put("records", objects);
		return rets;

	}
}
