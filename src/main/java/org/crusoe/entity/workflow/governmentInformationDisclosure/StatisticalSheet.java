package org.crusoe.entity.workflow.governmentInformationDisclosure;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "workflow_statisticalSheet")
public class StatisticalSheet {
	private Long id;
	private String annual;
	private String loginName;

	private String statisticalData;
	private Date fillingDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnnual() {
		return annual;
	}

	public void setAnnual(String annual) {
		this.annual = annual;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getFillingDate() {
		return fillingDate;
	}

	public void setFillingDate(Date fillingDate) {
		this.fillingDate = fillingDate;
	}

	@Lob
	public String getStatisticalData() {
		return statisticalData;
	}

	public void setStatisticalData(String statisticalData) {
		this.statisticalData = statisticalData;
	}

}
