
package  com.BackEnd.Master.GYM.services.Impl;

import java.util.*;

import org.springframework.stereotype.Service;
import  com.BackEnd.Master.GYM.Exceptions.EntityNotFoundException;
import  com.BackEnd.Master.GYM.Exceptions.InvalidEntityException;
import com.BackEnd.Master.GYM.entity.customer;
import com.BackEnd.Master.GYM.repository.customerRepo;
import com.BackEnd.Master.GYM.services.customerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class customerServiceImpl implements customerService{
    
    private final customerRepo customerRepo;

    @Override
    public customer findById(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("customer not found with ID: " + id));
    }

    @Override
    public List<customer> findAll() {
        return customerRepo.findAll();
    }

        @Override
    public List<customer> searchCustomers(String query) {
        return customerRepo.findByUserNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
                query, query, query);
    }

    @Override
    public long count() {
        return customerRepo.count();
    }
    
    @Override
    public customer findByUserName(String userName) {
        return customerRepo.findByUserName(userName);
    }

    @Override
    public List<customer> findByUserId(Long id) {
        return customerRepo.findByUserId(id);
    }

    @Override
    public customer insert(customer entity) {
        if (entity.getUserName() == null || entity.getUserName().isEmpty()) {
            throw new InvalidEntityException("Username cannot be empty.");
        }
        return customerRepo.save(entity);
    }

    @Override
    public customer update(customer Entity) {
        customer currentUser = customerRepo.findById(Entity.getId())
        .orElseThrow(() -> new IllegalArgumentException("customer not found"));

        currentUser.setUserName(Entity.getUserName());
        currentUser.setEmail(Entity.getEmail());
        currentUser.setTelephone(Entity.getTelephone());
        currentUser.setDateDebut(Entity.getDateDebut());
        currentUser.setDateFin(Entity.getDateFin());
        currentUser.setPack(Entity.getPack());
        currentUser.setMontPay(Entity.getMontPay());
        
        return customerRepo.save(currentUser);
    }

    @Override
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }


}
