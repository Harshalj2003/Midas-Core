package com.jpmc.midascore;

import com.jpmc.midascore.kafka.KafkaConfig;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        partitions = 1,
        topics = {"transactions"},
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        }
)
@Import(KafkaConfig.class)
public class TaskTwoTests {
    static final Logger logger = LoggerFactory.getLogger(TaskTwoTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;


    @Test
    void task_two_verifier() throws InterruptedException {
        logger.info("Starting task two verifier...");
        logger.info("KafkaTemplate in test: {}", kafkaTemplate);
        logger.info("KafkaProducer: {}", kafkaProducer);

        String[] transactionLines = fileLoader.loadStrings("/test_data/poiuytrewq.uiop");
        logger.info("Loaded {} transaction lines", transactionLines != null ? transactionLines.length : 0);

        // Wait for Kafka to be ready
        Thread.sleep(3000);

        if (transactionLines != null) {
            for (int i = 0; i < transactionLines.length; i++) {
                String transactionLine = transactionLines[i];
                logger.info("Sending transaction {}: {}", i + 1, transactionLine);
                kafkaProducer.send(transactionLine);
                Thread.sleep(200); // Delay between messages
            }
        }


        Thread.sleep(3000);
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to watch for incoming transactions");
        logger.info("kill this test once you find the answer");
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}


//old logic for more confusion and endless loop that tells to modify method repeatedly



//    @Test
//    void task_two_verifier() throws InterruptedException {
//        String[] transactionLines = fileLoader.loadStrings("/test_data/poiuytrewq.uiop");
//        for (String transactionLine : transactionLines) {
//            kafkaProducer.send(transactionLine);
//        }
//        Thread.sleep(2000);
//        logger.info("----------------------------------------------------------");
//        logger.info("----------------------------------------------------------");
//        logger.info("----------------------------------------------------------");
//        logger.info("use your debugger to watch for incoming transactions");
//        logger.info("kill this test once you find the answer");
//        while (true) {
//            Thread.sleep(20000);
//            logger.info("...");
//        }
//    }


