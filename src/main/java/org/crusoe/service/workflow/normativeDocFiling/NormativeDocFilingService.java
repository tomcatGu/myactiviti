package org.crusoe.service.workflow.normativeDocFiling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.apache.shiro.SecurityUtils;
import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFilingReply;
import org.crusoe.repository.jpa.OrganizationDao;
import org.crusoe.repository.jpa.workflow.normativeDocFiling.NormativeDocFilingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NormativeDocFilingService {
	@Autowired
	private NormativeDocFilingDao ndfDao;
	@Autowired
	private OrganizationDao oDao;

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
		if (isPassed)
			ndf.setStatus("已备案");
		else
			ndf.setStatus("待完善");
		ndfDao.save(ndf);
		return ndf;
	}

}
