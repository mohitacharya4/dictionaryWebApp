package com.dictionarywebapp.bean;

public class ApiRequest {
	private Words word;
	private UserDetails userDetails;
	private Login login;

	public Words getWord() {
		return word;
	}
	public void setWord(Words word) {
		this.word = word;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	
}
