package com.emailSenderApplication.emailSender.emailController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emailSenderApplication.emailSender.emailDao.CustomResponse;
import com.emailSenderApplication.emailSender.emailDao.EmailRequest;
import com.emailSenderApplication.emailSender.emailService.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;

	@PostMapping("/sendEmail")
	public ResponseEntity<CustomResponse> sendEmail(@RequestBody EmailRequest emailRequest){
		
		emailService.sendEmailWithHtml(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage());
		return ResponseEntity.ok(CustomResponse.builder().message("email send successfully")
				 .httpStatus(HttpStatus.OK).success(true).build());
	}
	
	@PostMapping("/send-with-file")
	public ResponseEntity<CustomResponse> sendEmail(@RequestPart EmailRequest emailRequest,@RequestPart MultipartFile file) throws IOException{
		
		emailService.sendEmailWithFile(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage(),file.getInputStream());
		return ResponseEntity.ok(CustomResponse.builder().message("email send successfully")
				 .httpStatus(HttpStatus.OK).success(true).build());
	}
}
