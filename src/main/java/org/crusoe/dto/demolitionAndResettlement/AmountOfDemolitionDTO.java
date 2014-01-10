package org.crusoe.dto.demolitionAndResettlement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class AmountOfDemolitionDTO {
	private Long id;
	private Long dwelling;// 民居
	private float acreageOfDwelling;// 民居面积
	private Long nonDwelling;// 非居
	private float acreageOfNonDwelling;// 非居面积
	private String tender;// 标段
	private Date theDayOfFillIn;// 填报日期

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDwelling() {
		return dwelling;
	}

	public void setDwelling(Long dwelling) {
		this.dwelling = dwelling;
	}

	public float getAcreageOfDwelling() {
		return acreageOfDwelling;
	}

	public void setAcreageOfDwelling(float acreageOfDwelling) {
		this.acreageOfDwelling = acreageOfDwelling;
	}

	public Long getNonDwelling() {
		return nonDwelling;
	}

	public void setNonDwelling(Long nonDwelling) {
		this.nonDwelling = nonDwelling;
	}

	public float getAcreageOfNonDwelling() {
		return acreageOfNonDwelling;
	}

	public void setAcreageOfNonDwelling(float acreageOfNonDwelling) {
		this.acreageOfNonDwelling = acreageOfNonDwelling;
	}

	public String getTender() {
		return tender;
	}

	public void setTender(String tender) {
		this.tender = tender;
	}

	public Date getTheDayOfFillIn() {
		return theDayOfFillIn;
	}

	public void setTheDayOfFillIn(Date theDayOfFillIn) {
		this.theDayOfFillIn = theDayOfFillIn;
	}

	public boolean isChanged() {
		if (dwelling == null && acreageOfDwelling == 0.0f && nonDwelling == null
				&& acreageOfNonDwelling == 0.0f)
			return false;
		else
			return true;
	}
}
