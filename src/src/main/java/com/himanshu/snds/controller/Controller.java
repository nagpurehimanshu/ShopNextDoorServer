package com.himanshu.snds.controller;

import com.himanshu.snds.requests.Registration;
import com.himanshu.snds.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController("/customer")
@Component
@Slf4j
public class Controller {

    @Autowired
    private CustomerService customerService;
    @PostMapping("/registerCustomer")
    ResponseEntity registerCustomer(@RequestBody Registration registrationRequest){
        try{
            return new ResponseEntity(customerService.registerCustomer(registrationRequest), HttpStatus.OK);
        }catch(JDBCException jdbc){
            return new ResponseEntity("Unable to connect to Database.", HttpStatus.GATEWAY_TIMEOUT);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/getCustomer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    //@GetMapping("/getCustomer")
    ResponseEntity<String> getCustomer(@QueryParam("username")String username){
        try{
            return new ResponseEntity<String>(customerService.getCustomer(username), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
