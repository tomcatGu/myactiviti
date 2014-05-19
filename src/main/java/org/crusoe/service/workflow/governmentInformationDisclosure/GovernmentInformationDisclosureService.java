package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.crusoe.dto.fulltextSearch.SearchResultDTO;
import org.crusoe.dto.task.TaskDTO;
import org.crusoe.entity.User;
import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.crusoe.entity.workflow.governmentInformationDisclosure.Reply;
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
	private AccountService accountService;
	@Autowired
	private HistoryService historyService;

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
			String mode, String obtainMode) {

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
		gid.setMode(mode);
		gid.setObtainMode(obtainMode);
		gidDao.save(gid);
		LuceneIKUtil<GovernmentInformationDisclosure> ik = new LuceneIKUtil<GovernmentInformationDisclosure>(
				"/IK");
		// execution.
		ik.updateIndex(execution.getProcessInstanceId(), gid.getId(),
				gid.getApplicationName(), "");

		return gid;

	}

	public GovernmentInformationDisclosure addReply(
			DelegateExecution execution, GovernmentInformationDisclosure gid,
			String reply) {
		Reply replyEntity = new Reply();
		replyEntity.setReply(reply);

		replyEntity.setReplyTime(new Date());

		replyEntity.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		gid.getReplies().add(replyEntity);
		String content = "";
		for (Reply aReply : gid.getReplies()) {

			content += aReply.getReply() + " ";

		}

		LuceneIKUtil<GovernmentInformationDisclosure> ik = new LuceneIKUtil<GovernmentInformationDisclosure>(
				"/IK");
		ik.updateIndex(execution.getProcessInstanceId(), gid.getId(),
				gid.getApplicationName(), content);
		return gidDao.save(gid);

	}

	public GovernmentInformationDisclosure saveReview(
			DelegateExecution execution, GovernmentInformationDisclosure gid,
			String review) {
		gid.setReview(review);

		LuceneIKUtil<GovernmentInformationDisclosure> ik = new LuceneIKUtil<GovernmentInformationDisclosure>(
				"/IK");
		ik.updateIndex(execution.getProcessInstanceId(), gid.getId(),
				gid.getApplicationName(), review);
		return gidDao.save(gid);

	}

	public HashMap<String, Object> search(String[] fields, String keyword,
			int start, int size) {

		LuceneIKUtil<GovernmentInformationDisclosure> ik = new LuceneIKUtil<GovernmentInformationDisclosure>(
				"/IK");
		HashMap<String, Object> rets = ik.search(fields, keyword, start, size);
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

				taskList.add(taskDTO);
				count++;
			}

		}
		rets.clear();
		rets.put("start", start);
		rets.put("size", size);
		rets.put("count", count);
		rets.put("records", taskList);
		return rets;

	}
}
