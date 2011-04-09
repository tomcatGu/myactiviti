package org.crusoe.myauth.dao;

import java.util.Iterator;
import java.util.List;

import org.crusoe.myauth.util.Page;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class GenericDao extends HibernateDaoSupport {

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 * 
	 * @param <T>
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Object... values) {
		return this.getHibernateTemplate().find(hql, values);

	}

	/**
	 * 根据命名参数查询
	 * 
	 * @param <T>
	 * @param hql
	 *            带有命名参数的hql语句
	 * @param paramNames
	 *            命名参数的名字
	 * @param values
	 *            命名参数的值<br>
	 *            <b>例如:</b><br>
	 *            findByNamedParams("from Test where t1 = :t",new
	 *            String[]{"t"},tValue);
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedParams(String hql, String[] paramNames,
			Object... values) {
		return this.getHibernateTemplate().findByNamedParam(hql, paramNames,
				values);
	}

	/**
	 * 创建Query对象.<br>
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query createQuery(String hql, Object... values) {
		// 这里的false表示不创建session保证,当前操作在spring同一个事务的管理下
		Query query = this.getSession(false).createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 执行一些必须的sql语句把内存中的对象同步到数据库中
	 */
	public void flush() {
		this.getHibernateTemplate().flush();
	}

	/**
	 * 清除对象缓存
	 */
	public void clear() {
		this.getHibernateTemplate().clear();
	}

	/**
	 * 返回iterator接口类型的结果
	 * 
	 * @param <T>
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Iterator<T> iterator(String hql, Object... values) {
		return this.getHibernateTemplate().iterate(hql, values);
	}

	/**
	 * @return 当前上下文的原生Hibernate session对象,依然受到spring事务管理不需要手动close
	 */
	public Session getNativeHibernateSession() {
		return this.getSessionFactory().getCurrentSession();
	}

	/**
	 * @return 当前上下文的原生Hibernate StatelessSession对象<br>
	 *         此对象不级联关联实例,忽略集合不触发Hibernate事件模型和拦截器,没有一级缓存,没有持久化上下文,接近JDBC.
	 */
	public StatelessSession getNativeStatelessHibernateSession() {
		return this.getSessionFactory().openStatelessSession();
	}

	/**
	 * 执行本地查询获得SQLQuery对象<br>
	 * 可以调用addEntity(*.class).list();获得对应实体list集合<br>
	 * addEntity.add(*.class).addJoin(*.class).list();获得一对多代理对象<br>
	 * 更多用法见google
	 * 
	 * @param sql
	 * @return
	 */
	public SQLQuery nativeSqlQuery(String sql) {
		return this.getSession(false).createSQLQuery(sql);
	}

	/**
	 * @param <T>
	 * @param countHql
	 *            计算数据总条数的hql语句(就是带count(*)的hql)
	 * @param hql
	 * @param pageNo
	 *            页面号
	 * @param pageSize
	 *            页面容量
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> pagedQuery(String countHql, String hql, int pageNo,
			int pageSize, Object... values) {
		// Count查询
		List<T> countlist = this.getHibernateTemplate().find(countHql, values);
		long totalCount = (Long) countlist.get(0);
		if (totalCount < 1)
			return new Page<T>();
		// 当前页的开始数据索引
		long startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = this.createQuery(hql, values);
		List<T> list = query.setFirstResult((int) startIndex).setMaxResults(
				pageSize).list();
		return new Page<T>(startIndex, totalCount, pageSize, list);
	}

	/**
	 * @param <T>
	 * @param countHql
	 *            计算数据总条数的hql语句(就是带count(*)的hql)
	 * @param hql
	 * @param startNo
	 *            分页从哪一条数据开始
	 * @param pageSize
	 *            页面容量
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> pagedQueryByStartNo(String countHql, String hql,
			int startNo, int pageSize, Object... values) {
		// Count查询
		List<T> countlist = getHibernateTemplate().find(countHql, values);
		long totalCount = (Long) countlist.get(0);
		if (totalCount < 1)
			return new Page();

		int startIndex = startNo;
		Query query = createQuery(hql, values);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		return new Page<T>(startIndex, totalCount, pageSize, list);
	}

	/**
	 * @return 获得spring的HibernateTemplate拥有更多的功能
	 */
	public HibernateTemplate getSpringHibernateTemplate() {
		return this.getHibernateTemplate();
	}
}
