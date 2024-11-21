package ciit.sqe.sqewheelz.Controllers;


import ciit.sqe.sqewheelz.Model.User;
import ciit.sqe.sqewheelz.Services.UserService;
import ciit.sqe.sqewheelz.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api")
public class AuthController {

    @Autowired
    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setRole("USER");
        return userService.registerUser(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> loggedInUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (loggedInUser.isPresent()) {
            User foundUser = loggedInUser.get();
            String token = jwtUtil.generateToken(foundUser.getEmail(), foundUser.getRole(), foundUser.getId());
            return "Bearer " + token;
         /*   if ("ADMIN".equals(foundUser.getRole())) {
                return "Admin login successful! Redirecting to admin dashboard.";
            } else {
                return "User login successful! Redirecting to user dashboard.";
            }*/
        } else {
            return "Invalid email or password.";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("Authorization", null);
        response.setHeader("Authorization", null);

        return "Logout successful!";
    }

//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//        boolean isAuthenticated = userService.loginUser(user.getEmail(), user.getPassword());
//
//        if(isAuthenticated) {
//            return "Login Successful";
//        }
//        else{
//            return "Invalid email or password";
//        }
//    }

}
