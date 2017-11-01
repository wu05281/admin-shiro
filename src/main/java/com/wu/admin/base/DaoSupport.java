package com.wu.admin.base;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author wubo
 * @version 创建时间 2017/10/10-下午5:23
 */
@Repository
public class DaoSupport {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession(){
        Session session = entityManager.unwrap(Session.class);
        return session;
    }


    /*
     *
	 * 创建Criteria实例
	 *
	 */

    public Criteria createCriteria(Class entityClass) {
        return getSession().createCriteria(entityClass);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 保存PO
     */
    public Serializable save(Object entity) {
        return (Serializable) getSession().save(entity);
    }

    /**
     * 保存或更新PO
     */
    public void saveOrUpdate(Object entity) {
        getSession().saveOrUpdate(entity);
    }

    /**
     * 更新PO
     */
    public void update(Object entity) {
        getSession().update(entity);
//        getSession().flush();
    }

    /**
     * 根据id删除PO
     */
    public void delete(Serializable id, Class clazz) {
        getSession().delete(this.get(id, clazz));
    }

    /**
     * 删除托管中的PO，注意：new之后set主键的不能使用此方法
     */
    public void deleteEntity(Object o) {
        getSession().delete(o);
    }

    /**
     * 根据id判断PO是否存在
     */
    public boolean exists(Serializable id, Class clazz) {
        return get(id, clazz) != null;
    }

    /**
     * 根据id加载PO
     */
    @SuppressWarnings("unchecked")
    public <T> T load(Serializable id, Class<T> clazz) {
        return getSession().load(clazz, id);
    }

    /**
     * 根据id获取PO
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Serializable id, Class<T> clazz) {
        return (T) getSession().get(clazz, id);
    }

    /**
     * 查询单列，eg. 总数，平均值
     */
    public Object getSingleColumnBySql(CharSequence queryString, Object... values) {
        SQLQuery query = getSession().createSQLQuery(queryString.toString());
        setParameter(query, values);
        return query.uniqueResult();
    }

    /**
     * 查询单列，eg. 总数，平均值
     */
    public Object getSingleColumnByHql(CharSequence queryString, Object... values) {
        Query query = getSession().createQuery(queryString.toString());
        setParameter(query, values);
        return query.uniqueResult();
    }


    /**
     * 查询单条记录
     * 查询列名的别名跟实体必须完全一致
     */
    public <T> T getSingleBySql(Class<T> clazz, CharSequence queryString, Object... values) {
        SQLQuery query = getSession().createSQLQuery(queryString.toString());
        setParameter(query, values);
        query.setMaxResults(1);
        AddScalar.addSclar(query, clazz);
        List<T> list = query.list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取单个实体，根据查询语句及参数获取。
     * 查询列名的别名跟实体必须完全一致（如 select a.uid as uid ao Admin as a , AdminRole as ar where a.uid=ar.adminId and a.uid=?
     */
    public <T> T getSingleByHql(Class<T> clazz, CharSequence queryString, Object... params) {
        Query query = getSession().createQuery(queryString.toString());
        setParameter(query, params);
        query.setMaxResults(1);
        query.setResultTransformer(Transformers.aliasToBean(clazz));
        List<T> list = query.list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取实体列表，根据查询语句及参数获取。
     * 查询列名的别名跟实体必须完全一致（如 select a.uid as uid ao Admin as a , AdminRole as ar where a.uid=ar.adminId and a.uid=?
     */
    public <T> List<T> listByHql(Class<T> cl, CharSequence queryString, Object... params) {
        Query query = getSession().createQuery(queryString.toString());
        setParameter(query, params);
        query.setResultTransformer(Transformers.aliasToBean(cl));
        return query.list();
    }

    /**
     * 利用原生sql查询，查询字段名或者别名需要跟bean中属性一一对应。
     */
    public <T> List<T> listBySql(Class cl, CharSequence queryString, Object... values) {
        SQLQuery query = getSession().createSQLQuery(queryString.toString());
        setParameter(query, values);
        AddScalar.addSclar(query, cl);
        return query.list();
    }

    public List listBySql(CharSequence queryString, Object... values) {
        SQLQuery query = getSession().createSQLQuery(queryString.toString());
        setParameter(query, values);
        return query.list();
    }


    @SuppressWarnings({"unchecked"})
    public <T> Pagination<T> pageByHql(Class<T> clazz, CharSequence queryString, int pageIndex,
                                       int pageSize, Object... params) {
        Query query = getSession().createQuery(queryString.toString());

        if ((pageSize > 0) && (pageIndex > 0)) {
            query.setFirstResult((pageIndex - 1) * pageSize);
            query.setMaxResults(pageIndex * pageSize);
        }

        setParameter(query, params);
        query.setResultTransformer(Transformers.aliasToBean(clazz));

        List<T> items = query.list();
        Long rowsCount = (Long) getSingleColumnByHql(getCountStr(queryString.toString()), params);
        Pagination<T> pagination = new Pagination((long) pageIndex, (long) pageSize, rowsCount);
        pagination.setItems(items);
        return pagination;
    }

    /**
     * 返回指定对象数据集合，查询字段名或者别名与指定对象字段一致。
     */
    @SuppressWarnings({"unchecked"})
    public <T> Pagination<T> pageBySql(Class<T> clazz, final CharSequence queryString,
                                       final Object[] values, int pageIndex, int pageSize) {
        SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
        if ((pageSize > 0) && (pageIndex > 0)) {
            sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
            sqlQuery.setMaxResults(pageIndex * pageSize);
        }
        setParameter(sqlQuery, values);
        AddScalar.addSclar(sqlQuery, clazz);
        List<T> items = sqlQuery.list();
        BigInteger rowsCount = (BigInteger) getSingleColumnBySql(getCountStr(queryString.toString()), values);

        Pagination<T> pagination = new Pagination((long) pageIndex, (long) pageSize, rowsCount.longValue());
        pagination.setItems(items);
        return pagination;
    }

    /**
     * 返回指定对象数据集合。
     */
    @SuppressWarnings({"unchecked"})
    public Pagination pageBySql(final CharSequence queryString,
                                final Object[] values, int pageIndex, int pageSize) {
        SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
        if ((pageSize > 0) && (pageIndex > 0)) {
            sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
            sqlQuery.setMaxResults(pageIndex * pageSize);
        }
        setParameter(sqlQuery, values);
        List items = sqlQuery.list();
        BigInteger rowsCount = (BigInteger) getSingleColumnBySql(getCountStr(queryString.toString()), values);

        Pagination pagination = new Pagination((long) pageIndex, (long) pageSize, rowsCount.longValue());
        pagination.setItems(items);
        return pagination;
    }

    /**
     * native sql excute
     */
    public int updateBySql(String sql, final Object[] values) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        setParameter(sqlQuery, values);
        return sqlQuery.executeUpdate();
    }

    /**
     */
    public int updateByHql(String hql, final Object[] values) {
        Query query = getSession().createQuery(hql);
        setParameter(query, values);
        return query.executeUpdate();
    }

    /**
     * 批量更新或者插入
     */
    public void insertOrUpdateBatch(String sql, BatchPreparedStatementSetter bpss) {
        jdbcTemplate.batchUpdate(sql, bpss);
    }

    /**
     * 强制清空session
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 清空session
     */

    public void clear() {
        getSession().clear();
    }

    private String getCountStr(String queryStr) {
        StringBuilder countSql = new StringBuilder("select count(1) ").append(queryStr.substring(queryStr.toLowerCase().indexOf("from")));
        return countSql.toString();
    }


    private Query setParameter(SQLQuery query, Object... values) {
        if (Objects.isNull(values) || values.length == 0) {
            return query;
        }
        int i = 0;
        for (Object value : values) {
            query.setParameter(i, value);
            i++;
        }
        return query;
    }

    private Query setParameter(Query query, Object... values) {
        if (Objects.isNull(values) || values.length == 0) {
            return query;
        }
        int i = 0;
        for (Object value : values) {
            query.setParameter(i, value);
            i++;
        }
        return query;
    }


}
