<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
</head>
<body>
    <h1>Edit Role</h1>
    
    <form action="role-edit" method="post">
        <input type="hidden" name="id" value="${role.id}" />

        <div>
            <label for="name">Tên quyền:</label>
            <input  id="name" name="name" value="${role.name}" required />
        </div>                        
        <div>
            <label for="description">mô tả:</label>
            <select id="description" name="description">
               <c:forEach var="item" items="${listRole}">
               		<option value="${item.description}">${item.description}</option>
               </c:forEach>
            </select>
        </div>

        <div>
            <button type="submit">Update Role</button>
            <a href="">Cancel</a>
        </div>
    </form>
</body>
</html>