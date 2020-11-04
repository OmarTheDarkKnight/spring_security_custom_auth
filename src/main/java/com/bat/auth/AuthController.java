package com.bat.auth;

import com.bat.user.User;
import com.bat.user.UserDto;
import com.bat.util.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
public class AuthController extends BaseController {
    private Logger logger = Logger.getLogger(getClass().getName());

    @GetMapping("/")
    public String landingPage() {
        return "landing-page";
//        return "login-form";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login-form";
    }

    @GetMapping("/registration-form")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("crmUser", new UserDto());
        return "registration-form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(
            @Valid @ModelAttribute("crmUser") UserDto userDto,
            BindingResult theBindingResult,
            Model theModel) {

        if (theBindingResult.hasErrors()){
            return "registration-form";
        }

        // check the database if user already exists
        User existing = userService.findUserByEmail(userDto.getEmail());
        if (existing != null){
            theModel.addAttribute("crmUser", new UserDto());
            theModel.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "registration-form";
        }
        // create user account
        userService.save(userDto);

        logger.info("Successfully created user: " + userDto.getEmail());

        return "registration-confirmation";
    }

    @GetMapping("/sys/")
    public String adminHome() {
        return "admin-home";
    }

    @GetMapping("/u/")
    public String userHome() {
        return "user-home";
    }

    @GetMapping("/unauthorized")
    public String showUnauthorizedPage() {
        return "unauthorized";
    }
}