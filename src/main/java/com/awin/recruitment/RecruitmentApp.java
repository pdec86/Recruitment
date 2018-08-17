package com.awin.recruitment;

import com.awin.recruitment.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import com.awin.recruitment.library.Consumer;
import com.awin.recruitment.library.Producer;
import com.awin.recruitment.model.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.BlockingQueue;

public final class RecruitmentApp {

    private RecruitmentApp() {
    }

    public static void main(
            String[] args
    ) {

        ClassPathXmlApplicationContext applicationContext = ClassPathXmlApplicationContextFactory.create();

        System.out.println("Recruitment app is running");

        BlockingQueue<Transaction> queue = applicationContext.getBean("transactionQueue", BlockingQueue.class);

        Consumer consumer = applicationContext.getBean("transactionConsumer", Consumer.class);
        consumer.consume(queue);

        Producer producer = applicationContext.getBean("transactionProducer", Producer.class);
        producer.produce(queue);
    }
}