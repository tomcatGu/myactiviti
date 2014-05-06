package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.crusoe.entity.User;
import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.crusoe.entity.workflow.governmentInformationDisclosure.Reply;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.GovernmentInformationDisclosureDao;
import org.crusoe.service.AccountService;
import org.joda.time.DateTime;
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
	@Autowired
	private AccountService accountService;

	public GovernmentInformationDisclosure save(String applicationName,
			String citizenName, String citizenWorkunit,
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

		return gidDao.save(gid);

	}

	public GovernmentInformationDisclosure addReply(
			GovernmentInformationDisclosure gid, String reply) {
		Reply replyEntity = new Reply();
		replyEntity.setReply(reply);

		replyEntity.setReplyTime(new Date());

		replyEntity.setUserLoginName(SecurityUtils.getSubject().getPrincipal()
				.toString());
		gid.getReplies().add(replyEntity);
		return gidDao.save(gid);

	}
}
