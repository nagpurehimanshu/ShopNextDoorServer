package com.himanshu.snds.services;

import com.himanshu.snds.entities.Customer;
import com.himanshu.snds.entities.Orders;
import com.himanshu.snds.entities.Shop;
import com.himanshu.snds.repository.CustomerRepository;
import com.himanshu.snds.repository.OrderRepository;
import com.himanshu.snds.repository.ShopRepository;
import com.himanshu.snds.requests.OrderRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    CustomerRepository customerRepository;

    public OrderRequests placeOrder(OrderRequests orderRequests){
        Orders newOrder = new Orders();

        long lastOrderNumber;
        if(orderRepository.findLastOrderNumber().size()==0) lastOrderNumber = 0;
        else lastOrderNumber = Long.parseLong(orderRepository.findLastOrderNumber().get(0));

        newOrder.setOrder_number(Long.toString(lastOrderNumber+1));
        newOrder.setCustomer_username(orderRequests.getCustomer_username());
        newOrder.setShop_username(orderRequests.getShop_username());
        newOrder.setAmount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        newOrder.setOrder_placed_date(sdf.format(new Date()));
        newOrder.setOrder_acceptance_date(null);
        newOrder.setOrder_completion_date(null);
        newOrder.setOrder_items(orderRequests.getOrder_items());
        newOrder.setOrder_status("pending");
        newOrder.setOrder_type(orderRequests.getOrder_type());
        newOrder.setOrder_mode(orderRequests.getOrder_mode());
        newOrder.setCustomer_name(orderRequests.getCustomer_name());
        newOrder.setShop_name(orderRequests.getShop_name());
        orderRepository.save(newOrder);

        orderRequests.setOrder_number(newOrder.getOrder_number());
        orderRequests.setResult("1");

        return orderRequests;
    }

    public OrderRequests getOrderDetails(String order_number){
        Orders orders = orderRepository.findByOrderNumber(order_number);
        Shop shop = shopRepository.findByUsername(orders.getShop_username());
        OrderRequests orderRequests = new OrderRequests();

        orderRequests.setOrder_number(orders.getOrder_number());
        orderRequests.setAmount(orders.getAmount());
        orderRequests.setCustomer_username(orders.getCustomer_username());
        orderRequests.setOrder_placed_date(orders.getOrder_placed_date());
        orderRequests.setOrder_acceptance_date(orders.getOrder_acceptance_date());
        orderRequests.setOrder_completion_date(orders.getOrder_completion_date());
        orderRequests.setOrder_items(orders.getOrder_items());
        orderRequests.setOrder_mode(orders.getOrder_mode());
        orderRequests.setOrder_status(orders.getOrder_type());
        orderRequests.setOrder_type(orders.getOrder_type());
        orderRequests.setShop_username(orders.getShop_username());
        orderRequests.setCustomer_name(orders.getCustomer_name());
        orderRequests.setShop_name(orders.getShop_name());
        orderRequests.setResult(shop.getAddress());

        return orderRequests;
    }

    public String getActiveOrderCount(String customer_username) {
        String count = orderRepository.getActiveOrderCount(customer_username);
        return "{\"result\":\"" + count + "\"}";
    }

    public List<OrderRequests> getCustomerOrders(String customer_username) {
        List<Orders> ordersList = orderRepository.getCustomerOrderDetails(customer_username);
        List<OrderRequests> orderRequestsList = new ArrayList<OrderRequests>();

        for(int i=0; i<ordersList.size(); i++){
            OrderRequests orderRequests = new OrderRequests();
            orderRequestsList.add(orderRequests);
            orderRequestsList.get(i).setAmount(ordersList.get(i).getAmount());
            orderRequestsList.get(i).setCustomer_username(ordersList.get(i).getCustomer_username());
            orderRequestsList.get(i).setOrder_placed_date(ordersList.get(i).getOrder_placed_date());
            orderRequestsList.get(i).setOrder_acceptance_date(ordersList.get(i).getOrder_acceptance_date());
            orderRequestsList.get(i).setOrder_completion_date(ordersList.get(i).getOrder_completion_date());
            orderRequestsList.get(i).setOrder_items(ordersList.get(i).getOrder_items());
            orderRequestsList.get(i).setOrder_mode(ordersList.get(i).getOrder_mode());
            orderRequestsList.get(i).setOrder_number(ordersList.get(i).getOrder_number());
            orderRequestsList.get(i).setOrder_status(ordersList.get(i).getOrder_status());
            orderRequestsList.get(i).setOrder_type(ordersList.get(i).getOrder_type());
            orderRequestsList.get(i).setCustomer_name(ordersList.get(i).getCustomer_name());
            orderRequestsList.get(i).setShop_name(ordersList.get(i).getShop_name());
            orderRequestsList.get(i).setShop_username(ordersList.get(i).getShop_username());
            Shop shop = shopRepository.findByUsername(ordersList.get(i).getShop_username());
            orderRequestsList.get(i).setResult(shop.getAddress());
        }

        return orderRequestsList;
    }

    public String getActiveOrderWithShop(String customer_username, String shop_username) {
        List<Orders> ordersList = orderRepository.findActiveOrderByCustomerAndShop(customer_username, shop_username);

        if(ordersList.size()==0) return "0";
        if(ordersList.size()>0){
            if(ordersList.get(0).getOrder_status().equals("completed") || ordersList.get(0).getOrder_status().equals("rejected") || ordersList.get(0).getOrder_status().equals("cancelled") ) return "0";
        }
        return "1";
    }


    public String updateOrderStatus(String order_number, String order_status, int amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        String curr_datetime = sdf.format(new Date());

        Orders orders = orderRepository.findByOrderNumber(order_number);
        orders.setOrder_status(order_status);
        orders.setAmount(amount);

        if(order_status.equals("accepted")){
            orders.setOrder_acceptance_date(curr_datetime);
        }else if(order_status.equals("completed")){
            orders.setOrder_completion_date(curr_datetime);
        }else if(order_status.equals("cancelled")){
            orders.setOrder_completion_date(curr_datetime);
        }
        orderRepository.save(orders);

        return "1";
    }

    public List<OrderRequests> getOrdersByShop(String shop_username) {
        List<Orders> ordersList = orderRepository.findOrdersByShop(shop_username);
        List<OrderRequests> orderRequestsList = new ArrayList<OrderRequests>();

        for(int i=0; i<ordersList.size(); i++){
            OrderRequests orderRequests = new OrderRequests();
            orderRequestsList.add(orderRequests);
            orderRequestsList.get(i).setAmount(ordersList.get(i).getAmount());
            orderRequestsList.get(i).setCustomer_username(ordersList.get(i).getCustomer_username());
            orderRequestsList.get(i).setOrder_placed_date(ordersList.get(i).getOrder_placed_date());
            orderRequestsList.get(i).setOrder_acceptance_date(ordersList.get(i).getOrder_acceptance_date());
            orderRequestsList.get(i).setOrder_completion_date(ordersList.get(i).getOrder_completion_date());
            orderRequestsList.get(i).setOrder_items(ordersList.get(i).getOrder_items());
            orderRequestsList.get(i).setOrder_mode(ordersList.get(i).getOrder_mode());
            orderRequestsList.get(i).setOrder_number(ordersList.get(i).getOrder_number());
            orderRequestsList.get(i).setOrder_status(ordersList.get(i).getOrder_status());
            orderRequestsList.get(i).setOrder_type(ordersList.get(i).getOrder_type());
            orderRequestsList.get(i).setCustomer_name(ordersList.get(i).getCustomer_name());
            orderRequestsList.get(i).setShop_name(ordersList.get(i).getShop_name());
            Customer customer = customerRepository.findByUsername(ordersList.get(i).getCustomer_username());
            orderRequestsList.get(i).setResult(customer.getAddress());
            orderRequestsList.get(i).setShop_username(ordersList.get(i).getShop_username());
        }

        return orderRequestsList;
    }
}
