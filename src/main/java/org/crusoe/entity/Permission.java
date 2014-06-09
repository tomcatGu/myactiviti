package org.crusoe.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;

@Entity
@Table(name = "ss_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Permission implements Serializable, AbstractSecureObject<Long> {
	private Long id;
	private String token;
	private String url;
	private String description;
	private List<Role> roles = Lists.newArrayList();

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "permissions")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
