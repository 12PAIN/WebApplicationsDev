<%@ page import="java.io.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body id="body">
        <% if(request.getAttribute("loginError") != null){%>
            <script>
                var body = document.getElementById("body");
                var br = document.createElement("p");
                br.innerText="Login Error!";
            </script>
        <% } %>
        <script>

            var form1 = document.createElement('form');
            form1.method = 'post';
            form1.action = 'table';

            var br1 = document.createElement('p');
            var input1 = document.createElement('input');
            input1.name = 'login';
            input1.placeholder = 'Your Login';
            br1.appendChild(input1);

            var br2 = document.createElement('p');
            var input2 = document.createElement('input');
            input2.name = 'password';
            input2.type = 'password';
            input2.placeholder = 'Your Password';
            br2.appendChild(input2);

            var br3 = document.createElement('p');
            var input3 = document.createElement('input');
            input3.type = 'submit';
            input3.value = 'Login';
            br3.appendChild(input3);

            form1.appendChild(br1);
            form1.appendChild(br2);
            form1.appendChild(br3);

            var form2 = document.createElement('form');
            form2.method = 'post';
            form2.action = 'register';

            var input4 = document.createElement('input');
            input4.type = 'submit';
            input4.value = 'Register';

            form2.appendChild(input4);

            body.appendChild(form1);
            body.appendChild(form2);

        </script>
    </body>
</html>