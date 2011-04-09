package org.crusoe.myauth.filter;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service("myAccessDecisionManager")
public class MyAccessDecisionManager implements AccessDecisionManager {


	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttribute)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// TODO Auto-generated method stub

		Iterator<ConfigAttribute> iter = configAttribute.iterator();
		while (iter.hasNext()) {
			ConfigAttribute ca = (ConfigAttribute) iter.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) {

					return;

				}

			}
		}

		throw new AccessDeniedException("no this rights.");

	}


	public boolean supports(ConfigAttribute arg0) {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
