package crm_app.controller;

import crm_app.entity.RoleEntity;
import crm_app.service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "rolecontroller", urlPatterns = {"/role", "/role-add", "/role-edit" })
public class RoleController extends HttpServlet {
    private RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String path = req.getServletPath();

        if(path.equals("/role")) {
            deleteRole(req, resp);
        }else if(path.equals("/role-add")) {
            addRole(req, resp);
        }else if(path.equals("/role-edit")) {
            editRole(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String path = req.getServletPath();

        if(path.equals("/role-add")) {
            addRolePost(req,resp);
        }else if(path.equals("/role-edit")) {
            updateRolePost(req, resp);
        }
    }

    private void addRolePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        roleService.insertRole(name, description);

        List<RoleEntity> lRole = roleService.getAllRole();

        req.setAttribute("lRole", lRole);

        req.getRequestDispatcher("role-add.jsp").forward(req, resp);
    }

    private void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleEntity> lRole = roleService.getAllRole();
        req.setAttribute("lRole", lRole);
        req.getRequestDispatcher("role-add.jsp").forward(req, resp);
    }

    private void deleteRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if(id !=null && "delete".equals(action)) {
            // Tính năng xóa
            roleService.deleteRoleById(Integer.parseInt(id));

        }

        List<RoleEntity> lRole = roleService.getAllRole();

        req.setAttribute("lRole", lRole);

        req.getRequestDispatcher("role-table.jsp").forward(req, resp);
    }

    private void editRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        RoleEntity role = roleService.getRoleById(id);

        req.setAttribute("role", role);
        req.setAttribute("listRole", roleService.getAllRole());

        req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
    }

    private void updateRolePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        //     int roleId = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");

        if (id != null ) {
            roleService.updateRole(Integer.parseInt(id), name, description);
        }

        // Sau khi cập nhật, chuyển lại đến trang danh sách người role
        deleteRole(req, resp);
    }
}
