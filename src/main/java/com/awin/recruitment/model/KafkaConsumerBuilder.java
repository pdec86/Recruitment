package com.awin.recruitment.model;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;

public class KafkaConsumerBuilder<K, V> {
    private KafkaConsumer<K, V> kafkaConsumer;
    private String topic;

    public KafkaConsumerBuilder(KafkaConsumer<K, V> kafkaConsumer, String topic) {
        this.kafkaConsumer = kafkaConsumer;
        this.topic = topic;
    }

    public KafkaConsumer<K, V> build() {
        kafkaConsumer.subscribe(Collections.singletonList(this.topic));
        return kafkaConsumer;
    }
}
