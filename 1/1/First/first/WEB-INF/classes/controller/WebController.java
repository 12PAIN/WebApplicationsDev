package controller;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class WebController extends HttpServlet
{
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  {   
    PrintWriter printWriter = null;
    try
	{
        printWriter = response.getWriter();
	}
	catch (Exception ex) {}
    try
    {         
       printWriter.println("Hello world !!!");       
    }    
    catch (Exception ex)
    {       
       printWriter.println("Error: "+ex.getMessage());     
    }
  }
}