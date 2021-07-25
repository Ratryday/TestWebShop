<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Adding new category</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Add new category</h2>
<div>
    <form method="post" action="/admin/category" th:object="${category}">
        <label for="name">Category name</label>
        <input type="text" th:field="*{categoryName}" id="name">
        <p style="color:red" th:if="${#fields.hasErrors('categoryName')}" th:errors="*{categoryName}">Name error</p>
        <input type="submit" value="Add">
    </form>
</div>
<form method="post" action="/admin">
    <input type="submit" value="Back to category list"/>
</form>
</body>
</html>
