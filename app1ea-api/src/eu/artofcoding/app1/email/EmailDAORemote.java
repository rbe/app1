/*
 * app1
 * app1ea-api
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 6/14/12 1:20 PM
 */

package eu.artofcoding.app1.email;

import eu.artofcoding.app1.ejb.GenericDAORemote;
import eu.artofcoding.app1.entity.EmailEntity;

import javax.ejb.Remote;

/**
 *
 */
@Remote
public interface EmailDAORemote extends GenericDAORemote<EmailEntity> {
}
