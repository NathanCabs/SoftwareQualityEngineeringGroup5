package ciit.sqe.sqewheelz.Controllers;


import ciit.sqe.sqewheelz.Model.User;
import ciit.sqe.sqewheelz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

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

    public AuthController(UserService userService) {
        this.userService = userService;
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
            if ("ADMIN".equals(foundUser.getRole())) {
                return "Admin login successful! Redirecting to admin dashboard.";
            } else {
                return "User login successful! Redirecting to user dashboard.";
            }
        } else {
            return "Invalid email or password.";
        }
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