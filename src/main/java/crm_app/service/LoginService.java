package crm_app.service;

import crm_app.entity.UserEntity;
import crm_app.repository.UserRepository;
import crm_app.utils.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginService {
    private UserRepository userRepository = new UserRepository();

    public boolean checkLogin(String email, String password, HttpServletResponse resp, String remember) {

        String passwordEncoded = MD5.getMd5(password);

        List<UserEntity> listUser = userRepository.findByEmailAndPassword(email, passwordEncoded);

        boolean isSuccess = listUser.size()>0;

        if(isSuccess) {
            // lưu trạng thái đã đăng nhập
            Cookie loggedInCookie = new Cookie("loggedIn", "true");
            Cookie cookie = new Cookie("authen", listUser.get(0).getName());


            resp.addCookie(loggedInCookie);
            resp.addCookie(cookie);

        }

        if(remember != null && isSuccess) {
            Cookie ckEmail = new Cookie("email", email);
            Cookie ckPassword = new Cookie("password", password);

            resp.addCookie(ckPassword);
            resp.addCookie(ckEmail);
        }
        return  listUser.size() > 0;

    }
}
