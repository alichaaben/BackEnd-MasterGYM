package com.BackEnd.Master.GYM.repository;


import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BackEnd.Master.GYM.entity.customer;

@Repository
public interface customerRepo extends JpaRepository <customer,Long> {

    List <customer> findByUserId(Long id);
    customer findByUserName(String userName);

        List<customer> findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
        String userName, String email, String telephone);

        long count();

}
