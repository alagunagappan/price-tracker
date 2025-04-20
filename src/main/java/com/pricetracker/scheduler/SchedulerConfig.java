package com.pricetracker.scheduler;

import com.pricetracker.service.AlertService;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail priceCheckJobDetail() {
        return JobBuilder.newJob(PriceCheckJob.class)
                .withIdentity("priceCheckJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger priceCheckTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(24) // Default to daily checks
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(priceCheckJobDetail())
                .withIdentity("priceCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger hourlyCheckTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForever();

        return TriggerBuilder.newTrigger()
                .forJob(priceCheckJobDetail())
                .withIdentity("hourlyCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger morningCheckTrigger() {
        DailyTimeIntervalScheduleBuilder scheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withIntervalInMinutes(2)
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(8, 0)); // 8 AM

        return TriggerBuilder.newTrigger()
                .forJob(priceCheckJobDetail())
                .withIdentity("morningCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger nightCheckTrigger() {
        DailyTimeIntervalScheduleBuilder scheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withIntervalInHours(24)
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(20, 0)); // 8 PM

        return TriggerBuilder.newTrigger()
                .forJob(priceCheckJobDetail())
                .withIdentity("nightCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }


    //below trigger can be uncommented for debugging
    /*@Bean
    public Trigger testCheckTrigger() {
        DailyTimeIntervalScheduleBuilder scheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withIntervalInMinutes(2)
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(8, 0)); // 8 AM

        return TriggerBuilder.newTrigger()
                .forJob(priceCheckJobDetail())
                .withIdentity("2minCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }*/
}
