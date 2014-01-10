package org.crusoe.test.controller;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.crusoe.mvc.ajax.resource.ResourceController;
import org.junit.After;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.annotation.Timed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Transactional
public class TestResourceController extends BaseTestController {
	ResourceController rc;

	@Override
	public void setUpTestDataWithinTransaction() {
		// TODO Auto-generated method stub
		super.setUpTestDataWithinTransaction();
		rc = (ResourceController) applicationContext
				.getBean("resourceController");
	}

	@Rollback(true)
	@Test
	@Timed(millis = 3500)
	public void testCreate() throws Exception {
		request.setRequestURI("/resource");

		request.setMethod("POST");
		request.setContent(new String(
				"{\"name\":\"/test\",\"type\":\"URI\",\"value\":\"/test\"}")
				.getBytes());

		adapter.handle(request, response, rc);

		Assert.assertEquals(200, response.getStatus());
		// assertTrue(mv.containsKey("id"));
	}

	@Test
	@Repeat(1)
	@Rollback(true)
	public void testGetCreateForm() throws Exception {
		request.setRequestURI("/resource/create");
		request.setMethod("GET");

		ModelAndView mv = adapter.handle(request, response, rc);
		assertEquals(200, response.getStatus());
		assertEquals("resource/createForm", mv.getViewName());
		String cs = (response.getContentAsString());
		assertEquals("application/json", cs);

		// mv.getView().;
	}

	@Test
	@Rollback(true)
	public void testGetIndexForm() throws Exception {
		request.setRequestURI("/resource/index");
		request.setMethod("GET");
		ModelAndView mv = adapter.handle(request, response, rc);
		assertEquals("resource/index", mv.getViewName());
		String cs = (response.getContentAsString());
	}

	@Test
	public void testDelete() throws Exception {
		request.setRequestURI("/resource/24");
		request.setMethod("DELETE");

	}

	@Test
	public void testUpdate() throws Exception {
		request.setRequestURI("/resource/update/24");
		request.setMethod("PUT");
	}

	@Test
	@Rollback(true)
	public void testGet() throws Exception {
		request.setRequestURI("/resource/1");
		request.setMethod("GET");
		adapter.handle(request, response, rc);

		System.out.println(response.getContentAsString());
	}

	@After
	public void tearDownWithinTransaction() {
		// execute "tear down" logic within the transaction
	}

}
