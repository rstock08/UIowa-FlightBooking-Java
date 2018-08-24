<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/28/2017
  Time: 8:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>
<html>
<head>
</head>
<body>
</body>
</html>
