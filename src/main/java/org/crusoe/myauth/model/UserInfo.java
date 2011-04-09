package org.crusoe.myauth.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfo implements UserDetails, AbstractSecureObject<Long> {

	// String uuid;
	Long id;
	String username;
	String realname;
	String password;
	String mobile;
	String email;
	Date accountExpireDate;
	Date credentialsExpireDate;
	byte state;
	Set<RoleInfo> roles = new HashSet<RoleInfo>();

	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(
				roles.size());

		for (RoleInfo role : roles) {
			grantedAuthorities.add(new GrantedAuthorityImpl(role.getValue()));
		}

		return grantedAuthorities;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Transient
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return (accountExpireDate == null) ? true : accountExpireDate
				.before(new Date());

	}

	@Transient
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !((byte) (state & (byte) 0x02) > 0);
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return (credentialsExpireDate == null) ? true : credentialsExpireDate
				.before(new Date());
	}

	@Transient
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return !((byte) (state & (byte) 0x01) > 0);

	}

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

	public Date getAccountExpireDate() {
		return accountExpireDate;
	}

	public void setAccountExpireDate(Date accountExpireDate) {
		this.accountExpireDate = accountExpireDate;
	}

	public Date getCredentialsExpireDate() {
		return credentialsExpireDate;
	}

	public void setCredentialsExpireDate(Date credentialsExpireDate) {
		this.credentialsExpireDate = credentialsExpireDate;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	@ManyToMany(targetEntity = RoleInfo.class, fetch = FetchType.EAGER)
	@JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleInfo> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonLocked(boolean isNonLocked) {
		if (isNonLocked ^ isAccountNonLocked())
			this.state = (byte) (this.state ^ (byte) 0x02);
	}

	public void setEnabled(boolean isEnabled) {
		if (isEnabled ^ isEnabled())
			this.state = (byte) (this.state ^ (byte) 0x01);
	}

	@Transient
	public void setRecvMsg(boolean isRecvMsg) {
		if (isRecvMsg ^ isRecvMsg())
			this.state = (byte) (this.state ^ (byte) 0x10);
	}

	@Transient
	public boolean isRecvMsg() {
		return (byte) (state & (byte) 0x10) > 0;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "userinfo_seq")
	// @SequenceGenerator(name = "userinfo_seq", sequenceName = "seq_userinfo")
	@GeneratedValue(generator = "userinfoGenerator")
	@GenericGenerator(name = "userinfoGenerator", strategy = "identity")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
