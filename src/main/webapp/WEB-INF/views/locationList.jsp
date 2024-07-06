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
    <h2>Location List</h2>
    
    <form action="${pageContext.request.contextPath}/location/search" method="post">
	    <input type="text" name="name" placeholder="Enter Location Name" value="${searchTermName}">
	    <input type="text" name="address" placeholder="Enter Location Address" value="${searchTermAddress}">
	    <button type="submit">Search</button>
	</form>
	<a href="${pageContext.request.contextPath}/location/locationregister">
        <button>Add Location</button>
    </a>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Address</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="location" items="${locationList}">
                <tr>
                    <td>${location.id}</td>
                    <td>${location.name}</td>
                    <td>${location.address}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/location/editlocation/${location.id}">Edit</a>
                        <a href="${pageContext.request.contextPath}/location/deletelocation/${location.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>