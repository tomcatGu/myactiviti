package org.crusoe.entity;

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

/**
 * 用户.
 * 
 * @author gwx
 */
@Entity
@Table(name = "ss_organization")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization implements Serializable, AbstractSecureObject<Long> {
	private String name;
	private Long id;
	private Organization parent;

	private List<User> users = Lists.newArrayList(); // 有序的关联对象集合

	// @NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 多对多定义
	@ManyToMany
	@JoinTable(name = "ss_organization_user", joinColumns = { @JoinColumn(name = "organization_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> roles) {
		this.users = roles;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;

	}

	@OneToOne
	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

}