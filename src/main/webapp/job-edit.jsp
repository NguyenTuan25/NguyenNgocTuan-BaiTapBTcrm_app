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
    <h1>Edit Job</h1>
    
    <form action="job-edit" method="post">
        <input type="hidden" name="id" value="${job.id}" />

        <div>
            <label for="name">name</label>
            <input type="text"  id="name" name="name" value="${job.name}" required />
        </div>     
        <div>
            <label for="start_date">start_date:</label>
            <input type="text"  id="start_date" name="start_date" value="${job.start_date}" required />
        </div>   
        <div>
            <label for="end_date">end_date:</label>
            <input type="text" id="end_date" name="end_date" value="${job.end_date}" required />
        </div>                              

        <div>
            <button type="submit">Update Job</button>
            <a href="">Cancel</a>
        </div>
    </form>
</body>
</html>