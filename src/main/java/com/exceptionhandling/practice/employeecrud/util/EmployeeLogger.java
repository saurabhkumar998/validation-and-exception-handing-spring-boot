package com.exceptionhandling.practice.employeecrud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeLogger {
    private static Logger LOGGER;

    private EmployeeLogger() {
        super();
    }

    /**
     * Print info logger in log file.
     *
     * @param object
     * @param message
     */
    public static void logInfo(Object object, String message) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(message);
        }
    }

    /**
     * Print debug logger in log file
     *
     * @param object
     * @param message
     */
    public static void logDebug(Object object, String message) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug(message);
        }
    }

    /**
     * Print info start logger in the log file
     *
     * @param object
     * @param message
     */
    public static void logStart(Object object, String message) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(message + " - START");
        }
    }

    /**
     * Print info end logger in the log file
     *
     * @param object
     * @param message
     */
    public static void logEnd(Object object, String message) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(message + " - END");
        }
    }

    /**
     * Print info end logger along with the time taken in the log file
     *
     * @param object
     * @param message
     * @param time
     */
    public static void logEnd(Object object, String message, Long time) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(message + " - end : " + time);
        }
    }

    /**
     *
     * @param object
     * @param message
     */
    public static void logError(Object object, Exception exception) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isErrorEnabled()) {
            LOGGER.error(exception.getMessage(), exception);
        }
    }

    /**
     *
     * @param object
     * @param message
     */
    public static void logTrace(Object object, String message) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace(message);
        }
    }

    /**
     *
     * @param object
     * @param message
     */
    public static void logWarn(Object object, String message) {
        LOGGER = LoggerFactory.getLogger(object.getClass());
        if(LOGGER.isWarnEnabled()) {
            LOGGER.warn(message);
        }
    }

}
