package com.dictionarywebapp.service;

import java.util.List;

import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.Words;

 public interface UserService {
	public List<Words> getWordsList(ApiRequest apiRequest);

}
