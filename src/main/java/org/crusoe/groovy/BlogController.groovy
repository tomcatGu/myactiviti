package org.crusoe.groovy;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

@RestController


public class BlogController {

	@RequestMapping(method = RequestMethod.GET, value = "miniblog/{s}")
	public @ResponseBody String blog(@PathVariable("s") int name) {
		String result = "Error! ";
System.out.println(name);
		return result;
	}
	
}
