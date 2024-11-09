package ciit.sqe.sqewheelz;

import ciit.sqe.sqewheelz.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqeWheelzApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SqeWheelzApplication.class, args);
    }

    @Override
    public void run(String... args){
        userService.hashAdminPassword();
    }

}
