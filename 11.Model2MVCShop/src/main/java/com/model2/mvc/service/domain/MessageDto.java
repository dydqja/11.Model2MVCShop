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
//    String content; ==> ������ ���ڸ޽��� ������ ���. 
}