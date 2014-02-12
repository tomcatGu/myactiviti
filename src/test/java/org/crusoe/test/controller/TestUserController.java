package org.crusoe.test.controller;

import static org.junit.Assert.*;

import org.crusoe.dto.UserDTO;
import org.crusoe.mvc.ajax.resource.ResourceController;
import org.crusoe.mvc.ajax.user.UserController;
import org.crusoe.test.util.IntegrationTestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/applicationcontext.xml",
		"file:src/main/webapp/WEB-INF/spring/mvc-config.xml" })
public class TestUserController {
	UserController uc;

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	private WebApplicationContext wac;
	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webApplicationContextSetup(wac).build();

	}

	@Test
	@Transactional
	public void testGetCreateForm() throws Exception {

		mockMvc.perform(get("/user/create").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk());

		mockMvc.perform(get("/user/create").accept(MediaType.TEXT_PLAIN))
				.andExpect(content().string(containsString("loginName")));
/*		UserDTO formObject=new UserDTO();
		mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(IntegrationTestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr("user", formObject)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("todo/add"));*/

	}
}
