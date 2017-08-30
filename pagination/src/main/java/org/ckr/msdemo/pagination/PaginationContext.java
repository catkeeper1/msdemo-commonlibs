package org.ckr.msdemo.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 * Store information for each request and its response
 */
public class PaginationContext {


    private static final Logger LOG = LoggerFactory.getLogger(PaginationContext.class);

    private static final ThreadLocal<QueryRequest> requestInfo =
        new ThreadLocal<>();

    private static final ThreadLocal<QueryResponse> responseInfo =
        new ThreadLocal<>();


    private static Long parseNum(String numStr) {
        if (numStr == null) {
            return null;
        }

        try {
            return Long.valueOf(numStr);
        } catch (NumberFormatException exp) {
            return null;
        }
    }

    /**
     * Parse request header and
     * set {@link PaginationContext.QueryRequest} (Range, SortBy) to {@link PaginationContext#requestInfo}
     */
    public static void parseRestPaginationParameters() {

        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if (request == null) {
            return;
        }

        QueryRequest queryRequest = new QueryRequest();
        queryRequest = parsePageRange(queryRequest, request);

        if (queryRequest.getStart() == null) {
            return;
        }

        queryRequest = parseSortBy(queryRequest, request);

        LOG.debug("queryRequest = {}", queryRequest);

        requestInfo.set(queryRequest);

    }

    /**
     * Set response header with header name <code>Content-Range</code>
     * and header value like <code>items 10-20/100</code> ,
     * where 10 is start number of current page, 20 end number, and 100 the total number of all pages.
     *
     * @param response ServerHttpResponse
     */
    public static void setRestPaginationResponse(ServerHttpResponse response) {

        QueryResponse queryResponse = responseInfo.get();

        if (queryResponse == null) {
            return;
        }

        String headerContent = "items " + queryResponse.getStart() + "-"
            + queryResponse.getEnd() + "/"
            + queryResponse.getTotal();

        LOG.debug("Content-Range={}", headerContent);

        response.getHeaders().set("Content-Range", headerContent);


    }

    /**
     * Set {@link PaginationContext.QueryResponse} to {@link PaginationContext#responseInfo}
     *
     * @param start start number of current page
     * @param end   end number of current page
     * @param total total number of all pages
     */
    public static void setResponseInfo(Long start, Long end, Long total) {
        QueryResponse response = new QueryResponse(start, end, total);
        responseInfo.set(response);
    }

    public static QueryRequest getQueryRequest() {
        return requestInfo.get();
    }

    public static QueryResponse getQueryResponse() {
        return responseInfo.get();
    }

    /**
     * Remove all request and response info in thread local after response completed.
     */
    public static void clearContextData() {
        if (requestInfo.get() != null) {
            LOG.debug("clear request info in thread local.");
            requestInfo.remove();
        }

        if (responseInfo.get() != null) {
            LOG.debug("clear response info in thread local.");
            responseInfo.remove();
        }
    }

    private static QueryRequest parsePageRange(QueryRequest range, HttpServletRequest webRequest) {

        Enumeration<String> headers = webRequest.getHeaders("Range");

        if (headers == null || (!headers.hasMoreElements())) {
            return range;
        }

        String rangeStr = headers.nextElement();
        LOG.debug("rangeStr = {}", rangeStr);

        if (!rangeStr.startsWith("items=")) {
            return range;
        }

        StringTokenizer tokenizer = new StringTokenizer(rangeStr.substring("items=".length()), "-");

        if (tokenizer.hasMoreTokens()) {
            String startStr = tokenizer.nextToken();

            Long start = parseNum(startStr);

            if (start == null) {
                return range;
            }

            range.setStart(start);
        }

        if (tokenizer.hasMoreTokens()) {
            String startStr = tokenizer.nextToken();

            Long end = parseNum(startStr);

            if (end != null) {
                range.setEnd(end);
            }

        }

        LOG.debug("start={}, end={}", range.getStart(), range.getEnd());

        return range;
    }


