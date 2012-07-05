package eu.artofcoding.test.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println(this + " YEEE-ha Starting context " + bundleContext);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println(this + " Stopping context");
    }

}
