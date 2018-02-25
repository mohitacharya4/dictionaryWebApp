package com.dictionarywebapp.dao;

import java.util.List;

import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.Words;

public interface UserDao {
	public List<Words> getWordsListFromDB();
	public List<Words> getSampleWordsListFromDB();
}
