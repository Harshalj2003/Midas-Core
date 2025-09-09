package com.jpmc.midascore.kafka;

import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic}") String topic,
                         KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        logger.info("KafkaProducer initialized with topic: {} and kafkaTemplate: {}", topic, kafkaTemplate);
    }

    public void send(String transactionLine) {
        try {
            String[] transactionData = transactionLine.split(", ");
            Transaction transaction = new Transaction(
                    Long.parseLong(transactionData[0]),
                    Long.parseLong(transactionData[1]),
                    Float.parseFloat(transactionData[2])
            );

            logger.info("Sending transaction to topic '{}': {}", topic, transaction);
            kafkaTemplate.send(topic, transaction);
            logger.info("Transaction sent successfully");

            System.out.println("✅ "+ transaction);
        } catch (Exception e) {
            logger.error("Error sending transaction: {}", e.getMessage(), e);
        }
    }
}


//package com.jpmc.midascore;
//
//import com.jpmc.midascore.foundation.Transaction;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaProducer {
//    private final String topic;
//    private final KafkaTemplate<String, Transaction> kafkaTemplate; //field watchpoint, and solution to debug
//
//    public KafkaProducer(@Value("${general.kafka-topic}") String topic, KafkaTemplate<String, Transaction> kafkaTemplate) {
//        this.topic = topic;
//        this.kafkaTemplate = kafkaTemplate; //field watchpoint, and solution to debug
//    }
//
//
//
//
////    public void send(String transactionLine) {
////        String[] transactionData = transactionLine.split(", ");
////        Transaction transaction = new Transaction(
////                Long.parseLong(transactionData[0]),
////                Long.parseLong(transactionData[1]),
////                Float.parseFloat(transactionData[2])
////        );
////        kafkaTemplate.send(topic, transaction);
////    }
//
//    //confusing causing issue
//
//    public void send(String transaction) {
//        String[] transactionData = transaction.split(", ");
//        kafkaTemplate.send(topic, new Transaction(Long.parseLong(transactionData[0]), Long.parseLong(transactionData[1]), Float.parseFloat(transactionData[2])));
//    }
//}