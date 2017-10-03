package org.ckr.msdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * A util class for query string processing.
 */
public class QueryStringUtil {

    private static Logger LOG = LoggerFactory.getLogger(QueryStringUtil.class);

    /**
     * Adjust query string according to parameters.
     * If the query string include something like
     * "/<span></span>*paramName| ... *<span></span>/" the part after "|" will be appended to the final query string
     * which will be used for real DB access. For example, there is a query string as below:
     * <pre>
     *     <code>
     *             select u.userName, u.userDescription, u.locked from User u where 1=1
     *              /<span></span>*userName| and u.userName = :userName *<span></span>/
     *              /<span></span>*userDesc| and u.userDescription like :userDesc *<span></span>/
     *     </code>
     * </pre>
     * If paramNames include "userName", the query string will be adjusted to:
     * <pre>
     *     <code>
     *             select u.userName, u.userDescription, u.locked from User u where 1=1 and u.userName = :userName
     *     </code>
     * </pre>
     * If paramNames include "userDesc", the query string will be adjusted to:
     * <pre>
     *     <code>
     *             select u.userName, u.userDescription, u.locked from User u where 1=1
     *             and u.userDescription like :userDesc
     *     </code>
     * </pre>
     *
     * This feature is very useful for the dynamic query scenario.
     *
     * @param ql    query string that will be adjusted
     * @param paramNames inject params to ql
     * @return adjusted query string
     */
    public static String adjustDynamicQueryString(String ql, Set<String> paramNames) {

        StringBuffer queryStr = new StringBuffer(ql);

        LOG.debug("before adjustment, the query string is {}", queryStr);


        for (int startInd = queryStr.indexOf("/*"); startInd >= 0; startInd = queryStr.indexOf("/*")) {

            int endInd = queryStr.indexOf("*/", startInd);

            if (endInd < 0) {
                break;
            }

            String criteriaStr = queryStr.substring(startInd + 2, endInd);

            LOG.debug("criteria string: {}", criteriaStr);

            StringTokenizer tokenizer = new StringTokenizer(criteriaStr, "|");
            if (tokenizer.countTokens() < 2) {
                LOG.error("invalid criteria string: {}", criteriaStr);
            }

            String criteriaName = tokenizer.nextToken().trim();

            if (paramNames != null && paramNames.contains(criteriaName)) {

                String criteriaContent = tokenizer.nextToken();

                queryStr.replace(startInd, endInd + 2, criteriaContent);

            } else {

                queryStr.replace(startInd, endInd + 2, "");
            }
        }

        LOG.debug("after adjustment, the query string is {}", queryStr);
        return queryStr.toString();

    }
}
