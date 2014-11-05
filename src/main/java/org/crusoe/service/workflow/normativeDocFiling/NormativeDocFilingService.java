package org.crusoe.service.workflow.normativeDocFiling;

import java.util.Date;

import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.crusoe.repository.jpa.OrganizationDao;
import org.crusoe.repository.jpa.workflow.normativeDocFiling.NormativeDocFilingDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NormativeDocFilingService {
	
	private NormativeDocFilingDao ndfDao;
	private OrganizationDao oDao;
	public NormativeDocFiling save(String fileName, Long organizationId,
			String messageNumber, String fileProperty,
			String contentClassification, Date releaseDate,
			Date implementationDate, String isOpen, String attachmentList) {
		NormativeDocFiling ndf = new NormativeDocFiling();
		ndf.setFileName(fileName);
		ndf.setMessageNumber(messageNumber);
		ndf.setFileProperty(fileProperty);
		ndf.setContentClassification(contentClassification);
		ndf.setReleaseDate(releaseDate);
		ndf.setImplementationDate(implementationDate);
		ndf.setIsOpen(isOpen);
		Organization o=oDao.findOne(organizationId);
		ndfDao.save(ndf);

		return ndf;
	}

}
