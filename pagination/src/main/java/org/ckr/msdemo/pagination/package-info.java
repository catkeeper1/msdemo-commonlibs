/**
 * Provide classes to implement HTTP endpoints that support dynamic pagination query.
 *
 * <p>Below features are included:
 * <ul>
 *     <li> Dynamic search criteria. For example, the search criteria can be changed base on the user input in UI.
 *     <li> Allow HTTP client to specify the range of records that should be returned.
 *     <li> Allow HTTP client to specify the sorting criteria. Multiple sorting criteria can be specified.
 *     <li> Return the no of total records for that query.
 * </ul>
 *
 * <p>To enable those pagination features, please place annotation {@link org.ckr.msdemo.pagination.EnablePagination}
 * in any java configuration class in an application.
 *
 * <p>This package include below key classes:
 * <ul>
 *     <li>{@link org.ckr.msdemo.pagination.PaginationInterceptor}: Intercept HTTP request and parse pagination
 *     request info. Such as, the range of records, sorting by which fields.
 *     <li>{@link org.ckr.msdemo.pagination.PaginationContext}: It hold the pagination info(such as, sorting by which
 *     fields, the total number of records for a query) so that this kind of data can be shared by different
 *     components.
 *     <li>{@link org.ckr.msdemo.pagination.RestPaginationResponseAdvice}: Adjust HTTP response to include the
 *     pagination info that should be returned to HTTP client in the header.
 *     <li>{@link org.ckr.msdemo.pagination.JpaRestPaginationService}: Implement the pagination query with JPA API.
 * </ul>
 *
 * Below is a sequence diagram illustrate how all those key classes work together:
 * <p><img src="paginationSequence.svg" alt="sequence diagram">
 * <!--
@startuml paginationSequence.svg
autonumber

participant  HTTPClient
participant  PaginationInterceptor
participant  RestPaginationResponseAdvice
participant  PaginationContext
participant  Controller
participant  Service

participant  JpaRestPaginationService
"HTTPClient" -> "PaginationInterceptor": Send request
"PaginationInterceptor" -> "PaginationContext": Parse and store range of records and sorting fields from\
HTTP request.


"PaginationInterceptor" -> "Controller": Pass HTTP request to controller.                                         \
                                                            \t
note right
  Controller is the controller
  object that provide HTTP
  endpoint for this
  pagination query.
end note
"Controller" -> "Service": Construct QL and parameters for the query.
note right
  Service is the service object that implement
  the query logic. It should launch the DB
  transaction.
end note
"Service" -> "JpaRestPaginationService" : Pass search criteria to JpaRestPaginationService
"JpaRestPaginationService" -> "PaginationContext": Retrieve pagination request info.
"JpaRestPaginationService" -> "JpaRestPaginationService": Retrieve query content from DB.
"JpaRestPaginationService" -> "JpaRestPaginationService": Retrieve  and total number of \
records for this query from DB.
"JpaRestPaginationService" -> "PaginationContext": Save the total number of records.
"Service" <- "JpaRestPaginationService" : Return result list
"Controller" <- "Service" : Return result. Commit DB transaction.
"Controller" -> "RestPaginationResponseAdvice" : Pass HTTP response to it
"PaginationContext" <- "RestPaginationResponseAdvice": Save total number of records in HTTP response header.
"RestPaginationResponseAdvice" -> "PaginationInterceptor": Pass HTTP response to it
"PaginationInterceptor" -> "PaginationContext": Clear context data(pagination info) to release memory.
"HTTPClient" <- "PaginationInterceptor": Return result list with pagination info.
@enduml .
-->
 */

package org.ckr.msdemo.pagination;
