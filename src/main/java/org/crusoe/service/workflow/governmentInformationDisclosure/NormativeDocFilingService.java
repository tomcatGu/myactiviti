package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.util.Date;

import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NormativeDocFilingService {
	public NormativeDocFiling save(String fileName, String departmentName,
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

		return ndf;
	}

}
