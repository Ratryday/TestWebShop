<html xmlns:th="http://www.thymeleaf.org">
<head>
    <!--/*@thymesVar id="category" type="com.ratryday.models.Category"*/-->
    <title th:text="${category}"></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>Hello</h2>
<div th:each="product : ${allProducts}">
    <form method="get" action="/product">
        <label>Product name</label>
        <p th:text="${product.productName}"></p>
        <label>Product Price</label>
        <p th:text="${product.productPrice}"></p>
        <label>Product Image</label>
        <p th:text="${product.productImage}"></p>
        <input type="hidden" name="productId" th:value="${product.productId}">
        <input type="submit" value="Open description">
    </form>
</div>
</body>
</html>
