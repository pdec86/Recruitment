package com.awin.recruitment.services;

import com.awin.recruitment.library.Consumer;
import com.awin.recruitment.model.KafkaConsumerBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

public class TransactionStreamReader extends Thread {
    private KafkaConsumer<Long, String> kafkaConsumer;
    private Consumer<ConsumerRecord<Long, String>> consumer;

    public TransactionStreamReader(KafkaConsumerBuilder<Long, String> kafkaConsumerBuilder, Consumer consumer) {
        super("TransactionStreamReader");
        this.kafkaConsumer = kafkaConsumerBuilder.build();
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Long, String> records = this.kafkaConsumer.poll(Duration.ofSeconds(10));
            if (records.count() > 0) {
                this.consumer.consume(records);
            }
        }
    }
}
