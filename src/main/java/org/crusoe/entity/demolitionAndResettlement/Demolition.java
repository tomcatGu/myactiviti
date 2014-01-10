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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.crusoe.dto.demolitionAndResettlement.ResettlementSituationDTO;
import org.crusoe.entity.AbstractSecureObject;
import org.crusoe.entity.User;

@Entity
@Table(name = "dr_demolition")
public class Demolition implements Serializable, AbstractSecureObject<Long> {

	private Long id;
	private String department;// 上报单位
	private String subjectName;// 项目名称
	private List<Company> mappingCompany;// 测绘公司
	private List<Company> assessmentCompany;// 评估公司
	private List<Company> demolitionCompany;// 拆迁公司
	private float capitalBudget;// 资金概算总额
	private AmountOfDemolition planned;// 计划拆迁量
	private List<MeasureToAssessTheSituation> measureToAssessTheSituations;// 丈量评估情况
	private List<AmountOfDemolition> actuals;// 实际拆迁情况
	private List<DemolitionProgress> signeds;// 签约拆除进展
	private List<ResettlementSituation> resettlments;// 安置情况
	private Date theDayOfFillIn;// 填报日期
	private String remark;// 备注
	private PersonOfFillIn thePersonOfFillIn;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<Company> getMappingCompany() {
		return mappingCompany;
	}

	public void setMappingCompany(List<Company> mappingCompany) {
		this.mappingCompany = mappingCompany;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<Company> getAssessmentCompany() {
		return assessmentCompany;
	}

	public void setAssessmentCompany(List<Company> assessmentCompany) {
		this.assessmentCompany = assessmentCompany;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<Company> getDemolitionCompany() {
		return demolitionCompany;
	}

	public void setDemolitionCompany(List<Company> demolitionCompany) {
		this.demolitionCompany = demolitionCompany;
	}

	public float getCapitalBudget() {
		return capitalBudget;
	}

	public void setCapitalBudget(float capitalBudget) {
		this.capitalBudget = capitalBudget;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "demolition")
	public AmountOfDemolition getPlanned() {
		return planned;
	}

	public void setPlanned(AmountOfDemolition planned) {
		this.planned = planned;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<MeasureToAssessTheSituation> getMeasureToAssessTheSituations() {
		return measureToAssessTheSituations;
	}

	public void setMeasureToAssessTheSituations(
			List<MeasureToAssessTheSituation> measureToAssessTheSituations) {
		this.measureToAssessTheSituations = measureToAssessTheSituations;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<AmountOfDemolition> getActuals() {
		return actuals;
	}

	public void setActuals(List<AmountOfDemolition> actuals) {
		this.actuals = actuals;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<DemolitionProgress> getSigneds() {
		return signeds;
	}

	public void setSigneds(List<DemolitionProgress> signeds) {
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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id")
	public PersonOfFillIn getThePersonOfFillIn() {
		return thePersonOfFillIn;
	}

	public void setThePersonOfFillIn(PersonOfFillIn thePersonOfFillIn) {
		this.thePersonOfFillIn = thePersonOfFillIn;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "id")
	public List<ResettlementSituation> getResettlments() {
		return resettlments;
	}

	public void setResettlments(List<ResettlementSituation> resettlments) {
		this.resettlments = resettlments;
	}

}
