package com.BackEnd.Master.GYM.services;

import java.util.List;
import java.util.Optional;

import com.BackEnd.Master.GYM.entity.Roles;

public interface RolesService {

    Roles findById(Long id);

    List<Roles> findAll();

    Optional<Roles> findByRoleName(String roleName);

    Roles insert(Roles Entity);

    Roles update(Roles Entity);

    void deleteById(Long id);

}
