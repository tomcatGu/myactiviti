package org.crusoe.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ss_menu")
public class Menu implements Serializable, AbstractSecureObject<Long> {
	private Long id;
	private Menu parent;
	private String title;
	private Permission permission;
	private String owner;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}


	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@ManyToOne
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

}
