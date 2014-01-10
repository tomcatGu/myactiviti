package org.crusoe.mvc.ajax.demolition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.crusoe.dto.RoleDTO;
import org.crusoe.dto.UserDTO;
import org.crusoe.dto.demolitionAndResettlement.AmountOfDemolitionDTO;
import org.crusoe.dto.demolitionAndResettlement.CompanyDTO;
import org.crusoe.dto.demolitionAndResettlement.DemolitionDTO;
import org.crusoe.dto.demolitionAndResettlement.DemolitionProgressDTO;
import org.crusoe.dto.demolitionAndResettlement.MeasureToAssessTheSituationDTO;
import org.crusoe.dto.demolitionAndResettlement.PersonOfFillInDTO;
import org.crusoe.dto.demolitionAndResettlement.ResettlementDetailDTO;
import org.crusoe.dto.demolitionAndResettlement.ResettlementSituationDTO;
import org.crusoe.entity.Role;
import org.crusoe.entity.User;
import org.crusoe.entity.demolitionAndResettlement.AmountOfDemolition;
import org.crusoe.entity.demolitionAndResettlement.Company;
import org.crusoe.entity.demolitionAndResettlement.Demolition;
import org.crusoe.entity.demolitionAndResettlement.DemolitionProgress;
import org.crusoe.entity.demolitionAndResettlement.MeasureToAssessTheSituation;
import org.crusoe.entity.demolitionAndResettlement.PersonOfFillIn;
import org.crusoe.entity.demolitionAndResettlement.ResettlementDetail;
import org.crusoe.entity.demolitionAndResettlement.ResettlementSituation;
import org.crusoe.service.demolitionAndResettlement.DemolitionService;
import org.crusoe.service.demolitionAndResettlement.PersonOfFillInService;
import org.crusoe.util.JSONUtil;
import org.crusoe.web.datatables.DataTableReturnObject;
import org.crusoe.web.datatables.JSONParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/demolition")
public class DemolitionController {
	@Autowired
	private DemolitionService demolitionService;

	@Autowired
	private PersonOfFillInService personOfFillInService;

