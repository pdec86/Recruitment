package com.awin.recruitment;

import com.awin.recruitment.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import com.awin.recruitment.services.TransactionProducer;
import com.awin.recruitment.services.TransactionStreamReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class RecruitmentApp {

    private RecruitmentApp() {
    }

    public static void main(
            String[] args
    ) {

        ClassPathXmlApplicationContext applicationContext = ClassPathXmlApplicationContextFactory.create();

        System.out.println("Recruitment app is running");

        TransactionStreamReader transactionStreamReader
                = applicationContext.getBean("transactionStreamReader", TransactionStreamReader.class);
        transactionStreamReader.start();

        TransactionProducer transactionProducer
                = applicationContext.getBean("transactionProducer", TransactionProducer.class);
        transactionProducer.start();
    }
}