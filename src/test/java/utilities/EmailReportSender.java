package utilities;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Properties;

public class EmailReportSender {
    // Create a logger instance using Log4j2
    public static Logger logger = LogManager.getLogger(EmailReportSender.class);

    public static void sendReport(String recipientEmail, String reportPath) {

        // Sender's email ID and password
        String senderEmail = "ahsan.clariv@gmail.com";
        String senderPassword = "mstv vggb nbmk kehz"; // Consider using environment variables for sensitive data

        logger.info("Sender email: {}", senderEmail); // Log sender's email
        logger.info("Sender password: {}", senderPassword); // Log sender's password (avoid doing this in production)

        // SMTP server configuration
        String host = "smtp.gmail.com";  // Update with your SMTP server address (e.g., for Gmail: smtp.gmail.com)

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Port for Gmail SMTP

        logger.info("SMTP properties: {}", properties); // Log SMTP properties

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Automation Test Report");

            // Create MimeBodyPart for the email content
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find the attached automation test report.");

            // Create MimeBodyPart for the report attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(reportPath));

            // Create a Multipart to combine body and attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Set the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            logger.info("Report sent successfully to {}", recipientEmail); // Log success
        } catch (Exception e) {
            logger.error("Failed to send the email: {}", e.getMessage(), e); // Log error with stack trace
        }

    }
}
