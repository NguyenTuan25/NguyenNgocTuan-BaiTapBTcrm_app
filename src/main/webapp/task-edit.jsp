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

    <form action="task-edit" method="post">
        <input type="hidden" name="id" value="${task.id}">
        <div class="form-group">
            <label for="taskName">Task Name</label>
            <input type="text" id="taskName" name="taskName" class="form-control" value="${task.taskName}" required>
        </div>

        <div class="form-group">
            <label for="job">Job</label>
            <select id="job" name="job" class="form-control" required>
                <c:forEach var="job" items="${listJob}">
                    <option value="${job.id}">${job.name}</option>
                </c:forEach>
            </select>
         </div>

        <div class="form-group">
            <label for="jobStartDate">Start Date</label>
            <input type="text" id="jobStartDate" name="jobStartDate" class="form-control"
                value="${task.jobStartDate}" required>
        </div>

        <div class="form-group">
            <label for="jobEndDate">End Date</label>
            <input type="text" id="jobEndDate" name="jobEndDate" class="form-control"
                value="${task.jobEndDate}" required>
        </div>

        <div class="form-group">
            <label for="user">Assigned User</label>
            <select id="user" name="user" class="form-control" required>
                <c:forEach var="user" items="${listUser}">
                    <option value="${user.id}">${user.fullname}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="status">Status</label>
            <select id="status" name="status" class="form-control" required>
                <c:forEach var="status" items="${listStatus}">
                    <option value="${status.id}">${status.name}</option>
                </c:forEach>
            </select>
        </div>

        <div>
            <button type="submit">Update User</button>
            <button type="submit">cancel</button>
        </div>
    </form>
</body>
</html>