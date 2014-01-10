package org.crusoe.dto.demolitionAndResettlement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class ResettlementDetailDTO {
	private Long id;
	private String resettlementName;// 安置项目名称
	private Long houseHolds;// 安置户数
	private Long ploidy;// 安置套数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResettlementName() {
		return resettlementName;
	}

	public void setResettlementName(String resettlementName) {
		this.resettlementName = resettlementName;
	}

	public Long getHouseHolds() {
		return houseHolds;
	}

	public void setHouseHolds(Long houseHolds) {
		this.houseHolds = houseHolds;
	}

	public Long getPloidy() {
		return ploidy;
	}

	public void setPloidy(Long ploidy) {
		this.ploidy = ploidy;
	}

	public boolean isChanged() {
		if (resettlementName.isEmpty() && houseHolds ==null	 && ploidy == null)
			return false;
		else
			return true;

	}
}
