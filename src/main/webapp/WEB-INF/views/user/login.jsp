<html>
<head>
    <title>Title</title>
</head>
<body>
<div th:if="${param.error}">
    Invalid username and password.
</div>
<form method="post" action="/user/singIn">
    <div>
        <label> User name : <input type="text" name="userName"/></label>
    </div>
    <div>
        <label> Password: <input type="password" name="userPassword"/></label>
    </div>
    <div>
        <input type="submit" value="Sign In"/>
    </div>
</form>
<form method="get" action="/registration">
    <input type="submit" value="Registration">
</form>
</body>
</html>
