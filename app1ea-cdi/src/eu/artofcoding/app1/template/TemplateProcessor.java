/*
 * app1
 * app1ea-cdi
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 6/13/12 6:41 PM
 */
package eu.artofcoding.app1.template;

import freemarker.cache.*;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This template processor acts as a facade to FreeMarker template engine.
 */
public class TemplateProcessor {

    /**
     * FreeMarker configuration.
     */
    private Configuration configuration;

    /**
     * Template loader.
     */
    private List<TemplateLoader> templateLoaders;

    /**
     * Factory method to create a simple template processor.
     * Use one of overloaded TemplateProcessor#addTemplateLoader(...) methods to add template sources.
     * @return Simple template processor.
     */
    public TemplateProcessor() {
        templateLoaders = new ArrayList<TemplateLoader>();
        // Create FreeMarker configuration
        configuration = new Configuration();
        // Specify how templates will see the data-model. This is an advanced topic...
        configuration.setObjectWrapper(new DefaultObjectWrapper());
    }

    /**
     * Add directories to use for templates.
     * @param templateDirectory
     * @throws URISyntaxException
     * @throws IOException
     */
    public void addTemplateLoader(URL[] templateDirectory) throws URISyntaxException, IOException {
        if (null != templateDirectory) {
            List<FileTemplateLoader> fileTemplateLoaders = new ArrayList<FileTemplateLoader>(templateDirectory.length);
            for (URL url : templateDirectory) {
                System.out.println("adding " + url.toURI().toASCIIString());
                fileTemplateLoaders.add(new FileTemplateLoader(new File(url.toURI())));
            }
            templateLoaders.addAll(fileTemplateLoaders);
        }
    }

    /**
     * Add classes (optional with relative paths) to use for templates.
     * @param classes Map with key=Class, value=String containing a path, see {@link ClassTemplateLoader}.
     */
    public void addTemplateLoader(Map<Class, String> classes) {
        if (null != classes) {
            List<ClassTemplateLoader> classTemplateLoaders = new ArrayList<ClassTemplateLoader>(classes.size());
            for (Class c : classes.keySet()) {
                classTemplateLoaders.add(new ClassTemplateLoader(c, classes.get(c)));
            }
            templateLoaders.addAll(classTemplateLoaders);
        }
    }

    /**
     * Add a servlet context.
     * @param servletContext The servlet context.
     */
    public void addTemplateLoader(ServletContext servletContext, String path) {
        if (null != servletContext) {
            templateLoaders.add(new WebappTemplateLoader(servletContext, path));
        }
    }

    /**
     * Create a list with all previously added template loaders and add them to configuration.
     */
    private void makeTemplateLoader() {
        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(templateLoaders.toArray(new TemplateLoader[templateLoaders.size()]));
        configuration.setTemplateLoader(multiTemplateLoader);
    }

    /**
     * Render template (UTF-8), output will be accessible through provided Writer instance.
     * @param templateName
     * @param root
     * @param out
     * @throws IOException
     * @throws TemplateException
     */
    public void renderTemplate(String templateName, Map<String, Object> root, Writer out) throws IOException, TemplateException {
        makeTemplateLoader();
        Template temp = configuration.getTemplate(templateName, "UTF-8");
        temp.process(root, out);
    }

    /**
     * Render a template, see {@link #renderTemplate(String, java.util.Map, java.io.Writer)} and return a String.
     * @param templateName
     * @param root
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String renderTemplateToString(String templateName, Map<String, Object> root) throws IOException, TemplateException {
        Writer o = new StringWriter();
        renderTemplate(templateName, root, o);
        o.flush();
        return o.toString();
    }

    /*
    public static void main(String[] args) throws Exception {
        // Create the root hash
        final Map<String, Object> root = new HashMap<String, Object>();
        root.put("user", "Big Joe");
        root.put("registrationUrl", "http://www.example.com/registration/complete/abc123");
        //
        URL[] templateDirectory = new URL[]{TemplateProcessor.class.getResource(".")};
        TemplateProcessor templateProcessor = new TemplateProcessor();
        templateProcessor.addTemplateLoader(templateDirectory);
        System.out.println("o=" + templateProcessor.renderTemplateToString("test_de.ftl", root));
        final OutputStreamWriter out = new OutputStreamWriter(System.out, Charset.forName("UTF-8"));
        templateProcessor.renderTemplate("test_de.ftl", root, out);
        out.flush();
    }
    */

}
