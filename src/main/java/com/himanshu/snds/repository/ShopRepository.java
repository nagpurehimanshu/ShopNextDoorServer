package com.himanshu.snds.repository;

import com.himanshu.snds.entities.Shop;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ShopRepository extends CrudRepository<Shop,Integer> {
    Shop findById(int id);
    Shop findByUsername(String username);

    @Query("select s from  Shop s where s.username = ?1 and s.password = ?2")
    Shop findByUsernameAndByPassword(String username, String password);

    @Query("select s from Shop s where s.verified = 2")
    List<Shop> getShopList();

    @Query("select s.name from Shop s")
    List<String> getShopNames();

    @Transactional
    @Modifying
    @Query("update Shop s set s.verified = ?2 where s.username = ?1")
    void setVerification(String username, int value);

    @Query("select s.address from Shop s where s.username = ?1")
    String getShopAddress(String shop_username);
}