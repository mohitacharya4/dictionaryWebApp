package com.dictionarywebapp.serviceImpl;

import java.util.List;
import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.Words;
import com.dictionarywebapp.service.UserService;
import com.dictionarywebapp.daoImpl.UserDaoImpl;

public class UserServiceImpl implements UserService{
	UserDaoImpl uDao;
	
	@Override
	public List<Words> getWordsList(ApiRequest apiRequest) {
		uDao = new UserDaoImpl();
		return uDao.getWordsListFromDB(apiRequest);
	}
}
