package org.crusoe.dto.foreignAffairsOffice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.crusoe.entity.foreignAffairsOffice.Person;

public class PersonDTO {
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

	private List<PersonDTO> abroadRelatives=new ArrayList();
	private List<PersonDTO> domesticeRelatives = new ArrayList();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getNativeHome() {
		return nativeHome;
	}
	public void setNativeHome(String nativeHome) {
		this.nativeHome = nativeHome;
	}
	public Date getAbroadTime() {
		return abroadTime;
	}
	public void setAbroadTime(Date abroadTime) {
		this.abroadTime = abroadTime;
	}
	public String getResidenceCountry() {
		return residenceCountry;
	}
	public void setResidenceCountry(String residenceCountry) {
		this.residenceCountry = residenceCountry;
	}
	public boolean isToLocate() {
		return toLocate;
	}
	public void setToLocate(boolean toLocate) {
		this.toLocate = toLocate;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getPoliticalLandscape() {
		return politicalLandscape;
	}
	public void setPoliticalLandscape(String politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOverseasMailingAddress() {
		return overseasMailingAddress;
	}
	public void setOverseasMailingAddress(String overseasMailingAddress) {
		this.overseasMailingAddress = overseasMailingAddress;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getAppellation() {
		return appellation;
	}
	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getPersonTag() {
		return personTag;
	}
	public void setPersonTag(long personTag) {
		this.personTag = personTag;
	}
	public List<PersonDTO> getDomesticeRelatives() {
		return domesticeRelatives;
	}
	public void setDomesticeRelatives(List<PersonDTO> domesticeRelatives) {
		this.domesticeRelatives = domesticeRelatives;
	}
	public List<PersonDTO> getAbroadRelatives() {
		return abroadRelatives;
	}
	public void setAbroadRelatives(List<PersonDTO> abroadRelatives) {
		this.abroadRelatives = abroadRelatives;
	}
}
