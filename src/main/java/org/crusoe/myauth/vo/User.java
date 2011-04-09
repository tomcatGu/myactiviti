package org.crusoe.myauth.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.CollectionOfElements;
import org.springframework.core.style.ToStringCreator;

public class User implements Serializable {
	public User() {
	}

	private Long id;
	private String username;
	private String realname;
	private String password;
	private String mobile = "";
	private String email = "";
	private Set<Role> roles = new HashSet<Role>();

	private String _roles;

	public String getRealname() {
		return realname;
	}

	//
	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@CollectionOfElements
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String toString() {
		return new ToStringCreator(this).append("id", id)
				.append("username", username).append("realname", realname)
				.append("password", password).toString();
	}

}
