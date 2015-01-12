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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Attachment;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.shiro.SecurityUtils;
import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.governmentInformationDisclosure.AttachmentEntity;
import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFilingAttachmentEntity;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFilingReply;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFilingStatus;
import org.crusoe.repository.jpa.OrganizationDao;
import org.crusoe.repository.jpa.workflow.normativeDocFiling.NormativeDocFilingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
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
		ndf.setCreateOn(new Date());
		ndf.setOrganizationId(organizationId);
		ndf.setStatus(NormativeDocFilingStatus.PENDING);
		// ndf.setStatus(NormativeDocFilingStatus.PENDING);
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
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
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
		ndf.setStatus(NormativeDocFilingStatus.PENDING);
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
			ndf.setStatus(NormativeDocFilingStatus.ACCEPT);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = null;

			HashMap hm = new HashMap();
			hm.put("title", ndf.getFileName());
			hm.put("messageNumber", ndf.getMessageNumber());
			hm.put("orderNumber", ndf.getOrderNumber().toString());
			hm.put("organizationName", oDao.findOne(ndf.getOrganizationId())
					.getName());
			Calendar c = Calendar.getInstance();
			hm.put("year", String.valueOf((c.get(Calendar.YEAR))));
			hm.put("month", String.valueOf((c.get(Calendar.MONTH) + 1)));
			hm.put("day", String.valueOf((c.get(Calendar.DAY_OF_MONTH))));
			if (NormativeDocFilingStatus.ACCEPT.name().equals(ndf.getStatus())) {
				hm.put("status", "准予备案");
			} else {
				hm.put("status", "不予备案");
			}
			ndf.getStatus().name();
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
					ndf.getFileName() + "备案确认函.doc", "", in);
			NormativeDocFilingAttachmentEntity ae = new NormativeDocFilingAttachmentEntity();
			ae.setTaskId(attachment.getId());
			ae.setName(attachment.getName());
			ndf.getAttachments().add(ae);
		} else
			ndf.setStatus(NormativeDocFilingStatus.REVISE);
		ndfDao.save(ndf);
		// execution
		return ndf;
	}

	public NormativeDocFiling saveReply(DelegateExecution execution,
			NormativeDocFiling ndf, String reply, String status,
			Long orderNumber) {
		NormativeDocFilingReply ndfReply = new NormativeDocFilingReply();
		ndfReply.setReply(reply);
		ndfReply.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		ndfReply.setReplyTime(new Date());

		ndf.getReplies().add(ndfReply);
		ndf.setOrderNumber(orderNumber);
		if ("accept".equals(status)) {
			ndf.setStatus(NormativeDocFilingStatus.ACCEPT);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = null;

			HashMap hm = new HashMap();
			hm.put("title", ndf.getFileName());
			hm.put("messageNumber", ndf.getMessageNumber());
			hm.put("orderNumber", ndf.getOrderNumber().toString());
			hm.put("organizationName", oDao.findOne(ndf.getOrganizationId())
					.getName());
			Calendar c = Calendar.getInstance();
			hm.put("year", String.valueOf((c.get(Calendar.YEAR))));
			hm.put("month", String.valueOf((c.get(Calendar.MONTH))));
			hm.put("day", String.valueOf((c.get(Calendar.DAY_OF_MONTH))));
			if (NormativeDocFilingStatus.ACCEPT.name().equals(ndf.getStatus())) {
				hm.put("status", "准予备案");
			} else {
				hm.put("status", "不予备案");
			}
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
					ndf.getFileName() + "备案确认函.doc", "", in);
			NormativeDocFilingAttachmentEntity ae = new NormativeDocFilingAttachmentEntity();
			ae.setTaskId(attachment.getId());
			ae.setName(attachment.getName());
			ndf.getAttachments().add(ae);
		} else if ("revise".equals(status)) {
			ndf.setStatus(NormativeDocFilingStatus.REVISE);

		} else if ("refuse".equals(status)) {
			ndf.setStatus(NormativeDocFilingStatus.REFUSE);

		}
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

	public Page<NormativeDocFiling> findByTitleAndCreateTimeAndOrganizationAndStatus(
			final String title, final Date startTime, final Date endTime,
			final Long organizationId, final String status,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return (Page<NormativeDocFiling>) ndfDao.findAll(
				new Specification<NormativeDocFiling>() {

					@Override
					public Predicate toPredicate(Root<NormativeDocFiling> root,
							CriteriaQuery<?> query, CriteriaBuilder builder) {
						// TODO Auto-generated method stub
						SimpleDateFormat dateformat1 = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Predicate predicate = builder.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						if (startTime.compareTo(endTime) != 0)
							expressions.add(builder.between(
									root.<Date> get("createOn"), startTime,
									endTime));
						if (!title.isEmpty())
							expressions.add(builder.like(
									root.<String> get("fileName"), "%" + title
											+ "%"));
						if (organizationId != -1L) {
							expressions.add(builder.equal(
									root.<String> get("organizationId"),
									organizationId));

						}
						if (!status.isEmpty())
							expressions.add(builder.equal(
									root.<String> get("status"), status));

						return predicate;
					}
				}, pageRequest);
	}

	public NormativeDocFiling findById(Long id) {
		// TODO Auto-generated method stub
		return ndfDao.findOne(id);
	}
}
