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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.crusoe.entity.workflow.governmentInformationDisclosure.StatisticalSheet;
import org.crusoe.repository.jpa.workflow.governmentInformationDisclosure.StatisticalSheetDao;
import org.crusoe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

	public StatisticalSheet total(String annual) {
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
		StatisticalSheet srcSheet = null;
		while (iter.hasNext()) {
			StatisticalSheet dstSheet = (StatisticalSheet) iter.next();
			if (srcSheet == null) {
				srcSheet = dstSheet;
				continue;
			}
			try {

				Document dstDocument = db.parse(new InputSource(
						new ByteArrayInputStream(dstSheet.getStatisticalData()
								.getBytes("utf-8"))));
				Document srcDocument = db.parse(new InputSource(
						new ByteArrayInputStream(srcSheet.getStatisticalData()
								.getBytes("utf-8"))));
				XPathFactory xFactory = XPathFactory.newInstance();
				XPath xpath = xFactory.newXPath();
				XPathExpression expr = xpath
						.compile("//spreadsheets/spreadsheet/rows/row");

				Object dstResult = expr.evaluate(dstDocument,
						XPathConstants.NODESET);
				NodeList nodes = (NodeList) dstResult;
				// System.out.println(nodes.getLength());
				for (int i = 0; i < nodes.getLength(); i++) {
					// System.out.println(nodes.item(i).getNodeValue());
					xpath.reset();
					String pathStr = "//spreadsheets/spreadsheet/rows/row["
							+ (i + 1) + "]/columns/column[3]/value";
					XPathExpression exprCol = xpath.compile(pathStr);
					Integer dstVal = -1;
					try {
						dstVal = Integer.parseInt((String) exprCol.evaluate(
								dstDocument, XPathConstants.STRING));
					} catch (NumberFormatException nfe) {
					}
					Integer srcVal = -1;
					try {
						srcVal = Integer.parseInt((String) exprCol.evaluate(
								srcDocument, XPathConstants.STRING));
					} catch (NumberFormatException nfe) {
					}

					if (dstVal != -1 || srcVal != -1) {
						xpath.reset();
						XPathExpression exprColNode = xpath
								.compile("//spreadsheets/spreadsheet/rows/row["
										+ i + 1 + "]/columns/column[3]");
						Object srcResult = exprColNode.evaluate(srcDocument,
								XPathConstants.NODESET);
						NodeList srcNodes = (NodeList) srcResult;
						System.out.println("srcnode=" + srcNodes.getLength());
						if (srcNodes.item(1).hasAttributes()) {// there was not
																// a celltype
							// node if only 3 elements
							Element cellTypeNode = srcDocument
									.createElement("cellType");
							cellTypeNode.setNodeValue("number");
							srcNodes.item(1).appendChild(cellTypeNode);

						}
						xpath.reset();
						exprColNode = xpath
								.compile("//spreadsheets/spreadsheet/rows/row["
										+ i + 1 + "]/columns/column[3]/value");
						srcResult = exprColNode.evaluate(srcDocument,
								XPathConstants.NODESET);
						srcNodes = (NodeList) srcResult;
						System.out.println("srcnode=" + srcNodes.getLength());
						srcNodes.item(1).setNodeValue(
								String.valueOf(dstVal.intValue()
										+ srcVal.intValue()));
					}

				}
				// NodeList sheetNode = document.getChildNodes();

			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return srcSheet;

	}
}
