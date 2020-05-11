package com.himanshu.snds.repository;

import com.himanshu.snds.entities.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer> {
    @Query("select o from Orders o where o.order_number = ?1")
    Orders findByOrderNumber(String order_number);

    @Query("select o.order_number from Orders o order by o.order_number desc")
    List<String> findLastOrderNumber();

    @Query("select count(*) from Orders o where o.customer_username = ?1 and o.order_status <> 'delivered' ")
    String getActiveOrderCount(String customer_username);

    @Query("select o from Orders o where o.customer_username = ?1 order by o.order_number desc")
    List<Orders> getCustomerOrderDetails(String customer_username);

    @Query("select o from Orders o where o.customer_username = ?1 and o.shop_username = ?2 order by o.order_number desc")
    List<Orders> findActiveOrderByCustomerAndShop(String customer_username, String shop_username);

    @Query("select o from Orders o where o.shop_username = ?1 and o.order_status<> 'delivered' and o.order_status <> 'rejected' ")
    List<Orders> findActiveOrdersByShop(String shop_username);
}
