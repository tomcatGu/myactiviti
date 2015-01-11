package org.crusoe.mvc.ajax.statistical;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.crusoe.dto.normativeDocFiling.NormativeDocFilingDTO;
import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFilingStatus;
import org.crusoe.service.OrganizationService;
import org.crusoe.service.workflow.normativeDocFiling.NormativeDocFilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/normativeStatistical")
public class NormativeDocFilingStatisticalController {
	@Autowired
	private NormativeDocFilingService ndfService;
	@Autowired
	private OrganizationService oService;

	@RequestMapping(value = "analyseByOrganization", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> statisticalByOrganization(
			@RequestParam("startTime") final String startTime,
			@RequestParam("endTime") final String endTime) {
		HashMap<String, Object> rets = new HashMap<String, Object>();
		SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");

		List<NormativeDocFiling> ndfs = Lists.newArrayList();
		try {
			ndfs = ndfService.findByCreateOn(dateformat.parse(startTime),
					dateformat.parse(endTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator iter = ndfs.iterator();
		HashMap<Long, Object> result = new HashMap<Long, Object>();
		while (iter.hasNext()) {
			HashMap<String, Object> statisticalResult;
			NormativeDocFiling ndf = (NormativeDocFiling) iter.next();
			if (result.containsKey(ndf.getOrganizationId())) {
				statisticalResult = (HashMap<String, Object>) result.get(ndf
						.getOrganizationId());

			} else {
				statisticalResult = new HashMap<String, Object>();
				result.put(ndf.getOrganizationId(), statisticalResult);
				statisticalResult.put("id", ndf.getOrganizationId());
				statisticalResult.put("organizationName",
						oService.findById(ndf.getOrganizationId()).getName());

			}

			Long temp = (Long) statisticalResult.get("total");
			if (temp == null) {
				statisticalResult.put("total", 1L);

			} else {

				statisticalResult.put("total", temp + 1);
			}

			if (NormativeDocFilingStatus.ACCEPT.name().equals(ndf.getStatus())) {
				temp = (Long) statisticalResult.get("approve");
				if (temp == null) {
					statisticalResult.put("approve", 1L);
				} else {
					statisticalResult.put("approve", temp + 1);
				}

			}
			Calendar releaseDate = Calendar.getInstance();
			releaseDate.setTime(ndf.getReleaseDate());
			releaseDate.add(Calendar.DAY_OF_YEAR, 30);
			if (ndf.getCreateOn().before(releaseDate.getTime())) {// 发布日期在备案日期前30天之内
				temp = (Long) statisticalResult.get("inTime");
				if (temp == null) {
					statisticalResult.put("inTime", 1L);
				} else {
					statisticalResult.put("inTime", temp + 1);
				}

			}

			if (NormativeDocFilingStatus.REFUSE.name().equals(ndf.getStatus())) {
				temp = (Long) statisticalResult.get("refuse");
				if (temp == null) {
					statisticalResult.put("refuse", 1L);
				} else {
					statisticalResult.put("refuse", temp + 1);
				}

			}

		}
		rets.put("err", false);
		rets.put("statisticalResult", result);
		return rets;
	}

	@RequestMapping(value = "listNormativeDocFiling", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> listNormativeDocFiling(
			@RequestParam("fileName") String fileName,
			@RequestParam("startTime") final String startTime,
			@RequestParam("endTime") final String endTime,
			@RequestParam(value = "organizationName", defaultValue = "") String organizationName,
			@RequestParam("status") String status,
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		ArrayList<NormativeDocFilingDTO> ndfDTOs = Lists.newArrayList();

		Sort sortRequest = "desc".equals(order.toLowerCase()) ? new Sort(
				Direction.DESC, new String[] { sort }) : new Sort(
				Direction.ASC, new String[] { sort });
		PageRequest pageRequest = new PageRequest(start / size, size,
				sortRequest);

		SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		Page<NormativeDocFiling> ndfs = null;
		Long organizationId = null;
		Organization o = oService.findbyName(organizationName);
		if (o == null) {
			organizationId = -1L;

		} else {
			organizationId = o.getId();

		}
		if (startTime.isEmpty() || endTime.isEmpty()) {
			Date now = new Date();
			ndfs = ndfService.findByTitleAndCreateTimeAndOrganizationAndStatus(
					fileName, now, now, organizationId, status, pageRequest);
		} else {

			try {
				ndfs = ndfService
						.findByTitleAndCreateTimeAndOrganizationAndStatus(
								fileName, dateformat.parse(startTime),
								dateformat.parse(endTime), organizationId,
								status, pageRequest);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Iterator iter = ndfs.iterator();
		while (iter.hasNext()) {
			NormativeDocFiling ndf = (NormativeDocFiling) iter.next();
			NormativeDocFilingDTO ndfDTO = new NormativeDocFilingDTO();
			ndfDTO.setId(ndf.getId());
			ndfDTO.setFileName(ndf.getFileName());
			ndfDTO.setFileProperty(ndf.getFileProperty());
			ndfDTO.setContentClassification(ndf.getContentClassification());
			ndfDTO.setIsOpen(ndf.getIsOpen());
			ndfDTO.setMessageNumber(ndf.getMessageNumber());
			ndfDTO.setImplementationDate(ndf.getImplementationDate());
			ndfDTO.setCreateOn(ndf.getCreateOn());
			ndfDTO.setReleaseDate(ndf.getReleaseDate());
			ndfDTO.setUsername(ndf.getUsername());
			ndfDTO.setOrganizationId(ndf.getOrganizationId());
			// ndfDTO.seto
			ndfDTO.setOrderNumber(ndf.getOrderNumber());
			switch (ndf.getStatus()) {
			case ACCEPT:
				ndfDTO.setStatus("准予备案");
				break;
			case REFUSE:
				ndfDTO.setStatus("不予备案");
				break;
			case PENDING:
				ndfDTO.setStatus("待审核");
				break;
			case REVISE:
				ndfDTO.setStatus("待修改");
				break;
			}
			ndfDTOs.add(ndfDTO);

		}

		Map<String, Object> rets = new ConcurrentHashMap<String, Object>();
		rets.put("count", ndfs.getTotalElements());
		rets.put("start", start);
		rets.put("size", size);
		rets.put("records", ndfDTOs);
		return rets;
	}
}
