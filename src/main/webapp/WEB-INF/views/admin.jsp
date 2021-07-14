<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Admin panel</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Admin Panel</h2>
<div>
    <form method="get" action="/addcategory">
        <input type="submit" value="Add new category">
    </form>
</div>
<div th:each="category : ${allCategory}">
    <form method="get" action="/products">
        <p th:text="${category.categoryName}"></p>
        <input type="hidden" name="categoryId" th:value="${categoryId}">
        <input type="submit" name="category" th:value="${category.categoryName}" text="Open products list">
    </form>
</div>
</body>
</html>
