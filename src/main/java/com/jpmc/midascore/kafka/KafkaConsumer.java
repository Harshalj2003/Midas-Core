package com.jpmc.midascore.kafka;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final TransactionService transactionService;
    private int transactionCount = 0;

    @Autowired
    public KafkaConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(Transaction transaction) {
        transactionCount++;

        logger.info("*** Transaction #{} received: SenderId={}, RecipientId={}, Amount={} ***",
                transactionCount,
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount());

        // Pass to business logic
        transactionService.processTransaction(transaction);
    }
}
