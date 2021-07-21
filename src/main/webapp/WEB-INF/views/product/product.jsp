<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Product</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<div>
    <table>
        <tr>
            <td><img th:src="${product.productImage}" width="240" height="180"></td>
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
            <td>Product description</td>
            <td><p th:text="${product.productDescription}"></p></td>
        </tr>
        <tr>
            <form th:method="put" action="/cart/create">
                <input type="hidden" name="productId" th:value="${product.productId}">
                <td><input type="number" name="productCount"></td>
                <td><input type="submit" value="Add to cart"></td>
            </form>
        </tr>
        <tr>
            <form method="get" action="/cart">
                <td><input type="submit" value="Cart"></td>
            </form>

        </tr>
        <tr>
            <form method="get" action="/product/products">
                <input type="hidden" name="categoryId" th:value="${product.category.categoryId}">
                <input type="submit" value="Back to product list"/>
            </form>
        </tr>
    </table>
</div>
</body>
</html>
