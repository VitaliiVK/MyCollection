<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>MyCollection</title>
    <c:url value="/images/favicon.ico" var="favicon_Url" />
    <link rel="shortcut icon" href="${favicon_Url}" type="image/ICO">
    <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
    <c:url value="/public/bootstrap-3.3.2/css/bootstrap.min.css" var="bootstrap_Url" />
    <link rel="stylesheet" href="${bootstrap_Url}">
</head>
<body>
    <div align="center">

        <c:if test="${registration ne null}">
            <div class="alert alert-success" role="alert">Registration is successfully!</div>
        </c:if>
        <c:if test="${param.error ne null}">
            <div class="alert alert-danger" role="alert">Wrong login or password!</div>
        </c:if>

        <h3>Login form</h3>

        <!-- url from SecurityConfig-->
        <c:url value="/j_spring_security_check" var="loginUrl" />

        <form action="${loginUrl}" method="POST" role="form">
            <div class="form-inline">
                <label>Login:</label><br/>
                <input type="text" name="j_login" value="${login}"class="form-control"><br/>
            </div>
            <div class="form-inline">
                <label>Password:</label><br/>
                <input type="password" name="j_password" value="${password}"class="form-control"> <br/>
            </div>
            <br/>
            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> submit</button>
        </form>

    </div>
</body>
</html>
