package com.example.shopmanagement.controller;


import com.example.shopmanagement.model.Shop;
import com.example.shopmanagement.repository.ShopRepo;
import com.example.shopmanagement.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShopController {
    @Autowired
    ShopService service;


    @GetMapping("/")
    public String viewRegistraionPage(Model model){
        Shop shop = new Shop();
        model.addAttribute("shop", shop);
        return "add-shop";
    }

    @Autowired
    ShopRepo repo;
    @GetMapping("/index")
    public String showShopList(Model model) {
        model.addAttribute("shop", repo.findAll());
        return "index";
    }

    @PostMapping("/addshop")
    public String addShop(@Valid Shop shop, BindingResult result){
        service.adduser(shop,result);
        return"add-shop";

    }

    @GetMapping("/edit/{id}")
    public String findShopById(@PathVariable ("id") long id ,Model model){
        Shop shop = service.findShopById(id);
        model.addAttribute("shop",shop);
        return "update-shop";

    }
    @PostMapping("update/{id}")
    public String updateShop(@PathVariable("id") long id,@Valid Shop shop,BindingResult result){
        if(result.hasErrors()){
            return"update-shop";
        }
        service.updateShop(id,shop);
        return"redirect:/index";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable  ("id") long id){
        Shop shop = service.findShopById(id);
        service.deleteUser(shop);
        return "redirect:/index";


    }




}
