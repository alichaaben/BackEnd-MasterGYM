package com.BackEnd.Master.GYM.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackEnd.Master.GYM.Exceptions.ResourceNotFoundException;
import com.BackEnd.Master.GYM.dto.RolesDto;
import com.BackEnd.Master.GYM.entity.Roles;
import com.BackEnd.Master.GYM.Mapper.RolesMapper;
import com.BackEnd.Master.GYM.services.RolesService;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/role")
@CrossOrigin("*")
public class RolesController {
    
    private final RolesService rolesService;
    private final RolesMapper rolesMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RolesDto> findById(@PathVariable Long id) {
        Roles entity = rolesService.findById(id);
        RolesDto roleDto = rolesMapper.map(entity);
        return ResponseEntity.ok(roleDto);
    }

    @GetMapping()
    public ResponseEntity<List<RolesDto>> findAll() {
        List<Roles> entities = rolesService.findAll();
        List<RolesDto> roleDto = rolesMapper.map(entities);
        return ResponseEntity.ok(roleDto);
    }

    @PostMapping()
    public ResponseEntity<RolesDto> insert(@RequestBody RolesDto dto) {
        Optional<Roles> existingRole = rolesService.findByRoleName(dto.getRoleName());
        if (existingRole.isPresent()) {
            throw new ResourceNotFoundException("Role already exists with name: " + dto.getRoleName());
        }
    
        Roles role = rolesMapper.unMap(dto);
        Roles entity = rolesService.insert(role);
        RolesDto rdto = rolesMapper.map(entity);
    
        return ResponseEntity.ok(rdto);
    }
    

    @PutMapping()
    public ResponseEntity<RolesDto> update(@RequestBody RolesDto dto) {

        Roles currentRoles = rolesService.findById(dto.getId());
        rolesMapper.updateEntityFromDto(currentRoles, dto);
        Roles updatedRoles = rolesService.update(currentRoles);
        RolesDto responseDto = rolesMapper.map(updatedRoles);

        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        rolesService.deleteById(id);
    }
}
