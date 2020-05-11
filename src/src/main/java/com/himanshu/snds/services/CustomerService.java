package com.himanshu.snds.services;

import com.himanshu.snds.entities.Customer;
import com.himanshu.snds.repository.CustomerRepository;
import com.himanshu.snds.requests.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.ws.rs.core.Response;

@Component
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public String registerCustomer(Registration registrationRequest){
        Customer customer = customerRepository.findByUsername(registrationRequest.getUsername());

         if(customer!=null){
                 if(customerRepository.findByMobile(customer.getMobile())!=null){
                     return "Mobile number already exists!";
                 }
                 else{
                     return "An account with same username already exists!";
                 }
         }else{
                 registerNewEntry(registrationRequest);
                 return "Customer account created successfully!";
         }
    }

    private void registerNewEntry(Registration registrationRequest) {
        try{
            Customer customer = new Customer();
            customer.setName(registrationRequest.getName());
            customer.setGender(registrationRequest.getGender());
            customer.setMobile(registrationRequest.getMobile());
            customer.setAddress(registrationRequest.getAddress());
            customer.setUsername(registrationRequest.getUsername());
            customer.setPassword(registrationRequest.getPassword());
            customerRepository.save(customer);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public String getCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if(customer!=null){
            return "{\"result\":\"1\"}";
        }else{
            return "{\"result\":\"1\"}";
        }
    }
}
