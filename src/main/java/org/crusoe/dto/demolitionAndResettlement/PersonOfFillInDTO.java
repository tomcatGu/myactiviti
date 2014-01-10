package org.crusoe.dto.demolitionAndResettlement;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public class PersonOfFillInDTO {
	private Long id;
	private String name;
	private List<DemolitionDTO> demolitions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DemolitionDTO> getDemolitions() {
		return demolitions;
	}

	public void setDemolitions(List<DemolitionDTO> demolitions) {
		this.demolitions = demolitions;
	}
}
