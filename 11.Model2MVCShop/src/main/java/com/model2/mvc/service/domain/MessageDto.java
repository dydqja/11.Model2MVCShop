package com.model2.mvc.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
public class MessageDto {
    private String to;
//    String content; ==> 별도의 문자메시지 보낼때 사용. 
}