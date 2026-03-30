package com.felicite.SGAE30.configurations;

import com.felicite.SGAE30.enums.Role;
import com.felicite.SGAE30.models.User;
import com.felicite.SGAE30.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {

        if (userRepo.findByRole(Role.ADMIN).isEmpty()) {
            User admin = new User();
            admin.setFirstname("Admin");
            admin.setLastname("User");
            admin.setEmail("admin@gmail.com");
            admin.setPhoneNumber("657938010");
            admin.setRole(Role.ADMIN);
            admin.setPassword("admin1012");

            userRepo.save(admin);

            System.out.println(">>> Premier Admin créé : admin@gmail.com / admin1012");
        }

    }
}
