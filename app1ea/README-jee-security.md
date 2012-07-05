# Java EE Security

1. Define roles
1. Add roles common to JEE application to enterprise application deployment descriptor (application.xml)
1. Add roles to EJB application(s), `META-INF/ejb-jar.xml`
1. Add roles to web application(s), `WEB-INF/web-xml`

## User Login

Use a secured URL (see `<security-constraint>` in `<web-module>/WEB-INF/web.xml`) to users. The container will redirect
to the login page if neccessary.

## Configuration

Add roles to enterprise application descriptor, `<ear>/META-INF/application.xml`:

    <application ...>
        [...]
        <security-role>
            <description>Adminstrator</description>
            <role-name>admin</role-name>
        </security-role>
        <security-role>
            <description>User</description>
            <role-name>user</role-name>
        </security-role>
    </application>

## Securing a Web Application

Add the roles which should be available to the web application in the `<web-module>/WEB-INF/web.xml`:

    <?xml version="1.0" encoding="UTF-8"?>
    <web-app ... version="3.0">
        ...
        <security-role>
            <description>Administrative role</description>
            <role-name>admin</role-name>
        </security-role>
        <security-role>
            <description>Ordinary users</description>
            <role-name>user</role-name>
        </security-role>
        ...
    </web-app>

Specify pages to redirect to when a user not authenticated and when a login has failed:

    <web-app ... version="3.0">
        ...
        <login-config>
            <auth-method>FORM</auth-method>
            <realm-name>A login realm</realm-name>
            <form-login-config>
                <form-login-page>/login.faces?error=0</form-login-page>
                <form-error-page>/login.faces?error=1</form-error-page>
            </form-login-config>
        </login-config>
        ...
    </web-app>

