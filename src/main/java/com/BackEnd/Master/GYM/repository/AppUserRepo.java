package com.BackEnd.Master.GYM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BackEnd.Master.GYM.entity.AppUsers;

@Repository
public interface AppUserRepo extends JpaRepository <AppUsers,Long> {

    AppUsers findByUserName(String userName);

}
