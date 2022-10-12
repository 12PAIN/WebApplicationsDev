<%@ page import="java.io.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Table</title>
    </head>
    <body id="body">
         <%
            Integer rows = (Integer)(request.getAttribute("rows"));
            int[] id = (int[])(request.getAttribute("id"));
            String[] name = (String[])(request.getAttribute("name"));
            int[] price = (int[])(request.getAttribute("price"));
            String[] description = (String[])(request.getAttribute("description"));
            String login = (String)(request.getAttribute("login"));
         %>

        <br>You are logined as <%= login %>! Want to exit?
        
        <script>

            var body = document.getElementById('body');
            var form = document.createElement('form');
            form.method = 'post';
            form.action = 'exit';

            var input = document.createElement('input');
            input.type = 'submit';
            input.value = 'Exit';

            form.appendChild(input);
            body.appendChild(form);

            var br = document.createElement('p');
            body.appendChild(br);

        </script>
        

        <script>

            var form1 = document.createElement('form');
            form1.method = 'post';
            form1.action = 'add';

            var input1 = document.createElement('input');
            input1.name = 'product_name';
            input1.placeholder = 'Product Name';
            form1.appendChild(input1);

            var input2 = document.createElement('input');
            input2.name = 'price';
            input2.placeholder = 'Price';
            form1.appendChild(input2);

            var input3 = document.createElement('input');
            input3.name = 'description';
            input3.placeholder = 'Description';
            form1.appendChild(input3);

            var input4 = document.createElement('input');
            input4.type = 'submit';
            input4.value = 'Add';
            form1.appendChild(input1);
            form1.appendChild(input2);
            form1.appendChild(input3);
            form1.appendChild(input4);

            body.appendChild(form1);

            var pRefresh = document.createElement('p');
            var formRefresh = document.createElement('form');
            formRefresh.method = 'post';
            formRefresh.action = 'table';

            var inputRefresh = document.createElement('input');
            inputRefresh.type = 'submit';
            inputRefresh.value = 'Refresh';

            formRefresh.appendChild(inputRefresh);
            pRefresh.appendChild(formRefresh);
            body.appendChild(pRefresh);

        </script>

        <br>

        <table id="table" border="1">
                <% for(int i = 0; i < rows; i++){ %>
                <tr <%="id="+id[i]%>>
                   <td><%= id[i]%></td>
                   <td><%= name[i]%></td>
                   <td><%= price[i]%></td>
                   <td><%= description[i]%></td>
                </tr>
                <% } %>

        </table>

        <script>
            var form3 = document.createElement('form');
            form3.method = 'post';
            form3.action = 'delete';

            var inpDelButton = document.createElement('input');
            inpDelButton.type = 'submit';
            inpDelButton.value = 'Delete';
            form3.appendChild(inpDelButton);

            var table = document.getElementById('table');
            
            form3.appendChild(table);
            body.appendChild(form3);
            

            var tr = document.createElement('tr');

            var th1 = document.createElement('th');
            th1.innerText = 'ID';
            tr.appendChild(th1);

            var th2 = document.createElement('th');
            th2.innerText = 'Product Name';
            tr.appendChild(th2);

            var th3 = document.createElement('th');
            th3.innerText = 'Price';
            tr.appendChild(th3);

            var th4 = document.createElement('th');
            th4.innerText = 'Description';
            tr.appendChild(th4);

            var th5 = document.createElement('th');
            th5.innerText = 'Delete?';
            tr.appendChild(th5);

            table.insertBefore(tr, table.firstChild);

            var tBody = document.getElementsByTagName('tBody')[0];
            console.log(tBody);
            if(tBody != undefined){
                var trTable = tBody.firstChild;
                do{
                    var currentId = trTable.id;
                    var currentInput = document.createElement('input');
                    currentInput.type = 'checkbox';
                    currentInput.name = 'to_delete';
                    currentInput.value = currentId;
                    var td = document.createElement('td');
                    td.appendChild(currentInput);
                    trTable.appendChild(td);
                    trTable = trTable.nextSibling.nextSibling;
                }while(trTable != null);
            }


        </script>

        </form>

    </body>
</html>