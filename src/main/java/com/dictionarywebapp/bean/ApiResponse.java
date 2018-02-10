package com.dictionarywebapp.bean;

import java.util.List;

public class ApiResponse {
	
	private String status;
	private String accesstoken;
	private String message;
	private List<Words> wordsList;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Words> getWordsList() {
		return wordsList;
	}
	public void setWordsList(List<Words> wordsList) {
		this.wordsList = wordsList;
	}
}
