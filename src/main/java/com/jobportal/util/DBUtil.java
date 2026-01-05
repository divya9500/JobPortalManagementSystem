package com.jobportal.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	  public static  Connection getConnection(){
	        try {


	            return DriverManager.getConnection(
	                  "jdbc:mysql://localhost:3306/job_application_db",
	                    "root",
	                    "1818"
	            );
	        }catch (Exception e){

	        }
	        return  null;
	    }
}
