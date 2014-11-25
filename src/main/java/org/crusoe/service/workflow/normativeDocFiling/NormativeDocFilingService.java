package org.crusoe.service.workflow.normativeDocFiling;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Attachment;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.shiro.SecurityUtils;
import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.governmentInformationDisclosure.AttachmentEntity;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFilingAttachmentEntity;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFilingReply;
import org.crusoe.repository.jpa.OrganizationDao;
import org.crusoe.repository.jpa.workflow.normativeDocFiling.NormativeDocFilingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class NormativeDocFilingService {
	@Autowired
	private NormativeDocFilingDao ndfDao;
	@Autowired
	private OrganizationDao oDao;
	@Autowired
	private TaskService taskService;

	public NormativeDocFiling save(DelegateExecution execution,
			String fileName, Long organizationId, String messageNumber,
			String fileProperty, String contentClassification,
			String releaseDate, String implementationDate, String isOpen,
			String attachmentList) {
		NormativeDocFiling ndf = new NormativeDocFiling();
		ndf.setFileName(fileName);
		ndf.setMessageNumber(messageNumber);
		ndf.setFileProperty(fileProperty);
		ndf.setContentClassification(contentClassification);
		ndf.setUsername(SecurityUtils.getSubject().getPrincipal().toString());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			ndf.setReleaseDate(formatter.parse(releaseDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ndf.setImplementationDate(formatter.parse(implementationDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ndf.setIsOpen(isOpen);

		String[] attachmentIds = attachmentList.split(",");
		for (String id : attachmentIds) {
			Attachment attachment = taskService.getAttachment(id);
			if (attachment != null) {
				NormativeDocFilingAttachmentEntity ae = new NormativeDocFilingAttachmentEntity();
				ae.setTaskId(attachment.getId());
				ae.setName(attachment.getName());
				ndf.getAttachments().add(ae);
			}

		}

		ndf.setOrganizationId(organizationId);
		ndf.setStatus("待审核");
		ndfDao.save(ndf);

		return ndf;
	}

	public NormativeDocFiling update(DelegateExecution execution,
			NormativeDocFiling orign, String fileName, Long organizationId,
			String messageNumber, String fileProperty,
			String contentClassification, String releaseDate,
			String implementationDate, String isOpen, String attachmentList) {
		NormativeDocFiling ndf = orign;
		ndf.setFileName(fileName);
		ndf.setMessageNumber(messageNumber);
		ndf.setFileProperty(fileProperty);
		ndf.setContentClassification(contentClassification);
		ndf.setUsername(SecurityUtils.getSubject().getPrincipal().toString());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			ndf.setReleaseDate(formatter.parse(releaseDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ndf.setImplementationDate(formatter.parse(implementationDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ndf.setIsOpen(isOpen);

		String[] attachmentIds = attachmentList.split(",");
		for (String id : attachmentIds) {
			Attachment attachment = taskService.getAttachment(id);
			if (attachment != null) {
				NormativeDocFilingAttachmentEntity ae = new NormativeDocFilingAttachmentEntity();
				ae.setTaskId(attachment.getId());
				ae.setName(attachment.getName());
				ndf.getAttachments().add(ae);
			}

		}

		ndf.setOrganizationId(organizationId);
		ndf.setStatus("待审核");
		ndfDao.save(ndf);

		return ndf;
	}

	public NormativeDocFiling saveReply(DelegateExecution execution,
			NormativeDocFiling ndf, String reply, boolean isPassed) {
		NormativeDocFilingReply ndfReply = new NormativeDocFilingReply();
		ndfReply.setReply(reply);
		ndfReply.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		ndfReply.setReplyTime(new Date());
		ndf.getReplies().add(ndfReply);
		if (isPassed) {
			ndf.setStatus("已备案");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = null;

			HashMap hm = new HashMap();
			hm.put("title", "OK");
			try {
				HWPFDocument doc = replaceDoc(hm, "templete/qrh.doc");
				doc.write(out);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			in = new ByteArrayInputStream(out.toByteArray());
			Attachment attachment = taskService.createAttachment("doc",
					taskService.createTaskQuery()
							.executionId(execution.getId()).singleResult()
							.getId(), execution.getProcessInstanceId(),
					"确认函.doc", "", in);
			NormativeDocFilingAttachmentEntity ae = new NormativeDocFilingAttachmentEntity();
			ae.setTaskId(attachment.getId());
			ae.setName(attachment.getName());
			ndf.getAttachments().add(ae);
		} else
			ndf.setStatus("待完善");
		ndfDao.save(ndf);
		// execution
		return ndf;
	}

	private HWPFDocument replaceDoc(Map<String, String> map, String srcPath) {

		String classPath = this.getClass().getClassLoader().getResource("")
				.getPath();
		String path = classPath.substring(0, classPath.indexOf("WEB-INF"))
				+ srcPath;
		try {
			// 读取word模板
			FileInputStream fis = new FileInputStream(new File(path));
			HWPFDocument doc = new HWPFDocument(fis);
			// 读取word文本内容
			Range bodyRange = doc.getRange();
			// 替换文本内容
			for (Map.Entry<String, String> entry : map.entrySet()) {
				bodyRange.replaceText("${" + entry.getKey() + "}",
						entry.getValue());
			}

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<NormativeDocFiling> findByCreateOn(Date startTime, Date endTime) {
		// TODO Auto-generated method stub

		return ndfDao.findByCreateOnBetween(startTime, endTime);
	}
}
