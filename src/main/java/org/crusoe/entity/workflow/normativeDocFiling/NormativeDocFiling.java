package org.crusoe.entity.workflow.normativeDocFiling;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.governmentInformationDisclosure.AttachmentEntity;

import com.google.common.collect.Lists;

@Entity
@Table(name = "workflow_normativeDocFiling")
public class NormativeDocFiling {

	private Long id;
	private String fileName;
	private Long organizationId;
	private String messageNumber;
	private String fileProperty;
	private String contentClassification;
	private Date releaseDate;
	private Date implementationDate;
	private String isOpen;
	private NormativeDocFilingStatus status;
	private Date createOn;
	private String username;

	private List<NormativeDocFilingReply> replies = Lists.newArrayList();

	private List<NormativeDocFilingAttachmentEntity> attachments = Lists
			.newArrayList();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}

	public String getFileProperty() {
		return fileProperty;
	}

	public void setFileProperty(String fileProperty) {
		this.fileProperty = fileProperty;
	}

	public String getContentClassification() {
		return contentClassification;
	}

	public void setContentClassification(String contentClassification) {
		this.contentClassification = contentClassification;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getImplementationDate() {
		return implementationDate;
	}

	public void setImplementationDate(Date implementationDate) {
		this.implementationDate = implementationDate;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public NormativeDocFilingStatus getStatus() {
		return status;
	}

	public void setStatus(NormativeDocFilingStatus status) {
		this.status = status;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	@ElementCollection
	@CollectionTable(name = "workflow_normativeDocFilingAttachment")
	public List<NormativeDocFilingAttachmentEntity> getAttachments() {
		return attachments;
	}

	public void setAttachments(
			List<NormativeDocFilingAttachmentEntity> attachments) {
		this.attachments = attachments;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "normativeDocFiling_id", referencedColumnName = "id")
	public List<NormativeDocFilingReply> getReplies() {
		return replies;
	}

	public void setReplies(List<NormativeDocFilingReply> replies) {
		this.replies = replies;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
