package org.crusoe.test.controller;

import org.crusoe.mvc.ajax.resource.ResourceController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/applicationcontext.xml",
		"file:src/main/webapp/WEB-INF/spring/mvc-config.xml" })
public class BaseTestController {

	@Autowired
	protected ApplicationContext applicationContext;
	protected MockHttpServletRequest request = new MockHttpServletRequest();
	protected MockHttpServletResponse response = new MockHttpServletResponse();
	protected AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
	HttpMessageConverter<?>[] messageConverters = new HttpMessageConverter[1];

	public BaseTestController() {
		super();
	}

	@Before
	public void setUpTestDataWithinTransaction() {

		request.addHeader("Accept",
				"text/html, text/plain, text/sgml, */*;q=0.01");
		request.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
		 //request.setHttpResponse(response);
		response.setOutputStreamAccessAllowed(true);
		messageConverters[0] = new MappingJacksonHttpMessageConverter();
		adapter.setApplicationContext(applicationContext);
		adapter.setMessageConverters(messageConverters);

	}

}