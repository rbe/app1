package eu.artofcoding.app1.web;

import eu.artofcoding.app1.helper.JeeHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class MyWebAppListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    /**
     * The ServletContext.
     */
    private ServletContext servletContext;

    /**
     * Default constructor.
     */
    public MyWebAppListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------

    /**
     * This method is called when the servlet context is initialized(when the Web application is deployed).
     * You can initialize servlet context related data here.
     * @param sce
     */
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext = sce.getServletContext();
        JeeHelper.log(this.servletContext, "A log test");
    }

    /**
     * This method is invoked when the Servlet Context (the Web application) is undeployed or Application Server shuts down.
     * @param sce
     */
    public void contextDestroyed(ServletContextEvent sce) {
        this.servletContext = null;
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------

    /**
     * Session is created.
     * @param se
     */
    public void sessionCreated(HttpSessionEvent se) {
    }

    /**
     * Session is destroyed.
     * @param se
     */
    public void sessionDestroyed(HttpSessionEvent se) {
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    /**
     * This method is called when an attribute is added to a session.
     * @param sbe
     */
    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    /**
     * This method is called when an attribute is removed from a session.
     * @param sbe
     */
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    /**
     * This method is invoked when an attibute is replaced in a session.
     * @param sbe
     */
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
    }

}
