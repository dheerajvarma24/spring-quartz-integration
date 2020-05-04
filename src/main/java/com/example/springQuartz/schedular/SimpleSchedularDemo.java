package com.example.springQuartz.schedular;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.example.springQuartz.configuration.AutoWiringSpringBeanJobFactory;
import com.example.springQuartz.jobs.DrinkWaterJob;

@Configuration
public class SimpleSchedularDemo {	
	
	Scheduler scheduler;

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}
	
	//Here we combine job details with triggers.
	@Bean
	public Scheduler scheduler() throws SchedulerException {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.setJobFactory(springBeanJobFactory());
			scheduler.start();
			scheduler.scheduleJob(getDrinkWaterJobDetails(), gettriggerofOneMinute());				
		} catch(Exception e) {
			//yet to implement the exception handler
		}
		return scheduler;
	}

	private JobDetail getDrinkWaterJobDetails() {
		return newJob().ofType(DrinkWaterJob.class)
				.withIdentity("DrinkWater-Job")
				.build();
	}
	
	private Trigger gettriggerofOneMinute() {
		return newTrigger().withIdentity("minute-trigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever())
				.build();
	}
}
