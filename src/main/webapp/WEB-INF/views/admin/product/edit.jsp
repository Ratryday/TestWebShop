<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Edit product</title>
</head>
<body>
<div>
    <form method="post" action="/admin/product/edit" th:object="${product}" enctype="multipart/form-data">
        <table>
            <input type="hidden" th:field="*{category}" th:value="${category}">
            <input type="hidden" th:field="*{productId}" th:value="${productId}">
            <input type="hidden" name="categoryId" th:value="${category.categoryId}">
            <tr>
                <td>
                    <label for="imageFile">Select image for product</label>
                    <img th:src="@{'/' + ${product.productImage}}" width="460" height="345">
                    <input type="file" id="imageFile" name="imageFile" value="imageFile">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="productName">Product name</label>
                    <input type="text" id="productName" th:field="*{productName}">
                    <p style="color:red" th:if="${#fields.hasErrors('productName')}" th:errors="*{productName}">Name error</p>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="productPrice">Product price</label>
                    <input type="number" step="any" id="productPrice" th:field="*{productPrice}">
                    <p style="color:red" th:if="${#fields.hasErrors('productPrice')}" th:errors="*{productPrice}">Price error</p>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="productDescription">Product description</label>
                    <input type="text" id="productDescription" th:field="*{productDescription}">
                    <p style="color:red" th:if="${#fields.hasErrors('productDescription')}" th:errors="*{productDescription}">Description error</p>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Edit product"></td>
            </tr>
        </table>
    </form>
</div>
<form method="get" action="/admin/product/products">
    <input type="hidden" name="categoryId" th:value="${category.categoryId}">
    <input type="submit" value="Back to category list"/>
</form>
</body>
</html>
