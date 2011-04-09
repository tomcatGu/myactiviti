package org.crusoe.myauth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import org.crusoe.myauth.dao.IBaseDao;
import org.crusoe.myauth.model.ResourceInfo;
import org.crusoe.myauth.model.RoleInfo;
import org.crusoe.myauth.model.UserInfo;
import org.crusoe.myauth.util.Page;
import org.crusoe.myauth.util.PageRequestParam;
import org.crusoe.myauth.vo.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("resourceService")
@Transactional
public class ResourceService {

	@Resource(name = "resourceDao")
	private IBaseDao<ResourceInfo, Long> resourceDao;

	@Resource(name = "roleDao")
	private IBaseDao<RoleInfo, String> roleDao;

	@Transactional
	public void addResource(ResourceInfo resourceInfo) {

		resourceDao.save(resourceInfo);

	}

	@Transactional
	@Secured({"ROLE_USER","AFTER_ACL_COLLECTION_READ"})
	public Page<ResourceInfo> getResources(PageRequestParam page) {

		// List<ResourceInfo> resources = new ArrayList<ResourceInfo>();
		Page<ResourceInfo> resources = resourceDao.pagedQueryByStartNo(
				"select count(*) from ResourceInfo r ",
				"from ResourceInfo r order by " + page.getSort() + " "
						+ page.getOrder(), page.getPage() * page.getRows(),
				page.getRows());

		return resources;
	}

	@Transactional
	public ResourceInfo load(long id) {
		// TODO Auto-generated method stub
		return resourceDao.load(Long.valueOf(id));
	}

	@Transactional
	public void remove(long id) {
		// TODO Auto-generated method stub

		resourceDao.delete(Long.valueOf(id));
	}

	@Transactional
	public void update(ResourceInfo resourceInfo) {
		// TODO Auto-generated method stub
		resourceDao.update(resourceInfo);
	}
}
