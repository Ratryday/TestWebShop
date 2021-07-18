<html>
<head>
    <title>Order cart</title>
</head>
<body>
<div>
    <table>
        <tr th:each="cartEntry : ${cart}">
            <td><p th:text="${cartEntry.product.productName}"></p></td>
            <td><p th:text="${cartEntry.productCount}"></p></td>
            <td><p th:text="${cartEntry.product.productPrice * cartEntry.productCount}" ></p></td>
            <td>
                <form method="post" action="/clearcart">
                    <input type="hidden" name="productId" th:value="${cartEntry.product.productId}">
                    <input type="submit" value="Remove product">
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <form method="get" action="/adminorders">
                    <input type="submit" value="Back">
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
