package crm_app.controller;

import crm_app.entity.JobEntity;
import crm_app.entity.StatusEntity;
import crm_app.entity.TaskEntity;
import crm_app.entity.UserEntity;
import crm_app.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "taskcontroller", urlPatterns = {"/task", "/task-add", "/task-edit"})
public class TaskController extends HttpServlet {
    private TaskService taskService = new TaskService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String path = req.getServletPath();

        if(path.equals("/task")) {
            deleteTask(req, resp);
        }else if(path.equals("/task-add")) {
            addTask(req, resp);
        } else if (path.equals("/task-edit")) {
            editTask(req, resp);
        }


    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String path = req.getServletPath();

        if(path.equals("/task-add")) {
            addTaskPost(req, resp);
        }else if(path.equals("/task-edit")) {
            updateTaskPost(req, resp);
        }
    }


    private void addTaskPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskName = req.getParameter("taskName");
        int job_id = Integer.parseInt(req.getParameter("job") != null ? req.getParameter("job") : "0");
        String jobStartDate = req.getParameter("jobStartDate");
        String jobEndDate = req.getParameter("jobEndDate");
        int status_id = Integer.parseInt(req.getParameter("status") != null ? req.getParameter("status") : "0");
        int user_id = Integer.parseInt(req.getParameter("user") != null ? req.getParameter("user") : "0");

        taskService.insertTask(taskName, job_id, jobStartDate,jobEndDate,status_id, user_id);

        List<JobEntity> listJob = taskService.getAllJob();

        List<UserEntity> listUser = taskService.getAllUser();

        List<StatusEntity> listStatus = taskService.getAllStatus();

        req.setAttribute("listJob", listJob);
        req.setAttribute("listUser", listUser);
        req.setAttribute("listStatus", listStatus);

        System.out.println("taskName: " + taskName);
        System.out.println("jobStartDate: " + jobStartDate);
        System.out.println("jobEndDate: " + jobEndDate);
        System.out.println("job_id: " + job_id);
        System.out.println("status_id: " + status_id);
        System.out.println("user_id: " + user_id);


        req.getRequestDispatcher("task-add.jsp").forward(req, resp);
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobEntity> listJob = taskService.getAllJob();

        List<UserEntity> listUser = taskService.getAllUser();

        List<StatusEntity> listStatus = taskService.getAllStatus();

        req.setAttribute("listJob", listJob);
        req.setAttribute("listUser", listUser);
        req.setAttribute("listStatus", listStatus);

        req.getRequestDispatcher("task-add.jsp").forward(req, resp);
    }
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if(id != null && "delete".equals(action)) {
            // Tính năng xóa
            taskService.deleteUserById(Integer.parseInt(id));

        }

        List<TaskEntity> listTask =taskService.getAllTask();

        req.setAttribute("listTask", listTask);

        req.getRequestDispatcher("task-table.jsp").forward(req, resp);
    }
    private void editTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        TaskEntity task = taskService.getTaskById(id);

        req.setAttribute("task", task);
        req.setAttribute("listStatus", taskService.getAllStatus()); // Gửi danh sách status để hiển thị trong dropdown
        req.setAttribute("listJob", taskService.getAllJob());
        req.setAttribute("listUser", taskService.getAllUser());
        req.getRequestDispatcher("task-edit.jsp").forward(req, resp); // Chuyển đến form chỉnh sửa task
    }

    private void updateTaskPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String taskName = req.getParameter("taskName");
        int job_id = Integer.parseInt(req.getParameter("job") != null ? req.getParameter("job") : "0");
        String jobStartDate = req.getParameter("jobStartDate");
        String jobEndDate = req.getParameter("jobEndDate");
        int status_id = Integer.parseInt(req.getParameter("status") != null ? req.getParameter("status") : "0");
        int user_id = Integer.parseInt(req.getParameter("user") != null ? req.getParameter("user") : "0");

        if (id != null) {
            taskService.updateTask(Integer.parseInt(id), taskName, job_id, jobStartDate, jobEndDate, user_id, status_id);
        }

        // Sau khi cập nhật, chuyển lại đến trang danh sách task
        deleteTask(req, resp);
    }

}
