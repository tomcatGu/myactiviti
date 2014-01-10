package org.crusoe.entity.demolitionAndResettlement;

import java.io.Serializable;
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

import org.crusoe.entity.AbstractSecureObject;

@Entity
@Table(name = "dr_resettlementSituation")
public class ResettlementSituation implements Serializable,
		AbstractSecureObject<Long> {
	private Long id;
	private Long houseHoldsOfMonetaryResettlement;// 货币安置户数
	private float priceOfMonetaryResettlement;// 货币安置总价
	private Long houseHoldsOfMaterialResettlement;// 实物安置户数
	private Long ploidyOfMaterialResettlement;// 实物安置套数
	private float acreageOfMaterialResettlement;// 实物安置面积
	private List<ResettlementDetail> resettlementDetails;// 实物安置详细
	private Date theDayOfFillIn;// 填报日期

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHouseHoldsOfMonetaryResettlement() {
		return houseHoldsOfMonetaryResettlement;
	}

	public void setHouseHoldsOfMonetaryResettlement(
			Long houseHoldsOfMonetaryResettlement) {
		this.houseHoldsOfMonetaryResettlement = houseHoldsOfMonetaryResettlement;
	}

	public float getPriceOfMonetaryResettlement() {
		return priceOfMonetaryResettlement;
	}

	public void setPriceOfMonetaryResettlement(float priceOfMonetaryResettlement) {
		this.priceOfMonetaryResettlement = priceOfMonetaryResettlement;
	}

	public Long getHouseHoldsOfMaterialResettlement() {
		return houseHoldsOfMaterialResettlement;
	}

	public void setHouseHoldsOfMaterialResettlement(
			Long houseHoldsOfMaterialResettlement) {
		this.houseHoldsOfMaterialResettlement = houseHoldsOfMaterialResettlement;
	}

	public Long getPloidyOfMaterialResettlement() {
		return ploidyOfMaterialResettlement;
	}

	public void setPloidyOfMaterialResettlement(
			Long ploidyOfMaterialResettlement) {
		this.ploidyOfMaterialResettlement = ploidyOfMaterialResettlement;
	}

	public float getAcreageOfMaterialResettlement() {
		return acreageOfMaterialResettlement;
	}

	public void setAcreageOfMaterialResettlement(
			float acreageOfMaterialResettlement) {
		this.acreageOfMaterialResettlement = acreageOfMaterialResettlement;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<ResettlementDetail> getResettlementDetails() {
		return resettlementDetails;
	}

	public void setResettlementDetails(
			List<ResettlementDetail> resettlementDetails) {
		this.resettlementDetails = resettlementDetails;
	}

	public Date getTheDayOfFillIn() {
		return theDayOfFillIn;
	}

	public void setTheDayOfFillIn(Date theDayOfFillIn) {
		this.theDayOfFillIn = theDayOfFillIn;
	}
}
