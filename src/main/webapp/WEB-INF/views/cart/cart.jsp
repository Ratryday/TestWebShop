<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Cart</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<div>
    <p th:text="${message}"></p>
    <table>
        <tr th:each="cartEntry : ${cart}">
            <td><p th:text="${cartEntry.product.productName}"></p></td>
            <td><p th:text="${cartEntry.productCount}"></p></td>
            <td><p th:text="${cartEntry.product.productPrice * cartEntry.productCount}"></p></td>
            <td>
                <form method="post" action="/cart/delete">
                    <input type="hidden" name="productId" th:value="${cartEntry.product.productId}">
                    <input type="submit" value="Remove product">
                </form>
            </td>
        </tr>
        <tr>
            <td><p th:unless="${message}">Total Product Price</p></td>
            <td><p th:text="${totalProductPrice}"></p></td>
        </tr>
        <tr>
            <td>
                <form method="get" action="/">
                    <input type="submit" value="Back to shop">
                </form>
            </td>
            <td>
                <form method="get" th:unless="${message}" action="/order">
                    <input type="submit" value="Buy">
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
