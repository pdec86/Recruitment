package com.awin.recruitment.services;

import com.awin.recruitment.library.Producer;
import com.awin.recruitment.model.TransactionFromStream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TransactionProducer extends Thread {
    private BlockingQueue<TransactionFromStream> queue;
    private Producer<TransactionFromStream> producer;
    private Integer fromStreamListMaxSize = 10;
    private List<TransactionFromStream> fromStreamList = new ArrayList<>();

    public TransactionProducer(BlockingQueue<TransactionFromStream> queue, Producer<TransactionFromStream> producer) {
        super("TransactionProducer");
        this.queue = queue;
        this.producer = producer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TransactionFromStream transactionFromStream = this.queue.poll(10, TimeUnit.SECONDS);
                if (this.fromStreamList.size() == this.fromStreamListMaxSize) {
                    this.producer.produce(this.fromStreamList);
                    this.fromStreamList.clear();
                }
                this.fromStreamList.add(transactionFromStream);
            } catch (InterruptedException e) {
                this.producer.produce(this.fromStreamList);
                this.fromStreamList.clear();
            }
        }
    }
}
