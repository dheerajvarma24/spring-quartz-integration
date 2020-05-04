package com.example.springQuartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springQuartz.Message.MessageService;

public class DrinkWaterJob implements Job {
	
	private static Logger log = LoggerFactory.getLogger(DrinkWaterJob.class);
	
	@Autowired
	private MessageService messageService;

	public void execute(JobExecutionContext context) throws JobExecutionException {		
		try {
			String message = messageService.getMessage();
			log.info(message);			
		} catch(Exception e) {
			log.error("Exception in DrinkWaterJob -> execute -> " + e.getMessage());
		}		
	}
}
