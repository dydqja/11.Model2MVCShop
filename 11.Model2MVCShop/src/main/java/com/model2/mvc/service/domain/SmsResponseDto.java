package com.model2.mvc.service.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
public class SmsResponseDto {
	
    public SmsResponseDto(String smsConfirmNum) {
		// TODO Auto-generated constructor stub
	}
	private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
    private String smsConfirmNum;
}