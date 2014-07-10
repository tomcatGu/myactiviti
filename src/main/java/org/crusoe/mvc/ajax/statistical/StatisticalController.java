package org.crusoe.mvc.ajax.statistical;

import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.apache.commons.lang.StringUtils;
import org.crusoe.entity.workflow.governmentInformationDisclosure.GovernmentInformationDisclosure;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.GovernmentInformationDisclosureDao;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/statistical")
public class StatisticalController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private GovernmentInformationDisclosureDao gidDao;

	@Autowired
	protected AccountService accountService;

	@RequestMapping(value = "counter/{processDefinitionId}", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> counter(@PathVariable String processDefinitionId,
			@RequestParam("statisticalRange") String statisticalRange,
			@RequestParam("separatorChars") String separatorChars,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> rets = new HashMap<String, Object>();
		List<HistoricProcessInstance> processes = historyService
				.createHistoricProcessInstanceQuery()
				.processDefinitionId(processDefinitionId).list();
		HashMap<String, Integer> statisticalResult = new HashMap<String, Integer>();
		for (HistoricProcessInstance process : processes) {
			List<HistoricVariableInstance> variables = historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(process.getId()).list();
			for (HistoricVariableInstance variable : variables) {
				if (variable.getVariableName().equals(statisticalRange)) {
					String val = (String) variable.getValue();
					String[] keys = StringUtils.split(val, separatorChars);
					for (String key : keys) {
						if (statisticalResult.containsKey(key)) {
							statisticalResult.put(key,
									statisticalResult.get(key) + 1);
						} else {
							statisticalResult.put(key, 1);
						}

					}
				}

			}
		}
		rets.put("err", false);
		rets.put("statisticalResult", statisticalResult);
		return rets;
	}

	@RequestMapping(value = "counterGID", method = RequestMethod.GET)
	public @ResponseBody
	HashMap<String, Object> counterGovernmentInformationDisclosure(
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> rets = new HashMap<String, Object>();

		HashMap<String, Integer> statisticalResult = new HashMap<String, Integer>();
		gidDao.findAll(new Specification<GovernmentInformationDisclosure>(){

			@Override
			public Predicate toPredicate(
					Root<GovernmentInformationDisclosure> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				// TODO Auto-generated method stub
				return null;
			}});

		/*
		 * 
		 @Override
        public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotBlank(keyword)) {
                expressions.add(cb.like(root.<String>get("keyword"), "%"+keyword+"%"));           //关键字
            }
            if (StringUtils.isNotBlank(knowledge)) {
                expressions.add(cb.like(root.<String>get("knowledge"), "%"+knowledge +"%"));      //知识点
            }
            if (NumberUtils.isDigits(type)) {
                expressions.add(cb.equal(root.<String>get("type"), type));    //l类型
            }
            if(StringUtils.isNotBlank(itemBankId)) {
                expressions.add(cb.equal(root.<String>get("puuid"), itemBankId));         //父节点
            }
            if(NumberUtils.isDigits(gradeId)) {
                expressions.add(cb.equal(root.<Grade>get("grade").<Long>get("id"), NumberUtils.toLong(gradeId)));           //年级
            }
            if(NumberUtils.isDigits(subjectId)) {
                expressions.add(cb.equal(root.<Subject>get("subject").<Long>get("id"), NumberUtils.toLong(subjectId)));     //学科
            }
            expressions.add(cb.equal(root.<Long>get("deleteBy"), 0));
            return predicate;
        }

		 
		 * **
		 */
		
		
		rets.put("err", false);
		rets.put("statisticalResult", statisticalResult);
		return rets;
	}
	@RequestMapping(value = "index")
	public String getIndexForm() {
		return "statistical/index";
	}
}
