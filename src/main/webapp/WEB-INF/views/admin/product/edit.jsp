<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Edit product</title>
</head>
<body>
<div>
    <form th:method="patch" action="/admin/product/edit" enctype="multipart/form-data">
        <table>
            <input type="hidden" name="categoryId" th:value="${categoryId}">
            <input type="hidden" name="productId" th:value="${product.productId}">
            <tr>
                <td>Select image for product</td>
                <td><img th:src="${product.productImage}" width="460" height="345"></td>
                <td><input type="file" name="imageFile" value="imageFile"></td>
            </tr>
            <tr>
                <td>Product name</td>
                <td><input type="text" name="productName" th:value="${product.productName}"></td>
            </tr>
            <tr>
                <td>Product price</td>
                <td><input type="number" step="any" name="productPrice" th:value="${product.productPrice}"></td>
            </tr>
            <tr>
                <td>Product description</td>
                <td><input type="text" name="productDescription" th:value="${product.productDescription}"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Edit product"></td>
            </tr>
        </table>
    </form>
</div>
<form method="get" action="/admin/product/products">
    <input type="hidden" name="categoryId" th:value="${categoryId}">
    <input type="submit" value="Back to category list"/>
</form>
</body>
</html>
