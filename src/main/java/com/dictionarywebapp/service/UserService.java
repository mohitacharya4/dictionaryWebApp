package com.dictionarywebapp.service;

import java.util.List;

import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.Words;

 public interface UserService {
	public List<Words> getWordsList();
	public List<Words> getSampleWordsList();
	public boolean getPremiumMembership(ApiRequest request); 
	public boolean addWord(ApiRequest request);
	public boolean deleteWord(int id);
	public boolean updateWord(ApiRequest request);
	public String loginPremiumUser(ApiRequest request);
}
