package org.crusoe.myauth.service;

import org.crusoe.myauth.model.AbstractSecureObject;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface IAclSecurityUtil {
	public void addPermission(AbstractSecureObject securedObject,
			Permission permission, Class clazz);

	public void addPermission(AbstractSecureObject securedObject,
			Sid recipient, Permission permission, Class clazz);

	public void deletePermission(AbstractSecureObject securedObject,
			Sid recipient, Permission permission);

}
