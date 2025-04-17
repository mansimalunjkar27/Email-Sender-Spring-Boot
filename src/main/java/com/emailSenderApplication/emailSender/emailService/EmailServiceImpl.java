package com.emailSenderApplication.emailSender.emailService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender javaMailSender;

	private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void senEmail(String to, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		simpleMailMessage.setFrom("mansimalunjkar57@gmail.com");
		javaMailSender.send(simpleMailMessage);
		logger.info("Email has been send....");
	}

	@Override
	public void sendEmail(String[] to, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		simpleMailMessage.setFrom("mansimalunjkar57@gmail.com");
		javaMailSender.send(simpleMailMessage);
		logger.info("Email Send to Multiple Users....");

	}

	@Override
	public void sendEmailWithHtml(String to, String subject, String htmlContent) {
		MimeMessage simpleMimeMsg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(simpleMimeMsg, true, "UTF-8");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);
			helper.setFrom("mansimalunjkar57@gmail.com");
			javaMailSender.send(simpleMimeMsg);
			logger.info("Email Send with html content ....");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendEmailWithFile(String to, String subject, String message, File file) {
		MimeMessage simpleMimeMsg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(simpleMimeMsg, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(message);
			// Make sure file exists before attaching
			if (file.exists()) {
				FileSystemResource fileSystemResource = new FileSystemResource(file);
				helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
				logger.info("Attachment added: " + file.getAbsolutePath());
			} else {
				logger.warn("File not found: " + file.getAbsolutePath());
			}
			helper.setFrom("mansimalunjkar57@gmail.com");
			javaMailSender.send(simpleMimeMsg);
			logger.info("Email sent with attachment: " + file.getName());
		} catch (MessagingException e) {
			logger.error("Error sending email with attachment: ", e);
		}
	}
		
		@Override
		public void sendEmailWithFile(String to, String subject, String message, InputStream fileStream) {
		    try {
		        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        
		        helper.setTo(to);
		        helper.setSubject(subject);
		        helper.setText(message);
		        helper.setFrom("mansimalunjkar57@gmail.com");

		        // Convert InputStream to a temporary file
//		        File file=new File("path");
		        File tempFile = File.createTempFile("attachment-", ".tmp");
		        Files.copy(fileStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		        FileSystemResource fileResource = new FileSystemResource(tempFile);
		        helper.addAttachment("attachment.pdf", fileResource);

		        javaMailSender.send(mimeMessage);
		        logger.info("Email sent with InputStream attachment.");

		        // Optionally delete the temp file after sending (careful if async)
		        tempFile.deleteOnExit();

		    } catch (Exception e) {
		        logger.error("Failed to send email with InputStream attachment: ", e);
		    }
		}
	

}
