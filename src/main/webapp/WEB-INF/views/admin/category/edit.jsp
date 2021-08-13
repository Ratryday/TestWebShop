<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Edit category</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Edit category</h2>
<div>
    <form th:method="post" action="/admin/category/edit" th:object="${category}">
        <label for="categoryName">Category name</label>
        <input type="text" id="categoryName" th:field="*{categoryName}">
        <p style="color:red" th:if="${#fields.hasErrors('categoryName')}" th:errors="*{categoryName}">Name error</p>
        <input type="hidden" th:field="*{categoryId}">
        <input type="submit" value="Edit">
    </form>
</div>
<form method="get" action="/admin">
    <input type="submit" value="Back to category list"/>
</form>
</body>
</html>
