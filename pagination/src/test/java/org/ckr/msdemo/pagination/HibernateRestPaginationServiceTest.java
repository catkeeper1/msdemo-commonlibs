package org.ckr.msdemo.pagination;

/**
 * Created by yukai.a.lin on 8/10/2017.
 */
public class HibernateRestPaginationServiceTest {


//    @Test
//    public void testAdjustQueryStringAll() {
//        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
//        Map<String, Object> params = new HashMap<>();
//        params.put("userName", "abc");
//        params.put("Desc", "def");
//        String parsedQuery = HibernateRestPaginationService.adjustQueryString(rawQuery, params);
//        System.out.println(parsedQuery);
//        assertThat(parsedQuery).isEqualTo("select u.a, u.b from User u where 1=1  and u.Name = :userName   and u.Description like :Desc ");
//    }
//
//    @Test
//    public void testAdjustQueryStringPartial() {
//        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
//        Map<String, Object> params = new HashMap<>();
//        params.put("userName", "abc");
//        String parsedQuery = HibernateRestPaginationService.adjustQueryString(rawQuery, params);
//        System.out.println(parsedQuery);
//        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1  and u.Name = :userName");
//    }
//
//    @Test
//    public void testAdjustQueryStringNone() {
//        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
//        Map<String, Object> params = new HashMap<>();
//        String parsedQuery = HibernateRestPaginationService.adjustQueryString(rawQuery, params);
//        System.out.println(parsedQuery);
//        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1");
//    }
}
