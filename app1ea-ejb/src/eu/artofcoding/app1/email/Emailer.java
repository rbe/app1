package eu.artofcoding.app1.email;

import eu.artofcoding.app1.entity.EmailEntity;
import eu.artofcoding.app1.helper.JmsHelper;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import java.util.Set;

/**
 * Send an email by creating an object message with an {@link EmailEntity} entity instance and sending it to a JMS destination.
 */
@Stateless
public class Emailer implements EmailerRemote {

    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/topic/emailer")
    private Topic topic;

    /**
     * Constructor.
     */
    public Emailer() {
    }

    /**
     * Send an email.
     * @param fromAddress Sender address.
     * @param toAddress   Recipient address(es).
     * @param subject     The subject.
     * @param body        The body.
     * @param contentType The content type, e.g. "text/plain" or "text/html".
     */
    @Override
    public void sendMail(String fromAddress, Set<String> toAddress, String subject, String body, String contentType) {
        JmsHelper jmsHelper = null;
        try {
            jmsHelper = JmsHelper.createTopicProducer(connectionFactory, topic);
            ObjectMessage message = jmsHelper.getSession().createObjectMessage();
            // Create Email entity instance and populate it with data
            EmailEntity email = new EmailEntity();
            email.setFromAddress(fromAddress);
            StringBuilder toAddressBuilder=new StringBuilder();
            for (String to:toAddress) {
                toAddressBuilder.append(to).append(",");
            }
            email.setToAddress(toAddressBuilder.toString());
            email.setSubject(subject);
            email.setBody(body);
            email.setContentType(contentType);
            // Set object in message and send it
            message.setObject(email);
            jmsHelper.getMessageProducer().send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (null != jmsHelper) {
                jmsHelper.close();
            }
        }
    }

    /**
     * Convenience method for sending a plain text message.
     * @param fromAddress Sender address.
     * @param toAddress   Recipient address(es).
     * @param subject     The subject.
     * @param body        The plain text body.
     */
    @Override
    public void sendPlainTextMail(String fromAddress, Set<String> toAddress, String subject, String body) {
        sendMail(fromAddress, toAddress, subject, body, "text/plain");
    }

}
