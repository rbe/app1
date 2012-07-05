/*
 * app1
 * app1ea-api
 * Copyright (C) 2011-2012 art of coding UG, http://www.art-of-coding.eu
 *
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 *
 * rbe, 6/14/12 12:51 PM
 */
package eu.artofcoding.app1.ejb;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User: rbe
 * Date: 13.06.12
 * Time: 17:26
 */
@Remote
public interface GenericDAORemote<T extends Serializable> {

    T create(T entity) throws Exception;

    T update(T entity);

    boolean delete(T entity);

    T findById(int id);

    List<T> findAll();

    T findOne(String namedQuery, Map<String, Object> parameters);

}
