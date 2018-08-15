package com.awin.recruitment.model;

import com.awin.recruitment.library.Consumer;
import com.awin.recruitment.library.JsonParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerImpl extends Thread implements Consumer<ConsumerRecord<Long, String>> {
    private KafkaConsumer<Long, String> kafkaConsumer;
    private ArrayBlockingQueue<Transaction> data;
    private JsonParser jsonParser;

    public ConsumerImpl(KafkaConsumer<Long, String> kafkaConsumer, ArrayBlockingQueue<Transaction> data, JsonParser jsonParser) {
        super("Consumer");
        this.kafkaConsumer = kafkaConsumer;
        this.data = data;
        this.jsonParser = jsonParser;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Long, String> records = this.kafkaConsumer.poll(Duration.ofSeconds(10));
            if (records.count() > 0) {
                consume(records);
            }
        }
    }

    @Override
    public void consume(Iterable<ConsumerRecord<Long, String>> messages) {
        for (ConsumerRecord<Long, String> message : messages) {
            Transaction transaction = jsonParser.fromJson(message.value(), Transaction.class);
            if (transaction != null) {
                try {
                    this.data.put(transaction);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
