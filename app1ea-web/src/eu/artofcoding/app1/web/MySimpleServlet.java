package eu.artofcoding.app1.web;

import eu.artofcoding.app1.cdi.MyFirstCDIBean;
import eu.artofcoding.app1.ejb.MyStatelessRemote;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MySimpleServlet", urlPatterns = {"/simple"})
public class MySimpleServlet extends HttpServlet {

    @EJB
    private MyStatelessRemote myStateless;

    @Inject
    private MyFirstCDIBean myFirstCDIBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        String name = request.getParameter("name");
        if (null == name) {
            writer.write("Please give parameter 'name'");
        } else {
            String s1 = myStateless.sayHello(name);
            String s2 = "No CDI bean!";
            if (null != myFirstCDIBean) {
                s2 = myFirstCDIBean.sayHelloWorldFrom(name);
            }
            writer.write(s1 + "\n");
            writer.write(s2 + "\n");
        }
        writer.close();
    }

}
