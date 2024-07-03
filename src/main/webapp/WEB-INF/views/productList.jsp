<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h2>Product List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Product Code</th>
                <th>Product Name</th>
                <th>Description</th>
                <th>Category</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${productList}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.productCode}</td>
                    <td>${product.productName}</td>
                    <td>${product.description}</td>
                    <td>${product.categoryName}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/product/editproduct/${product.id}">Edit</a>
                        <a href="${pageContext.request.contextPath}/product/deleteproduct/${product.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>