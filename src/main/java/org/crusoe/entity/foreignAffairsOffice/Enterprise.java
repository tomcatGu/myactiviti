package org.crusoe.entity.foreignAffairsOffice;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fao_Enterprise")
public class Enterprise implements Serializable {
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

	public long getNature() {
		return this.nature;
	}

	public void setNature(long nature) {
		this.nature = nature;
	}

	public String getInvestorCountry() {
		return this.investorCountry;
	}

	public void setInvestorCountry(String investorCountry) {
		this.investorCountry = investorCountry;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = {
			javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE,
			javax.persistence.CascadeType.REMOVE })
	public Person getEnterpriseLegalRepresentative() {
		return this.enterpriseLegalRepresentative;
	}

	public void setEnterpriseLegalRepresentative(
			Person enterpriseLegalRepresentative) {
		this.enterpriseLegalRepresentative = enterpriseLegalRepresentative;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
}
