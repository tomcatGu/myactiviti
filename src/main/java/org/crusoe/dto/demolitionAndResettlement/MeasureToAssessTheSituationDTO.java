package org.crusoe.dto.demolitionAndResettlement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class MeasureToAssessTheSituationDTO {
	private Long id;
	private Date theFirstDayOfAssessment;// 进场丈量评估首日日期
	private Long dwelling;// 民居户数
	private Long nonDwelling;// 非居户数
	private Date theDayOfFillIn;// 填报日期

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTheFirstDayOfAssessment() {
		return theFirstDayOfAssessment;
	}

	public void setTheFirstDayOfAssessment(Date theFirstDayOfAssessment) {
		this.theFirstDayOfAssessment = theFirstDayOfAssessment;
	}

	public Long getDwelling() {
		return dwelling;
	}

	public void setDwelling(Long dwelling) {
		this.dwelling = dwelling;
	}

	public Long getNonDwelling() {
		return nonDwelling;
	}

	public void setNonDwelling(Long nonDwelling) {
		this.nonDwelling = nonDwelling;
	}

	public Date getTheDayOfFillIn() {
		return theDayOfFillIn;
	}

	public void setTheDayOfFillIn(Date theDayOfFillIn) {
		this.theDayOfFillIn = theDayOfFillIn;
	}

	public boolean isChanged() {

		if (dwelling == null && nonDwelling == null)
			return false;
		else
			return true;
	}
}
