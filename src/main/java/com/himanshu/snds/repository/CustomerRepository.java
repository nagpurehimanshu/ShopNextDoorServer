package com.himanshu.snds.repository;

import com.himanshu.snds.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    Customer findById(int id);

    Customer findByMobile(String mobile);
    Customer findByUsername(String username);

    @Query("select c from  Customer c where c.username = ?1 and c.password = ?2")
    Customer findByUsernameAndByPassword(String username, String password);

    @Query("select c from Customer c")
    List<Customer> getCustomerList();

    @Query("select c.address from Customer c where c.username = ?1")
    String getCustomerAddress(String customer_username);
}