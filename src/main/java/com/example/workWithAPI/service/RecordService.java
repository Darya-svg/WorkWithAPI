package com.example.workWithAPI.service;

import com.example.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.yaml")
@Slf4j
public class RecordService {

  private RestTemplate restTemplate;
  private KafkaTemplate<String, Record> kafkaTemplate;


  @Value("${api.url}")
  private String API_URL;
  @Value("${topic.name}")
  private String TOPIC;

  @Autowired
  public RecordService(
      KafkaTemplate<String, Record> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
    this.restTemplate = new RestTemplate();
  }

  private Record getRecordFromApi() {
    return this.restTemplate.getForObject(API_URL, Record.class);
  }

  public void printRecord(Record record) {
    log.info(
        "Received record - number=[{}] fact=[{}]", record.getNumber(),
        record.getText());
  }

  public void sendRecord(Record record) {
    kafkaTemplate.send(TOPIC, record.getNumber(), record);
  }

  public void processRecord() {
    Record record = getRecordFromApi();
    printRecord(record);
    sendRecord(record);
  }
}

