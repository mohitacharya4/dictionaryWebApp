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
import com.dictionarywebapp.utilities.Details;


public class UserDaoImpl implements UserDao{
	
	private Words word;
	static Connection conn = null;
	String sql;
	String query="";
	Details dt = new Details();
	Properties constant =new Properties();
	UserDaoImpl uDao = null;
	Properties pros = null;
	ApiRequest request =  null;
	
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
	/*Method to get the list of words form the Database*/
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
			pst.setString(2, request.getUserDetails().getPassword());
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
}
