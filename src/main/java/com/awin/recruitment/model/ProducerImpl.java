package com.awin.recruitment.model;

import com.awin.recruitment.library.JsonParser;
import com.awin.recruitment.library.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ArrayBlockingQueue;

public class ProducerImpl extends Thread implements Producer<Transaction> {
    private KafkaProducer<Long, String> kafkaProducer;
    private ArrayBlockingQueue<Transaction> data;
    private JsonParser jsonParser;

    public ProducerImpl(KafkaProducer<Long, String> kafkaProducer, ArrayBlockingQueue<Transaction> data, JsonParser jsonParser) {
        super("Producer");
        this.kafkaProducer = kafkaProducer;
        this.data = data;
        this.jsonParser = jsonParser;
    }

    @Override
    public void run() {
        while (true) {
            produce(data);
        }
    }

    @Override
    public void produce(Iterable<Transaction> messages) {
        while (true) {
            Transaction transaction = ((ArrayBlockingQueue<Transaction>) messages).poll();
            if (transaction != null) {
                String transactionJson = jsonParser.toJson(transaction);
                final ProducerRecord<Long, String> record =
                        new ProducerRecord<>("my-example-topic2", System.currentTimeMillis(), transactionJson);
                kafkaProducer.send(record);
            }
        }
    }
}
