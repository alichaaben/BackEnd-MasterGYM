package com.BackEnd.Master.GYM.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.BackEnd.Master.GYM.Exceptions.ResourceNotFoundException;
import com.BackEnd.Master.GYM.dto.customerDto;
import com.BackEnd.Master.GYM.entity.AppUsers;
import com.BackEnd.Master.GYM.entity.customer;
import com.BackEnd.Master.GYM.Mapper.customerMapper;
import com.BackEnd.Master.GYM.services.AppUserService;
import com.BackEnd.Master.GYM.services.customerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class customerController {

    private final customerService custService;
    private final customerMapper custMapper;
    private final AppUserService userRepo;

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<customerDto> findById(@PathVariable Long id) {
        customer entity = custService.findById(id);
        customerDto customerDto = custMapper.map(entity);
        return ResponseEntity.ok(customerDto);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping()
    public ResponseEntity<List<customerDto>> findAll() {
        List<customer> entities = custService.findAll();
        List<customerDto> customerDtos = custMapper.map(entities);
        return ResponseEntity.ok(customerDtos);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Coach')")
    @GetMapping("/count")
    public ResponseEntity<Long> countAllCustomers() {
        long entities = custService.count();
        return ResponseEntity.ok(entities);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Coach')")
    @GetMapping("/search")
    public ResponseEntity<List<customerDto>> searchCustomers(@RequestParam String query) {
        List<customer> entities = custService.searchCustomers(query);
        return ResponseEntity.ok(custMapper.map(entities));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Coach')")
    @GetMapping("/filtre-name")
    public ResponseEntity<customerDto> filtre(@RequestParam String userName) {
        customer entity = custService.findByUserName(userName);
        customerDto customerDto = custMapper.map(entity);
        return ResponseEntity.ok(customerDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Coach')")
    @GetMapping("/filtre-user/{id}")
    public ResponseEntity<List<customerDto>> findByUserId(@PathVariable Long id) {
        List<customer> entity = custService.findByUserId(id);
        List<customerDto> customerDto = custMapper.map(entity);
        return ResponseEntity.ok(customerDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Coach')")
    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        String imagePath = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/customer/" + imageName;
        File imgFile = new File(imagePath);

        if (!imgFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new FileSystemResource(imgFile));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Coach')")
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<customerDto> insert(
            @RequestParam("userName") String userName,
            @RequestParam("email") String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            @RequestParam("pack") String pack,
            @RequestParam("userId") Long userId,
            @RequestParam("montPay") String montPay,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {

        AppUsers user = userRepo.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        customer customer = new customer();
        customer.setUserName(userName);
        customer.setEmail(email);
        customer.setTelephone(telephone);
        customer.setDateDebut(dateDebut);
        customer.setDateFin(dateFin);
        customer.setPack(pack);
        customer.setUser(user);
        customer.setMontPay(montPay);

        if (profileImage.isEmpty()) {
            throw new RuntimeException("Profile image is required");
        }

        @SuppressWarnings("null")
        String imageName = StringUtils.cleanPath(profileImage.getOriginalFilename());
        String uploadDir = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/customer/";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String imagePath = uploadDir + imageName;
        profileImage.transferTo(new File(imagePath));

        customer.setProfileImage(imageName);

        customer entity = custService.insert(customer);

        customerDto responseDto = custMapper.map(entity);

        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Coach')")
    @PutMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<customerDto> update(
            @RequestParam("id") Long id,
            @RequestParam("userName") String userName,
            @RequestParam("email") String email,
            @RequestParam("telephone") String telephone,
            @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            @RequestParam("pack") String pack,
            @RequestParam("userId") Long userId,
            @RequestParam("montPay") String montPay,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {

        customer currentCustomer = custService.findById(id);
        if (currentCustomer == null) {
            throw new ResourceNotFoundException("customer not found with ID: " + id);
        }
        String currentImage = currentCustomer.getProfileImage();

        currentCustomer.setUserName(userName);
        currentCustomer.setEmail(email);
        currentCustomer.setTelephone(telephone);
        currentCustomer.setDateDebut(dateDebut);
        currentCustomer.setDateFin(dateFin);
        currentCustomer.setPack(pack);
        currentCustomer.setMontPay(montPay);

        AppUsers user = userRepo.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        currentCustomer.setUser(user);

        if (profileImage != null && !profileImage.isEmpty()) {
            @SuppressWarnings("null")
            String imageName = StringUtils.cleanPath(profileImage.getOriginalFilename());
            String uploadDir = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/customer/";
            File uploadDirFile = new File(uploadDir);

            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            String imagePath = uploadDir + imageName;
            profileImage.transferTo(new File(imagePath));
            currentCustomer.setProfileImage(imageName);
        }

        if (currentImage != null && currentCustomer.getProfileImage() == currentImage) {
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

        customer updatedCustomer = custService.update(currentCustomer);

        customerDto responseDto = custMapper.map(updatedCustomer);

        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        customer customer = custService.findById(id);
        if (customer == null) {
            throw new ResourceNotFoundException("customer not found with ID: " + id);
        }
        String uploadDir = "/home/ali/Bureau/frelance/Master GYM/FrontEnd/src/assets/customer/";
        String imagePath = uploadDir + customer.getProfileImage();
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

        custService.deleteById(id);

        String jsonResponse = "{\"message\": \"Customer and associated image deleted successfully.\"}";

        return ResponseEntity.ok(jsonResponse);
    }

}
