package com.BackEnd.Master.GYM.services;

import java.util.List;

import com.BackEnd.Master.GYM.entity.customer;

public interface customerService {
    
    customer findById(Long id);

    List<customer> findAll();

    customer findByUserName(String userName);

    List <customer> findByUserId(Long id);

    customer insert(customer Entity);

    customer update(customer Entity);

    void deleteById(Long id);

    List<customer> searchCustomers(String query);

    long count();

}
