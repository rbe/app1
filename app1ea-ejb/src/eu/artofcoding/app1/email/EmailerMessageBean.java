package eu.artofcoding.app1.email;

import eu.artofcoding.app1.entity.EmailEntity;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/emailer"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        })
public class EmailerMessageBean implements MessageListener {

    /**
     * SMTP mail server connection for JavaMail.
     */
    @Resource(lookup = "java:/app1-smtp")
    private Session mailSession;

    @EJB
    private EmailDAORemote emailDAO;

    /**
     * Constructor.
     */
    public EmailerMessageBean() {
    }

    /**
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        EmailEntity email = null;
        // ObjectMessage?
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                // Get object, cast to Email
                email = (EmailEntity) objectMessage.getObject();
                if (null != email) {
                    // Create message
                    MimeMessage m = new MimeMessage(mailSession);
                    // From
                    Address from = new InternetAddress(email.getFromAddress());
                    m.setFrom(from);
                    // To
                    for (String toAddress : email.getToAddress().split("[.]")) {
                        m.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toAddress));
                    }
                    // Subject
                    m.setSubject(email.getSubject());
                    m.setSentDate(new java.util.Date());
                    // Set content and MIME type
                    m.setContent(email.getBody(), email.getContentType());
                    // Send mail
                    Transport.send(m);
                    // Set successfully-sent-flag
                    email.setSentSuccessfully(true);
                    email.setSentDate(new Timestamp(new Date().getTime()));
                } else {
                    // Set successfully-sent-flag
                    email.setSentSuccessfully(false);
                    // Reset sent-date as we did not send the mail
                    //email.setSentDate(null);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                try {
                    emailDAO.create(email);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
