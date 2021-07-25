<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Spring Security Example </title>
</head>
<body>
<div th:if="${param.error}">
    Invalid username and password.
</div>
<form method="post" action="/admin">
    <div>
        <label> User Name : <input type="text" name="userName"/></label>
    </div>
    <div>
        <label> Password: <input type="password" name="userPassword"/></label>
    </div>
    <div>
        <input type="submit" value="Sign In"/>
    </div>
</form>
</body>
</html>