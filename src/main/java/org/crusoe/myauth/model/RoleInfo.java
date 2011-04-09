package org.crusoe.myauth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleInfo implements Serializable, AbstractSecureObject<Long> {
	// String uuid;
	String name;
	String value;
	private Set<ResourceInfo> resources = new HashSet<ResourceInfo>();
	private Long id;

	/*
	 * @Id
	 * 
	 * @Column(length = 32)
	 * 
	 * @GeneratedValue(generator = "system-uuid")
	 * 
	 * @GenericGenerator(name = "system-uuid", strategy = "uuid.hex") public
	 * String getUuid() { return uuid; }
	 * 
	 * public void setUuid(String uuid) { this.uuid = uuid; }
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToMany(targetEntity = ResourceInfo.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "role_resource", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<ResourceInfo> getResources() {
		return resources;
	}

	public void setResources(Set<ResourceInfo> resources) {
		this.resources = resources;
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "roleinfo_seq")
	// @SequenceGenerator(name = "roleinfo_seq", sequenceName = "seq_roleinfo")
	@GeneratedValue(generator = "roleinfoGenerator")
	@GenericGenerator(name = "roleinfoGenerator", strategy = "identity")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
