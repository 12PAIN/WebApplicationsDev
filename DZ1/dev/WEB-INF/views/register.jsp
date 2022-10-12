<%@ page import="java.io.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body id="body">
        <% if(request.getAttribute("regError") != null){%>
            <script>
                var body = document.getElementById('body');
                var br = document.createElement('p');
                br.innerText = 'Registration Error!';

                body.appendChild(br);
            </script>
        <% } %>

        <script>

            var br1 = document.createElement('p');
            var input1 = document.createElement('input');
            input1.name = 'new_login';
            input1.placeholder = 'New login';
            br1.appendChild(input1);

            var br2 = document.createElement('p');
            var input2 = document.createElement('input');
            input2.name = 'new_password';
            input2.type = 'password';
            input2.placeholder = 'New password';
            br2.appendChild(input2);

            var br3 = document.createElement('p');
            var input3 = document.createElement('input');
            input3.type = 'submit';
            input3.value = 'Register and Login';
            br3.appendChild(input3);

            var form = document.createElement('form');
            form.method = 'post';
            form.action = 'UserCreate';

            form.appendChild(br1);
            form.appendChild(br2);
            form.appendChild(br3);

            body.appendChild(form);

        </script>
    </body>
</html>