package com.dictionarywebapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.ApiResponse;
import com.dictionarywebapp.bean.Words;
import com.dictionarywebapp.serviceImpl.UserServiceImpl;
import com.dictionarywebapp.utilities.Details;

@RestController  
public class MainController {
	
	ApiResponse apiResponse  = null;
	Details dt = null;
	UserServiceImpl service = null;
	Properties constants =new Properties();
	
	
	@RequestMapping(method = RequestMethod.POST, value ="/getWordsList")
	public ApiResponse getWordsList(@RequestBody ApiRequest apiRequest)
	{	
		ApiResponse apiResponse  = new ApiResponse();
		Details dt = new Details();
		service=new UserServiceImpl();
		constants=dt.getConstants();
		if(apiRequest == null){
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
			return apiResponse;
		}else{
			List<Words> wordsList=new ArrayList<Words>();
			wordsList=service.getWordsList(apiRequest);
			apiResponse.setWordsList(wordsList);
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
			return apiResponse;	
		}
	}
}
