package org.ckr.msdemo.dao;

import org.ckr.msdemo.util.DbAccessUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class BaseJpaDao {

    protected EntityManager entityManager;

    protected <T> List<T> executeDynamicQuery(String ql, Map<String, Object> params) {


        String adjustedQl = DbAccessUtil.adjustDynamicQueryString(ql, params.keySet());

        Query query = entityManager.createQuery(adjustedQl);

        DbAccessUtil.setQueryParameter(query, params);

        return query.getResultList();
    }
}
