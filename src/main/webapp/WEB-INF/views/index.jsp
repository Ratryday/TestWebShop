<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Test Web Shop</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>My Web Shop</h2>
<p th:text="${massage}"></p>
<table>
    <tr>
        <th>Categories</th>
    </tr>
    <div th:each="category : ${allCategory}">
        <tr>
            <td><p th:text="${category.categoryName}"></p></td>
            <td>
                <form method="get" action="/product/products" style="display:inline;">
                    <input type="hidden" name="categoryId" th:value="${category.categoryId}">
                    <input type="submit" value="Open products list">
                </form>
            </td>
        </tr>
    </div>
</table>
</body>
</html>
