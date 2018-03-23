package com.dictionarywebapp.dao;

import java.util.List;

import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.ApiResponse;
import com.dictionarywebapp.bean.Words;

public interface UserDao {
	public List<Words> getWordsListFromDB();
	public List<Words> getSampleWordsListFromDB();
	public boolean getPremiumMembership(ApiRequest request);
	public boolean addWord(ApiRequest request);
	public boolean deleteWord(int id);
	public boolean updateWord(ApiRequest request);
	public String loginPremiumUser(ApiRequest request);
	public ApiResponse forgetPassword(ApiRequest request);
	public ApiResponse resetPassword(ApiRequest request);
}
