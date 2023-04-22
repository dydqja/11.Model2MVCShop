package com.model2.mvc.service.domain;



import java.util.List;


public class SmsRequestDto {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
	private List<MessageDto> messages;
	
	public SmsRequestDto(String type, String contentType, String countryCode, String from, String content, List<MessageDto> messages) {
		this.type = type;
		this.contentType = contentType;
		this.countryCode = countryCode;
		this.from = from;
		this.content = content;
		this.messages = messages;
	}

	public String getType() {
		return type;
	}

	public String getContentType() {
		return contentType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getFrom() {
		return from;
	}

	public String getContent() {
		return content;
	}

	public List<MessageDto> getMessages() {
		return messages;
	}
	
	public static class Builder {
		
		private String type;
		private String contentType;
	    private String countryCode;
	    private String from;
	    private String content;
	    private List<MessageDto> messages;
	    
	    public Builder type(String type) {
	    	this.type = type;
	    	return this;	    	
	    }
		
	    public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder messages(List<MessageDto> messages) {
            this.messages = messages;
            return this;
        }
        
        public SmsRequestDto build() {
        	return new SmsRequestDto(type, contentType, countryCode, from, content, messages);
        }
	}	
}