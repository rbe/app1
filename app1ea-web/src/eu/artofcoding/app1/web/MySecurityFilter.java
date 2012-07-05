package eu.artofcoding.app1.web;

import eu.artofcoding.app1.helper.JeeHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

/**
 *
 */
@WebFilter(filterName = "MySecurityFilter", urlPatterns = {"/*"})
public class MySecurityFilter implements Filter {

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        Principal principal = httpServletRequest.getUserPrincipal();
        if (null != principal) {
            JeeHelper.log(httpServletRequest.getServletContext(), "The actual authenticated principals' name is " + principal.getName());
        }
        chain.doFilter(req, resp);
    }

}
