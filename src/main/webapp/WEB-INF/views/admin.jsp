<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Admin panel</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Admin Panel</h2>
<p th:text="${massage}"></p>
<div>
    <form method="get" action="/addcategory">
        <input type="submit" value="Add new category">
    </form>
</div>
<div>
    <form method="get" action="/adminorders">
        <input type="submit" value="Orders">
    </form>
</div>
<table>
    <tr>
        <th>Categories</th>
    </tr>
    <div th:each="category : ${allCategory}">
        <tr>
            <td><p th:text="${category.categoryName}"></p></td>
            <td>
                <form method="get" action="/adminproducts" style="display:inline;">
                    <input type="hidden" name="categoryId" th:value="${category.categoryId}">
                    <input type="submit" name="category" value="Open products list">
                </form>
                |
                <form method="get" action="/edit" style="display:inline;">
                    <input type="hidden" name="categoryId" th:value="${category.categoryId}">
                    <input type="submit" value="Edit category">
                </form>
                |
                <form method="post" action="/delete" style="display:inline;">
                    <input type="hidden" name="categoryId" th:value="${category.categoryId}">
                    <input type="submit" value="Delete category">
                </form>
            </td>
        </tr>
    </div>
</table>
</body>
</html>
