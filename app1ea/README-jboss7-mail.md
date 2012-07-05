# JBoss and JavaMail

## Configuration

Add a mail server TCP/IP address to `<outbound-socket-binding>`s:

    <socket-binding-group name="standard-sockets" default-interface="public">
        ...
        <outbound-socket-binding name="mail-smtp">
            <remote-destination host="localhost" port="25"/>
        </outbound-socket-binding>
        <outbound-socket-binding name="example-smtp">
            <remote-destination host="smtp.gmail.com" port="465"/>
        </outbound-socket-binding>
    </socket-binding-group>

Add a mail session:

    <subsystem xmlns="urn:jboss:domain:mail:1.0">
        <mail-session jndi-name="java:/example-mail">
            <smtp-server outbound-socket-binding-ref="example-smtp" ssl="true">
                <login name="nobody" password="pass"/>
            </smtp-server>
            <pop3-server outbound-socket-binding-ref="example-pop3" port="995" ssl="true">
                <login name="nobody" password="pass"/>
            </pop3-server>
            <imap-server outbound-socket-binding-ref="example-imap" port="993" ssl="true">
                <login name="nobody" password="pass"/>
            </imap-server>
        </mail-session>
    </subsystem>
