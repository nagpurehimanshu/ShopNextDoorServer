package com.himanshu.snds.services;

import com.himanshu.snds.entities.Shop;
import com.himanshu.snds.repository.ShopRepository;
import com.himanshu.snds.requests.ShopRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepository;

    public ShopRequests registerShop(ShopRequests shopRequests){
        Shop shop = shopRepository.findByUsername(shopRequests.getUsername());

        if(shop==null){
            Shop newShop = new Shop();
            newShop.setName(shopRequests.getName());
            newShop.setUsername(shopRequests.getUsername());
            newShop.setPassword(shopRequests.getPassword());
            newShop.setAddress(shopRequests.getAddress());
            newShop.setOwner_name(shopRequests.getOwner_name());
            newShop.setOwner_mobile(shopRequests.getOwner_mobile());
            newShop.setEmail(shopRequests.getEmail());
            newShop.setShop_type(shopRequests.getShop_type());
            newShop.setVerified(1);
            shopRepository.save(newShop);

            shopRequests.setResult("1");
        }else{
            shopRequests.setResult("2");
        }
        return shopRequests;
    }

    public ShopRequests getShop(String username){
        Shop shop = shopRepository.findByUsername(username);
        ShopRequests shopRequests = new ShopRequests();

        if(shop!=null){
            if(shop.getVerified()==1){
                shopRequests.setResult("1");
            }else if(shop.getVerified()==2){
                shopRequests.setResult("2");
            }else{
                shopRequests.setResult("3");
            }
            shopRequests.setUsername(shop.getUsername());
            shopRequests.setPassword(shop.getPassword());
            shopRequests.setOwner_name(shop.getOwner_name());
            shopRequests.setOwner_mobile(shop.getOwner_mobile());
            shopRequests.setEmail(shop.getEmail());
            shopRequests.setAddress(shop.getAddress());
            shopRequests.setName(shop.getName());
            shopRequests.setShop_type(shop.getShop_type());
        }else{
            shopRequests.setResult("4");
        }

        return shopRequests;
    }

    public List<ShopRequests> getShopList(){
        List<Shop> shopList = shopRepository.getShopList();
        List<ShopRequests> shopRequestsList = new ArrayList<ShopRequests>();

        if(shopList.size()>0) {
            for (int i = 0; i < shopList.size(); i++) {
                shopRequestsList.add(new ShopRequests());
                shopRequestsList.get(i).setName(shopList.get(i).getName());
                shopRequestsList.get(i).setAddress(shopList.get(i).getAddress());
                shopRequestsList.get(i).setOwner_mobile(shopList.get(i).getOwner_mobile());
                shopRequestsList.get(i).setEmail(shopList.get(i).getEmail());
                shopRequestsList.get(i).setShop_type(shopList.get(i).getShop_type());
                shopRequestsList.get(i).setOwner_name(shopList.get(i).getOwner_name());
                shopRequestsList.get(i).setUsername(shopList.get(i).getUsername());
                shopRequestsList.get(i).setPassword(shopList.get(i).getPassword());
                shopRequestsList.get(i).setResult("1");
            }
        }else{
            shopRequestsList.add(new ShopRequests());
            shopRequestsList.get(0).setResult("0");
        }
        return shopRequestsList;
    }

    public void setVerification(String username, int value){
        shopRepository.setVerification(username, value);
    }


    public String updateShop(String username, String owner_mobile, String address, String email) {
        Shop shop = shopRepository.findByUsername(username);
        shop.setOwner_mobile(owner_mobile);
        shop.setEmail(email);
        shop.setAddress(address);
        shopRepository.save(shop);
        return "1";
    }
}