package org.crusoe.test.controller;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.crusoe.mvc.ajax.resource.ResourceController;
import org.crusoe.mvc.ajax.user.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/applicationcontext.xml",
		"file:src/main/webapp/WEB-INF/spring/mvc-config.xml" })
public class TestUserController {
	UserController uc;

	@Autowired
	protected ApplicationContext applicationContext;

	// @Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {

	}

	@Test
	public void testGetCreateForm() throws Exception {
		uc = (UserController) applicationContext.getBean("userController");
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.uc).build();

		mockMvc.perform(
				get("/user/createForm").accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("Create User")));

	}
}
