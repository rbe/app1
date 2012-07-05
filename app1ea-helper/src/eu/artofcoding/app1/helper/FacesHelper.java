/*
 * app1
 * app1ea-helper
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 6/16/12 11:22 AM
 */
package eu.artofcoding.app1.helper;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class FacesHelper {

    private static final Logger logger = Logger.getLogger(FacesHelper.class.getName());

    /**
     *
     */
    public void getHttpServletRequest() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
    }

    /**
     * 
     * @return
     */
    public HttpServletResponse getHttpServletResponse() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return (HttpServletResponse) ctx.getExternalContext().getResponse();
    }

    /**
     *
     */
    public void readParameter() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> keymap = ctx.getExternalContext().getRequestParameterMap();
        for (String key : keymap.keySet()) {
            System.out.println("Parameter: " + key + " = " + keymap.get(key));
        }
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public Object writeToSessionMap(String key, Object value) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return ctx.getExternalContext().getSessionMap().put(key, value);
    }

    /**
     * @param key
     * @return
     */
    public Object readSessionMap(String key) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, Object> map = ctx.getExternalContext().getSessionMap();
        return map.get(key);
    }

    /**
     * 
     * @param url
     */
    public void redirect(String url) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse res = (HttpServletResponse) ctx.getExternalContext().getResponse();
        try {
            res.sendRedirect(url);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add a JSF message for a certain component.
     * @param message
     */
    public static void addFacesMessage(String component, String message, String detail) {
        FacesMessage facesMessage = new FacesMessage(message, detail);
        FacesContext.getCurrentInstance().addMessage(component, facesMessage);
    }

    /**
     * Add a JSF message.
     * @param message
     */
    public static void addFacesMessage(String message) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, new FacesMessage(message));
    }

    /**
     *
     */
    public void writeMessage() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nur zur Information", "");
        ctx.addMessage(null, message);
    }

}