	@RequestMapping(value = "index")
	public String list(ServletRequest request) {

		return "demolition/index";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listForm(Model model) {
		return "demolition/list";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		DemolitionDTO demolitionDTO = new DemolitionDTO();
		// 测绘公司
		CompanyDTO c1 = new CompanyDTO();
		c1.setTender("一标");
		CompanyDTO c2 = new CompanyDTO();
		c2.setTender("二标");
		demolitionDTO.getMappingCompany().add(c1);
		demolitionDTO.getMappingCompany().add(c2);
		// 评估公司
		CompanyDTO c3 = new CompanyDTO();
		c3.setTender("一标");
		CompanyDTO c4 = new CompanyDTO();
		c4.setTender("二标");
		demolitionDTO.getAssessmentCompany().add(c3);
		demolitionDTO.getAssessmentCompany().add(c4);
		// 拆迁公司
		CompanyDTO c5 = new CompanyDTO();
		c5.setTender("一标");
		CompanyDTO c6 = new CompanyDTO();
		c6.setTender("二标");
		demolitionDTO.getDemolitionCompany().add(c5);
		demolitionDTO.getDemolitionCompany().add(c6);

		// 丈量评估情况
		MeasureToAssessTheSituationDTO mtatsDTO = new MeasureToAssessTheSituationDTO();
		Calendar cal = Calendar.getInstance();
		mtatsDTO.setTheFirstDayOfAssessment(cal.getTime());
		demolitionDTO.getMeasureToAssessTheSituations().add(mtatsDTO);

		// 签约拆除进展
		DemolitionProgressDTO dpDTO = new DemolitionProgressDTO();
		AmountOfDemolitionDTO amountOfDemolitionDTO = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO.setTender("一标");

		AmountOfDemolitionDTO amountOfDemolitionDTO1 = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO1.setTender("二标");
		dpDTO.getContracted().add(amountOfDemolitionDTO);
		dpDTO.getContracted().add(amountOfDemolitionDTO1);

		AmountOfDemolitionDTO amountOfDemolitionDTO2 = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO2.setTender("一标");
		AmountOfDemolitionDTO amountOfDemolitionDTO3 = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO3.setTender("二标");

		dpDTO.getDismantled().add(amountOfDemolitionDTO2);
		dpDTO.getDismantled().add(amountOfDemolitionDTO3);

		demolitionDTO.getSigneds().add(dpDTO);

		// 实际拆迁情况
		AmountOfDemolitionDTO actualAmountOfDemolitionDTO = new AmountOfDemolitionDTO();
		demolitionDTO.getActuals().add(actualAmountOfDemolitionDTO);

		// 安置情况
		ResettlementSituationDTO resettlementSituationDTO = new ResettlementSituationDTO();
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		demolitionDTO.getResettlments().add(resettlementSituationDTO);

		model.addAttribute("demolition", demolitionDTO);

		return "demolition/createForm";
	}

	@RequestMapping(value = "append", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> append(
			@Valid @RequestBody DemolitionDTO newDemolition,
			RedirectAttributes redirectAttributes) {

		Demolition demolition = demolitionService.findById(newDemolition
				.getId());

		Calendar cal = Calendar.getInstance();
		// isChanged检查传入参数是否需要保存
		// 设置丈量评估情况
		if (demolition.getMeasureToAssessTheSituations() == null) {
			demolition
					.setMeasureToAssessTheSituations(new ArrayList<MeasureToAssessTheSituation>());
		}
		Iterator iter = newDemolition.getMeasureToAssessTheSituations()
				.iterator();
		while (iter.hasNext()) {

			MeasureToAssessTheSituationDTO mtatsDTO = (MeasureToAssessTheSituationDTO) iter
					.next();
			if (!mtatsDTO.isChanged())
				continue;
			MeasureToAssessTheSituation mtasts = new MeasureToAssessTheSituation();
			BeanUtils.copyProperties(mtatsDTO, mtasts);
			mtasts.setTheDayOfFillIn(cal.getTime());
			demolition.getMeasureToAssessTheSituations().add(mtasts);

		}

		// 设置签约拆迁进展
		if (demolition.getSigneds() == null) {
			demolition.setSigneds(new ArrayList<DemolitionProgress>());
		}
		iter = newDemolition.getSigneds().iterator();
		while (iter.hasNext()) {
			DemolitionProgressDTO dpDTO = (DemolitionProgressDTO) iter.next();
			if (!dpDTO.isChanged())
				continue;
			DemolitionProgress dp = new DemolitionProgress();
			dp.setTheFirstDayOfFormallySigned(dpDTO
					.getTheFirstDayOfFormallySigned());
			dp.setAcceptance(dpDTO.getAcceptance());
			dp.setContracted(new ArrayList<AmountOfDemolition>());
			dp.setDismantled(new ArrayList<AmountOfDemolition>());

			// 设置已签约的情况
			Iterator iter1 = dpDTO.getContracted().iterator();
			while (iter1.hasNext()) {
				AmountOfDemolitionDTO aodDTO = (AmountOfDemolitionDTO) iter1
						.next();
				AmountOfDemolition aod = new AmountOfDemolition();
				BeanUtils.copyProperties(aodDTO, aod);
				dp.getContracted().add(aod);
			}
			// 设置已拆除的情况
			iter1 = dpDTO.getDismantled().iterator();
			while (iter1.hasNext()) {
				AmountOfDemolitionDTO aodDTO = (AmountOfDemolitionDTO) iter1
						.next();
				AmountOfDemolition aod = new AmountOfDemolition();
				BeanUtils.copyProperties(aodDTO, aod);
				dp.getDismantled().add(aod);
			}
			dp.setTheDayOfFillIn(cal.getTime());
			demolition.getSigneds().add(dp);

		}

		// 设置实际拆迁情况
		if (demolition.getActuals() == null) {
			demolition.setActuals(new ArrayList<AmountOfDemolition>());
		}
		iter = newDemolition.getActuals().iterator();
		while (iter.hasNext()) {

			AmountOfDemolitionDTO aodDTO = (AmountOfDemolitionDTO) iter.next();
			if (!aodDTO.isChanged())
				continue;
			AmountOfDemolition aod = new AmountOfDemolition();
			BeanUtils.copyProperties(aodDTO, aod);
			aod.setTheDayOfFillIn(cal.getTime());
			demolition.getActuals().add(aod);
		}
		// 设置安置情况
		if (demolition.getResettlments() == null) {
			demolition.setResettlments(new ArrayList<ResettlementSituation>());
		}
		iter = newDemolition.getResettlments().iterator();
		while (iter.hasNext()) {
			ResettlementSituationDTO rsDTO = (ResettlementSituationDTO) iter
					.next();
			if (!rsDTO.isChanged())
				continue;
			ResettlementSituation rs = new ResettlementSituation();
			rs.setHouseHoldsOfMonetaryResettlement(rsDTO
					.getHouseHoldsOfMonetaryResettlement());
			rs.setPriceOfMonetaryResettlement(rsDTO
					.getPriceOfMonetaryResettlement());
			rs.setHouseHoldsOfMaterialResettlement(rsDTO
					.getHouseHoldsOfMaterialResettlement());
			rs.setPloidyOfMaterialResettlement(rsDTO
					.getPloidyOfMaterialResettlement());
			rs.setAcreageOfMaterialResettlement(rsDTO
					.getAcreageOfMaterialResettlement());

			rs.setResettlementDetails(new ArrayList<ResettlementDetail>());
			Iterator iter1 = rsDTO.getResettlementDetails().iterator();
			while (iter1.hasNext()) {
				ResettlementDetailDTO rdDTO = (ResettlementDetailDTO) iter1
						.next();
				ResettlementDetail rd = new ResettlementDetail();
				BeanUtils.copyProperties(rdDTO, rd);
				rs.getResettlementDetails().add(rd);
			}
			rs.setTheDayOfFillIn(cal.getTime());
			demolition.getResettlments().add(rs);

		}

		// 设置备注
		demolition.setRemark(newDemolition.getRemark());
		try {
			demolitionService.save(demolition);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return Collections.singletonMap("errmsg", "Throw exccption!");
		}

		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return Collections.singletonMap("id", demolition.getId());
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> create(
			@Valid @RequestBody DemolitionDTO newDemolition,
			RedirectAttributes redirectAttributes) {
		Demolition demolition = transfer(newDemolition);
		// 保存输入的信息
		try {
			demolitionService.save(demolition);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return Collections.singletonMap("id", demolition.getId());
	}

	private Demolition transfer(DemolitionDTO newDemolition) {
		Demolition demolition = new Demolition();
		demolition.setDepartment(newDemolition.getDepartment());
		demolition.setSubjectName(newDemolition.getSubjectName());
		demolition.setCapitalBudget(newDemolition.getCapitalBudget());
		// 设置测绘公司信息
		demolition.setMappingCompany(new ArrayList<Company>());
		Iterator iter = newDemolition.getMappingCompany().iterator();
		while (iter.hasNext()) {
			CompanyDTO cDTO = (CompanyDTO) iter.next();
			Company c = new Company();
			BeanUtils.copyProperties(cDTO, c);
			demolition.getMappingCompany().add(c);
		}
		// 设置评估公司信息
		demolition.setAssessmentCompany(new ArrayList<Company>());
		iter = newDemolition.getAssessmentCompany().iterator();
		while (iter.hasNext()) {
			CompanyDTO cDTO = (CompanyDTO) iter.next();
			Company c = new Company();
			BeanUtils.copyProperties(cDTO, c);
			demolition.getAssessmentCompany().add(c);
		}
		// 设置拆迁公司信息
		demolition.setDemolitionCompany(new ArrayList<Company>());
		iter = newDemolition.getDemolitionCompany().iterator();
		while (iter.hasNext()) {
			CompanyDTO cDTO = (CompanyDTO) iter.next();
			Company c = new Company();
			BeanUtils.copyProperties(cDTO, c);
			demolition.getDemolitionCompany().add(c);
		}
		// 设置资金概算总额
		demolition.setCapitalBudget(newDemolition.getCapitalBudget());
		// 设置计划拆迁量

		Calendar cal = Calendar.getInstance();

		demolition.setPlanned(new AmountOfDemolition());
		demolition.getPlanned().setDwelling(
				newDemolition.getPlanned().getDwelling());
		demolition.getPlanned().setAcreageOfDwelling(
				newDemolition.getPlanned().getAcreageOfDwelling());
		demolition.getPlanned().setNonDwelling(
				newDemolition.getPlanned().getNonDwelling());
		demolition.getPlanned().setAcreageOfNonDwelling(
				newDemolition.getPlanned().getAcreageOfNonDwelling());
		demolition.getPlanned().setTheDayOfFillIn(cal.getTime());
		// 设置丈量评估情况
		demolition
				.setMeasureToAssessTheSituations(new ArrayList<MeasureToAssessTheSituation>());
		iter = newDemolition.getMeasureToAssessTheSituations().iterator();
		while (iter.hasNext()) {

			MeasureToAssessTheSituationDTO mtatsDTO = (MeasureToAssessTheSituationDTO) iter
					.next();
			MeasureToAssessTheSituation mtasts = new MeasureToAssessTheSituation();
			BeanUtils.copyProperties(mtatsDTO, mtasts);
			mtasts.setTheDayOfFillIn(cal.getTime());
			demolition.getMeasureToAssessTheSituations().add(mtasts);

		}

		// 设置签约拆迁进展
		demolition.setSigneds(new ArrayList<DemolitionProgress>());
		iter = newDemolition.getSigneds().iterator();
		while (iter.hasNext()) {
			DemolitionProgressDTO dpDTO = (DemolitionProgressDTO) iter.next();
			DemolitionProgress dp = new DemolitionProgress();
			dp.setTheFirstDayOfFormallySigned(dpDTO
					.getTheFirstDayOfFormallySigned());
			dp.setAcceptance(dpDTO.getAcceptance());
			dp.setContracted(new ArrayList<AmountOfDemolition>());
			dp.setDismantled(new ArrayList<AmountOfDemolition>());

			// 设置已签约的情况
			Iterator iter1 = dpDTO.getContracted().iterator();
			while (iter1.hasNext()) {
				AmountOfDemolitionDTO aodDTO = (AmountOfDemolitionDTO) iter1
						.next();
				AmountOfDemolition aod = new AmountOfDemolition();
				BeanUtils.copyProperties(aodDTO, aod);
				dp.getContracted().add(aod);
			}
			// 设置已拆除的情况
			iter1 = dpDTO.getDismantled().iterator();
			while (iter1.hasNext()) {
				AmountOfDemolitionDTO aodDTO = (AmountOfDemolitionDTO) iter1
						.next();
				AmountOfDemolition aod = new AmountOfDemolition();
				BeanUtils.copyProperties(aodDTO, aod);
				dp.getDismantled().add(aod);
			}
			dp.setTheDayOfFillIn(cal.getTime());
			demolition.getSigneds().add(dp);

		}

		// 设置实际拆迁情况
		demolition.setActuals(new ArrayList<AmountOfDemolition>());
		iter = newDemolition.getActuals().iterator();
		while (iter.hasNext()) {
			AmountOfDemolitionDTO aodDTO = (AmountOfDemolitionDTO) iter.next();
			AmountOfDemolition aod = new AmountOfDemolition();
			BeanUtils.copyProperties(aodDTO, aod);
			aod.setTheDayOfFillIn(cal.getTime());
			demolition.getActuals().add(aod);
		}
		// 设置安置情况
		demolition.setResettlments(new ArrayList<ResettlementSituation>());
		iter = newDemolition.getResettlments().iterator();
		while (iter.hasNext()) {
			ResettlementSituationDTO rsDTO = (ResettlementSituationDTO) iter
					.next();
			ResettlementSituation rs = new ResettlementSituation();
			rs.setHouseHoldsOfMonetaryResettlement(rsDTO
					.getHouseHoldsOfMonetaryResettlement());
			rs.setPriceOfMonetaryResettlement(rsDTO
					.getPriceOfMonetaryResettlement());
			rs.setHouseHoldsOfMaterialResettlement(rsDTO
					.getHouseHoldsOfMaterialResettlement());
			rs.setPloidyOfMaterialResettlement(rsDTO
					.getPloidyOfMaterialResettlement());
			rs.setAcreageOfMaterialResettlement(rsDTO
					.getAcreageOfMaterialResettlement());

			rs.setResettlementDetails(new ArrayList<ResettlementDetail>());
			Iterator iter1 = rsDTO.getResettlementDetails().iterator();
			while (iter1.hasNext()) {
				ResettlementDetailDTO rdDTO = (ResettlementDetailDTO) iter1
						.next();
				ResettlementDetail rd = new ResettlementDetail();
				BeanUtils.copyProperties(rdDTO, rd);
				rs.getResettlementDetails().add(rd);
			}
			rs.setTheDayOfFillIn(cal.getTime());
			demolition.getResettlments().add(rs);

		}

		// 设置备注
		demolition.setRemark(newDemolition.getRemark());

		// 设置填表日期
		demolition.setTheDayOfFillIn(new Date());
		// 设置填表人
		String principal = SecurityUtils.getSubject().getPrincipal().toString();
		PersonOfFillIn pofi = personOfFillInService.find(principal);
		if (pofi == null) {

			pofi = new PersonOfFillIn();
			pofi.setName(principal);
			personOfFillInService.save(pofi);

		}
		demolition.setThePersonOfFillIn(pofi);
		return demolition;
	}

	private DemolitionDTO transfer(Demolition newDemolition) {

		DemolitionDTO demolitionDTO = new DemolitionDTO();
		demolitionDTO.setId(newDemolition.getId());
		demolitionDTO.setDepartment(newDemolition.getDepartment());
		demolitionDTO.setSubjectName(newDemolition.getSubjectName());
		demolitionDTO.setCapitalBudget(newDemolition.getCapitalBudget());
		// 设置测绘公司信息
		demolitionDTO.setMappingCompany(new ArrayList<CompanyDTO>());
		Iterator iter = newDemolition.getMappingCompany().iterator();
		while (iter.hasNext()) {
			Company c = (Company) iter.next();
			CompanyDTO cDTO = new CompanyDTO();
			BeanUtils.copyProperties(c, cDTO);
			demolitionDTO.getMappingCompany().add(cDTO);
		}
		// 设置评估公司信息
		demolitionDTO.setAssessmentCompany(new ArrayList<CompanyDTO>());
		iter = newDemolition.getAssessmentCompany().iterator();
		while (iter.hasNext()) {
			Company c = (Company) iter.next();
			CompanyDTO cDTO = new CompanyDTO();
			BeanUtils.copyProperties(c, cDTO);
			demolitionDTO.getAssessmentCompany().add(cDTO);
		}
		// 设置拆迁公司信息
		demolitionDTO.setDemolitionCompany(new ArrayList<CompanyDTO>());
		iter = newDemolition.getDemolitionCompany().iterator();
		while (iter.hasNext()) {
			Company c = (Company) iter.next();
			CompanyDTO cDTO = new CompanyDTO();
			BeanUtils.copyProperties(c, cDTO);
			demolitionDTO.getDemolitionCompany().add(cDTO);
		}
		// 设置资金概算总额
		demolitionDTO.setCapitalBudget(newDemolition.getCapitalBudget());
		// 设置计划拆迁量

		// Calendar cal = Calendar.getInstance();

		demolitionDTO.setPlanned(new AmountOfDemolitionDTO());
		demolitionDTO.getPlanned().setId(newDemolition.getPlanned().getId());
		demolitionDTO.getPlanned().setDwelling(
				newDemolition.getPlanned().getDwelling());
		demolitionDTO.getPlanned().setAcreageOfDwelling(
				newDemolition.getPlanned().getAcreageOfDwelling());
		demolitionDTO.getPlanned().setNonDwelling(
				newDemolition.getPlanned().getNonDwelling());
		demolitionDTO.getPlanned().setAcreageOfNonDwelling(
				newDemolition.getPlanned().getAcreageOfNonDwelling());
		demolitionDTO.getPlanned().setTheDayOfFillIn(
				newDemolition.getPlanned().getTheDayOfFillIn());
		// 设置丈量评估情况
		demolitionDTO
				.setMeasureToAssessTheSituations(new ArrayList<MeasureToAssessTheSituationDTO>());
		iter = newDemolition.getMeasureToAssessTheSituations().iterator();
		while (iter.hasNext()) {

			MeasureToAssessTheSituation mtats = (MeasureToAssessTheSituation) iter
					.next();
			MeasureToAssessTheSituationDTO mtastsDTO = new MeasureToAssessTheSituationDTO();
			BeanUtils.copyProperties(mtats, mtastsDTO);
			mtastsDTO.setTheDayOfFillIn(mtats.getTheDayOfFillIn());
			demolitionDTO.getMeasureToAssessTheSituations().add(mtastsDTO);

		}

		// 设置签约拆迁进展
		demolitionDTO.setSigneds(new ArrayList<DemolitionProgressDTO>());
		iter = newDemolition.getSigneds().iterator();
		while (iter.hasNext()) {
			DemolitionProgress dp = (DemolitionProgress) iter.next();
			DemolitionProgressDTO dpDTO = new DemolitionProgressDTO();
			dpDTO.setId(dp.getId());
			dpDTO.setTheFirstDayOfFormallySigned(dp
					.getTheFirstDayOfFormallySigned());
			dpDTO.setAcceptance(dp.getAcceptance());
			dpDTO.setContracted(new ArrayList<AmountOfDemolitionDTO>());
			dpDTO.setDismantled(new ArrayList<AmountOfDemolitionDTO>());

			// 设置已签约的情况
			Iterator iter1 = dp.getContracted().iterator();
			while (iter1.hasNext()) {
				AmountOfDemolitionDTO aodDTO = new AmountOfDemolitionDTO();
				AmountOfDemolition aod = (AmountOfDemolition) iter1.next();
				BeanUtils.copyProperties(aod, aodDTO);
				dpDTO.getContracted().add(aodDTO);
			}
			// 设置已拆除的情况
			iter1 = dp.getDismantled().iterator();
			while (iter1.hasNext()) {
				AmountOfDemolitionDTO aodDTO = new AmountOfDemolitionDTO();
				AmountOfDemolition aod = (AmountOfDemolition) iter1.next();
				BeanUtils.copyProperties(aod, aodDTO);
				dpDTO.getDismantled().add(aodDTO);
			}
			dpDTO.setTheDayOfFillIn(dp.getTheDayOfFillIn());
			demolitionDTO.getSigneds().add(dpDTO);

		}

		// 设置实际拆迁情况
		demolitionDTO.setActuals(new ArrayList<AmountOfDemolitionDTO>());
		iter = newDemolition.getActuals().iterator();
		while (iter.hasNext()) {
			AmountOfDemolition aod = (AmountOfDemolition) iter.next();
			AmountOfDemolitionDTO aodDTO = new AmountOfDemolitionDTO();
			BeanUtils.copyProperties(aod, aodDTO);
			aodDTO.setTheDayOfFillIn(aod.getTheDayOfFillIn());
			demolitionDTO.getActuals().add(aodDTO);
		}
		// 设置安置情况
		demolitionDTO
				.setResettlments(new ArrayList<ResettlementSituationDTO>());
		iter = newDemolition.getResettlments().iterator();
		while (iter.hasNext()) {
			ResettlementSituation rs = (ResettlementSituation) iter.next();
			ResettlementSituationDTO rsDTO = new ResettlementSituationDTO();
			rsDTO.setId(rs.getId());
			rsDTO.setHouseHoldsOfMonetaryResettlement(rs
					.getHouseHoldsOfMonetaryResettlement());
			rsDTO.setPriceOfMonetaryResettlement(rs
					.getPriceOfMonetaryResettlement());
			rsDTO.setHouseHoldsOfMaterialResettlement(rs
					.getHouseHoldsOfMaterialResettlement());
			rsDTO.setPloidyOfMaterialResettlement(rs
					.getPloidyOfMaterialResettlement());
			rsDTO.setAcreageOfMaterialResettlement(rs
					.getAcreageOfMaterialResettlement());

			rsDTO.setResettlementDetails(new ArrayList<ResettlementDetailDTO>());
			Iterator iter1 = rs.getResettlementDetails().iterator();
			while (iter1.hasNext()) {
				ResettlementDetail rd = (ResettlementDetail) iter1.next();
				ResettlementDetailDTO rdDTO = new ResettlementDetailDTO();
				BeanUtils.copyProperties(rd, rdDTO);
				rsDTO.getResettlementDetails().add(rdDTO);
			}
			rsDTO.setTheDayOfFillIn(rs.getTheDayOfFillIn());
			demolitionDTO.getResettlments().add(rsDTO);
		}

		// 设置备注
		demolitionDTO.setRemark(newDemolition.getRemark());

		// 设置填表日期
		demolitionDTO.setTheDayOfFillIn(newDemolition.getTheDayOfFillIn());
		// 设置填表人

		PersonOfFillIn pofi = newDemolition.getThePersonOfFillIn();
		PersonOfFillInDTO pofiDTO = new PersonOfFillInDTO();
		BeanUtils.copyProperties(pofi, pofiDTO);
		pofiDTO.setDemolitions(null);

		demolitionDTO.setThePersonOfFillIn(pofiDTO);
		return demolitionDTO;
	}

	@RequestMapping(value = "getDemolitions", method = RequestMethod.POST)
	public @ResponseBody
	DataTableReturnObject getDemolitions(@RequestBody JSONParam[] params) {

		// System.out.println(order);
		Map<String, Object> rets = new ConcurrentHashMap<String, Object>();

		// convertToMap定义于父类，将参数数组中的所有元素加入一个HashMap
		HashMap<String, Object> paramMap = JSONUtil.convertToMap(params);
		// String principal =
		// SecurityUtils.getSubject().getPrincipal().toString();
		// paramMap.put("thePersonOfFillIn.name#EQ", principal);

		int sEcho = Integer.valueOf(paramMap.get("sEcho").toString());
		// String customerName = paramMap.get("customerName");
		int start = Integer.parseInt(paramMap.get("iDisplayStart").toString());
		int length = Integer
				.parseInt(paramMap.get("iDisplayLength").toString());

		// customerService.search返回的第一个元素是满足查询条件的记录总数，后面的是
		// 页面当前页需要显示的记录数据

		String iSortingCols = paramMap.get("iSortingCols").toString();

		int sortingCols = Integer.parseInt(iSortingCols);
		Sort.Order[] orders = new Sort.Order[sortingCols];
		for (int i = 0; i < sortingCols; i++) {
			Object sortCol = paramMap.get("iSortCol_" + String.valueOf(i));
			String sortName = paramMap.get("mDataProp_" + sortCol.toString())
					.toString();
			String sortDirection = paramMap
					.get("sSortDir_" + String.valueOf(i)).toString();
			orders[i] = new Sort.Order(
					"asc".equals(sortDirection) ? Sort.Direction.ASC
							: Sort.Direction.DESC, sortName);

		}

		PageRequest pageRequest = new PageRequest(start / length, length,
				new Sort(orders));
		// new PageRequest(1,15,new Sort() );
		Page<Demolition> demolitions = demolitionService.search(paramMap,
				pageRequest);

		// 将查询结果转换为一维数组
		Object[] data = new Object[demolitions.getNumberOfElements()];
		Iterator iter = demolitions.iterator();
		int i = 0;
		while (iter.hasNext()) {
			Demolition demolition = (Demolition) iter.next();
			DemolitionDTO demolitionDTO = transfer(demolition);
			/*
			 * PersonOfFillIn pofi = demolition.getThePersonOfFillIn();
			 * PersonOfFillInDTO pofiDTO = new PersonOfFillInDTO(); if (pofi !=
			 * null) { BeanUtils.copyProperties(pofi, pofiDTO);
			 * demolition.setThePersonOfFillIn(null); }
			 * 
			 * BeanUtils.copyProperties(demolition, demolitionDTO);
			 */
			data[i++] = demolitionDTO;
		}

		return new DataTableReturnObject(demolitions.getTotalElements(),
				demolitions.getTotalElements(), sEcho, data);
	}

	@RequestMapping(value = "append/{id}", method = RequestMethod.GET)
	public String appendForm(@PathVariable("id") Long id, Model model) {
		DemolitionDTO demolitionDTO = transfer(demolitionService.findById(id));

		// 丈量评估情况
		MeasureToAssessTheSituationDTO mtatsDTO = new MeasureToAssessTheSituationDTO();
		Calendar cal = Calendar.getInstance();
		mtatsDTO.setTheFirstDayOfAssessment(cal.getTime());
		demolitionDTO.getMeasureToAssessTheSituations().clear();
		demolitionDTO.getMeasureToAssessTheSituations().add(mtatsDTO);

		// 签约拆除进展
		DemolitionProgressDTO dpDTO = new DemolitionProgressDTO();
		AmountOfDemolitionDTO amountOfDemolitionDTO = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO.setTender("一标");

		AmountOfDemolitionDTO amountOfDemolitionDTO1 = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO1.setTender("二标");
		dpDTO.getContracted().add(amountOfDemolitionDTO);
		dpDTO.getContracted().add(amountOfDemolitionDTO1);

		AmountOfDemolitionDTO amountOfDemolitionDTO2 = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO2.setTender("一标");
		AmountOfDemolitionDTO amountOfDemolitionDTO3 = new AmountOfDemolitionDTO();
		amountOfDemolitionDTO3.setTender("二标");

		dpDTO.getDismantled().add(amountOfDemolitionDTO2);
		dpDTO.getDismantled().add(amountOfDemolitionDTO3);
		demolitionDTO.getSigneds().clear();
		demolitionDTO.getSigneds().add(dpDTO);

		// 实际拆迁情况
		AmountOfDemolitionDTO actualAmountOfDemolitionDTO = new AmountOfDemolitionDTO();
		demolitionDTO.getActuals().clear();
		demolitionDTO.getActuals().add(actualAmountOfDemolitionDTO);

		// 安置情况
		ResettlementSituationDTO resettlementSituationDTO = new ResettlementSituationDTO();
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		resettlementSituationDTO.getResettlementDetails().add(
				new ResettlementDetailDTO());
		demolitionDTO.getResettlments().clear();
		demolitionDTO.getResettlments().add(resettlementSituationDTO);

		model.addAttribute("demolition", demolitionDTO);
		return "demolition/appendForm";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, ? extends Object> delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) throws Exception {
		demolitionService.delete(id);

		Map<String, String> msgs = new HashMap<String, String>();

		msgs.put("msg", "删除用户成功");
		return msgs;
	}

}
