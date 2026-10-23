package com.example.shopmanagement.Jobs;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail sendDetailsJobDetail() {
        return JobBuilder.newJob(SendRegistrationDetails.class)
                .withIdentity("SendDetails")
                .usingJobData("ShopId", 1L)
                .storeDurably()
                .build();
    }


    @Bean
    public Trigger sendDetailsTrigger(JobDetail sendDetailsJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(sendDetailsJobDetail)
                .withIdentity("SendDetailsTrigger")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(10)
                                .repeatForever()
                )
                .build();
    }

}
