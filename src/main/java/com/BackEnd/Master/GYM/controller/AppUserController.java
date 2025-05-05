package com.BackEnd.Master.GYM.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.BackEnd.Master.GYM.Exceptions.ResourceNotFoundException;
import com.BackEnd.Master.GYM.dto.AppUserDto;
import com.BackEnd.Master.GYM.entity.AppUsers;
import com.BackEnd.Master.GYM.entity.Roles;
import com.BackEnd.Master.GYM.Mapper.AppUserMapper;
import com.BackEnd.Master.GYM.services.AppUserService;
import com.BackEnd.Master.GYM.repository.RolesRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserMapper appUserMapper;
    private final RolesRepo rolesRepo;
    //private final PasswordEncoder passwordEncoder;

    //@PreAuthorize("hasAuthority('ROLE_Employe','ROLE_Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> findById(@PathVariable Long id) {
        AppUsers entity = appUserService.findById(id);
        AppUserDto userDto = appUserMapper.map(entity);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping()
    public ResponseEntity<List<AppUserDto>> findAll() {
        List<AppUsers> entities = appUserService.findAll();
        List<AppUserDto> userDtos = appUserMapper.map(entities);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/filtre")
    public ResponseEntity<AppUserDto> filtre(@RequestParam String userName) {
        AppUsers entity = appUserService.findByUserName(userName);
        AppUserDto userDto = appUserMapper.map(entity);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        String imagePath = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/Profile-img/" + imageName;
        File imgFile = new File(imagePath);

        if (!imgFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Remplacez par le type MIME correct
                    .body(new FileSystemResource(imgFile));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<AppUserDto> insert(
            @RequestParam("userName") String userName,
            @RequestParam("email") String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam("roleName") String roleName,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {

        Roles role = rolesRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Hachage du mot de passe avec BCrypt
        //String hashedPassword = passwordEncoder.encode(motDePasse);

        AppUsers user = new AppUsers();
        user.setUserName(userName);
        user.setEmail(email);
        user.setTelephone(telephone);
        //user.setMotDePasse(hashedPassword);
        user.setMotDePasse(motDePasse);
        user.setRole(role);

        if (profileImage.isEmpty()) {
            throw new RuntimeException("Profile image is required");
        }

        @SuppressWarnings("null")
        String imageName = StringUtils.cleanPath(profileImage.getOriginalFilename());
        String uploadDir = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/Profile-img/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String imagePath = uploadDir + imageName;
        profileImage.transferTo(new File(imagePath));

        // user.setProfileImage(imagePath); pour sauv just le nom de l'imge dans la BD
        user.setProfileImage(imageName);

        AppUsers entity = appUserService.insert(user);

        AppUserDto responseDto = appUserMapper.map(entity);

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<AppUserDto> update(
            @RequestParam("id") Long id,
            @RequestParam("userName") String userName,
            @RequestParam("email") String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam("roleName") String roleName,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {

        AppUsers currentUser = appUserService.findById(id);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        String currentImage = currentUser.getProfileImage();

        currentUser.setUserName(userName);
        currentUser.setEmail(email);
        currentUser.setTelephone(telephone);
        // Hachage du mot de passe avec BCrypt
        //String hashedPassword = passwordEncoder.encode(motDePasse);
        //currentUser.setMotDePasse(hashedPassword);
        currentUser.setMotDePasse(motDePasse);
        Roles role = rolesRepo.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
        currentUser.setRole(role);

        if (profileImage != null && !profileImage.isEmpty()) {
            @SuppressWarnings("null")
            String imageName = StringUtils.cleanPath(profileImage.getOriginalFilename());
            String uploadDir = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/Profile-img/";
            File uploadDirFile = new File(uploadDir);

            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            String imagePath = uploadDir + imageName;
            profileImage.transferTo(new File(imagePath));

            // pour sauv just le nom de l'imge dans la BD:
            // currentUser.setProfileImage(imagePath);
            currentUser.setProfileImage(imageName);
        }

        if (currentImage != null && currentUser.getProfileImage() == currentImage) {
            File imageFile = new File(currentImage);
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Image deleted successfully: ");
                } else {
                    System.out.println("Failed to delete image: ");
                }
            } else {
                System.out.println("Image file not found: ");
            }
        }

        AppUsers updatedUser = appUserService.update(currentUser);

        AppUserDto responseDto = appUserMapper.map(updatedUser);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        AppUsers user = appUserService.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        String uploadDir = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/Profile-img/";
        String imagePath = uploadDir + user.getProfileImage();
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Image deleted successfully: ");
                } else {
                    System.out.println("Failed to delete image: ");
                }
            } else {
                System.out.println("Image file not found: ");
            }
        }

        appUserService.deleteById(id);

        // Crée une chaîne JSON
        String jsonResponse = "{\"message\": \"User and associated image deleted successfully.\"}";

        return ResponseEntity.ok(jsonResponse);
    }

}
