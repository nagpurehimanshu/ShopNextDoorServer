package com.himanshu.snds.services;

import com.himanshu.snds.entities.Customer;
import com.himanshu.snds.entities.Orders;
import com.himanshu.snds.entities.Shop;
import com.himanshu.snds.repository.CustomerRepository;
import com.himanshu.snds.requests.CustomerRequests;
import com.himanshu.snds.requests.OrderRequests;
import com.himanshu.snds.requests.ShopRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<CustomerRequests> getCustomerList() {
        List<Customer> customerList = customerRepository.getCustomerList();
        List<CustomerRequests> customerRequestsList = new ArrayList<CustomerRequests>();

        if(customerList.size()>0) {
            for (int i = 0; i < customerList.size(); i++) {
                customerRequestsList.add(new CustomerRequests());
                customerRequestsList.get(i).setName(customerList.get(i).getName());
                customerRequestsList.get(i).setAddress(customerList.get(i).getAddress());
                customerRequestsList.get(i).setGender(customerList.get(i).getGender());
                customerRequestsList.get(i).setMobile(customerList.get(i).getMobile());
                customerRequestsList.get(i).setUsername(customerList.get(i).getUsername());
                customerRequestsList.get(i).setPassword(customerList.get(i).getPassword());
                customerRequestsList.get(i).setResult("1");
            }
        }else{
            customerRequestsList.add(new CustomerRequests());
            customerRequestsList.get(0).setResult("0");
        }
        return customerRequestsList;
    }

    public String updateCustomer(String username, String mobile, String address) {
        Customer customer = customerRepository.findByUsername(username);
        customer.setMobile(mobile);
        customer.setAddress(address);
        customerRepository.save(customer);
        return "1";
    }
}