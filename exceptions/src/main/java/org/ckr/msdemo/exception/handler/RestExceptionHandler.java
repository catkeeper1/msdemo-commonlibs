package org.ckr.msdemo.exception.handler;

import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.exception.BaseException;
import org.ckr.msdemo.exception.SystemException;
import org.ckr.msdemo.exception.valueobject.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;


/**
 * This is a util class for exception handling. When an exception is caught by global exception handler.
 * The global exception handler can call the {@link #handleException(Throwable, AbstractMessageSource)} to generate an
 * propery responding for json REST api. Below is an example: <br>
 * <pre>
 *         <code>
 *         &#064;ControllerAdvice(annotations = RestController.class)
 *              public class GlobalRestExceptionHandler {
 *
 *              &#064;Autowired
 *              private AbstractMessageSource messageSource;
 *
 *              &#064;ExceptionHandler(value = Throwable.class)
 *              public ResponseEntity handleException(final Throwable exp) {
 *                  return RestExceptionHandler.handleException(exp, messageSource);
 *              }
 *
 *          }
 *
 *         </code>
 *     </pre>
 */
public final class RestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * The constant for name of header that store the exception ID.
     */
    public static final String EXCEPTION_ID = "EXCEPTION_ID";

    private RestExceptionHandler() {
    }

    /**
     * When an exception is caught, this method can be used to generate a json REST response.
     * First of all, this method should log the exception in log file so that developer can trace the stack later.
     * Then, this method will check whether this exception is caused by {@link ApplicationException}. If so, it will
     * use messageSource to generate message that will be shown to end user. Please refer {@link ErrorResponse} for
     * the json format.
     *
     * @param exp           The exception that is caught and will be handled to generate a json response.
     * @param messageSource If the exception that The message source that will be used
     * @return an instance of ReponseEntity that can be used by Spring MVC controller to generate response
     *     Header "EXCEPTION_ID" of this response store the exception ID of the handled exception which can be use
     *     to trace the exception stack later. The HTTP status is INTERNAL_SERVER_ERROR(500).
     *
     */
    public static ResponseEntity<ErrorResponse> handleException(Throwable exp,
                                                                AbstractMessageSource messageSource) {

        LOG.debug("start to handle exception");

        BaseException be = null;

        if (exp instanceof BaseException) {

            be = (BaseException) exp;

        } else {
            be = (BaseException) getCause(exp, BaseException.class);
        }

        if (be == null) {
            be = new SystemException("System exception.", exp);
        }

        LOG.error("exception was caught:", be);

        HttpHeaders headers = new HttpHeaders();

        headers.add(EXCEPTION_ID, be.getExceptionId());

        HttpServletRequest httpRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Locale locale = RequestContextUtils.getLocale(httpRequest);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setExceptionId(be.getExceptionId());

        if (be instanceof ApplicationException) {
            ApplicationException appExp = (ApplicationException) be;
            for (int i = 0; i < appExp.getMessageList().size(); i++) {
                ApplicationException.ExceptionMessage expMsg = appExp.getMessageList().get(i);
                LOG.debug("return exception message with msg code = {} params = {} message = {}",
                        expMsg.getMessageCode(),
                        expMsg.getMessageParams(),
                        expMsg.getMessage());

                String msg = expMsg.getMessage();

                if (msg == null || "".equals(msg.trim())) {
                    msg = messageSource.getMessage(expMsg.getMessageCode(),
                            expMsg.getMessageParams(),
                            locale);
                }

                errorResponse.addMessage(expMsg.getMessageCode(), msg);

            }
        }

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * Retrieve a cause of exception(parameter exp) if the cause is an instance of a class(parameter clazz).
     * This method go through the cause chain of an exception and return the cause if the cause is an instance
     * of parameter clazz. <br>
     * For example, There are 4 types of exception A, B, C and D. An instance of A is thrown. Then, other method
     * that catch excption A and use an instance of B to wrap it and re-thrown the instance of B.
     * This step is repeated. Finally, the cause chain looks like:<br>
     * A -&gt; B -&gt; C -&gt; D <br>
     * When exception D is caught and call: <br>
     * <code>getCause(exceptionInClassD, B);</code>
     * the instance of B will be returned.
     *
     * @param exp   The cause chain of this exception will be gone through.
     * @param clazz If a cause is instance of this class, it will be returned.
     * @return A cause of parameter exp.
     */
    public static Throwable getCause(Throwable exp, Class<?> clazz) {

        Throwable tmp = exp;

        while (tmp != null) {
            if (clazz.isInstance(tmp)) {
                return tmp;
            }

            tmp = tmp.getCause();
        }

        return null;
    }

}
