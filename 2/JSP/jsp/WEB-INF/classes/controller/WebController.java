package controller;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



public class WebController extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  {    
    PrintWriter printWriter = null;
    try	
	{			
        printWriter = response.getWriter();
	}
	catch (Exception ex)
	{		
	}
	
	
    try
    {	
	   HttpSession session = request.getSession(true);	   
	   Object nn = session.getAttribute("n");
	   int n = 0;
       if (nn!=null)
	   {
		   n = ((Integer)nn).intValue();
	   }		   
	   n=n+1;	   	   	   	   
	   session.setAttribute("n",Integer.valueOf(n));           
	   
	   
	   
	   request.setAttribute("n",new Integer(n));        
	   String view = "main";
	   request.getRequestDispatcher("/WEB-INF/views/"+view+".jsp").forward(request,response);  	   
	  
    }    
    catch (Exception ex)
    {       
       printWriter.println("Error: "+ex.getMessage());     
    }
  }
}