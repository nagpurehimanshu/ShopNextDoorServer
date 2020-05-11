package com.himanshu.snds.services;

import com.himanshu.snds.entities.Shop;
import com.himanshu.snds.repository.ShopRepository;
import com.himanshu.snds.requests.ShopRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
            newShop.setVerified(1);
            shopRepository.save(newShop);

            shopRequests.setResult("1");
        }else{
            shopRequests.setResult("2");
        }
        return shopRequests;
    }

    public ShopRequests getShop(String username, String password){
        Shop shop = shopRepository.findByUsernameAndByPassword(username, password);
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
            shopRequests.setAddress(shop.getAddress());
            shopRequests.setName(shop.getName());
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
}