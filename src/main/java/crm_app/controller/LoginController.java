package crm_app.controller;

import crm_app.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    /**
     *
     * Tính năng đăng nhập : Tức là lấy email và mật khẩu người dùng nhập kiểm tra nó có tồn tại trong csdl
     * hay không
     *
     * Bước 1 : Lấy giá trị tham số email và mật khẩu người dùng truyền vào
     * Bước 2 : Viết câu truy vấn kiểm tra xem email và mật khẩu
     * Bước 3 : Lấy dữ liệu từ câu truy vấn kiểm tra xem có dữ liệu hay không. Nếu có dữ liệu thì sẽ là đăng nhập
     *  thành công ngược lại là đăng nhập thất bại
     *
     * */

    private LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Hello world");
        String email = "";
        String password = "";

        Cookie [] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie item : cookies) {
                String name = item.getName();
                String value = item.getValue();
                if(name.equals("email")) {
                    email = value ;
                }
                if(name.equals("password")) {
                    password = value;
                }
            }
        }

//        req.setAttribute("email", "Khanguyen");
//        req.setAttribute("password", "Khanguyen");
        System.out.print("Test");
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
//        value="${password}
//        value="${email}
//        action="login" method="POST"
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Kiểm tra người dùng có chọn remember me không ?
        String remember = req.getParameter("remember");

        // Kiểm tra thông tin đăng nhập thông qua LoginService
        boolean isSuccess = loginService.checkLogin(email, password, resp, remember );


        System.out.println(" " + isSuccess);
        System.out.println("email:" + email + " - password " + password);
        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            // Nếu không thành công, chuyển lại về login.jsp
             req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
