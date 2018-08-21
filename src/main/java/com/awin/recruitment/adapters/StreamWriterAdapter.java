package com.awin.recruitment.adapters;

import com.awin.recruitment.library.StreamWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class StreamWriterAdapter implements StreamWriter<Long, String> {
    private KafkaProducer<Long, String> kafkaProducer;
    private String topic;

    public StreamWriterAdapter(KafkaProducer<Long, String> kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }

    public void send(Long key, String value) {
        final ProducerRecord<Long, String> record = new ProducerRecord<>(topic, key, value);
        kafkaProducer.send(record);
    }
}
