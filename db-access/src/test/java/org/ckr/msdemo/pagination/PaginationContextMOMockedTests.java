package org.ckr.msdemo.pagination;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Created by Administrator on 10/4/2019.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestContextHolder.class, ServletRequestAttributes.class})
public class PaginationContextMOMockedTests {

    private ServletRequestAttributes servletRequestAttributes;

    private HttpServletRequest httpServletRequest;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RequestContextHolder.class);

        servletRequestAttributes = PowerMockito.mock(ServletRequestAttributes.class);
        httpServletRequest = PowerMockito.mock(HttpServletRequest.class);
    }

    @Test
    public void testParseRestPaginationParameters() {

        doTestParseRestPaginationParameters("items=0-100",
                0L,
                100L,
                "+abCde,-fdsD,+hhh",
                new boolean[] {true, false, true},
                new String[] {"abCde", "fdsD", "hhh"});

//        doTestParseRestPaginationParameters("items=20-23",
//                20L,
//                23L,
//                "-ace",
//                new boolean[] {false},
//                new String[] {"ace", "fdsD", "hhh"});
    }

    private void doTestParseRestPaginationParameters(final String rangeStr,
                                                     final Long start,
                                                     final Long end,
                                                     final String sortStr,
                                                     final boolean[] isAsc,
                                                     final String[] sortedField) {

        //mock
        when(RequestContextHolder.getRequestAttributes()).thenReturn(servletRequestAttributes);

        when(servletRequestAttributes.getRequest()).thenReturn(httpServletRequest);

        List<String> rangeValues = new ArrayList<>();
        rangeValues.add(rangeStr);
        when(httpServletRequest.getHeaders("Range")).thenReturn(Collections.enumeration(rangeValues));

        List<String> sortedByValues = new ArrayList<>();
        sortedByValues.add(sortStr);
        when(httpServletRequest.getHeaders("SortBy")).thenReturn(Collections.enumeration(sortedByValues));

        //execute
        PaginationContext.parseRestPaginationParameters();

        //verify
        verify(httpServletRequest, times(1)).getHeaders("Range");
        verify(httpServletRequest, times(1)).getHeaders("SortBy");
        verify(httpServletRequest, times(1)).getHeaders("FilterBy");

        PaginationContext.QueryRequest queryRequest = PaginationContext.getQueryRequest();


        assertThat(queryRequest.getStart()).isEqualTo(start);
        assertThat(queryRequest.getEnd()).isEqualTo(end);

        for (int i = 0; i < queryRequest.getSortCriteriaList().size(); i++) {

            PaginationContext.SortCriteria sortCriterial = queryRequest.getSortCriteriaList().get(i);

            assertThat(sortCriterial.isAsc()).isEqualTo(isAsc[i]);
            assertThat(sortCriterial.getFieldName()).isEqualTo(sortedField[i]);

        }


    }
}
