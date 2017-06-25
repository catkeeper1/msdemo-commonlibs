package org.ckr.msdemo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(BaseException.class);

    private static final long serialVersionUID = 9112831069901766558L;

    private String exceptionId;


    protected BaseException() {
        super();
        genExceptionId();
    }

    protected BaseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        genExceptionId();
    }

    protected BaseException(String arg0) {
        super(arg0);
        genExceptionId();
    }

    protected BaseException(Throwable arg0) {
        super(arg0);
        genExceptionId();
    }

    private void genExceptionId() {
        if (exceptionId != null) {
            return;
        }

        DateFormat format = new SimpleDateFormat("yyMMddkkmmssSSS");
        StringBuilder buffer = new StringBuilder();

        buffer.append(format.format(new Date()));

        NumberFormat numberFormat = new DecimalFormat("0000");
        numberFormat.setMaximumIntegerDigits(4);
        buffer.append(numberFormat.format(this.hashCode()));

        exceptionId = buffer.toString();

        LOG.debug("generated exception ID is {}", exceptionId);
    }



    public String getExceptionId() {
        return exceptionId;
    }


    @Override
    public void printStackTrace(PrintStream stream) {
        stream.append("Exception ID:" + getExceptionId());
        super.printStackTrace(stream);
    }

    @Override
    public void printStackTrace(PrintWriter writer) {
        writer.append("Exception ID:" + getExceptionId());
        super.printStackTrace(writer);
    }

    @Override
    public String getMessage() {
        StringBuilder result = new StringBuilder(50);

        result.append("Exception ID:")
              .append(getExceptionId())
              .append("\r\n")
              .append(super.getMessage());

        return result.toString();
    }
}
