package eu.artofcoding.app1.entity;

import eu.artofcoding.app1.user.PersonDAORemote;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 */
@Stateless
@RolesAllowed({"admin"})
public class PersonDAO extends GenericDAO<PersonEntity> implements PersonDAORemote {

    /**
     * Constructor.
     */
    public PersonDAO() {
        super(PersonEntity.class);
    }

}
