package com.dictionarywebapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dictionarywebapp.bean.ApiRequest;
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
		apiResponse  = new ApiResponse();
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
		apiResponse  = new ApiResponse();
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
	public ApiResponse getPremiumRegistration(@RequestBody ApiRequest request)
	{	
		apiResponse  = new ApiResponse();
		boolean isUserRegistered = false;
		service = new UserServiceImpl();
		dt = new Details();
		constants = dt.getConstants();

		isUserRegistered = service.getPremiumMembership(request);
		if(isUserRegistered)
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
		else
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
		
		return apiResponse;
	}
	/*To add a new word in the list of words*/
	@RequestMapping(method = RequestMethod.POST, value = "/addNewWord")
	public ApiResponse addWord(@RequestBody ApiRequest request)
	{	
		apiResponse  = new ApiResponse();
		boolean isWordAdded = false;
		service = new UserServiceImpl();
		dt = new Details();
		constants = dt.getConstants();

		isWordAdded = service.addWord(request);
		if(isWordAdded)
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
		else
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
		
		return apiResponse;
	}
	/*To delete a word in the list of words*/
	@RequestMapping(method = RequestMethod.GET, value = "/deleteSelectedWord")
	public ApiResponse deleteWord(@RequestParam(value = "id") int id)
	{	
		apiResponse  = new ApiResponse();
		boolean isWordDeleted = false;
		service = new UserServiceImpl();
		dt = new Details();
		constants = dt.getConstants();

		isWordDeleted = service.deleteWord(id);
		if(isWordDeleted)
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
		else
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
		
		return apiResponse;
	}
	/*To delete a word in the list of words*/
	@RequestMapping(method = RequestMethod.POST, value = "/updateSelectedWord")
	public ApiResponse updateWord(@RequestBody ApiRequest request)
	{	
		apiResponse  = new ApiResponse();
		boolean isWordUpdated = false;
		service = new UserServiceImpl();
		dt = new Details();
		constants = dt.getConstants();

		isWordUpdated = service.updateWord(request);
		if(isWordUpdated)
			apiResponse.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
		else
			apiResponse.setStatus(constants.getProperty("API_STATUS_FAILURE"));
		
		return apiResponse;
	}
}
