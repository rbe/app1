package eu.artofcoding.app1.cliremotejms;

import eu.artofcoding.app1.helper.JmsHelper;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

/**
 *
 */
public class SendMessage {

    /* Just add jboss-sasl.jar to classpath
    static {
        Security.addProvider(new JBossSaslProvider());
    }
    */

    static final String USER = "rbe";
    static final String PASSWORD = "abc";

    public static void main(String[] args) throws NamingException, JMSException {
        // Setup JNDI
        /* Use this or use jndi.properties on classpath
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        props.put(Context.PROVIDER_URL, "remote://localhost:4447");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.SECURITY_PRINCIPAL, USER);
        props.put(Context.SECURITY_CREDENTIALS, PASSWORD);
        Context initialContext = new InitialContext(props);
        */
        Context initialContext = new InitialContext();
        // Connection
        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("jms/RemoteConnectionFactory");
        Connection connection = connectionFactory.createConnection(USER, PASSWORD);
        // Producer
        Destination destination = (Destination) initialContext.lookup("jms/topic/test");
        Session producerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = producerSession.createProducer(destination);
        TextMessage textMessage = producerSession.createTextMessage();
        textMessage.setText("Hey there, it's " + new Date() + " now.");
        producer.send(textMessage);
        // Disconnect
        // TODO JmsHelper.close(connection, producerSession, producer);
        initialContext.close();
    }


}
