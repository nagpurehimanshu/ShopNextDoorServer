package com.himanshu.snds.services;

import com.himanshu.snds.entities.Customer;
import com.himanshu.snds.entities.Orders;
import com.himanshu.snds.repository.CustomerRepository;
import com.himanshu.snds.requests.CustomerRequests;
import com.himanshu.snds.requests.OrderRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerRequests registerCustomer(CustomerRequests customerRequests){
        Customer customer = customerRepository.findByUsername(customerRequests.getUsername());

        if(customer==null){
            if(customerRepository.findByMobile(customerRequests.getMobile())!=null){
                customerRequests.setResult("2");
                return customerRequests;
            }
            else{
                Customer newCustomer = new Customer();
                newCustomer.setName(customerRequests.getName());
                newCustomer.setGender(customerRequests.getGender());
                newCustomer.setMobile(customerRequests.getMobile());
                newCustomer.setAddress(customerRequests.getAddress());
                newCustomer.setUsername(customerRequests.getUsername());
                newCustomer.setPassword(customerRequests.getPassword());
                customerRepository.save(newCustomer);
                customerRequests.setResult("1");
                return customerRequests;
            }
        }else{
            customerRequests.setResult("3");
            return customerRequests;
        }
    }


    public CustomerRequests getCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username);
        CustomerRequests customerRequests = new CustomerRequests();
        //Customer Already Exists
        if(customer!=null){
            customerRequests.setName(customer.getName());
            customerRequests.setResult("1");
            customerRequests.setUsername(customer.getUsername());
            customerRequests.setAddress(customer.getAddress());
            customerRequests.setGender(customer.getGender());
            customerRequests.setMobile(customer.getMobile());
            customerRequests.setPassword(customer.getPassword());
        }
        //Does not exist
        else{
            customerRequests.setResult("2");
        }
        return customerRequests;
    }
}