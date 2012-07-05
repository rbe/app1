package eu.artofcoding.app1.web.user;

import eu.artofcoding.app1.entity.PersonEntity;
import eu.artofcoding.app1.user.PersonDAORemote;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;
import java.io.Serializable;

/**
 * Manage details of a person.
 */
public class PersonBean implements Serializable {

    @EJB
    private PersonDAORemote personDAO;

    private PersonEntity person;

    public PersonBean() {
        person = new PersonEntity();
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    /**
     * @return
     */
    public String createAccount() {
        final String personName = person.getFirstname() + " " + person.getSurname();
        System.out.println("Creating account for " + person.getEmailAddress() + "/" + person.getPassword().length());
        try {
            personDAO.create(person);
            return "thankyou";
        } catch (PersistenceException e) {
            return "adatabaseerror";
        } catch (Exception e) {
            return "error";
        }
    }

}
