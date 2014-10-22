package org.crusoe.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

import com.google.common.collect.Lists;

public class OrganizationDTO implements Serializable {
	private String text;
	private Long id;
	private String parent;
	private boolean children;

	private List<UserDTO> users = Lists.newArrayList(); // 有序的关联对象集合
	private List<OrganizationDTO> organizations = Lists.newArrayList();

	// @NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;

	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<OrganizationDTO> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<OrganizationDTO> organizations) {
		this.organizations = organizations;
	}

	public boolean isChildren() {
		return children;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}

}