package org.crusoe.myauth.service.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.crusoe.myauth.model.AbstractSecureObject;
import org.crusoe.myauth.service.IAclSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("aclSecurity")
public class AclSecurityUtilImpl implements IAclSecurityUtil {

	private static Log logger = LogFactory.getLog(IAclSecurityUtil.class);

	private MutableAclService mutableAclService;

	@Autowired
	public void setMutableAclService(MutableAclService mutableAclService) {
		this.mutableAclService = mutableAclService;
	}

	@Override
	@Transactional
	public void addPermission(AbstractSecureObject securedObject,
			Permission permission, Class clazz) {
		// TODO Auto-generated method stub
		addPermission(securedObject, new PrincipalSid(getUsername()),
				permission, clazz);

	}

	@Override
	@Transactional
	public void addPermission(AbstractSecureObject securedObject,
			Sid recipient, Permission permission, Class clazz) {
		// TODO Auto-generated method stub

		MutableAcl acl;
		ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(),
				(Serializable) securedObject.getId());

		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
		} catch (NotFoundException nfe) {
			acl = mutableAclService.createAcl(oid);
		}

		acl.insertAce(acl.getEntries().size(), permission, recipient, true);
		mutableAclService.updateAcl(acl);

		if (logger.isDebugEnabled()) {
			logger.debug("Added permission " + permission + " for Sid "
					+ recipient + " securedObject " + securedObject);
		}

	}

	@Override
	@Transactional
	public void deletePermission(AbstractSecureObject securedObject,
			Sid recipient, Permission permission) {
		// TODO Auto-generated method stub

	}

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

}
