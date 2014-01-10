package org.crusoe.dto.demolitionAndResettlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ResettlementSituationDTO {
	private Long id;
	private Long houseHoldsOfMonetaryResettlement;// 货币安置户数
	private float priceOfMonetaryResettlement;// 货币安置总价
	private Long houseHoldsOfMaterialResettlement;// 实物安置户数
	private Long ploidyOfMaterialResettlement;// 实物安置套数
	private float acreageOfMaterialResettlement;// 实物安置面积
	private List<ResettlementDetailDTO> resettlementDetails = new ArrayList<ResettlementDetailDTO>();// 实物安置详细
	private Date theDayOfFillIn;// 填报日期

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

	public List<ResettlementDetailDTO> getResettlementDetails() {
		return resettlementDetails;
	}

	public void setResettlementDetails(
			List<ResettlementDetailDTO> resettlementDetails) {
		this.resettlementDetails = resettlementDetails;
	}

	public Date getTheDayOfFillIn() {
		return theDayOfFillIn;
	}

	public void setTheDayOfFillIn(Date theDayOfFillIn) {
		this.theDayOfFillIn = theDayOfFillIn;
	}

	public boolean isChanged() {

		if (houseHoldsOfMonetaryResettlement == null
				&& priceOfMonetaryResettlement == 0.0f
				&& houseHoldsOfMaterialResettlement == null
				&& ploidyOfMaterialResettlement == null
				&& acreageOfMaterialResettlement == 0.0f)
			return false;
		else
			return true;
	}
}
