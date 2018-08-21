package com.awin.recruitment.model;

import com.awin.recruitment.library.Consumer;
import com.awin.recruitment.library.JsonParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.BlockingQueue;

public class ConsumerImpl implements Consumer<ConsumerRecord<Long, String>> {
    private JsonParser jsonParser;
    private BlockingQueue<TransactionFromStream> queue;

    public ConsumerImpl(JsonParser jsonParser, BlockingQueue<TransactionFromStream> queue) {
        this.jsonParser = jsonParser;
        this.queue = queue;
    }

    @Override
    public void consume(Iterable<ConsumerRecord<Long, String>> messages) {
        for (ConsumerRecord<Long, String> record : messages) {
            TransactionFromStream transaction = jsonParser.fromJson(record.value(), TransactionFromStream.class);
            if (transaction != null) {
                try {
                    if (queue != null) {
                        queue.put(transaction);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
