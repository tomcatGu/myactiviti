package org.crusoe.myauth.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.crusoe.myauth.util.Page;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface IBaseDao<T, PK extends Serializable> {
	public T load(PK id) throws DataAccessException;

	public T get(PK id) throws DataAccessException;

	public boolean contains(T t) throws DataAccessException;

	public void refresh(T t, LockMode lockMode) throws DataAccessException;

	public void refresh(T t) throws DataAccessException;

	public void save(T t) throws DataAccessException;

	public void saveOrUpdate(T t) throws DataAccessException;

	public void saveOrUpdateAll(Collection<T> entities)
			throws DataAccessException;

	public void update(T t, LockMode lockMode) throws DataAccessException;

	public void update(T t) throws DataAccessException;

	// public void delete(T t, LockMode lockMode) throws DataAccessException;

	public void delete(PK t) throws DataAccessException;

	public void deleteAll(Collection<T> entities) throws DataAccessException;

	public List<T> find(String queryString, Object value)
			throws DataAccessException;

	public List<T> find(String queryString, Object[] values)
			throws DataAccessException;

	public List<T> find(String queryString) throws DataAccessException;

	public List<T> list() throws DataAccessException;

	public <T> Page<T> pagedQueryByStartNo(String countHql, String hql,
			int startNo, int pageSize, Object... values) throws DataAccessException;

	public List<T> findByNamedQuery(String queryName, Object... values)
			throws DataAccessException;
}
