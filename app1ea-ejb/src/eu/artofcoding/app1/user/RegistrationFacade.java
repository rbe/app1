package eu.artofcoding.app1.user;

import eu.artofcoding.app1.email.Emailer;
import eu.artofcoding.app1.entity.EmailDAO;
import eu.artofcoding.app1.entity.PersonEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 */
@Stateless
public class RegistrationFacade {
    
    /*
    @EJB
    private Emailer emailer;
    */

    /**
     * Constructor.
     */
    public RegistrationFacade() {
    }

    /**
     * Register a person, Step 1: send email with activation link.
     * @param person Unregistered person.
     */
    public void step1(PersonEntity person) {
        //emailer.sendMail();
    }

}
