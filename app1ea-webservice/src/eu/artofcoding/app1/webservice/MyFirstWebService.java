package eu.artofcoding.app1.webservice;

import eu.artofcoding.app1.ejb.MyStatelessRemote;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://test")
public class MyFirstWebService {

    @EJB
    MyStatelessRemote myStateless;

    @WebMethod
    public String sayHelloWorldFrom(String from) {
        String result = myStateless.sayHello(from);  // "Hello, world, from " + from;
        System.out.println(result);
        return result;
    }

}
