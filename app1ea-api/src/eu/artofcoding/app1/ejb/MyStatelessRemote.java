/*
 * app1
 * app1ea-ejbapi
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 6/13/12 6:01 PM
 */
package eu.artofcoding.app1.ejb;

import javax.ejb.Remote;

/**
 *
 */
@Remote
public interface MyStatelessRemote {

    /**
     * @param to
     * @return
     */
    String sayHello(String to);

}
