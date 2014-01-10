package org.crusoe.entity.foreignAffairsOffice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "fao_Person")
public class Person implements Serializable {
	private long id;
	private long orderNumber;
	private String name;
	private int sex = 0;
	private Date birthday;
	private String nativeHome;
	private Date abroadTime;
	private String residenceCountry;
	private boolean toLocate;
	private String degree;
	private String politicalLandscape;
	private String expertise;
	private String interest;
	private String unit;
	private String post;
	private String telephone;
	private String email;
	private String overseasMailingAddress;
	private String resume;
	private String major;
	private String appellation;
	private String remark;
	private long personTag = 0L;

	private List<Person> abroadRelatives = new ArrayList();
	private List<Person> domesticeRelatives = new ArrayList();

	public long getPersonTag() {
		return this.personTag;
	}

	public void setPersonTag(long personTag) {
		this.personTag = personTag;
	}

	public Person() {
	}

	public Person(long personTag) {
		this.personTag = personTag;
	}

	public void addAbroadRelative() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNativeHome() {
		return this.nativeHome;
	}

	public void setNativeHome(String nativeHome) {
		this.nativeHome = nativeHome;
	}

	public Date getAbroadTime() {
		return this.abroadTime;
	}

	public void setAbroadTime(Date abroadTime) {
		this.abroadTime = abroadTime;
	}

	public String getResidenceCountry() {
		return this.residenceCountry;
	}

	public void setResidenceCountry(String residenceCountry) {
		this.residenceCountry = residenceCountry;
	}

	public boolean isToLocate() {
		return this.toLocate;
	}

	public void setToLocate(boolean toLocate) {
		this.toLocate = toLocate;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPoliticalLandscape() {
		return this.politicalLandscape;
	}

	public void setPoliticalLandscape(String politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}

	public String getExpertise() {
		return this.expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getInterest() {
		return this.interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOverseasMailingAddress() {
		return this.overseasMailingAddress;
	}

	public void setOverseasMailingAddress(String overseasMailingAddress) {
		this.overseasMailingAddress = overseasMailingAddress;
	}

	public String getResume() {
		return this.resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getAppellation() {
		return this.appellation;
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = {
			javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE,
			javax.persistence.CascadeType.REMOVE })
	@JoinTable(name = "Person_AbroadRelative")
	@OrderBy("appellation DESC")
	public List<Person> getAbroadRelatives() {
		return this.abroadRelatives;
	}

	public void setAbroadRelatives(List<Person> abroadRelatives) {
		this.abroadRelatives = abroadRelatives;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.ALL })
	@JoinTable(name = "Person_DomesticeRelative")
	@OrderBy("appellation DESC")
	public List<Person> getDomesticeRelatives() {
		return this.domesticeRelatives;
	}

	public void setDomesticeRelatives(List<Person> domesticcRelatives) {
		this.domesticeRelatives = domesticcRelatives;
	}

	public long getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
}

