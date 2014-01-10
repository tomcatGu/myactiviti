package org.crusoe.dto.demolitionAndResettlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.crusoe.entity.User;

public class DemolitionDTO {

	private Long id;
	private String department;// 上报单位
	private String subjectName;// 项目名称
	private List<CompanyDTO> mappingCompany = new ArrayList<CompanyDTO>();// 测绘公司
	private List<CompanyDTO> assessmentCompany = new ArrayList<CompanyDTO>();;// 评估公司
	private List<CompanyDTO> demolitionCompany = new ArrayList<CompanyDTO>();;// 拆迁公司
	private float capitalBudget;// 资金概算总额
	private AmountOfDemolitionDTO planned;// 计划拆迁量
	private List<MeasureToAssessTheSituationDTO> measureToAssessTheSituations = new ArrayList<MeasureToAssessTheSituationDTO>();// 丈量评估情况
	private List<AmountOfDemolitionDTO> actuals = new ArrayList<AmountOfDemolitionDTO>();// 实际拆迁情况
	private List<DemolitionProgressDTO> signeds = new ArrayList<DemolitionProgressDTO>();// 签约拆除进展
	private List<ResettlementSituationDTO> resettlments = new ArrayList<ResettlementSituationDTO>();// 安置情况
	private Date theDayOfFillIn;// 填报日期
	private String remark;// 备注
	private PersonOfFillInDTO thePersonOfFillIn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<CompanyDTO> getMappingCompany() {
		return mappingCompany;
	}

	public void setMappingCompany(List<CompanyDTO> mappingCompany) {
		this.mappingCompany = mappingCompany;
	}

	public List<CompanyDTO> getAssessmentCompany() {
		return assessmentCompany;
	}

	public void setAssessmentCompany(List<CompanyDTO> assessmentCompany) {
		this.assessmentCompany = assessmentCompany;
	}

	public List<CompanyDTO> getDemolitionCompany() {
		return demolitionCompany;
	}

	public void setDemolitionCompany(List<CompanyDTO> demolitionCompany) {
		this.demolitionCompany = demolitionCompany;
	}

	public float getCapitalBudget() {
		return capitalBudget;
	}

	public void setCapitalBudget(float capitalBudget) {
		this.capitalBudget = capitalBudget;
	}

	public AmountOfDemolitionDTO getPlanned() {
		return planned;
	}

	public void setPlanned(AmountOfDemolitionDTO planned) {
		this.planned = planned;
	}

	public List<MeasureToAssessTheSituationDTO> getMeasureToAssessTheSituations() {
		return measureToAssessTheSituations;
	}

	public void setMeasureToAssessTheSituations(
			List<MeasureToAssessTheSituationDTO> measureToAssessTheSituations) {
		this.measureToAssessTheSituations = measureToAssessTheSituations;
	}

	public List<AmountOfDemolitionDTO> getActuals() {
		return actuals;
	}

	public void setActuals(List<AmountOfDemolitionDTO> actuals) {
		this.actuals = actuals;
	}

	public List<DemolitionProgressDTO> getSigneds() {
		return signeds;
	}

	public void setSigneds(List<DemolitionProgressDTO> signeds) {
		this.signeds = signeds;
	}

	public Date getTheDayOfFillIn() {
		return theDayOfFillIn;
	}

	public void setTheDayOfFillIn(Date theDayOfFillIn) {
		this.theDayOfFillIn = theDayOfFillIn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PersonOfFillInDTO getThePersonOfFillIn() {
		return thePersonOfFillIn;
	}

	public void setThePersonOfFillIn(PersonOfFillInDTO thePersonOfFillIn) {
		this.thePersonOfFillIn = thePersonOfFillIn;
	}

	public List<ResettlementSituationDTO> getResettlments() {
		return resettlments;
	}

	public void setResettlments(List<ResettlementSituationDTO> resettlments) {
		this.resettlments = resettlments;
	}

}
