package com.emailSenderApplication.emailSender;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.emailSenderApplication.emailSender.emailService.EmailService;

@SpringBootTest
public class EmailSenderTest {
	
	@Autowired
	private EmailService emailService;

	@Test
	void emailSendTest() {
		System.out.println("sending email...");
		emailService.senEmail("mansimalunjkar2018@gmail.com","Testing Email", "Email send successfully");
	}
	
	@Test
	void sendHtmlInEmail() {
		System.out.println("html sending email...");
		String html="<h1 style='color:red;'>mansi mail send</h1>";
		emailService.sendEmailWithHtml("mansimalunjkar2018@gmail.com","Testing Email",html);

	}
	
	@Test
	void senEmailWithFIle() {
		  File file;
		try {
			file = new File(getClass().getClassLoader().getResource("static/admissionForm.pdf").toURI());
			emailService.sendEmailWithFile("mansimalunjkar2018@gmail.com",
					"Testing Email", "Email send successfully", 
					file);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
