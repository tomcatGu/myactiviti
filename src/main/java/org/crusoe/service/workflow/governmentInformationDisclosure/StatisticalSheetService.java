package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.util.Date;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.crusoe.entity.workflow.governmentInformationDisclosure.StatisticalSheet;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.StatisticalSheetDao;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StatisticalSheetService {
	@Autowired
	private AccountService accountService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private StatisticalSheetDao statisticalSheetDao;

	public void save(String createUserName, String annual,
			String statisticalData) {
		StatisticalSheet sheet = new StatisticalSheet();
		sheet.setLoginName(createUserName);
		sheet.setAnnual(annual);
		sheet.setFillingDate(new Date());
		sheet.setStatisticalData(statisticalData);
		statisticalSheetDao.save(sheet);
	}
}
