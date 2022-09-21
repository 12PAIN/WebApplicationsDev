package controller;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;



public class WebController extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  {    
    PrintWriter printWriter = null;
    try{printWriter = response.getWriter();} catch (Exception ex){}
	
    try
    {	
		String servletPath =(String)(request.getServletPath());
		if(servletPath.equals("/login")){
			pageResponse(request, response);
		}
		else if(servletPath.equals("/add")){
			dbAddRow(request, response);
			pageResponse(request, response);
		}else if(servletPath.equals("/delete")){
			dbDeleteRows(request, response);
			pageResponse(request, response);
		};
		
    }    
    catch (Exception ex){printWriter.println("Error: "+ex.getMessage());}
  }

	protected static void pageResponse(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{

		PrintWriter printWriter = null;
		
    	try{printWriter = response.getWriter();}catch (Exception ex){}
		try{Class.forName("com.mysql.cj.jdbc.Driver");} catch(Exception ex){}
		try{Connection conn = getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count_rows FROM products");
			resultSet.next();
			int rows = resultSet.getInt("count_rows");
			resultSet.close();

			resultSet = statement.executeQuery("SELECT * FROM products");

			int[] id = new int[rows];
			String[] name = new String[rows];
			int[] price = new int[rows];
			String[] description = new String[rows];

    	    int i = 0;


    	    while(resultSet.next()){
    	        id[i] = resultSet.getInt("id");
    	        name[i] = resultSet.getString("ProductName");
    	        price[i] = resultSet.getInt("Price");
    	        description[i] = resultSet.getString("Description");  
    	        i++;
    	    }

			request.setAttribute("rows", Integer.valueOf(rows)); 
			request.setAttribute("id", id);   
			request.setAttribute("name", name);   
			request.setAttribute("price", price);   
			request.setAttribute("description", description);   
		    String view = "main";
		    request.getRequestDispatcher("/WEB-INF/views/"+view+".jsp").forward(request,response);
			conn.close();
		}
		catch (Exception ex)
		{       
			printWriter.println("Error: "+ex.getMessage());     
		}


  	}

	protected static void dbAddRow(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{

		PrintWriter printWriter = null;
		
    	try{printWriter = response.getWriter();}catch (Exception ex){}
		try{Class.forName("com.mysql.cj.jdbc.Driver");} catch(Exception ex){}
		try{Connection conn = getConnection();
			Statement statement = conn.createStatement();

			String name = new String();
			int price=-1;
			String description = new String();

			if(request.getParameter("product_name") != null){
				name = (String)(request.getParameter("product_name"));
				String price_tmp = (String)(request.getParameter("price"));
				price = Integer.parseInt(price_tmp);
				description = (String)(request.getParameter("description"));
			}

			String sqlInsert = "INSERT INTO products(ProductName, Price, Description) Values (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
			preparedStatement.setString(3, description);

			preparedStatement.executeUpdate();

			conn.close();
		}
		catch (Exception ex)
		{       
			printWriter.println("Error: "+ex.getMessage());     
		}


  	}

	protected static void dbDeleteRows(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{

		PrintWriter printWriter = null;
		
    	try{printWriter = response.getWriter();}catch (Exception ex){}
		try{Class.forName("com.mysql.cj.jdbc.Driver");} catch(Exception ex){}
		try{Connection conn = getConnection();
			String sqlDelete = "DELETE FROM products WHERE id = (?)";

			String[] to_delete = request.getParameterValues("to_delete");

			if(to_delete != null){
				PreparedStatement preparedStatement = conn.prepareStatement(sqlDelete);
				for(String to_delete_row: to_delete){

					preparedStatement.setString(1, to_delete_row);

					preparedStatement.executeUpdate();

				}
			}

			conn.close();
		}
		catch (Exception ex)
		{       
			printWriter.println("Error: "+ex.getMessage());     
		}


  	}

    protected static Connection getConnection() throws SQLException, IOException{

		String url = "jdbc:mysql://localhost/LAB_1?serverTimezone=Europe/Moscow&useSSL=false";
    	String username = "root";
    	String password = "starwars123G";

    	return DriverManager.getConnection(url, username, password);
 	}

}

