package com.awin.recruitment.model;

import com.awin.recruitment.library.JsonParser;
import com.awin.recruitment.library.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ArrayBlockingQueue;

public class ProducerImpl implements Producer<Transaction>, Runnable {
    private KafkaProducer<Long, String> kafkaProducer;
    private JsonParser jsonParser;
    private Iterable<Transaction> queue;

    public ProducerImpl(KafkaProducer<Long, String> kafkaProducer, JsonParser jsonParser) {
        this.kafkaProducer = kafkaProducer;
        this.jsonParser = jsonParser;
    }

    @Override
    public void run() {
        while (true) {
            Transaction transaction = ((ArrayBlockingQueue<Transaction>) queue).poll();
            if (transaction != null) {
                String transactionJson = jsonParser.toJson(transaction);
                final ProducerRecord<Long, String> record =
                        new ProducerRecord<>("my-example-topic2", System.currentTimeMillis(), transactionJson);
                kafkaProducer.send(record);
            }
        }
    }

    @Override
    public void produce(Iterable<Transaction> queue) {
        this.queue = queue;

        Thread thread = new Thread(this);
        thread.start();
    }
}
