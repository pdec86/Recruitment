package com.awin.recruitment.model;

import com.awin.recruitment.library.JsonParser;
import com.awin.recruitment.library.Producer;
import com.awin.recruitment.library.StreamWriter;

public class ProducerImpl implements Producer<TransactionFromStream> {
    private StreamWriter<Long, String> streamWriter;
    private JsonParser jsonParser;

    public ProducerImpl(StreamWriter<Long, String> streamWriter, JsonParser jsonParser) {
        this.streamWriter = streamWriter;
        this.jsonParser = jsonParser;
    }

    @Override
    public void produce(Iterable<TransactionFromStream> messages) {
        for (TransactionFromStream transactionFromStream : messages) {
            Transaction transaction = new Transaction(
                    transactionFromStream.getID(),
                    transactionFromStream.getSaleDate(),
                    transactionFromStream.getProductList());
            String transactionJson = jsonParser.toJson(transaction);
            streamWriter.send(Long.parseLong(transactionFromStream.getID()), transactionJson);
        }
    }
}
