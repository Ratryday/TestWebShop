<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Edit category</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Edit category</h2>
<div>
    <form method="post" action="/edit">
        <label>Category name</label>
        <input type="text" name="categoryName" th:value="${category.categoryName}">
        <input type="hidden" name="categoryId" th:value="${category.categoryId}">
        <input type="submit" value="Edit">
    </form>
</div>
<form method="post" action="/admin">
    <input type="submit" value="Back to category list"/>
</form>
</body>
</html>
