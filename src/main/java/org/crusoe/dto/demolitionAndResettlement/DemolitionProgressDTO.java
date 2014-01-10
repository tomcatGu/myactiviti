package org.crusoe.dto.demolitionAndResettlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public class DemolitionProgressDTO {
	private Long id;
	private Date theFirstDayOfFormallySigned;// 正式签约首日日期
	private List<AmountOfDemolitionDTO> contracted = new ArrayList<AmountOfDemolitionDTO>();// 已签约
	private List<AmountOfDemolitionDTO> dismantled = new ArrayList<AmountOfDemolitionDTO>();// 已拆除
	private Date acceptance;// 拆除验收日期
	private Date theDayOfFillIn;// 填报日期

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTheFirstDayOfFormallySigned() {
		return theFirstDayOfFormallySigned;
	}

	public void setTheFirstDayOfFormallySigned(Date theFirstDayOfFormallySigned) {
		this.theFirstDayOfFormallySigned = theFirstDayOfFormallySigned;
	}

	public List<AmountOfDemolitionDTO> getContracted() {
		return contracted;
	}

	public void setContracted(List<AmountOfDemolitionDTO> contracted) {
		this.contracted = contracted;
	}

	public List<AmountOfDemolitionDTO> getDismantled() {
		return dismantled;
	}

	public void setDismantled(List<AmountOfDemolitionDTO> dismantled) {
		this.dismantled = dismantled;
	}

	public Date getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(Date acceptance) {
		this.acceptance = acceptance;
	}

	public Date getTheDayOfFillIn() {
		return theDayOfFillIn;
	}

	public void setTheDayOfFillIn(Date theDayOfFillIn) {
		this.theDayOfFillIn = theDayOfFillIn;
	}

	public boolean isChanged() {
		if (theFirstDayOfFormallySigned == null)
			return false;
		return true;
	}
}
