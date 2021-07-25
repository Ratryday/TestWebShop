<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title th:text="${category.categoryName}"></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Products</h2>
<form method="get" action="/cart">
    <input type="submit" value="Cart">
</form>
<form method="get" action="/user">
    <input type="submit" value="Sing In">
</form>
<div th:each="product : ${allProducts}">
    <form method="get" action="/product">
        <table>
            <input type="hidden" name="categoryId" th:value="${category.categoryId}">
            <input type="hidden" name="productId" th:value="${product.productId}">
            <tr>
                <td><img th:src="@{'/' + ${product.productImage}}" width="240" height="180"></td>
            </tr>
            <tr>
                <td>Product name</td>
                <td><p th:text="${product.productName}"></p></td>
            </tr>
            <tr>
                <td>Product price</td>
                <td><p th:text="${product.productPrice}"></p></td>
            </tr>
            <tr>
                <td><input type="submit" value="Open description"></td>
            </tr>
        </table>
    </form>
</div>
    <form method="get" action="/">
        <input type="submit" value="Back to category list"/>
    </form>
</body>
</html>
