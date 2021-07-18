<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Add product</title>
</head>
<body>
<div>
    <form method="post" action="/addproduct" enctype="multipart/form-data">
        <table>
            <input type="hidden" name="categoryId" th:value="${categoryId}">
            <tr>
                <td>Select image for product</td>
                <td><input type="file" name="imageFile" value="imageFile"></td>
            </tr>
            <tr>
                <td>Product name</td>
                <td><input type="text" name="productName"></td>
            </tr>
            <tr>
                <td>Product price</td>
                <td><input type="number" step="any" name="productPrice"></td>
            </tr>
            <tr>
                <td>Product description</td>
                <td><input type="text" name="productDescription"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Add new product"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
