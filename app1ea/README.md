# app1ea

[Java EE Security README](README-jee-security.md)  
[JBoss 7 Datasource README](README-jboss7-datasource.md)  
[JBoss 7 Mail README](README-jboss7-mail.md)

## JDK 7 and OS X

Due to a bug in the OS X poll implementation (see [bug 7159361](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7159361)), add this to your JVM options:

    -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.KQueueSelectorProvider

## JBoss

Use profile standalone-full to support messaging:

$ jboss-as-7.1.1.Final/bin/standalone.sh -c standalone-full.xml

# Resources

* [The Java EE 6 Tutorial, Volume I](http://docs.oracle.com/cd/E19226-01/820-7627/index.html)