Protect resources for users, user must have a login but access does not have to be encrypted:

    <web-app ... version="3.0">
        ....
        <security-constraint>
            <display-name>User security constraint</display-name>
            <web-resource-collection>
                <web-resource-name>Protected resources</web-resource-name>
                <url-pattern>/user/*</url-pattern>
                <http-method>GET</http-method>
                <http-method>POST</http-method>
            </web-resource-collection>
            <auth-constraint>
                <role-name>admin</role-name>
                <role-name>user</role-name>
            </auth-constraint>
            <user-data-constraint>
                <transport-guarantee>NONE</transport-guarantee>
            </user-data-constraint>
        </security-constraint>
        ...
    </web-app>

Resources under /admin should be accessed through HTTPS only:

    <web-app ... version="3.0">
        ....
        <security-constraint>
            <display-name>Admin security constraint</display-name>
            <web-resource-collection>
                <web-resource-name>Protected resources</web-resource-name>
                <url-pattern>/admin/*</url-pattern>
                <http-method>GET</http-method>
                <http-method>POST</http-method>
            </web-resource-collection>
            <auth-constraint>
                <role-name>admin</role-name>
            </auth-constraint>
            <user-data-constraint>
                <transport-guarantee>CONFIDENTIAL</transport-guarantee>
            </user-data-constraint>
        </security-constraint>
    </web-app>

### login.xhtml

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml"
          xmlns:h="http://java.sun.com/jsf/html"
          xmlns:f="http://java.sun.com/jsf/core">
    <h:head>
        <title>Login</title>
    </h:head>
    <h:body>
        <form id="loginForm" action="j_security_check" method="post">
            <h:messages id="messages"/>
            <h:panelGrid columns="2">
                <f:facet name="header">
                    Welcome to app1
                </f:facet>

                <h:outputLabel for="j_username" value="Username: *"/>
                <input id="j_username" name="j_username" type="text"/>

                <h:outputLabel for="j_password" value="Password: *"/>
                <input id="j_password" name="j_password" type="password"/>

                <f:facet name="footer">
                    <h:commandButton value="Login"/>
                </f:facet>
            </h:panelGrid>
        </form>
    </h:body>
    </html>

## Securing an EJB Application

1. Declare roles in EJBs, classes or methods

## Tests

### Login with existing username/password

Consequence:

1. Principal is set
1. See log entry from MySecurityFilter in log file

The log:

    16:01:14,194 INFO  [org.apache.catalina.core.ContainerBase.[jboss.web].[default-host].[/app1ea-web]] (http--127.0.0.1-8080-3) The actual authenticated principals' name is ralf@bensmann.com

### Login with non-existing username

Consequence:

1. Log entry with "Login failure: javax.security.auth.login.FailedLoginException"
1. No principal is set in context

The log:

    15:21:12,643 ERROR [org.jboss.security.authentication.JBossCachedAuthenticationManager] (http--127.0.0.1-8080-1) Login failure: javax.security.auth.login.FailedLoginException: PB00019: Processing Failed:No matching username found in Principals
        at org.jboss.security.auth.spi.DatabaseServerLoginModule.getUsersPassword(DatabaseServerLoginModule.java:186) [picketbox-4.0.7.Final.jar:4.0.7.Final]

### Authentication failure after successful one

Consequence:

1. You saw a log entry showing an successful authentication
1. Log entry with "Login failure: javax.security.auth.login.FailedLoginException"
1. No principal is set in context

The log:

    15:18:24,870 INFO  [org.apache.catalina.core.ContainerBase.[jboss.web].[default-host].[/app1ea-web]] (http--127.0.0.1-8080-1) The actual authenticated principals' name is ralf@bensmann.com
    15:21:12,643 ERROR [org.jboss.security.authentication.JBossCachedAuthenticationManager] (http--127.0.0.1-8080-1) Login failure: javax.security.auth.login.FailedLoginException: PB00019: Processing Failed:No matching username found in Principals
        at org.jboss.security.auth.spi.DatabaseServerLoginModule.getUsersPassword(DatabaseServerLoginModule.java:186) [picketbox-4.0.7.Final.jar:4.0.7.Final]

## JBoss and JAAS

### Web Application Configuration

Add a `<web-module>/WEB-INF/jboss-web.xml` and specify the `<security-domain>` to use:

    <?xml version="1.0" encoding="UTF-8"?>
    <jboss-web>
        <security-domain>java:/jaas/app1Domain</security-domain>
    </jboss-web>


### JBoss Configuration

Create a new security domain:

    <subsystem xmlns="urn:jboss:domain:security:1.1">
        <security-domain name="app1Domain" cache-type="default">
            <authentication>
                <login-module code="org.jboss.security.auth.spi.DatabaseServerLoginModule" flag="required">
                    <module-option name="dsJndiName" value="java:jboss/datasources/app1Datasource"/>
                    <module-option name="principalsQuery" value="select password from person where email_address = ?" />
                    <module-option name="rolesQuery" value="select role_name, 'Roles' from person_role where email_address = ?" />
                </login-module>
            </authentication>
        </security-domain>
    </subsystem>

Take care, `<module-option name="dsJndiName" value="...">` should match `<datasource jndi-name="...">` in `<datasources>` section.

You can use the following options:

* hashAlgorithm
* hashEncoding
* hashStorePassword


    <subsystem xmlns="urn:jboss:domain:security:1.1">
        <security-domain name="app1Domain" cache-type="default">
            <authentication>
                <login-module code="org.jboss.security.auth.spi.DatabaseServerLoginModule" flag="required">
                    ...
                    <module-option name="hashAlgorithm" value="MD5"/>
                    <module-option name="hashEncoding" value="BASE64"/>
                    <module-option name="hashStorePassword" value="true"/>
                </login-module>
            </authentication>
        </security-domain>
    </subsystem>

## Resources

* [Java SE 6 Security](http://java.sun.com/javase/6/docs/technotes/guides/security/)
* [The Java EE 6 Tutorial, Volume I, Part VI, Chapter 23, Introduction to Security in the Java EE Platform](http://docs.oracle.com/cd/E19226-01/820-7627/bnbwj/index.html)
* [The Java EE 6 Tutorial, Volume I, Part VI, Chapter 24, Getting Started Securing Enterprise Applications](http://docs.oracle.com/cd/E19226-01/820-7627/bnbyk/index.html)
* [The Java EE 6 Tutorial, Volume I, Part VI, Chapter 25, Getting Started Securing Web Applications](http://docs.oracle.com/cd/E19226-01/820-7627/bncas/index.html)
