package com.ibm.cloudoe.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Customer
 */
@WebServlet("/Customer")
public class Customer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static String mysql = "jdbc:mysql://192.155.247.248:3307/d88e2e016980240778956b47b3b9e4ba9";

	private static String user = "uC9JZcsM8THRd";
	private static String password = "pn0A3soYykztM";
    private static String driver = "com.mysql.jdbc.Driver";
	  
	
	/**
     * Default constructor. 
     */
    public Customer() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String val = "no action";
		if(action.equalsIgnoreCase("init"))
		  val = this.createTable();
		if(action.equalsIgnoreCase("insert"))
			  val = this.Insert(request.getParameter("name"), request.getParameter("email"),
					  request.getParameter("telephone"), request.getParameter("pwd"));
		if(action.equalsIgnoreCase("select"))
			  val = this.Select();	
		response.setContentType("text/html");
		response.getWriter().println(val);				
	}
	
	

	public String createTable(){
		  System.out.println("Creating a Mysql Table to Store Java Types!");
		  Connection con = null;
		  
		  try{
			  Class.forName(driver).newInstance();
			  con = DriverManager.getConnection(mysql,user, password);
			  Statement st = con.createStatement();
			  String table = "CREATE TABLE CUSTOMERS (NAME VARCHAR(512), EMAIL VARCHAR(255), TELEPHONE VARCHAR(20), PWD VARCHAR(20))" ;
			  st.executeUpdate(table);
			  System.out.println(table);
			  con.close();
		  } 
		  catch(Exception e){
			  e.printStackTrace();
			  return e.getMessage();
		  }
		  return "OK";
	}
	

		public String Insert(String name, String email, String telephone, String pwd ){
		  System.out.println("INSERT");
		  Connection con = null;
		  
		  try{
			  Class.forName(driver).newInstance();
			  con = DriverManager.getConnection(mysql,user, password);
			  Statement st = con.createStatement();
			  String sql = "INSERT INTO CUSTOMERS (NAME, EMAIL, TELEPHONE, PWD) VALUES(";
			  sql = sql + "'" + name + "', ";
			  sql = sql + "'" + email + "', ";
			  sql = sql + "'" + telephone + "', ";
			  sql = sql + "'" + pwd + "')";
			  st.execute(sql);
			  System.out.println(sql);
			  con.close();
		  } 
		  catch(Exception e){
			  e.printStackTrace();
			  return e.getMessage();
		  }
		  return "OK";
	}



		public String Select(){
		  System.out.println("SELECT");
		  Connection con = null;

		  StringBuffer data = new StringBuffer(); 
		  data.append("customer database - name,email,phone,pwd <BR/> ");

		  try{		  
			  Class.forName(driver).newInstance();
			  con = DriverManager.getConnection(mysql,user, password);
			  Statement st = con.createStatement();
			  String sql = "SELECT * FROM  CUSTOMERS";
			  ResultSet rs = st.executeQuery(sql);
			  while(rs.next()){
				  data.append(rs.getString(1) + ",");
				  data.append(rs.getString(2) + ",");
				  data.append(rs.getString(3) + ",");
				  data.append(rs.getString(4) + "<BR/>");
			  }
			  System.out.println(sql);
			  con.close();
		  } 
		  catch(Exception e){
			  StringWriter errors = new StringWriter();
			  e.printStackTrace(new PrintWriter(errors));
			  return errors.toString();
		  }
		  return data.toString();
	}


		
	
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
