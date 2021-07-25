<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Order</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<div>
    <form method="post" action="/order/buy" th:object="${order}">
        <table>
            <tr>
                <td><label for="customerName">Enter your name</label></td>
                <td>
                    <input type="text" id="customerName" th:field="*{customerName}" placeholder="Your name">
                    <p style="color:red" th:if="${#fields.hasErrors('customerName')}" th:errors="*{customerName}">Name error</p>
                </td>
            </tr>
            <tr>
                <td><label for="customerSurname">Enter your surname</label></td>
                <td>
                    <input type="text" id="customerSurname" th:field="*{customerSurname}" placeholder="Your surname">
                    <p style="color:red" th:if="${#fields.hasErrors('customerSurname')}" th:errors="*{customerSurname}">Surname error</p>
                </td>
            </tr>
            <tr>
                <td><label for="mailAddress">Enter your email</label></td>
                <td>
                    <input type="text" id="mailAddress" th:field="*{mailAddress}" placeholder="Your email">
                    <p style="color:red" th:if="${#fields.hasErrors('mailAddress')}" th:errors="*{mailAddress}">Email error</p>
                </td>
            </tr>
            <tr>
                <td><label for="phoneNumber">Enter your phone number</label></td>
                <td>
                    <input type="text" id="phoneNumber" th:field="*{phoneNumber}" placeholder="Your phone number">
                    <p style="color:red" th:if="${#fields.hasErrors('phoneNumber')}" th:errors="*{phoneNumber}">Phone number error</p>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Buy"></td>
            </tr>
        </table>
    </form>
</div>
<form method="get" action="/cart">
    <input type="submit" value="Back to cart">
</form>
</body>
</html>
