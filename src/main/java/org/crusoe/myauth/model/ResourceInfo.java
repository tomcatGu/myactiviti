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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.Cascade;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceInfo implements Serializable, AbstractSecureObject<Long> {
	// private String uuid;
	private String name;
	private String type;
	private String value;
	private Set<RoleInfo> roles = new HashSet<RoleInfo>();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// mappedBy表示关联的被维护端，它的值是关联维护端维护关联的属性
	@ManyToMany(mappedBy = "resources", targetEntity = RoleInfo.class, fetch = FetchType.EAGER, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleInfo> roles) {
		this.roles = roles;
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "userinfo_seq")
	// @SequenceGenerator(name = "userinfo_seq", sequenceName = "seq_userinfo")
	@GeneratedValue(generator = "resourceinfoGenerator")
	@GenericGenerator(name = "resourceinfoGenerator", strategy = "identity")
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
}
