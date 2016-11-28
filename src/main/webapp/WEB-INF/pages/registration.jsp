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

        <c:if test="${error ne null}">
            <div class="alert alert-danger" role="alert"><c:out value="${error}"/></div>
        </c:if>

        <h3>Registration form:</h3>

        <c:url value="/registration" var="loginUrl" />
        <form action="${loginUrl}" method="POST" class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-5 control-label">*Login:</label>
                <div class="col-sm-2">
                    <input type="text" name="login" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">*Password:</label>
                <div class="col-sm-2">
                    <input type="password" name="password" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">*confirm Password:</label>
                <div class="col-sm-2">
                    <input type="password" name="password_confirm" class="form-control">
                </div>
            </div>
            <br/>
            <div class="form-group">
                <label class="col-sm-5 control-label">Name:</label>
                <div class="col-sm-2">
                    <input type="text" name="name" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">Surname:</label>
                <div class="col-sm-2">
                    <input type="text" name="surname" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">Email:</label>
                <div class="col-sm-2">
                    <input type="text" name="email" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">Country:</label>
                <div class="col-sm-2">
                    <input type="text" name="country" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">City:</label>
                <div class="col-sm-2">
                    <input type="text" name="city" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">Phone:</label>
                <div class="col-sm-2">
                    <input type="text" name="phone" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">Specialization:</label>
                <div class="col-sm-2">
                    <input type="text" name="specialization" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-5 control-label">Other information:</label>
                <div class="col-sm-2">
                    <textarea name="information" cols="40" rows="5" class="form-control"></textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span>Submit</button>
        </form>

        <br/> *fields are required! <br/>

    </div>
</body>
</html>
