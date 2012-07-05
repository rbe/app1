/*
 * app1
 * app1ea-cdi
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 6/13/12 6:41 PM
 */
package eu.artofcoding.app1.cdi;

import javax.inject.Named;

@Named
public class MyFirstCDIBean {

    public MyFirstCDIBean() {
    }

    public String sayHelloWorldFrom(String from) {
        return "Hey CDI, " + from;
    }

}
