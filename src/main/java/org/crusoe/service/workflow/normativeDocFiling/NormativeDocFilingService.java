package org.crusoe.service.workflow.normativeDocFiling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
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
		ndfDao.save(ndf);

		return ndf;
	}

}
