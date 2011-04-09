package org.crusoe.myauth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;

import org.crusoe.myauth.model.ResourceInfo;
import org.crusoe.myauth.model.RoleInfo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mySecurityMetadataSource")

public class InvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	private EntityManagerFactory entityManagerFactory;

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();;

	private boolean useAntPath = true;

	private boolean lowercaseComparisons = true;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub

		/*
		 * FilterInvocation filterInvocation = (FilterInvocation) object; String
		 * requestURI = filterInvocation.getRequestUrl(); Map<String, String>
		 * urlAuthorities = this .getUrlAuthorities(filterInvocation);
		 * 
		 * String grantedAuthorities = null; for (Iterator<Map.Entry<String,
		 * String>> iter = urlAuthorities .entrySet().iterator();
		 * iter.hasNext();) { Map.Entry<String, String> entry = iter.next();
		 * String url = entry.getKey(); if (urlMatcher.pathMatchesUrl(url,
		 * requestURI)) { grantedAuthorities = entry.getValue(); break; }
		 * 
		 * }
		 */

		String requestURI = ((FilterInvocation) object).getRequestUrl();
		System.out.println("request is" + requestURI);

		Iterator iter = entityManagerFactory.createEntityManager()
				.createQuery("select r from ResourceInfo r").getResultList()
				.iterator();
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		while (iter.hasNext()) {
			ResourceInfo ri = (ResourceInfo) iter.next();

			if (urlMatcher.pathMatchesUrl(ri.getValue(), requestURI)) {

				Iterator roles = ri.getRoles().iterator();

				while (roles.hasNext()) {
					RoleInfo roleInfo = (RoleInfo) roles.next();
					ConfigAttribute ca = new SecurityConfig(roleInfo.getValue());
					atts.add(ca);
				}
			}

		}

		return atts;
		/*
		 * if (grantedAuthorities != null) { ConfigAttributeEditor
		 * configAttrEditor = new ConfigAttributeEditor();
		 * configAttrEditor.setAsText(grantedAuthorities); return
		 * (ConfigAttributeDefinition) configAttrEditor.getValue(); }
		 * 
		 * return null;
		 */
	}

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 
	 * @param filterInvocation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getUrlAuthorities(
			FilterInvocation filterInvocation) {
		ServletContext servletContext = filterInvocation.getHttpRequest()
				.getSession().getServletContext();
		return (Map<String, String>) servletContext
				.getAttribute("urlAuthorities");
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	@Autowired
	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

}
