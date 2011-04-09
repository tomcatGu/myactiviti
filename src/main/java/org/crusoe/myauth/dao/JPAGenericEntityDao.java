package org.crusoe.myauth.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.crusoe.myauth.util.Page;
import org.hibernate.LockMode;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class JPAGenericEntityDao<T, PK extends Serializable> extends
		JpaDaoSupport implements IBaseDao<T, PK> {

	protected Class<T> entityClass;

	public JPAGenericEntityDao(Class<T> type) {
		this.entityClass = type;
	}

	protected Class getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			logger.debug("T class = " + entityClass.getName());
		}
		return entityClass;
	}

	public boolean contains(T t) throws DataAccessException {
		// TODO Auto-generated method stub
		return this.getJpaTemplate().contains(t);
	}

	public void delete(PK id) throws DataAccessException {
		// TODO Auto-generated method stub
		// 浣跨敤Query鍒犻櫎瀵硅薄
		this.getJpaTemplate().remove(
				this.getJpaTemplate().find(getEntityClass(), id));

	}

	public void deleteAll(Collection<T> entities) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	public List<T> find(String queryString, Object value)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return this.getJpaTemplate().find(queryString, value);
	}

	public List<T> find(String queryString, Object[] values)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<T> find = (List<T>) this.getJpaTemplate()
				.find(queryString, values);
		return find;
	}

	public List<T> find(String queryString) throws DataAccessException {
		// TODO Auto-generated method stub
		List<T> find = (List<T>) this.getJpaTemplate().find(queryString);
		return find;
	}

	/**
	 * @param <T>
	 * @param countHql
	 *            璁＄畻鏁版嵁鎬绘潯鏁扮殑hql璇彞(灏辨槸甯ount(*)鐨刪ql)
	 * @param hql
	 * @param startNo
	 *            鍒嗛〉浠庡摢涓�潯鏁版嵁寮�
	 * @param pageSize
	 *            椤甸潰瀹归噺
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> pagedQueryByStartNo(String countHql, final String hql,
			final int startNo, final int pageSize, Object... values)
			throws DataAccessException {
		// Count鏌ヨ
		List<T> countlist = this.getJpaTemplate().find(countHql);
		long totalCount = (Long) countlist.get(0);
		if (totalCount < 1)
			return new Page();

		int startIndex = startNo;
		List<T> list = // this.getJpaTemplate().find(hql, values);

		getJpaTemplate().executeFind(new JpaCallback() {

			public Object doInJpa(EntityManager arg0)
					throws PersistenceException {
				Query query = (Query) arg0.createQuery(hql);
				// Iterator<Entry<String, Object>> iterator =
				// parameters.entrySet().iterator();
				// while (iterator.hasNext()) {
				// Entry<String, Object> entry = iterator.next();
				// if (entry.getValue() != null) {
				// query.setParameter(clearSymbol(entry.getKey()),
				// entry.getValue());
				// }
				// }
				return query.setFirstResult(startNo).setMaxResults(pageSize)
						.getResultList();
			}
		});

		return new Page<T>(startIndex, totalCount, pageSize, list);
	}

	public T get(Object id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> list() throws DataAccessException {
		// TODO Auto-generated method stub

		return this.getJpaTemplate().find(
				" from " + this.getEntityClass().getSimpleName());

	}

	public T load(Object id) throws DataAccessException {
		// TODO Auto-generated method stub
		return (T) this.getJpaTemplate().find(this.getEntityClass(), id);
	}

	public void refresh(T t, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	public void refresh(T t) throws DataAccessException {
		// TODO Auto-generated method stub
		this.getJpaTemplate().refresh(t);
	}

	public void save(T t) throws DataAccessException {
		// TODO Auto-generated method stub
		this.getJpaTemplate().persist(t);
		this.getJpaTemplate().flush();
	}

	public void saveOrUpdate(T t) throws DataAccessException {
		// TODO Auto-generated method stub
		this.getJpaTemplate().merge(t);
	}

	public void update(T t, LockMode lockMode) throws DataAccessException {
		// TODO Auto-generated method stub
		this.getJpaTemplate().merge(t);
	}

	public void update(T t) throws DataAccessException {
		// TODO Auto-generated method stub
		this.getJpaTemplate().merge(t);
	}

	public List<T> findByNamedQuery(String queryName, Object... values)
			throws DataAccessException {
		// TODO Auto-generated method stub

		return (List<T>) this.getJpaTemplate().findByNamedQuery(queryName,
				values);

	}


	public T get(PK id) throws DataAccessException {
		// TODO Auto-generated method stub
		return this.getJpaTemplate().find(entityClass, id);
	}

	public T load(PK id) throws DataAccessException {
		// TODO Auto-generated method stub
		return this.getJpaTemplate().find(entityClass, id);
	}


	public void saveOrUpdateAll(Collection<T> entities)
			throws DataAccessException {
		// TODO Auto-generated method stub

	}

}
