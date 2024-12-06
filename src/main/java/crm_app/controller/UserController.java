package crm_app.controller;

import crm_app.entity.RoleEntity;
import crm_app.entity.UserEntity;
import crm_app.service.UserService;
import crm_app.entity.UserTaskStatusDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "usercontroller", urlPatterns = {"/user", "/user-add", "/user-edit", "/user-view"})
public class UserController extends HttpServlet {
    private UserService service = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String path = req.getServletPath(); //trả ra đường dẫn servlet đang gọi

        if (path.equals("/user")) {
            loadUser(req, resp);
        }else if(path.equals("/user-add")) {
            addUser(req, resp);
        }else if(path.equals("/user-edit")) {
            editUser(req,resp);
        } else if (path.equals("/user-view")) {
            viewUser(req,resp);
        }


    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String path = req.getServletPath();

        if (path.equals("/user-add")) {
            addUserPost(req,resp);
        }else if(path.equals("/user-edit")) {
            updateUserPost(req, resp);
        }
    }
    private void addUserPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        int roleId = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");

        service.insertUser(email, password, fullname, roleId);

        List<RoleEntity> listRole = service.getAllRole();

        req.setAttribute("listRole", listRole);

        req.getRequestDispatcher("user-add.jsp").forward(req, resp);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<RoleEntity> listRole = service.getAllRole();

        req.setAttribute("listRole", listRole);

        req.getRequestDispatcher("user-add.jsp").forward(req, resp);
    }

    private void loadUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if(id != null && "delete".equals(action)) {
            // Tính năng xóa
            service.deleteUserById(Integer.parseInt(id));

        }

        List<UserEntity> listUser =service.getAllUser();

        req.setAttribute("listUser", listUser);

        req.getRequestDispatcher("user-table.jsp").forward(req, resp);
    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        UserEntity user = service.getUserById(id);

        req.setAttribute("user", user);
        req.setAttribute("listRole", service.getAllRole()); // Gửi danh sách vai trò để hiển thị trong dropdown

        req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
    }

    private void updateUserPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        int roleId = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");

        if (id != null) {
            service.updateUser(Integer.parseInt(id), email, password, fullname, roleId); // Cập nhật người dùng
        }

        // Sau khi cập nhật, chuyển lại đến trang danh sách người dùng
        loadUser(req, resp);
    }

    private void viewUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int id = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");
        UserTaskStatusDTO userTaskStatusDTO = null;
        if (id != 0 && "view".equals(action)){
            userTaskStatusDTO = service.getUserTaskStatus(id);
        }


        req.setAttribute("user", userTaskStatusDTO);
        req.getRequestDispatcher("user-view.jsp").forward(req,resp);
    }
}
