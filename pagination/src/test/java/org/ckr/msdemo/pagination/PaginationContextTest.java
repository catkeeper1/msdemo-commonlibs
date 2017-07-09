package org.ckr.msdemo.pagination;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Administrator on 2017/7/8.
 */
public class PaginationContextTest {
    private @Mocked
    RequestContextHolder requestContextHolder;

    private @Mocked
    ServletRequestAttributes servletRequestAttributes;

    private @Mocked
    HttpServletRequest httpServletRequest;

    @Test
    public void testParseRestPaginationParameters() {

        doTestParseRestPaginationParameters("items=0-100",
                                            0L,
                                            100L,
                                            "+abCde,-fdsD,+hhh",
                                            new boolean[]{true, false, true},
                                            new String[]{"abCde", "fdsD", "hhh"});

        doTestParseRestPaginationParameters("items=20-23",
                                            20L,
                                            23L,
                                            "-ace",
                                            new boolean[]{false},
                                            new String[]{"ace", "fdsD", "hhh"});
    }

    private void doTestParseRestPaginationParameters(final String rangeStr,
                                                     final Long start,
                                                     final Long end,
                                                     final String sortStr,
                                                     final boolean[] isAsc,
                                                     final String[] sortedField) {
        new Expectations(){{

            RequestContextHolder.getRequestAttributes(); result = servletRequestAttributes;

            servletRequestAttributes.getRequest(); result = httpServletRequest;

            List<String> rangeValues = new ArrayList<>();
            rangeValues.add(rangeStr);


            httpServletRequest.getHeaders("Range");
            times = 1;
            result = Collections.enumeration(rangeValues);

            List<String> sortedByValues = new ArrayList<>();
            sortedByValues.add(sortStr);
            httpServletRequest.getHeaders("SortBy");
            times = 1;
            result = Collections.enumeration(sortedByValues);

        }};

        PaginationContext.parseRestPaginationParameters();


        PaginationContext.QueryRequest queryRequest = PaginationContext.getQueryRequest();

        assertThat(queryRequest.getStart()).isEqualTo(start);
        assertThat(queryRequest.getEnd()).isEqualTo(end);

        for(int i = 0 ; i < queryRequest.getSortCriteriaList().size(); i++) {

            PaginationContext.SortCriteria sortCriterial = queryRequest.getSortCriteriaList().get(i);

            assertThat(sortCriterial.isAsc()).isEqualTo(isAsc[i]);
            assertThat(sortCriterial.getFieldName()).isEqualTo(sortedField[i]);

        }
    }
}
