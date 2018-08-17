package com.awin.recruitment.model;

import com.awin.recruitment.library.Consumer;
import com.awin.recruitment.library.JsonParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;

public class ConsumerImpl implements Consumer<Transaction>, Runnable {
    private KafkaConsumer<Long, String> kafkaConsumer;
    private JsonParser jsonParser;
    private Iterable<Transaction> queue;

    public ConsumerImpl(KafkaConsumerBuilder<Long, String> kafkaConsumerBuilder, JsonParser jsonParser) {
        this.kafkaConsumer = kafkaConsumerBuilder.build();
        this.jsonParser = jsonParser;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Long, String> records = this.kafkaConsumer.poll(Duration.ofSeconds(10));
            if (records.count() > 0) {
                for (ConsumerRecord<Long, String> record : records) {
                    Transaction transaction = jsonParser.fromJson(record.value(), Transaction.class);
                    if (transaction != null) {
                        try {
                            if (queue instanceof BlockingQueue<?>) {
                                ((BlockingQueue<Transaction>) queue).put(transaction);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void consume(Iterable<Transaction> queue) {
        this.queue = queue;

        Thread thread = new Thread(this);
        thread.start();
    }
}
