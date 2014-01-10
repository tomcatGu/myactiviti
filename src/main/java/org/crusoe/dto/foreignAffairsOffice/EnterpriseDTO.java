package org.crusoe.dto.foreignAffairsOffice;

import org.crusoe.entity.foreignAffairsOffice.Person;

public class EnterpriseDTO {
	public static final String OverseasChineseEnterprise = "OCE";
	public static final String QiaoShuEnterprise = "QSE";
	private long id;
	private long orderNumber;
	private String name;
	private long nature;
	private String investorCountry;
	private String address;
	private String zipcode;
	private Person enterpriseLegalRepresentative;
	private String remark;
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
	public long getNature() {
		return nature;
	}
	public void setNature(long nature) {
		this.nature = nature;
	}
	public String getInvestorCountry() {
		return investorCountry;
	}
	public void setInvestorCountry(String investorCountry) {
		this.investorCountry = investorCountry;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public Person getEnterpriseLegalRepresentative() {
		return enterpriseLegalRepresentative;
	}
	public void setEnterpriseLegalRepresentative(
			Person enterpriseLegalRepresentative) {
		this.enterpriseLegalRepresentative = enterpriseLegalRepresentative;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
