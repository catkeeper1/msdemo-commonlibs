package org.ckr.msdemo.exception;

/**
 * Include classes for exception handling.
 *
 * <p>This package include below exceptions:
 * <ul>
 *     <li>{@link org.ckr.msdemo.exception.BaseException}: All exception defined in this project should extends this
 *     class.
 *     <li>{@link org.ckr.msdemo.exception.SystemException}: If an exception was caught and this exception is something
 *     cannot be handled by end user(such as DB is down). This exception should be wrapped with SystemException and
 *     thrown
 *     <li>{@link org.ckr.msdemo.exception.ApplicationException}: If want to show error messages to user and ask end
 *     user to do something, this exception should be thrown. Such as, a field cannot be empty. When this validation is
 *     failed, an ApplicationException with message "Field XXX cannot be empty" should be thrown.
 *     <li>
 * </ul>
 *
 *
 */