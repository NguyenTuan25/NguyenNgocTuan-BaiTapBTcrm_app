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
    <h1>Edit User</h1>
    
    <form action="user-edit" method="post">
        <input type="hidden" name="id" value="${user.id}" />

        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}" required />
        </div>
        
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" value="${user.password}" required />
        </div>
        
        <div>
            <label for="fullname">Full Name:</label>
            <input type="text" id="fullname" name="fullname" value="${user.fullname}" required />
        </div>

        <div>
            <label for="role">Role:</label>
            <select id="role" name="role">
               <c:forEach var="item" items="${listRole}">
               		<option value="${item.id}">${item.description}</option>
               </c:forEach>
            </select>
        </div>

        <div>
            <button type="submit">Update User</button>
            <a href="">Cancel</a>
        </div>
    </form>
</body>
</html>