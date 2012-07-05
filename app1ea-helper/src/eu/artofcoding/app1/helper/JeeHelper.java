package eu.artofcoding.app1.helper;

import javax.servlet.ServletContext;

/**
 *
 */
public class JeeHelper {

    /**
     * Log a message and associated exception to the servlet context application log.
     * @param message Message to be logged
     * @param throwable Exception to be logged
     */
    public static void log(ServletContext servletContext, String message, Throwable throwable) {
        if (servletContext != null) {
            if (null != throwable) {
                servletContext.log(message, throwable);
            } else {
                servletContext.log(message);
            }
        } else {
            System.out.println(message);
            throwable.printStackTrace(System.out);
        }
    }

    /**
     * Convenience method for log(ServletContext, String, null), without Throwable.
     * @param servletContext
     * @param message
     */
    public static void log(ServletContext servletContext, String message) {
        log(servletContext, message, null);
    }

}
