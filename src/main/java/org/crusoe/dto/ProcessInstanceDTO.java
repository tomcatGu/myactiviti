package org.crusoe.dto;

import org.crusoe.dto.task.TaskDTO;

public class ProcessInstanceDTO {
	private String id;
	private String processInstanceId;
	private String processDefinitionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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
		if (obj instanceof ProcessInstanceDTO) {
			ProcessInstanceDTO temp = (ProcessInstanceDTO) obj;
			if (temp.getId().equals(this.id))
				return true;
			else
				return false;

		} else
			return false;
	}

}
