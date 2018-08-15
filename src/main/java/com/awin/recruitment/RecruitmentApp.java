package com.awin.recruitment;

import com.awin.recruitment.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import com.awin.recruitment.library.JsonParser;
import com.awin.recruitment.model.ConsumerImpl;
import com.awin.recruitment.model.KafkaConsumerBuilder;
import com.awin.recruitment.model.ProducerImpl;
import com.awin.recruitment.model.Transaction;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ArrayBlockingQueue;

public final class RecruitmentApp {

    private static ArrayBlockingQueue<Transaction> data = new ArrayBlockingQueue<>(10);

    private RecruitmentApp() {
    }

    public static void main(
            String[] args
    ) {

        ClassPathXmlApplicationContext applicationContext = ClassPathXmlApplicationContextFactory.create();
        KafkaConsumerBuilder<Long, String> kafkaConsumerBuilder = applicationContext.getBean("kafkaConsumerBuilder", KafkaConsumerBuilder.class);

        System.out.println("Recruitment app is running");

        try (KafkaConsumer<Long, String> kafkaConsumer = kafkaConsumerBuilder.build()) {
            JsonParser jsonParser = applicationContext.getBean("gsonParser", JsonParser.class);
            ConsumerImpl consumer = new ConsumerImpl(kafkaConsumer, data, jsonParser);
            consumer.start();

            ProducerImpl producer = new ProducerImpl(applicationContext.getBean("kafkaProducer", KafkaProducer.class), data, jsonParser);
            producer.start();

            consumer.join();
            producer.join();
        } catch (Exception ignored) {
        }
    }
}