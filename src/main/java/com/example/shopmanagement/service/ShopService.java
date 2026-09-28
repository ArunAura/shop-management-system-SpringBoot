package com.example.shopmanagement.service;
import com.example.shopmanagement.model.Shop;
import com.example.shopmanagement.repository.ShopRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class ShopService {

    @Autowired
    ShopRepo repo;
    public String adduser(@Valid Shop shop, BindingResult result){
        if(result.hasErrors()){
            return "add-shop";
        }
        repo.save(shop);
        return "redirect:/";
    }

    public Shop findShopById(long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid shop id: " + id));
    }




    public void updateShop(long id, @Valid Shop shop) {
        shop.setId(id);
        repo.save(shop);
    }

    public void deleteUser(Shop shop){
        repo.delete(shop);

    }
}







