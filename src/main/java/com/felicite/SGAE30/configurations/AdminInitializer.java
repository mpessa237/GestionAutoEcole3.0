package com.felicite.SGAE30.configurations;

import com.felicite.SGAE30.enums.Role;
import com.felicite.SGAE30.models.User;
import com.felicite.SGAE30.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println(">>>> DÉMARRAGE DE L'INITIALISATION DE L'ADMIN <<<<");

        try {
            long count = userRepo.count();
            System.out.println("Nombre d'utilisateurs actuels : " + count);

            if (count == 0) {
                User admin = new User();
                admin.setFirstname("Fogang Mpessa");
                admin.setLastname("Herve");
                admin.setEmail("hervempessa7@gmail.com");
                admin.setPhoneNumber("657938010");
                admin.setRole(Role.ADMIN);
                admin.setPassword(passwordEncoder.encode("jupitere10"));
                admin.setEnabled(true);

                userRepo.saveAndFlush(admin); // saveAndFlush force l'écriture immédiate
                System.out.println("✅ Premier compte Admin créé avec succès !");
            } else {
                System.out.println("L'admin existe déjà, pas de création nécessaire.");
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'initialisation : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
