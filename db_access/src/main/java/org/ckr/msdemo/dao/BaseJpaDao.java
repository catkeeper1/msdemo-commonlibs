package org.ckr.msdemo.dao;

import org.ckr.msdemo.util.DbAccessUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BaseJpaDao {

    protected EntityManager entityManager;

    protected <T> List<T> executeDynamicQuery(final String ql,
                                              final Map<String, Object> params,
                                              final Function<Object[], T> mapper) {


        String adjustedQl = DbAccessUtil.adjustDynamicQueryString(ql, params.keySet());

        Query query = entityManager.createQuery(adjustedQl);

        DbAccessUtil.setQueryParameter(query, params);

        List rawResultList = query.getResultList();
        List<T> resultList = DbAccessUtil.convertRawListToTargetList(rawResultList, mapper);

        return resultList;
    }
}
