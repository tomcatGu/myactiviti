package org.crusoe.entity.demolitionAndResettlement;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dr_amountOfDemolition")
public class AmountOfDemolition {
	private Long id;
	private Long dwelling;// 民居
	private float acreageOfDwelling;// 民居面积
	private Long nonDwelling;// 非居
	private float acreageOfNonDwelling;// 非居面积
	private String tender;// 标段
	private Date theDayOfFillIn;// 填报日期
	private Demolition demolition;// 拆迁项目

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToOne
	public Demolition getDemolition() {
		return demolition;
	}

	public void setDemolition(Demolition demolition) {
		this.demolition = demolition;
	}
}
