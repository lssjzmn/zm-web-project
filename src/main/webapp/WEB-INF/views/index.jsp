<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/css/main.css"/>
    <link rel="stylesheet" type="text/css" media="all"
          href="${pageContext.request.contextPath}/css/buttons.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/buz/login.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/libs/niceadmin/jquery-1.8.3.min.js"></script>

</head>
<body>
<h2>Hello World!</h2>
<br/>
<a href="/entry/welcome/10086.html">跳转login</a>
<br/>
<button id="loginBtn" class="button-primary" onclick="loginEvent()">login</button>
</body>
</html>
