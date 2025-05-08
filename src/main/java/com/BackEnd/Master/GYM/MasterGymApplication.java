package com.BackEnd.Master.GYM;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import com.BackEnd.Master.GYM.entity.AppUsers;
import com.BackEnd.Master.GYM.entity.Roles;
import com.BackEnd.Master.GYM.repository.AppUserRepo;
import com.BackEnd.Master.GYM.repository.RolesRepo;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class MasterGymApplication {

	private final AppUserRepo appUserRepository;
	private final RolesRepo rolesRepo;
	private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(MasterGymApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase() {
		return args -> {
			// Vérifier si un rôle "Admin" existe, sinon le créer
			Optional<Roles> adminRoleOpt = rolesRepo.findByRoleName("ROLE_Admin");
			Roles adminRole = adminRoleOpt.orElseGet(() -> {
				Roles newRole = new Roles();
				newRole.setRoleName("ROLE_Admin");
				newRole.setDescription("Administrator role with full access");
				return rolesRepo.save(newRole);
			});

			Optional<Roles> coatchRoleOpt = rolesRepo.findByRoleName("ROLE_Coach");
			coatchRoleOpt.orElseGet(() -> {
				Roles newcoatchRole = new Roles();
				newcoatchRole.setRoleName("ROLE_Coach");
				newcoatchRole.setDescription("Standard user role with limited access");
				return rolesRepo.save(newcoatchRole);
			});

			Optional<Roles> userRoleOpt = rolesRepo.findByRoleName("ROLE_User");
			userRoleOpt.orElseGet(() -> {
				Roles newUserRole = new Roles();
				newUserRole.setRoleName("ROLE_User");
				newUserRole.setDescription("Standard user role with limited access");
				return rolesRepo.save(newUserRole);
			});

			AppUsers existingAdmin = appUserRepository.findByUserName("Admin");
			if (existingAdmin == null) {
				AppUsers adminUser = new AppUsers();
				adminUser.setUserName("Admin");
				adminUser.setEmail("admin@example.com");
				adminUser.setTelephone("22865991");
				adminUser.setMotDePasse(passwordEncoder.encode("ali1234"));
				adminUser.setRole(adminRole);
				adminUser.setProfileImage("default.png");
				adminUser.setDescription("Administrator account with full access");

				appUserRepository.save(adminUser);
				System.out.println("Admin user created successfully");
			} else {
				System.out.println("Admin user already exists, skipping creation.");
			}

		};
	}

}
