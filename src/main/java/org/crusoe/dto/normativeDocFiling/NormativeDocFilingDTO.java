package org.crusoe.dto.normativeDocFiling;

import java.util.Date;

public class NormativeDocFilingDTO {
	private Long id;
	private String fileName;
	private Long organizationId;
	private String messageNumber;
	private String fileProperty;
	private String contentClassification;
	private Date releaseDate;
	private Date implementationDate;
	private String isOpen;
	private String status;
	private Date createOn;
	private String username;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
