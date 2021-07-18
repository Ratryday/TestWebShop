<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Order</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<div>
    <form method="post" action="/buy">
        <table>
            <tr>
                <td><label text="Enter your name"></label></td>
                <td><input type="text" name="customerName" value="Your name"></td>
            </tr>
            <tr>
                <td><label text="Enter your surname"></label></td>
                <td><input type="text" name="customerSurname" value="Your surname"></td>
            </tr>
            <tr>
                <td><label text="Enter your email"></label></td>
                <td><input type="text" name="mailAddress" value="Your email"></td>
            </tr>
            <tr>
                <td><label text="Enter your phone number"></label></td>
                <td><input type="text" name="phoneNumber" value="Your phone number"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Buy"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
