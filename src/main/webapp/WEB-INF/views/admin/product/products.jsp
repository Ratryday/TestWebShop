<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Admin product list</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Product list admin panel</h2>
<p th:text="${message}"></p>
<div>
    <form method="get" action="/admin/product/new">
        <input type="hidden" name="categoryId" th:value="${category.categoryId}">
        <input type="submit" value="Add new product">
    </form>
</div>
<table>
    <tr>
        <th><p th:text="${category.categoryName}"></p></th>
    </tr>
    <div th:each="product : ${allProducts}">
        <tr>
            <td><p th:text="${product.productName}"></p></td>
            <td>
                <form method="get" action="/admin/product/edit" style="display:inline;">
                    <input type="hidden" name="productId" th:value="${product.productId}">
                    <input type="hidden" name="categoryId" th:value="${category.categoryId}">
                    <input type="submit" value="Edit product">
                </form>
                |
                <form th:method="delete" action="/admin/product/delete" style="display:inline;">
                    <input type="hidden" name="productId" th:value="${product.productId}">
                    <input type="hidden" name="categoryId" th:value="${category.categoryId}">
                    <input type="submit" value="Delete product">
                </form>
            </td>
        </tr>
    </div>
</table>
<form method="get" action="/admin">
    <input type="submit" value="Back to category list"/>
</form>
</body>
</html>
