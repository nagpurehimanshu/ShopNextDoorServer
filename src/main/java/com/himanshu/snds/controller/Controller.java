package com.himanshu.snds.controller;

        import com.himanshu.snds.entities.Orders;
        import com.himanshu.snds.requests.CustomerRequests;
        import com.himanshu.snds.requests.OrderRequests;
        import com.himanshu.snds.requests.ShopRequests;
        import com.himanshu.snds.services.CustomerService;
        import com.himanshu.snds.services.OrderService;
        import com.himanshu.snds.services.ShopService;
        import lombok.extern.slf4j.Slf4j;
        import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
        import org.hibernate.criterion.Order;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Component;
        import org.springframework.web.bind.annotation.*;

        import java.util.ArrayList;
        import java.util.List;

@RestController("/")
@Component
@Slf4j
public class Controller {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/registerCustomer")
    ResponseEntity<CustomerRequests> registerCustomer(@RequestBody CustomerRequests customerRequests){
        try{
            return new ResponseEntity<CustomerRequests>(customerService.registerCustomer(customerRequests), HttpStatus.OK);
        }catch (Exception e){
            customerRequests.setResult("4");
            System.out.println(e.getMessage());
            return new ResponseEntity<CustomerRequests>(customerRequests, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomer")
    ResponseEntity<CustomerRequests> getCustomer(@RequestParam String username){
        try{
            return new ResponseEntity<CustomerRequests>(customerService.getCustomer(username), HttpStatus.OK);
        }catch(Exception e){
            CustomerRequests customerRequests = new CustomerRequests();
            customerRequests.setResult("3");
            System.out.println(e.getMessage());
            return new ResponseEntity<CustomerRequests>(customerRequests, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomerList")
    ResponseEntity<List<CustomerRequests>> getCustomerList(){
        try{
            return new ResponseEntity<List<CustomerRequests>>(customerService.getCustomerList(), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            List<CustomerRequests> customerRequestsList = new ArrayList<CustomerRequests>();
            customerRequestsList.add(new CustomerRequests());
            customerRequestsList.get(0).setResult("0");
            return new ResponseEntity<List<CustomerRequests>>(customerRequestsList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCustomer")
    ResponseEntity<String> updateCustomer(@RequestParam String username, @RequestParam String mobile, @RequestParam String address){
        try{
            return new ResponseEntity<String>(customerService.updateCustomer(username, mobile, address), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("-1", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registerShop")
    ResponseEntity<ShopRequests> registerShop(@RequestBody ShopRequests shopRequests){
        try{
            return new ResponseEntity<ShopRequests>(shopService.registerShop(shopRequests), HttpStatus.OK);
        }catch (Exception e){
            //Server exception
            shopRequests.setResult("3");
            System.out.println(e.getMessage());
            return new ResponseEntity<ShopRequests>(shopRequests, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getShop")
    ResponseEntity<ShopRequests> getShop(@RequestParam String username){
        try{
            return new ResponseEntity<ShopRequests>(shopService.getShop(username), HttpStatus.OK);
        }catch (Exception e){
            ShopRequests shopRequests = new ShopRequests();
            shopRequests.setResult("5");
            System.out.println(e.getMessage());
            return new ResponseEntity<ShopRequests>(shopRequests, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getShopList")
    ResponseEntity<List<ShopRequests>> getShopList(){
        try{
            return new ResponseEntity<List<ShopRequests>>(shopService.getShopList(), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            List<ShopRequests> shopRequestsList = new ArrayList<ShopRequests>();
            shopRequestsList.add(new ShopRequests());
            shopRequestsList.get(0).setResult("0");
            return new ResponseEntity<List<ShopRequests>>(shopRequestsList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateShop")
    ResponseEntity<String> updateShop(@RequestParam String username, @RequestParam String mobile, @RequestParam String address){
        try{
            return new ResponseEntity<String>(customerService.updateCustomer(username, mobile, address), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("-1", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/placeOrder")
    ResponseEntity<OrderRequests> placeOrder(@RequestBody OrderRequests orderRequests){
        try{
            return new ResponseEntity<OrderRequests>(orderService.placeOrder(orderRequests), HttpStatus.OK);
        }catch(Exception e){
            orderRequests.setResult("0");
            System.out.println(e.getMessage());
            return new ResponseEntity<OrderRequests>(orderRequests, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOrderDetails")
    ResponseEntity<OrderRequests> getOrderDetails(@RequestParam String order_number){
        try{
            return new ResponseEntity<OrderRequests>(orderService.getOrderDetails(order_number), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            OrderRequests orderRequests = new OrderRequests();
            orderRequests.setResult("0");
            return new ResponseEntity<OrderRequests>(orderRequests, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getActiveOrderCount")
    ResponseEntity<String> getActiveOrderCount(@RequestParam String customer_username){
        try{
            return new ResponseEntity<String>(orderService.getActiveOrderCount(customer_username), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("{\"result\":\"-1\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/setVerification")
    ResponseEntity<String> setVerification(@RequestParam String username, @RequestParam int value){
        try{
            shopService.setVerification(username, value);
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomerOrders")
    ResponseEntity<List<OrderRequests>> getCustomerOrders(@RequestParam String customer_username){
        try{
            return new ResponseEntity<List<OrderRequests>>(orderService.getCustomerOrders(customer_username), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            List<OrderRequests> orderRequestsList = new ArrayList<OrderRequests>();
            orderRequestsList.add(new OrderRequests());
            orderRequestsList.get(0).setResult("0");
            return new ResponseEntity<List<OrderRequests>>(orderRequestsList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getActiveOrderWithShop")
    ResponseEntity<String> getActiveOrderWithShop(@RequestParam String customer_username, String shop_username){
        try{
            return new ResponseEntity<String>(orderService.getActiveOrderWithShop(customer_username, shop_username), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("-1", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateOrderStatus")
    ResponseEntity<String> updateOrderStatus(@RequestParam String order_number, @RequestParam String order_status){
        try{
            return new ResponseEntity<String>(orderService.updateOrderStatus(order_number, order_status), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("-1", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOrdersByShop")
    ResponseEntity<List<OrderRequests>> getOrdersByShop(@RequestParam String shop_username){
        try{
            return new ResponseEntity<List<OrderRequests>>(orderService.getOrdersByShop(shop_username), HttpStatus.OK);
        }catch (Exception e){
            List<OrderRequests> orderRequestsList = new ArrayList<OrderRequests>();
            OrderRequests orderRequests = new OrderRequests();
            orderRequestsList.add(orderRequests);
            orderRequestsList.get(0).setResult("-1");
            System.out.println(e.getMessage());
            return new ResponseEntity<List<OrderRequests>>(orderRequestsList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}