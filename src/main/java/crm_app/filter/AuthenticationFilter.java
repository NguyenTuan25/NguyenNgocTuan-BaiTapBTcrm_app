package crm_app.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authenFilter", urlPatterns = {"/user"})
public class AuthenticationFilter extends HttpFilter {
    /**
     * Khi người dùng gọi link/users
     * Bước 1 : Kiểm tra xem người dùng đã đăng nhập rồi hay chưa. Nếu đã đăng nhập thì cho thấy
     * 			nội dung còn chưa đăng nhập đá ra trang login. ( cookies )
     * Bước 2 : Đối với link/users chỉ có user có quyền là admin thì mới vô được còn không có quyền admin
     * 			đá về trang dashboard ( cookies )
     *
     * */

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Filter actived");

        // Kiểm tra người dùng đã đăng nhập hay chưa
        boolean isLoggedIn = false;
        String authen = ""; // Vai trò của người dùng
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loggedIn")) {
                    isLoggedIn = true;
                }
                if (cookie.getName().equals("authen")) {
                    authen = cookie.getValue();
                }
            }
        }

        // Nếu người dùng chưa đăng nhập, chuyển hướng về trang login
        if (!isLoggedIn) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;  // Kết thúc xử lý filter
        }

        // Kiểm tra nếu người dùng đã đăng nhập nhưng không có quyền admin
        if (!"ROLE_ADMIN".equals(authen)) {
            System.out.println("Unauthorized access. Redirecting to index.");
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;  // Kết thúc xử lý filter
        }
        // Cho đi tiếp
        chain.doFilter(req, res);
    }
}
