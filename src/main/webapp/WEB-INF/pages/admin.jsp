<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>MyCollection</title>
        <c:url value="/images/favicon.ico" var="favicon_Url" />
        <link rel="shortcut icon" href="${favicon_Url}" type="image/ICO">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body> <!--secret admin page-->
        <div align="center">
            <h3>Admin page(in developing)</h3>  <!--text-->
            <h4>Add user to black list</h4>
            <input type="text" name="search" placeholder="Search" value="${search}" class="form-control">
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    </body>
</html>
