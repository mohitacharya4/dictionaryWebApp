package com.dictionarywebapp.daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.dictionarywebapp.bean.ApiRequest;
import com.dictionarywebapp.bean.Words;
import com.dictionarywebapp.dao.UserDao;
import com.dictionarywebapp.serviceImpl.UserServiceImpl;
import com.dictionarywebapp.utilities.Details;
import com.dictionarywebapp.utilities.EncryptionDecryptionUtility;
import com.dictionarywebapp.utilities.Mail;
import com.dictionarywebapp.bean.ApiResponse;;

public class UserDaoImpl implements UserDao{
	
	private Words word;
	static Connection conn = null;
	String sql;
	String query="";
	Details dt = new Details();
	Properties constants =new Properties();
	UserDaoImpl uDao = null;
	Properties pros = null;
	ApiRequest request =  null;
	ApiResponse response = null;
	UserServiceImpl service = null;
	
	/*Method to get an instance of connection*/
	public Connection getConnection(){
		try{
			Properties props = new Properties();
			props.load(dt.getPropertyfile("/application.properties"));
			String conurl=props.getProperty("url");
			String user=props.getProperty("userdb");
			String pass=props.getProperty("passdb").trim();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( conurl,user,pass );

		}catch( Exception se )
		{	
			se.printStackTrace();
		}
		return conn;
	}
	/*Method to get the list of words from the Database*/
	public List<Words> getWordsListFromDB() {
		List<Words> wordsList=new ArrayList<Words>();
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		
		try{
			conn=getConnection();
			request = new ApiRequest();
			sql="SELECT id, word, meaning, category from words order by word";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				word=new Words();
				word.setId((rs.getInt("id")));
				word.setWord(rs.getString("word"));
				word.setMeaning(rs.getString("meaning"));
				word.setCategory(rs.getString("category"));
				wordsList.add(word);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try {
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wordsList;
	}
	/*Method to get the sample list of words from the Database*/
	public List<Words> getSampleWordsListFromDB() {
		List<Words> wordsList=new ArrayList<Words>();
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		
		try{
			conn=getConnection();
			request = new ApiRequest();
			sql="SELECT id, word, meaning, category from sample_words order by word";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				word=new Words();
				word.setId((rs.getInt("id")));
				word.setWord(rs.getString("word"));
				word.setMeaning(rs.getString("meaning"));
				word.setCategory(rs.getString("category"));
				wordsList.add(word);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try {
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wordsList;
	}
	public boolean getPremiumMembership(ApiRequest request) {
		boolean isUserRegistered = false;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		int count = 0;
		
		try {
			conn=getConnection();
			query = "insert into userdetails (name , password , email, isAdmin)"
					+ " values (?,?,?,?)";
			pst = conn.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			pst.setString(1, request.getUserDetails().getName());
			pst.setString(2, EncryptionDecryptionUtility.encode(request.getUserDetails().getPassword()));
			pst.setString(3, request.getUserDetails().getEmail());
			pst.setBoolean(4, request.getUserDetails().isAdmin());
			count =	pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			isUserRegistered = count > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return isUserRegistered;
	}
	public boolean addWord(ApiRequest request) {
		boolean isWordAdded = false;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		int count = 0;
		
		try {
			conn=getConnection();
			query = "insert into words (word , meaning , category)"
					+ " values (?,?,?)";
			pst = conn.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			pst.setString(1, request.getWord().getWord());
			pst.setString(2, request.getWord().getMeaning());
			pst.setString(3, request.getWord().getCategory());    //if this field is not required then from front-end send any default value like "Miscellaneous"
			count =	pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			isWordAdded = count > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return isWordAdded;
	}
	public boolean deleteWord(int id) {
		boolean isWordDeleted = false;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		int count = 0;
		
		try {
			conn=getConnection();
			query = "delete from words where id = ?";
			pst = conn.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			pst.setInt(1, id);
			count =	pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			isWordDeleted = count > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return isWordDeleted;
	}
	public boolean updateWord(ApiRequest request) {
		boolean isWordUpdated = false;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		int count = 0;
		
		try {
			conn=getConnection();
			query = "UPDATE words" + 
					" SET word = ?, meaning = ?, category = ?" + 
					" WHERE id = ?";
			pst = conn.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			pst.setString(1, request.getWord().getWord());
			pst.setString(2, request.getWord().getMeaning());
			pst.setString(3, request.getWord().getCategory());    //if this field is not required then from front-end send any default value like "Miscellaneous"			count =	pst.executeUpdate();
			pst.setInt(4, request.getWord().getId());
			count =	pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			isWordUpdated = count > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return isWordUpdated;
	}
	public String loginPremiumUser(ApiRequest request) {
		boolean isUserPremium = false;
		boolean isUserAdmin = false;
		ResultSet rs = null;
		ResultSet rsAdmin = null;
		PreparedStatement pst = null;
		PreparedStatement pstAdmin = null;
		Connection conn = null;
		int count = 0;
		int countAdmin = 0;
		String queryForPremium = "";
		String queryForAdmin = "";
		try {
			conn=getConnection();
			queryForPremium = "select count(1) from userdetails" + 
					" WHERE email = ? and password = ?";
			queryForAdmin = "select count(1) from userdetails" + 
					" WHERE email = ? and password = ? and isAdmin = ?";
			pst = conn.prepareStatement(queryForPremium,pst.RETURN_GENERATED_KEYS);
			pstAdmin = conn.prepareStatement(queryForAdmin,pstAdmin.RETURN_GENERATED_KEYS);
			pst.setString(1, request.getLogin().getEmail());
			pst.setString(2, EncryptionDecryptionUtility.encode(request.getLogin().getPassword()));
			pstAdmin.setString(1, request.getLogin().getEmail());
			pstAdmin.setString(2, EncryptionDecryptionUtility.encode(request.getLogin().getPassword()));
			pstAdmin.setInt(3, 1);
			
			rs = pst.executeQuery();
			rsAdmin = pstAdmin.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			} 
			isUserPremium = count > 0 ? true : false;
			if(rsAdmin.next()) {
				countAdmin = rsAdmin.getInt(1);
			} 
			isUserAdmin = countAdmin > 0 ? true : false;
			if(isUserPremium) {
				if(isUserAdmin) {
					return "Admin";
				}
				return "Premium";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return "LoginFailed";
	}
	/*It will set the OTP in the DB and will send it to the  user mail also.*/
	public ApiResponse forgetPassword(ApiRequest request) {
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		int count = 0;
		String otp = null;
		constants = dt.getConstants();
		service = new UserServiceImpl();
		response = new ApiResponse();
		try {
			conn=getConnection();
			query = "UPDATE userdetails" + 
					" SET otp = ?" + 
					" WHERE email = ?";
			otp = service.generateOTP();
			pst = conn.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			pst.setString(1, otp);
			pst.setString(2, request.getUserDetails().getEmail());
			count =	pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if(count != 1) {
				response.setStatus(constants.getProperty("API_STATUS_FAILURE"));
				response.setMessage(constants.getProperty("OTP_UPDATE_FAILURE"));
			}
			else {
				response = Mail.sendMailToUser(request.getUserDetails().getEmail(), otp, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return response;
	}
	/*To check whether the OTP is valid for provided mail or not*/
	public boolean checkOtpValidity(ApiRequest request) {
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		int count = 0;
		constants = dt.getConstants();
		service = new UserServiceImpl();
		boolean isOtpValid = false;
		try {
			conn=getConnection();
			query = "SELECT count(1) from userdetails" + 
					" where otp = ?" + 
					" and email = ?";
			pst = conn.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			pst.setString(1, request.getUserDetails().getOtp());
			pst.setString(2, request.getUserDetails().getEmail());
			rs = pst.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			if(count == 1) {
				isOtpValid = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return isOtpValid;
	}
	/*It will reset the password if the OTP is valid for mailId provided*/
	public ApiResponse resetPassword(ApiRequest request) {
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		int count = 0;
		constants = dt.getConstants();
		service = new UserServiceImpl();
		boolean isOtpValid = false;
		response = new ApiResponse();
		try {
			isOtpValid = checkOtpValidity(request);
			conn=getConnection();
			query = "UPDATE userdetails" + 
					" SET password = ?" + 
					" WHERE email = ?";
			if(isOtpValid) {
				pst = conn.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
				pst.setString(1, EncryptionDecryptionUtility.encode(request.getUserDetails().getPassword()));
				pst.setString(2, request.getUserDetails().getEmail());
				count =	pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				if(count != 1) {
					response.setStatus(constants.getProperty("API_STATUS_FAILURE"));
					response.setMessage(constants.getProperty("PASSWORD_UPDATE_FAILURE"));
				}else {
					response.setStatus(constants.getProperty("API_STATUS_SUCCESS"));
					response.setMessage(constants.getProperty("PASSWORD_UPDATE_SUCCESS"));				
				}
			}else {
				response.setStatus(constants.getProperty("API_STATUS_FAILURE"));
				response.setMessage(constants.getProperty("INVALID_OTP"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(rs != null) rs.close();
				if(pst != null) pst.close();
				if(conn != null) conn.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return response;
	}

	
}
