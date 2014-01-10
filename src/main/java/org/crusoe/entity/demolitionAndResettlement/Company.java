package org.crusoe.entity.demolitionAndResettlement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.crusoe.entity.AbstractSecureObject;

@Entity
@Table(name = "dr_company")
public class Company implements Serializable, AbstractSecureObject<Long> {

	private Long id;
	private String name;// 名称
	private String tender;// 标段

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

	public String getTender() {
		return tender;
	}

	public void setTender(String tender) {
		this.tender = tender;
	}

}
