package org.crusoe.service.workflow.governmentInformationDisclosure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
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

	public StatisticalSheet save(String createUserName, String annual, String statisticalData, String status) {
		StatisticalSheet sheet = new StatisticalSheet();
		sheet.setLoginName(createUserName);
		sheet.setAnnual(annual);
		sheet.setStatus(status);
		sheet.setFillingDate(new Date());
		sheet.setStatisticalData(statisticalData);
		return statisticalSheetDao.save(sheet);
	}

	public boolean isExists(String annual, String createUserName) {
		return !statisticalSheetDao.findByAnnualAndLoginName(annual, createUserName).isEmpty();

	}

	public StatisticalSheet update(StatisticalSheet ss, String createUserName, String annual, String statisticalData,
			String status) {
		StatisticalSheet sheet = new StatisticalSheet();
		sheet.setId(ss.getId());
		sheet.setLoginName(createUserName);
		sheet.setAnnual(annual);
		sheet.setStatus(status);
		sheet.setFillingDate(new Date());
		sheet.setStatisticalData(statisticalData);
		return statisticalSheetDao.save(sheet);
	}

	public boolean isFloat(String str) {
		Pattern p = null;
		Matcher m = null;
		String floatPattern = "^([0-9]{1}[.]{0,1}[0-9]*)$";
		p = Pattern.compile(floatPattern);
		m = p.matcher(str);
		boolean b = m.matches();
		if (b)
			return true;
		else
			return false;
	}

	public StatisticalSheet total(String annual, String status) {
		String[] annualList = annual.split(",");
		List<StatisticalSheet> sheets = new ArrayList<StatisticalSheet>();
		for (int i = 0; i < annualList.length; i++) {

			sheets.addAll(statisticalSheetDao.findByAnnualAndStatus(annualList[i], status));

		}

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
		Document srcDocument = null;

		while (iter.hasNext()) {
			StatisticalSheet dstSheet = (StatisticalSheet) iter.next();
			if (srcSheet == null) {
				srcSheet = dstSheet;
				try {
					srcDocument = db.parse(
							new InputSource(new ByteArrayInputStream(srcSheet.getStatisticalData().getBytes("utf-8"))));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			try {

				Document dstDocument = db.parse(
						new InputSource(new ByteArrayInputStream(dstSheet.getStatisticalData().getBytes("utf-8"))));

				XPathFactory xFactory = XPathFactory.newInstance();
				XPath xpath = xFactory.newXPath();
				XPathExpression expr = xpath.compile("//spreadsheets/spreadsheet/rows/row");

				Object dstResult = expr.evaluate(dstDocument, XPathConstants.NODESET);
				NodeList nodes = (NodeList) dstResult;
				// System.out.println(nodes.getLength());
				for (int i = 0; i < nodes.getLength(); i++) {
					// System.out.println(nodes.item(i).getNodeValue());
					xpath.reset();
					String pathStr = "//spreadsheets/spreadsheet/rows/row[" + (i + 1) + "]/columns/column[3]/value";
					XPathExpression exprCol = xpath.compile(pathStr);
					Number dstVal = null;

					try {
						String dstValStr = (String) exprCol.evaluate(dstDocument, XPathConstants.STRING);
						if (isFloat(dstValStr))
							dstVal = Float.parseFloat(dstValStr);
						else
							dstVal = Integer.parseInt(dstValStr);
					} catch (NumberFormatException nfe) {
					}
					Number srcVal = null;
					try {
						String srcValStr = (String) exprCol.evaluate(srcDocument, XPathConstants.STRING);
						if (isFloat(srcValStr))
							srcVal = Float.parseFloat(srcValStr);
						else
							srcVal = Integer.parseInt(srcValStr);
					} catch (NumberFormatException nfe) {
					}

					if (dstVal != null || srcVal != null) {
						xpath.reset();
						XPathExpression exprColNode = xpath.compile(
								"//spreadsheets/spreadsheet/rows/row[" + (i + 1) + "]/columns/column[3]/cellType");
						XPathExpression exprColNodeParent = xpath
								.compile("//spreadsheets/spreadsheet/rows/row[" + (i + 1) + "]/columns/column[3]");
						Object srcResult = exprColNode.evaluate(srcDocument, XPathConstants.NODESET);
						NodeList srcNodes = (NodeList) srcResult;
						NodeList srcParentNodes = (NodeList) exprColNodeParent.evaluate(srcDocument,
								XPathConstants.NODESET);
						System.out.println("srcnode=" + srcNodes.getLength());
						if (srcNodes.getLength() == 0) {// there was not
														// a celltype
							// node if only 3 elements
							Element cellTypeNode = srcDocument.createElement("cellType");
							cellTypeNode.setNodeValue("number");
							srcParentNodes.item(0).appendChild(cellTypeNode);

						}
						xpath.reset();
						exprColNode = xpath.compile(
								"//spreadsheets/spreadsheet/rows/row[" + (i + 1) + "]/columns/column[3]/value");
						srcResult = exprColNode.evaluate(srcDocument, XPathConstants.NODESET);
						srcNodes = (NodeList) srcResult;

						// srcNodes.item(arg0)
						System.out.println("srcnode=" + srcNodes.getLength());
						if (dstVal == null)
							dstVal = 0;
						if (srcVal == null)
							srcVal = 0;

						Number addResult;
						if (isFloat(dstVal.toString()) || isFloat(srcVal.toString())) {
							addResult = dstVal.floatValue() + srcVal.floatValue();

						} else {
							addResult = dstVal.intValue() + srcVal.intValue();
						}

						if (srcNodes.getLength() == 0) {
							exprColNodeParent = xpath
									.compile("//spreadsheets/spreadsheet/rows/row[" + (i + 1) + "]/columns/column[3]");
							srcParentNodes = (NodeList) exprColNodeParent.evaluate(srcDocument, XPathConstants.NODESET);
							Element valueNode = srcDocument.createElement("value");
							Text text = srcDocument.createTextNode(addResult.toString());

							valueNode.appendChild(text);
							srcParentNodes.item(0).appendChild(valueNode);

						} else {
							Node node = srcNodes.item(0);
							// int total = dstVal.intValue() +
							// srcVal.intValue();
							node.getFirstChild().setNodeValue(addResult.toString());

							System.out.println(node.getNodeName() + node.getNodeValue());

						}
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

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer();

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		try {
			transformer.transform(new DOMSource(srcDocument), new StreamResult(buffer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		String s = buffer.toString();
		StatisticalSheet totalSheet = new StatisticalSheet();
		totalSheet.setStatisticalData(s);
		totalSheet.setAnnual(annual);
		totalSheet.setStatus(status);
		return totalSheet;

	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		statisticalSheetDao.delete(id);
	}

	public Iterator<StatisticalSheet> findByAnnualAndStatus(String annual, String status, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		Page<StatisticalSheet> ss = statisticalSheetDao.findByAnnualAndStatus(annual, status, pageRequest);
		if (ss != null)
			return ss.iterator();
		return null;
	}

	public Object findById(long id) {
		// TODO Auto-generated method stub
		return statisticalSheetDao.findOne(id);

	}
}
