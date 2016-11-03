<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>MyCollection</title>
    <c:url value="/images/favicon.ico" var="favicon_Url" />
    <link rel="shortcut icon" href="${favicon_Url}" type="image/ICO">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
    <div align="center">

        <h3>Create your new Collection:</h3>

        <c:url value="/add_collection" var="add_collection_URL" />
        <form action="${add_collection_URL}" enctype="multipart/form-data" method="POST">
            <div class="form-inline">
                <label>*Collection name:</label><br/>
                <input type="text" name="name" class="form-control"/><br/>
            </div>
            <div class="form-inline">
                <label>*Type of instances:</label><br/>
                <input type="text" name="type" class="form-control"/><br/><br/>
            </div>

            <label>Photo cover:</label><br/><input type="file" name="photo"><br/>
            <div class="form-inline">
                <label>Other information:</label><br/>
                <textarea name="information" cols="40" rows="5" class="form-control"></textarea><br/>
            </div>
            <br/>
            <button type="submit" value="Create myCollection" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> submit</button>
        </form>

        <br/> *fields are required! <br/>

    </div>
</body>
</html>
