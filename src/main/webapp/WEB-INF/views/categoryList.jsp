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
	<form action="${pageContext.request.contextPath}/category/search" method="post">
	    <input type="text" name="name" placeholder="Enter Category Name" value="${searchTermName}">
	    <input type="text" name="description" placeholder="Enter Category Description" value="${searchTermDescription}">
	    <button type="submit">Search</button>
	</form>
	
	<h2>Category List</h2>
	<a href="${pageContext.request.contextPath}/category/categoryregister">
        <button>Add Category</button>
    </a>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categoryList}">
                <tr>
                    <td>${category.id}</td>
                    <td>${category.name}</td>
                    <td>${category.description}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/category/editcategory/${category.id}">Edit</a>
                        <a href="${pageContext.request.contextPath}/category/deletecategory/${category.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>