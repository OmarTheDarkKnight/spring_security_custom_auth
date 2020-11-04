package com.bat.auth;

import com.bat.role.Role;
import com.bat.user.User;
import com.bat.user.UserService;
import com.bat.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("\n\nIn Custom Auth Success Handler\n\n");

        String userName = authentication.getName();
        System.out.println("user email = " + userName);
        User theUser = userService.findUserByEmail(userName);

        // place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

        // forward to home page based on role
        Role role = theUser.getRoles().get(0);
        System.out.println("Role: " + role.getRole());
        if(role.getRole().equals("ROLE_"+Constants.ROLE_ADMIN))
            response.sendRedirect(request.getContextPath() + "/sys/");
        else if(role.getRole().equals("ROLE_"+Constants.ROLE_USER))
            response.sendRedirect(request.getContextPath() + "/u/");
    }

}
