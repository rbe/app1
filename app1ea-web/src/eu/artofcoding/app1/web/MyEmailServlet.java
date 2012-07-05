package eu.artofcoding.app1.web;

import eu.artofcoding.app1.email.EmailerRemote;
import eu.artofcoding.app1.template.TemplateProcessor;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 */
@WebServlet(name = "MyEmailServlet", urlPatterns = {"/email"})
public class MyEmailServlet extends HttpServlet {

    @Inject
    private TemplateProcessor templateProcessor;

    @EJB
    private EmailerRemote emailer;

    /**
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        // Setup TemplateProcessor to load templates from web/public
        templateProcessor.addTemplateLoader(getServletContext(), "public");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // Data model for template
            final Map<String, Object> root = new HashMap<String, Object>();
            root.put("user", "Big Joe");
            root.put("registrationUrl", "http://www.example.com/registration/complete/abc123");
            // Render template depending on request locale
            Locale locale = request.getLocale();
            String body = templateProcessor.renderTemplateToString("registration_step1_" + locale.getLanguage() + ".ftl", root);
            // Get recipients
            Set<String> toAddress = new HashSet<>();
            String[] recipients = request.getParameterValues("recipient");
            if (null != recipients) {
                for (String recipient : recipients) {
                    toAddress.add(recipient);
                }
                // Send email
                emailer.sendMail("alice@example.com", toAddress, "This is an email!", body, "text/html; charset=utf8");
                // Write response
                out.write("Sent test mail!\n");
                out.write(body);
            } else {
                throw new IllegalStateException("No recipients!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Write response
            out.write("Error sending test mail!\n");
            out.write(e.toString());
        } finally {
            out.close();
        }
    }

}
