/*
 * app1
 * app1ea-ejb
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 6/14/12 10:15 AM
 */
package eu.artofcoding.app1.entity;

import eu.artofcoding.app1.email.EmailDAORemote;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 */
@Stateless
@RolesAllowed({"admin"})
public class EmailDAO extends GenericDAO<EmailEntity> implements EmailDAORemote {

    /**
     * Constructor.
     */
    public EmailDAO() {
        super(EmailEntity.class);
    }

    /*
    @Override
    public Email create(Email entity) throws Exception {
        // From
        EmailAddress fromAddress = resolveEmailAddress(entity.getFromAddress());
        entity.setFromAddress(fromAddress);
        // To
        List<EmailAddress> toAddress = new ArrayList<EmailAddress>();
        for (EmailAddress to : entity.getToAddress()) {
            toAddress.add(resolveEmailAddress(to));
        }
        entity.setToAddress(toAddress);
        // Persis
        return super.create(entity);
    }
    */

    /**
     * Resolve an already existing email address (when using a newly created instance of EmailAddress).
     * @param emailAddress The email address to resolve.
     * @return Persisted EmailAddress entity (with id).
    private EmailAddress resolveEmailAddress(EmailAddress emailAddress) throws Exception {
    if (null != emailAddress && emailAddress.getId() == 0) {
    Map<String, Object> queryParam = new HashMap<String, Object>();
    queryParam.put("address", emailAddress.getAddress());
    EmailAddress _emailAddress = emailAddressDAO.findOne("EmailAddress.findByAddress", queryParam);
    if (null != _emailAddress) emailAddress = _emailAddress;
    }
    if (null == emailAddress) {
    emailAddress = emailAddressDAO.create(emailAddress);
    }
    System.out.println(this + ".resolveEmailAddress(" + emailAddress.getAddress() + ") found #" + emailAddress.getId());
    return emailAddress;
    }
     */

}
