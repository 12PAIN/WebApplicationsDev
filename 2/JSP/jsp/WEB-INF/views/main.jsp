<%@ page language="java" contentType="text/html; charset=Windows-1251" pageEncoding="Windows-1251" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=Windows-1251">
  <title>Main page</title>
 </head>

 <body>
  <form method="post" action="test">   
     <% 
      Integer n = (Integer)(request.getAttribute("n"));      
     %>    
     <%=""+n%>
     <input type="submit" value="Refresh"></td>
  </form>
 </body>

</html>