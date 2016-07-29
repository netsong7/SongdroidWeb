package com.songdroid.spring;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class MySQLConnectionTest {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://netsong7.synology.me:3306/netsong7?allowMultiQueries=true";
	private static final String USER = "user1";
	private static final String PW = "1111";
			
	
	@Test
	public void testConnection() throws Exception{
		Class.forName(DRIVER);
		
		try(
			Connection con = DriverManager.getConnection(URL, USER, PW)){
			System.out.println(con);
		}catch(Exception e){ e.printStackTrace(); }
	}
}
