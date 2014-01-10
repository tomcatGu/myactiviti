package org.crusoe.util;

import java.util.HashMap;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class DataTablesUtil {
	public static PageRequest extractPageRequestFromDataTablesParam(
			HashMap<String, Object> paramMap) {
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
		return pageRequest;
	}

}
