package org.crusoe.entity.demolitionAndResettlement;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dr_personOfFillIn")
public class PersonOfFillIn {
	private Long id;
	private String name;
	private List<Demolition> demolitions;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "thePersonOfFillIn")
	public List<Demolition> getDemolitions() {
		return demolitions;
	}

	public void setDemolitions(List<Demolition> demolitions) {
		this.demolitions = demolitions;
	}
}