    private static QueryRequest parseSortBy(QueryRequest request, HttpServletRequest webRequest) {
        Enumeration<String> headers = webRequest.getHeaders("SortBy");


        if (headers == null || (!headers.hasMoreElements())) {
            request.setSortCriteriaList(new ArrayList<>());
            return request;
        }

        String sortByStr = headers.nextElement();

        LOG.debug("parseSortBy(). sortByStr = {}", sortByStr);


        StringTokenizer tokenizer = new StringTokenizer(sortByStr, ",");

        List<SortCriteria> sortCriteriaList = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            String criteriaStr = tokenizer.nextToken();

            if (criteriaStr.length() <= 1) {
                LOG.error("invlaid sort critiera:{}", criteriaStr);
            }

            SortCriteria sortCriteria = new SortCriteria();

            if (criteriaStr.startsWith(" ") || criteriaStr.startsWith("+")) {
                sortCriteria.setAsc(true);

            } else if (criteriaStr.startsWith("-")) {
                sortCriteria.setAsc(false);
            } else {
                LOG.error("invlaid sort critiera:{}", criteriaStr);
                continue;
            }

            sortCriteria.setFieldName(criteriaStr.substring(1));

            LOG.debug("sortCriteria.fieldName = {}, asc = {}", sortCriteria.getFieldName(), sortCriteria.isAsc());

            sortCriteriaList.add(sortCriteria);
        }

        request.setSortCriteriaList(sortCriteriaList);

        return request;
    }

    /**
     * This is used to store the query raw data(the range of records that should be returned).
     * {@link PaginationContext} extract query data from HTTP request objects and store in a object of this
     * class. When developers implement , they just need to get the
     * query raw data from this class but not HTTP request so that it will not coupled with any thing in controller
     * layer.
     */
    public static class QueryRequest {

        /**
         * This is used to specified the range of records should be returned by the query.
         * If start = 11 and end = 20, it means it is expected that this query should return records from
         * 11th record to 20th record.
         */
        private Long start;

        /**
         * This is used to specify the range of records should be returned by the query.
         *
         * @see QueryRequest#start
         */
        private Long end;

        /**
         * This is used to specify how sorting should be done.
         * Assume there are 2 records in this fields(record1 and record2). record1.isAsc = true,
         * record1.fieldName = "abc, record2.isAsc = false, record2.fieldName = "def". It means something like:
         * "SELECT ... FROM ... ORDER BY abc DESC, def ASC".
         */
        private List<SortCriteria> sortCriteriaList = new ArrayList<>();

        public Long getStart() {
            return start;
        }

        public void setStart(Long start) {
            this.start = start;
        }

        public Long getEnd() {
            return end;
        }

        public void setEnd(Long end) {
            this.end = end;
        }


        public List<SortCriteria> getSortCriteriaList() {
            return sortCriteriaList;
        }

        public void setSortCriteriaList(List<SortCriteria> sortCriteriaList) {
            this.sortCriteriaList = sortCriteriaList;
        }

        @Override
        public String toString() {
            return "QueryRequest{"
                + "start=" + start
                + ", end=" + end
                + ", sortCriteriaList=" + sortCriteriaList
                + '}';
        }
    }

    /**
     * This is used by the {@link QueryRequest} to store information about sorting.
     */
    public static class SortCriteria {
        /**
         * Indicate sorting will be done with asc or desc. True means asc.
         */
        private boolean isAsc;

        /**
         * the field used for sorting.
         */
        private String fieldName;

        public boolean isAsc() {
            return isAsc;
        }

        public void setAsc(boolean isAsc) {
            this.isAsc = isAsc;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return "SortCriteria{"
                + "isAsc=" + isAsc
                + ", fieldName='" + fieldName + '\''
                + '}';
        }
    }

    /**
     * This is used to decouple the query result from HTTP response. Objects of this class are used to store raw data
     * of query result.  should return an instance of this class
     * and the  will use this object to
     * generate an response that can be returned by a Spring MVC controller method.
     */
    public static class QueryResponse {


        /**
         * {@link QueryRequest#start} and {@link QueryRequest#end} is used to specify the range of records the caller
         * want to retrieve. However, it is possible that caller want to retrieve records 100 to 110 but there is only
         * 104 records available in total. At this moment, the  should return
         * an object with {@link QueryResponse#start} = 100, {@link QueryResponse#total} = 104 and
         * include records from 100th record to 104th record.
         */
        private Long start;


        private Long end;

        /**
         * This is used to store the actual total number of available records for this query.
         *
         * @see QueryResponse#start
         */
        private Long total;

        public QueryResponse() {
            super();
        }

        public QueryResponse(Long start, Long end, Long total) {
            this.start = start;
            this.end = end;
            this.total = total;
        }

        public QueryResponse(Long start, Long end) {
            this.start = start;
            this.end = end;
        }

        public Long getStart() {
            return start;
        }

        public void setStart(Long start) {
            this.start = start;
        }

        public Long getEnd() {
            return end;
        }


        public Long getTotal() {
            return total;
        }


        public void setEnd(Long end) {
            this.end = end;
        }

        public void setTotal(Long total) {
            this.total = total;
        }
    }

}
