package eu.artofcoding.app1.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 */
@Stateless
@RolesAllowed({"admin", "user"})
public class MyStateless implements MyStatelessRemote {

    public MyStateless() {
    }

    @Override
    public String sayHello(String to) {
        return "EJB: Hello " + to;
    }

}
