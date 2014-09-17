package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.crusoe.entity.workflow.governmentInformationDisclosure.StatisticalSheet;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.StatisticalSheetDao;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	public StatisticalSheet save(String createUserName, String annual,
			String statisticalData) {
		StatisticalSheet sheet = new StatisticalSheet();
		sheet.setLoginName(createUserName);
		sheet.setAnnual(annual);
		sheet.setFillingDate(new Date());
		sheet.setStatisticalData(statisticalData);
		return statisticalSheetDao.save(sheet);
	}

	public void total(String annual) {
		List<StatisticalSheet> sheets = statisticalSheetDao
				.findByAnnual(annual);
		Iterator iter = sheets.iterator();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (iter.hasNext()) {
			StatisticalSheet sheet = (StatisticalSheet) iter.next();
			try {
				Document document = db.parse(new InputSource(
						new ByteArrayInputStream(sheet.getStatisticalData()
								.getBytes("utf-8"))));
				NodeList sheetNode = document.getChildNodes();

			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
