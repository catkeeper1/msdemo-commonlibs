/**
 * This module to is resolve complicated dynamic query
 * with dynamic pagination like page size limitation and sorting.
 * <p><img src="org.ckr.msdemo.pagination.Interceptor.svg" alt="class diagram">
 * <!--
 * @startuml org.ckr.msdemo.pagination.Interceptor.svg
 * autonumber
 * "Browser" -> "PaginationInterceptor": Send request
 * participant  RestPaginationResponseAdvice
 * "PaginationInterceptor" -> "PaginationContext": Parse request and save to Threadlocal
 * "Controller" -> "JpaRestPaginationService" : Controller handle request with JpaRestPaginationService
 * "JpaRestPaginationService" -> "PaginationContext": Get request info from Threadlocal
 * "JpaRestPaginationService" -> "PaginationContext": execute query with pagination
 * "JpaRestPaginationService" -> "PaginationContext": execute query with total record count
 * "JpaRestPaginationService" -> "PaginationContext": Save pagination and total record count to Threadlocal
 * "Controller" <- "JpaRestPaginationService" : Return result list
 * "PaginationContext" <- "RestPaginationResponseAdvice": Save custom header to response header
 * "Browser" <- "Controller": Return result list with custom header
 * "PaginationInterceptor" -> "PaginationContext": Clear context data in the current thread
 * @enduml .-->
 */

package org.ckr.msdemo.pagination;
