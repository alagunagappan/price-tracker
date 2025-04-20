package com.pricetracker.scheduler;

import com.pricetracker.model.Frequency;
import com.pricetracker.service.AlertService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PriceCheckJob implements Job {

    @Autowired
    private AlertService alertService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String triggerName = context.getTrigger().getKey().getName();
        Frequency frequency = Frequency.DAILY; // default

        if (triggerName.contains("morning")) {
            frequency = Frequency.MORNING;
        } else if (triggerName.contains("night")) {
            frequency = Frequency.NIGHT;
        } else if (triggerName.contains("hourly")) {
            frequency = Frequency.HOURLY;
        }

        System.out.println("Notification Job has been triggered with frequency: " + frequency + " at " + LocalDateTime.now());
        alertService.checkAndTriggerAlerts(frequency);
    }
}
