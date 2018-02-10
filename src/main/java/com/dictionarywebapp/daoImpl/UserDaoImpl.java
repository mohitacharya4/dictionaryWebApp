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
	
	public List<Words> getWordsListFromDB(ApiRequest request) {
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
}
