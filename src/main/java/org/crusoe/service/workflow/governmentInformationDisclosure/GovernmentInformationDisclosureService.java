package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.util.Date;

import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.GovernmentInformationDisclosureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public GovernmentInformationDisclosure save(String applicationName,
			String citizenName, String citizenWorkunit,
			String citizenCertificate, String citizenCertificateID,
			String citizenTelphone, String citizenZipcode,
			String citizenAddress, String citizenFax, String citizenEmail,
			String groupName, String groupID, String groupDelegate,
			String groupDelegateName, String groupDelegateTelphone,
			String groupDelegateFax, String groupDelegateAddress,
			String groupDelegateEmail, Date applicationTime,
			String submitDepartment, String description, String purpose,
			String mode, String obtainMode) {
		GovernmentInformationDisclosure gid = new GovernmentInformationDisclosure();
		return gidDao.save(gid);

	}
}
