package org.crusoe.util.persisterce;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

public class SearchFilter {

	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			Object value = entry.getValue().toString();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}
			String[] names = StringUtils.split(entry.getKey(), "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
			}
			SearchFilter filter = new SearchFilter(names[1], Operator.valueOf(names[0]), value);
			filters.put(filter.fieldName, filter);
		}

		return filters;
	}
	
	public static Map<String, SearchFilter> parse2(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			Object value = entry.getValue().toString();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}
			//String[] names = StringUtils.split(entry.getKey(), "_");
			//if (names.length != 2) {
			//	throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
			//}
			SearchFilter filter = new SearchFilter(entry.getKey(), Operator.EQ, value);
			filters.put(filter.fieldName, filter);
		}

		return filters;
	}
	public static Map<String, SearchFilter> parse3(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String name=entry.getKey().toString();
			Object value = entry.getValue().toString();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			
			String[] names = StringUtils.split(entry.getKey(), "#");
			if (names.length == 2) {
				SearchFilter filter = new SearchFilter(names[0], Operator.valueOf(names[1]), value);
				filters.put(filter.fieldName, filter);
			}
			
		}

		return filters;
	}
}
