<%@ page import="java.io.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--<html>
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

</html>-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP</title>
    </head>
    <body>
         <%
         Integer rows = (Integer)(request.getAttribute("rows"));
         int[] id = (int[])(request.getAttribute("id"));
         String[] name = (String[])(request.getAttribute("name"));
         int[] price = (int[])(request.getAttribute("price"));
         String[] description = (String[])(request.getAttribute("description"));
         %>

        <form method="post" action="add">
            <input name="product_name" placeholder="Product Name"><input name="price" placeholder="Price"><input name="description" placeholder="Description"><input type="submit" value="add">
        </form>

         <br><form method="post" action="delete">
        <input type="submit" value="delete">
        <table border="1">
                <tr>
                   <th>ID</th>
                   <th>Product Name</th>
                   <th>Price</th>
                   <th>Description</th>
                   <th>Delete?</th>
                </tr>
                <% for(int i = 0; i < rows; i++){ %>
                <tr>
                   <td><%= id[i]%></td>
                   <td><%= name[i]%></td>
                   <td><%= price[i]%></td>
                   <td><%= description[i]%></td>
                   <td>
                    
                    <input type="checkbox" name="to_delete" <%="value="+id[i]%>>

                   </td>
            
                </tr>
                <% } %>

        </table>


        </form>

    </body>
</html>