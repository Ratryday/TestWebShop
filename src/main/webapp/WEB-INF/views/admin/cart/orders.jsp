<html>
<head>
    <title>Orders List</title>
</head>
<body>
<table>
    <tr>
        <th>Orders</th>
    </tr>
    <div th:each="order : ${allOrders}">
        <tr>
            <td><p th:text="${order.customerName}"></p></td>
            <td><p th:text="${order.customerSurname}"></p></td>
            <td><p th:text="${order.phoneNumber}"></p></td>
            <td><p th:text="${order.mailAddress}"></p></td>
            <td>
                <form method="get" action="/admin/cart/cartEntry" style="display:inline;">
                    <input type="hidden" name="cartId" th:value="${order.cart.cartId}">
                    <input type="submit" name="category" value="Open products list">
                </form>
            </td>
        </tr>
    </div>
    <form method="get" action="/admin" style="display:inline;">
        <input type="submit" value="Back">
    </form>
</table>
</body>
</html>
