package org.crusoe.tests.controller;

import static org.junit.Assert.*;

import java.util.Map;

import junit.framework.Assert;

import org.crusoe.mvc.ajax.resource.ResourceController;
import org.crusoe.myauth.service.IAclSecurityUtil;
import org.crusoe.myauth.service.ResourceService;
import org.crusoe.myauth.service.RoleService;
import org.crusoe.myauth.vo.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcAclService;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/app-config.xml",
		"file:src/main/webapp/WEB-INF/spring/applicationcontext.xml" })
@Transactional
public class TestResourceController {
	@Autowired
	private ApplicationContext applicationContext;
	MockHttpServletRequest request = new MockHttpServletRequest();
	MockHttpServletResponse response = new MockHttpServletResponse();
	AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
	HttpMessageConverter<?>[] messageConverters = new HttpMessageConverter[1];
	ResourceController rc;

	@Autowired
	private JdbcMutableAclService mutableAclService;

	@Before
	public void setUpTestDataWithinTransaction() {
		rc = (ResourceController) applicationContext
				.getBean("resourceController");
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");

		rc.setResourceService((ResourceService) applicationContext
				.getBean("resourceService"));
		rc.setRoleService((RoleService) applicationContext
				.getBean("roleService"));
		rc.setAclSecurity((IAclSecurityUtil) applicationContext
				.getBean("aclSecurity"));

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"jimi", "jimi", AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		messageConverters[0] = new MappingJacksonHttpMessageConverter();
		adapter.setMessageConverters(messageConverters);

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
		request.setRequestURI("/resource/add");
		request.setMethod("GET");

		ModelAndView mv = adapter.handle(request, response, rc);
		assertEquals("resource/createForm", mv.getViewName());
	}

	@Test
	@Rollback(true)
	public void testGetIndexForm() throws Exception {
		request.setRequestURI("/resource/index");
		request.setMethod("GET");
		ModelAndView mv = adapter.handle(request, response, rc);
		assertEquals("resource/index", mv.getViewName());
	}

	@Test
	public void testDelete() throws Exception {
		request.setRequestURI("/resource/24");
		request.setMethod("DELETE");

	}

	@Test
	public void testUpdate() throws Exception {
		request.setRequestURI("/resource/24/edit");
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

	@Test
	@Rollback(true)
	public void testAcl() throws Exception {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"jimi", "jimi", AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		ObjectIdentity oid = new ObjectIdentityImpl(Resource.class, 3);
		MutableAcl acl = mutableAclService.createAcl(oid);
		acl.insertAce(0, BasePermission.ADMINISTRATION, new PrincipalSid(
				authentication), true);
		acl.insertAce(1, BasePermission.DELETE, new GrantedAuthoritySid(
				"ROLE_ADMIN"), true);
		acl.insertAce(2, BasePermission.READ, new GrantedAuthoritySid(
				"ROLE_USER"), true);
		mutableAclService.updateAcl(acl);
	}

	@After
	public void tearDownWithinTransaction() {
		// execute "tear down" logic within the transaction
	}

}
