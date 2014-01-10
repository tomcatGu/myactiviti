package org.crusoe.util;

import java.util.HashMap;

import org.crusoe.web.datatables.JSONParam;

public class JSONUtil {

	public static HashMap<String, Object> convertToMap(JSONParam[] params) {
		// TODO Auto-generated method stub
		
		HashMap<String,Object> paramMap=new HashMap<String,Object>();
		for(int i=0;i<params.length;i++){
			
			paramMap.put(params[i].getName(), params[i].getValue());
			
			
			
		}
		
		
		return paramMap;
	}

}
