package com.himanshu.snds.repository;

import com.himanshu.snds.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    Customer findById(int id);
    Customer findByMobile(String mobile);
    Customer findByUsername(String username);
}
