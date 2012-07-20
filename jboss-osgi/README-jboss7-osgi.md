# JBoss

## Configure the OSGi Subsystem

Add some capabilities by adding <capability> elements.
Lets add OSGi Blueprint support through Apache Aries: 

    <subsystem xmlns="urn:jboss:domain:osgi:1.2" activation="lazy">
        ...
        <capabilities>
            ...
            <capability name="org.apache.aries:org.apache.aries.util:0.4"/>
            <capability name="org.apache.aries.proxy:org.apache.aries.proxy:0.4"/>
            <capability name="org.apache.aries.blueprint:org.apache.aries.blueprint:0.4"/>
            <capability name="org.jboss.osgi.xerces:jbosgi-xerces:2.10.0"/>
        </capabilities>
    </subsystem>



    <subsystem xmlns="urn:jboss:domain:osgi:1.2" activation="lazy">
        ...
        <capabilities>
            ...
            <capability name="org.jboss.osgi.xerces:jbosgi-xerces:2.10.0"/>
        </capabilities>
    </subsystem>

Enable the OSGi console:

    <subsystem xmlns="urn:jboss:domain:osgi:1.2" activation="lazy">
        ...
        <capabilities>
            ...
            <capability name="org.ops4j.pax.web:pax-web-jsp:1.1.2" startlevel="1"/>
            <capability name="org.ops4j.pax.web:pax-web-jetty-bundle:1.1.2" startlevel="1"/>
            <capability name="org.apache.felix:org.apache.felix.webconsole:3.1.6.SP1" startlevel="1"/>
        </capabilities>
    </subsystem>
