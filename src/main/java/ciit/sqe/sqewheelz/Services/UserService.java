package ciit.sqe.sqewheelz.Services;


import ciit.sqe.sqewheelz.Model.User;
import ciit.sqe.sqewheelz.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User registerUser(String name, String email, String password, String role) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with this email.");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        User user = new User(name, email, encodedPassword, role);
        return userRepository.save(user);
    }

    public void hashAdminPassword(){
        Long adminID = 1L;
        User admin = userRepository.findById(adminID).orElse(null);

        if (admin != null && !admin.getPassword().startsWith("$2a")) {
            String hashedPassword = bCryptPasswordEncoder.encode(admin.getPassword());
            admin.setPassword(hashedPassword);
            userRepository.save(admin);
        }
    }

    //ENCRYPTED PASSWORD METHODS
    public boolean loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword());
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> bCryptPasswordEncoder.matches(password, user.getPassword()));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateUser(Long id, User userDeets) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        if(userDeets.getName() != null) {
            user.setName(userDeets.getName());
        }
        if(userDeets.getEmail() != null) {
            user.setEmail(userDeets.getEmail());
        }

        if(userDeets.getPassword() != null && !userDeets.getPassword().isEmpty()) {
            String hashedPassword = bCryptPasswordEncoder.encode(userDeets.getPassword());
            user.setPassword(hashedPassword);
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }







    //NOT ENCRYPTED PASSWORDS
 /*  public boolean loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }*/
}
