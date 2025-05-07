package com.BackEnd.Master.GYM.services;

import java.util.List;

import com.BackEnd.Master.GYM.entity.AppUsers;

public interface AppUserService {

    AppUsers findById(Long id);

    List<AppUsers> findAll();

    AppUsers findByUserName(String userName);

    List<AppUsers> findByRoleRoleName(String roleName);

    AppUsers insert(AppUsers Entity);

    AppUsers update(AppUsers Entity);

    AppUsers updatePassword(Long userId ,String password);

    void deleteById(Long id);

    List<AppUsers> searchUsers(String query);

    long count();

    long countByRoleRoleName(String roleName);

}
