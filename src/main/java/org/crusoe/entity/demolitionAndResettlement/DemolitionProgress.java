package org.crusoe.entity.demolitionAndResettlement;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dr_demolitionProgress")
public class DemolitionProgress {
	private Long id;
	private Date theFirstDayOfFormallySigned;// 正式签约首日日期
	private List<AmountOfDemolition> contracted;// 已签约
	private List<AmountOfDemolition> dismantled;// 已拆除
	private Date acceptance;
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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "demolitionProgress_ID", referencedColumnName = "id")
	public List<AmountOfDemolition> getContracted() {
		return contracted;
	}

	public void setContracted(List<AmountOfDemolition> contracted) {
		this.contracted = contracted;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "demolitionProgress_ID", referencedColumnName = "id")
	public List<AmountOfDemolition> getDismantled() {
		return dismantled;
	}

	public void setDismantled(List<AmountOfDemolition> dismantled) {
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
}
