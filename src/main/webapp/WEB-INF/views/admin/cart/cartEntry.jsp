<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Order cart</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<p th:text="${message}"></p>
<div>
    <table>
        <tr th:each="cartEntry : ${cart}">
            <td><p th:text="${cartEntry.product.productName}"></p></td>
            <td><p th:text="${cartEntry.productCount}"></p></td>
            <td><p th:text="${cartEntry.product.productPrice * cartEntry.productCount}" ></p></td>
            <td>
                <form th:method="delete" action="/admin/cart/delete">
                    <input type="hidden" name="productId" th:value="${cartEntry.product.productId}">
                    <input type="hidden" name="cartId" th:value="${cart.cartId}">
                    <input type="submit" value="Remove product">
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <form method="get" action="/admin/cart/orders">
                    <input type="submit" value="Back">
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
