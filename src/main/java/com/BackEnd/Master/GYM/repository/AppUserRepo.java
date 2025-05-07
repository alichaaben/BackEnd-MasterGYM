package com.BackEnd.Master.GYM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BackEnd.Master.GYM.entity.AppUsers;

@Repository
public interface AppUserRepo extends JpaRepository<AppUsers, Long> {

    AppUsers findByUserName(String userName);

    List<AppUsers> findByRoleRoleName(String roleName);

    List<AppUsers> findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
            String userName, String email, String telephone);

    long count();

    long countByRoleRoleName(String roleName);

}