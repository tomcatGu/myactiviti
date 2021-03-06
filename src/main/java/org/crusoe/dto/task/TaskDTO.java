package org.crusoe.dto.task;

import java.util.Date;

import org.crusoe.dto.UserDTO;

public class TaskDTO {
	private String id;
	private String name;
	private String assignee;
	private Date endTime;
	private Date startTime;
	private Date createTime;
	private Date dueDate;
	private String taskDefinitionKey;
	private String processDefinitionId;
	private String initiatorUserId;
	private UserDTO initiatorUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof TaskDTO) {
			TaskDTO temp = (TaskDTO) obj;
			if (temp.getId().equals(this.id))
				return true;
			else
				return false;

		} else
			return false;

	}

	public String getInitiatorUserId() {
		return initiatorUserId;
	}

	public void setInitiatorUserId(String initiatorUserId) {
		this.initiatorUserId = initiatorUserId;
	}

	public UserDTO getInitiatorUser() {
		return initiatorUser;
	}

	public void setInitiatorUser(UserDTO initiatorUser) {
		this.initiatorUser = initiatorUser;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
