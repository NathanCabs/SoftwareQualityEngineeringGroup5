package ciit.sqe.sqewheelz.Controllers;


import ciit.sqe.sqewheelz.Model.User;
import ciit.sqe.sqewheelz.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Get all users (admin)
    @GetMapping
    public List<User> getAllUsers(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        System.out.println(role);
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied: Admin only");
        }
        return userService.getAllUsers();
    }

    //Get user by id (Admin or by own user)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        System.out.println(role);
        System.out.println(userId);
        if ("ADMIN".equals(role) || id.equals(userId)) {
            return userService.getUserById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        }
        else{
            throw new RuntimeException("Access denied: You can only view your own profile");
        }
    }

    //Update user profile (admin or by own user)
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");

        if ("ADMIN".equals(role) || id.equals(userId)) {
            userService.updateUser(id, user);
            return "User updated successfully";
        }
        else{
            throw new RuntimeException("Access denied: You can only update your own profile");
        }
    }

    //Delete user (admin only)
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if(!"ADMIN".equals(role)){
            throw new RuntimeException("Access denied: Admins only");
        }
        userService.deleteUser(id);
        return "User deleted successfully";

    }



}
