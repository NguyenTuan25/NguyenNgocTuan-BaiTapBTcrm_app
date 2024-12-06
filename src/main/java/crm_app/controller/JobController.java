package crm_app.controller;

import crm_app.entity.JobEntity;
import crm_app.service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "jobcontroller", urlPatterns = {"/job", "/job-add", "/job-edit"})
public class JobController extends HttpServlet {
    private JobService jobService = new JobService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String path = req.getServletPath();
        if(path.equals("/job")) {
            deleteJob(req, resp);
        }else if(path.equals("/job-add")) {
            addJob(req, resp);
        }else if(path.equals("/job-edit")) {
            editJob(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String path = req.getServletPath();
        if(path.equals("/job-add")) {
            addJobPost(req, resp);
        }else if(path.equals("/job-edit")) {
            updateJobPost(req, resp);
        }
    }

    private void addJobPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String start_date = req.getParameter("start_date");
        String end_date = req.getParameter("end_date");

        jobService.insertJob(name, start_date, end_date);

        List<JobEntity> listJob = jobService.getAllJob();

        req.setAttribute("listJob", listJob);

        req.getRequestDispatcher("job-add.jsp").forward(req, resp);
    }

    private void addJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobEntity> listJob = jobService.getAllJob();
        req.setAttribute("listJob", listJob);
        req.getRequestDispatcher("job-add.jsp").forward(req, resp);
    }
    private void deleteJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if(id != null && "delete".equals(action)) {
            // Tính năng xóa
            jobService.deleteJobById(Integer.parseInt(id));

        }

        List<JobEntity> listJob =jobService.getAllJob();

        req.setAttribute("listJob", listJob);

        req.getRequestDispatcher("job-table.jsp").forward(req, resp);
    }

    private void editJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        JobEntity job = jobService.getJobById(id);

        req.setAttribute("job", job);

        req.getRequestDispatcher("job-edit.jsp").forward(req, resp);
    }

    private void updateJobPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String start_date = req.getParameter("start_date");
        String end_date = req.getParameter("end_date");


        if (id != null) {
            jobService.updateRole(Integer.parseInt(id), name, start_date, end_date); // Cập nhật người dùng
        }

        // Sau khi cập nhật, chuyển lại đến trang danh sách người dùng
        deleteJob(req, resp);
    }
}
