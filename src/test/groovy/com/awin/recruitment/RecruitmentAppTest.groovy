package com.awin.recruitment

import com.awin.recruitment.adapters.GsonParserAdapter
import com.awin.recruitment.library.Consumer
import com.awin.recruitment.library.JsonParser
import com.awin.recruitment.library.Producer
import com.awin.recruitment.library.StreamWriter
import com.awin.recruitment.model.ConsumerImpl
import com.awin.recruitment.model.ProducerImpl
import com.awin.recruitment.model.Product
import com.awin.recruitment.model.TransactionFromStream
import org.apache.kafka.clients.consumer.ConsumerRecord
import spock.lang.Specification

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

class RecruitmentAppTest extends Specification {

    def "Consumer consumes messages and puts created transactions to queue"() {

        setup:
        BlockingQueue<TransactionFromStream> queue = new ArrayBlockingQueue<>(10)
        JsonParser jsonParser = new GsonParserAdapter()
        Consumer<ConsumerRecord<Long, String>> consumer = new ConsumerImpl(jsonParser, queue)

        String id = "19323412"
        String saleDate = "2018-08-21 17:00:00"
        List<Product> productList = new ArrayList<>()
        productList.add(new Product("Test name", new BigDecimal("320.21")))
        TransactionFromStream transaction = new TransactionFromStream(id, saleDate, productList)

        ConsumerRecord<Long, String> record = new ConsumerRecord("topic", 1, 0, 1313231L, jsonParser.toJson(transaction))
        List<ConsumerRecord<Long, String>> messages = new ArrayList<>()
        messages.add(record)

        when:
        consumer.consume(messages)

        then:
        queue.size() == 1

        expect:
        queue.poll() == transaction
    }

    def "Producer consumes transactions from queue and produces new, enriched transaction"() {

        setup:
        StreamWriter streamWriterAdapter = Mock(StreamWriter.class)
        JsonParser jsonParser = new GsonParserAdapter()
        Producer<TransactionFromStream> producer = new ProducerImpl(streamWriterAdapter, jsonParser)

        String id = "19323412"
        String saleDate = "2018-08-21 17:00:00"
        List<Product> productList = new ArrayList<>()
        productList.add(new Product("Test name", new BigDecimal("320.21")))
        TransactionFromStream transaction = new TransactionFromStream(id, saleDate, productList)
        List<TransactionFromStream> messages = new ArrayList<>()
        messages.add(transaction)

        when:
        producer.produce(messages)

        then:
        1 * streamWriterAdapter.send(19323412, '{"ID":"19323412","saleDate":"2018-08-21 17:00:00","productList":[{"name":"Test name","amount":320.21}],"amountSum":320.21}')
    }
}
