package org.crusoe.mvc.ajax.statistical;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.crusoe.dto.normativeDocFiling.NormativeDocFilingDTO;
import org.crusoe.entity.Organization;
import org.crusoe.entity.workflow.normativeDocFiling.NormativeDocFiling;
import org.crusoe.service.OrganizationService;
import org.crusoe.service.workflow.normativeDocFiling.NormativeDocFilingService;
import org.springframework.beans.factory.annotation.Autowired;
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
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

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

			if ("已备案".equals(ndf.getStatus())) {
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
			if (ndf.getCreateOn().before(releaseDate.getTime())) {
				temp = (Long) statisticalResult.get("inTime");
				if (temp == null) {
					statisticalResult.put("inTime", 1L);
				} else {
					statisticalResult.put("inTime", temp + 1);
				}

			}

			if ("不予备案".equals(ndf.getStatus())) {
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
	List<NormativeDocFilingDTO> listNormativeDocFiling(
			@RequestParam("title") String title,
			@RequestParam("startTime") final String startTime,
			@RequestParam("endTime") final String endTime,
			@RequestParam("organizationName") String organizationName,
			@RequestParam("status") String status) {
		ArrayList<NormativeDocFilingDTO> ndfDTOs = Lists.newArrayList();

		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		List<NormativeDocFiling> ndfs = null;
		Long organizationId = null;
		Organization o = oService.findbyName(organizationName);
		if (o == null) {
			organizationId = -1L;

		} else {
			organizationId = o.getId();

		}
		try {
			ndfs = ndfService.findByTitleAndCreateTimeAndOrganizationAndStatus(title,
					dateformat.parse(startTime), dateformat.parse(endTime),
					organizationId,status);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator iter = ndfs.iterator();
		while (iter.hasNext()) {
			NormativeDocFiling ndf = (NormativeDocFiling) iter.next();
			NormativeDocFilingDTO ndfDTO = new NormativeDocFilingDTO();
			try {
				PropertyUtils.copyProperties(ndfDTO, ndf);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ndfDTOs.add(ndfDTO);

		}

		return ndfDTOs;
	}
}
