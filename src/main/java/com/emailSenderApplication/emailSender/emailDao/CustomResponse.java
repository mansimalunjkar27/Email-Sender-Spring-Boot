package com.emailSenderApplication.emailSender.emailDao;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomResponse {

	private String message;
	 private HttpStatus httpStatus;
	private boolean success=false;
}
