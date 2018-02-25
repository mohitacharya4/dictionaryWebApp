package com.dictionarywebapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dictionarywebapp.bean.ApiResponse;
import com.dictionarywebapp.bean.Words;
import com.dictionarywebapp.serviceImpl.UserServiceImpl;
import com.dictionarywebapp.utilities.Details;

@RestController  
public class MainController {
	
	ApiResponse apiResponse = null;
	Details dt = null;
	UserServiceImpl service = null;
	Properties constants = new Properties();
	
	/*To get the list of words from the Database*/
	@RequestMapping(method = RequestMethod.GET, value = "/getWordsList")
	public ApiResponse getWordsList()
	{	
		ApiResponse apiResponse  = new ApiResponse();
		Details dt = new Details();
		service = new UserServiceImpl();
		constants = dt.getConstants();
		List<Words> wordsList = new ArrayList<Words>();
		wordsList = service.getWordsList();
		apiResponse.setWordsList(wordsList);
		if(apiResponse.getWordsList().isEmpty())
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
		else
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
		
		return apiResponse;	
	}
	/*To get the list of sample words from the Database*/
	@RequestMapping(method = RequestMethod.GET, value = "/getSampleWordsList")
	public ApiResponse getSampleWordsList()
	{	
		ApiResponse apiResponse  = new ApiResponse();
		Details dt = new Details();
		service = new UserServiceImpl();
		constants = dt.getConstants();
		List<Words> wordsList = new ArrayList<Words>();
		wordsList = service.getSampleWordsList();
		apiResponse.setWordsList(wordsList);
		if(apiResponse.getWordsList().isEmpty())
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
		else
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
		
		return apiResponse;	
	}
	/*To register for premium*/
	@RequestMapping(method = RequestMethod.POST, value = "/getPremiumRegistration")
	public ApiResponse getPremiumRegistration()
	{	
		ApiResponse apiResponse  = new ApiResponse();
		Details dt = new Details();
		service = new UserServiceImpl();
		constants = dt.getConstants();
		List<Words> wordsList = new ArrayList<Words>();
		wordsList = service.getSampleWordsList();
		apiResponse.setWordsList(wordsList);
		if(apiResponse.getWordsList().isEmpty())
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
		else
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
		
		return apiResponse;	
	}
}
