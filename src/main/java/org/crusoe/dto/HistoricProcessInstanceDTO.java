package org.crusoe.dto;

import java.util.Date;

public class HistoricProcessInstanceDTO {
	private String id;
	private String processDefinitionId;
	private String startUserId;
	private Date startTime;
	private Date endTime;
	private String businessKey;
	private String status;
	private String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof HistoricProcessInstanceDTO) {
			HistoricProcessInstanceDTO temp = (HistoricProcessInstanceDTO) obj;
			if (temp.getId().equals(this.id))
				return true;
			else
				return false;

		} else
			return false;
	}
}
