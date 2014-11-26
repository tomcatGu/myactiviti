package org.crusoe.entity.workflow.governmentInformationDisclosure;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.crusoe.entity.AbstractSecureObject;

import com.google.common.collect.Lists;

@Entity
@Table(name = "workflow_governmentInformationDisclosure")
public class GovernmentInformationDisclosure implements Serializable,
		AbstractSecureObject<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3375672783594298303L;
	private Long id;
	private String applicationName;
	private String citizenName;
	private String citizenWorkunit;
	private String citizenCertificate;
	private String citizenCertificateID;
	private String citizenTelphone;
	private String citizenZipcode;
	private String citizenAddress;
	private String citizenFax;
	private String citizenEmail;
	private String groupName;
	private String groupID;
	private String groupDelegate;
	private String groupDelegateName;
	private String groupDelegateTelphone;
	private String groupDelegateFax;
	private String groupDelegateAddress;
	private String groupDelegateEmail;
	private Date applicationTime;
	private String submitDepartment;

	private String description;
	private String purpose;
	private String mode;
	private String obtainMode;
	private List<Reply> replies = Lists.newArrayList();
	private String review;

	private String formOfDisclosure;
	private String formOfResponse;

	private Date createTime;
	private String createUser;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getCitizenName() {
		return citizenName;
	}

	public void setCitizenName(String citizenName) {
		this.citizenName = citizenName;
	}

	public String getCitizenWorkunit() {
		return citizenWorkunit;
	}

	public void setCitizenWorkunit(String citizenWorkunit) {
		this.citizenWorkunit = citizenWorkunit;
	}

	public String getCitizenCertificate() {
		return citizenCertificate;
	}

	public void setCitizenCertificate(String citizenCertificate) {
		this.citizenCertificate = citizenCertificate;
	}

	public String getCitizenCertificateID() {
		return citizenCertificateID;
	}

	public void setCitizenCertificateID(String citizenCertificateID) {
		this.citizenCertificateID = citizenCertificateID;
	}

	public String getCitizenTelphone() {
		return citizenTelphone;
	}

	public void setCitizenTelphone(String citizenTelphone) {
		this.citizenTelphone = citizenTelphone;
	}

	public String getCitizenZipcode() {
		return citizenZipcode;
	}

	public void setCitizenZipcode(String citizenZipcode) {
		this.citizenZipcode = citizenZipcode;
	}

	public String getCitizenAddress() {
		return citizenAddress;
	}

	public void setCitizenAddress(String citizenAddress) {
		this.citizenAddress = citizenAddress;
	}

	public String getCitizenFax() {
		return citizenFax;
	}

	public void setCitizenFax(String citizenFax) {
		this.citizenFax = citizenFax;
	}

	public String getCitizenEmail() {
		return citizenEmail;
	}

	public void setCitizenEmail(String citizenEmail) {
		this.citizenEmail = citizenEmail;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupDelegate() {
		return groupDelegate;
	}

	public void setGroupDelegate(String groupDelegate) {
		this.groupDelegate = groupDelegate;
	}

	public String getGroupDelegateName() {
		return groupDelegateName;
	}

	public void setGroupDelegateName(String groupDelegateName) {
		this.groupDelegateName = groupDelegateName;
	}

	public String getGroupDelegateTelphone() {
		return groupDelegateTelphone;
	}

	public void setGroupDelegateTelphone(String groupDelegateTelphone) {
		this.groupDelegateTelphone = groupDelegateTelphone;
	}

	public String getGroupDelegateFax() {
		return groupDelegateFax;
	}

	public void setGroupDelegateFax(String groupDelegateFax) {
		this.groupDelegateFax = groupDelegateFax;
	}

	public String getGroupDelegateAddress() {
		return groupDelegateAddress;
	}

	public void setGroupDelegateAddress(String groupDelegateAddress) {
		this.groupDelegateAddress = groupDelegateAddress;
	}

	public String getGroupDelegateEmail() {
		return groupDelegateEmail;
	}

	public void setGroupDelegateEmail(String groupDelegateEmail) {
		this.groupDelegateEmail = groupDelegateEmail;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getSubmitDepartment() {
		return submitDepartment;
	}

	public void setSubmitDepartment(String submitDepartment) {
		this.submitDepartment = submitDepartment;
	}

	@Column(length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getObtainMode() {
		return obtainMode;
	}

	public void setObtainMode(String obtainMode) {
		this.obtainMode = obtainMode;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "governmentInformationDisclosure_id", referencedColumnName = "id")
	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getFormOfDisclosure() {
		return formOfDisclosure;
	}

	public void setFormOfDisclosure(String formOfDisclosure) {
		this.formOfDisclosure = formOfDisclosure;
	}

	public String getFormOfResponse() {
		return formOfResponse;
	}

	public void setFormOfResponse(String formOfResponse) {
		this.formOfResponse = formOfResponse;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
