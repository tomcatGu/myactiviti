package org.crusoe.entity.demolitionAndResettlement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dr_resettlementDetail")
public class ResettlementDetail {
	private Long id;
	private String resettlementName;// 安置项目名称
	private Long houseHolds;// 安置户数
	private Long ploidy;// 安置套数

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
