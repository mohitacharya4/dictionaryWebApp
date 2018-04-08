package com.dictionarywebapp.serviceImpl;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.ApiResponse;
import com.dictionarywebapp.bean.Words;
import com.dictionarywebapp.daoImpl.UserDaoImpl;
import com.dictionarywebapp.service.UserService;
import com.dictionarywebapp.utilities.Details;
import com.dictionarywebapp.utilities.Mail;

public class UserServiceImpl implements UserService{
	UserDaoImpl uDao;
	Details dt;
	
	@Override
	public List<Words> getWordsList() {
		uDao = new UserDaoImpl();
		return uDao.getWordsListFromDB();
	}
	@Override
	public List<Words> getSampleWordsList() {
		uDao = new UserDaoImpl();
		return uDao.getSampleWordsListFromDB();
	}
	@Override
	public boolean getPremiumMembership(ApiRequest request) {
		uDao = new UserDaoImpl();
		return uDao.getPremiumMembership(request);
	}
	@Override
	public boolean addWord(ApiRequest request) {
		uDao = new UserDaoImpl();
		return uDao.addWord(request);
	}
	@Override
	public boolean deleteWord(int id) {
		uDao = new UserDaoImpl();
		return uDao.deleteWord(id);
	}
	@Override
	public boolean updateWord(ApiRequest request) {
		uDao = new UserDaoImpl();
		return uDao.updateWord(request);
	}
	@Override
	public String loginPremiumUser(ApiRequest request) {
		uDao = new UserDaoImpl();
		return uDao.loginPremiumUser(request);
	}
	@Override
	public ApiResponse forgetPassword(ApiRequest request) {
		uDao = new UserDaoImpl();
		return uDao.forgetPassword(request);
	}
	@Override
	public String generateOTP() {
		Properties props=new Properties();
		Details dt = new Details();
		String  otpString =null;
		try{
			dt=new Details();
			props.load(dt.getPropertyfile("/application.properties"));
			int len = Integer.parseInt(props.getProperty("length"));
			// Using numeric values
			String numbers = props.getProperty("numbers");
			// Using random method
			Random rndm_method = new Random();
			char[] otp = new char[len];

			for (int i = 0; i < len; i++)
			{
				otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
			}
			otpString=String.valueOf(otp);
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return otpString;
	}
	public ApiResponse resetPassword(ApiRequest request) {
		uDao = new UserDaoImpl();
		return uDao.resetPassword(request);
	}
	public ApiResponse sendSuggestionWordMailToAdmin(String word) {
		return Mail.sendSuggestionMailToAdmin(word);
	}
}
