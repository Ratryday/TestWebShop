<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Test Web Shop</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Hello World!</h2>
<div th:each="category : ${allCategory}">
    <form method="get" action="/products">
        <input type="hidden" name="categoryId" th:value="${categoryId}">
        <input type="submit" name="category" th:value="${category.categoryName}">
    </form>
</div>
</body>
</html>
