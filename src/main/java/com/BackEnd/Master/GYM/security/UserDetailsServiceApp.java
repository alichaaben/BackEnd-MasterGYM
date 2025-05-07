package com.BackEnd.Master.GYM.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BackEnd.Master.GYM.entity.AppUsers;
import com.BackEnd.Master.GYM.repository.AppUserRepo;

import java.util.Collections;

@Service
public class UserDetailsServiceApp implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    public UserDetailsServiceApp(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUsers user = appUserRepo.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User Not Found !!");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),                 // Nom d'utilisateur
                user.getMotDePasse(),               // Mot de passe
                Collections.singletonList(authority) // Liste des autorités
        );
    }
}
