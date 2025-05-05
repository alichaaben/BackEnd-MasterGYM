package com.BackEnd.Master.GYM.services;

import java.util.List;

import com.BackEnd.Master.GYM.entity.AppUsers;

public interface AppUserService {

    AppUsers findById(Long id);

    List<AppUsers> findAll();

    AppUsers findByUserName(String userName);

    AppUsers insert(AppUsers Entity);

    AppUsers update(AppUsers Entity);

    void deleteById(Long id);

}
