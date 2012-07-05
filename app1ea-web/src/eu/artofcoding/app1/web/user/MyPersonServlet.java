package eu.artofcoding.app1.web.user;

import eu.artofcoding.app1.entity.PersonEntity;
import eu.artofcoding.app1.user.PersonDAORemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet(name = "MyPersonServlet", urlPatterns = {"/person"})
public class MyPersonServlet extends HttpServlet {

    @EJB
    private PersonDAORemote personDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonEntity person = new PersonEntity();
        person.setFirstname("Ralf");
        person.setSurname("Bensmann");
        person.setEmailAddress("ralf@bensmann.com");
        try {
            personDAO.create(person);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        response.getWriter().write("Created " + person);
    }

}
