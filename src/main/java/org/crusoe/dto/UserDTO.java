package org.crusoe.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

import com.google.common.collect.Lists;

public class UserDTO implements Serializable {
	public UserDTO() {
	}

	private Long id;
	private String loginName;
	private String name;
	private String password;
	private String mobile = "";
	private String email = "";
	private String status = "";
	private Long organizationId;
	private List<RoleDTO> roles = new ArrayList<RoleDTO>();

	private String _roles;

	@NotEmpty
	public String getName() {
		return name;
	}

	//
	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	// @NotEmpty
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Email
	// @NotEmpty
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return new ToStringCreator(this).append("id", id)
				.append("loginName", loginName).append("name", name)
				.append("password", password).toString();
	}

	public String[] toArray() {
		// TODO Auto-generated method stub
		String[] arr = new String[6];
		arr[0] = this.loginName;
		arr[1] = this.name;
		arr[2] = this.mobile;
		arr[3] = this.email;
		arr[4] = this.getRoleNames();
		arr[5] = this.status;
		return arr;
	}

	private String getRoleNames() {
		// TODO Auto-generated method stub
		return roles.toString();
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

}
