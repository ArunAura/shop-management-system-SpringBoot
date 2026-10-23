package com.example.shopmanagement.Jobs;

import com.example.shopmanagement.model.Shop;
import com.example.shopmanagement.repository.ShopRepo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Optional;


@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SendRegistrationDetails implements Job {

    @Autowired
    private ShopRepo repo;

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        JobDataMap data = context.getJobDetail().getJobDataMap();
        long shopId = data.getLong("ShopId");


        Optional<Shop> shop = repo.findById(shopId);

        if (shop.isPresent()) {
            Shop shopDetails = shop.get();
            System.out.println("<<<<<<<<REGISTRATION DETAILS SENT>>>>>>>>");
            System.out.println(shopDetails.getId());
            System.out.println(shopDetails.getOwnername());
            System.out.println(shopDetails.getShoptype());
            System.out.println(shopDetails.getAdress());
            System.out.println(shopDetails.getCountry());
            System.out.println("<<<<<<<<<<>>>>>>>>");

            data.put("ShopId", shopId + 1);


        } else {
            System.out.println("Shop not found: " + shopId);
            data.put("ShopId", shopId + 1);
        }


    }
}
