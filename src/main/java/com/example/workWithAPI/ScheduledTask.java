package com.example.workWithAPI;

import com.example.workWithAPI.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTask {

  private final RecordService recordService;

  @Autowired
  public ScheduledTask(RecordService recordService) {
    this.recordService = recordService;
  }

  @Scheduled(fixedDelay = 5000)
  public void executeTask() {
    recordService.processRecord();
  }
}
