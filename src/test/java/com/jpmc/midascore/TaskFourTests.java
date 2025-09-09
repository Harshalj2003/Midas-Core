//package com.jpmc.midascore;
//
//import com.jpmc.midascore.kafka.KafkaProducer;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.test.annotation.DirtiesContext;
//
//@SpringBootTest
//@DirtiesContext
//@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
//public class TaskFourTests {
//    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);
//
//    @Autowired
//    private KafkaProducer kafkaProducer;
//
//    @Autowired
//    private UserPopulator userPopulator;
//
//    @Autowired
//    private FileLoader fileLoader;
//
//    @Test
//    void task_four_verifier() throws InterruptedException {
//        userPopulator.populate();
//        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
//        for (String transactionLine : transactionLines) {
//            kafkaProducer.send(transactionLine);
//        }
//        Thread.sleep(2000);
//
//
//        logger.info("----------------------------------------------------------");
//        logger.info("----------------------------------------------------------");
//        logger.info("----------------------------------------------------------");
//        logger.info("use your debugger to find out what wilbur's balance is after all transactions are processed");
//        logger.info("kill this test once you find the answer");
//        while (true) {
//            Thread.sleep(20000);
//            logger.info("...");
//        }
//    }
//}


package com.jpmc.midascore;

import com.jpmc.midascore.kafka.KafkaProducer;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.entity.UserRecord;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskFourTests {

    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRepository userRepository;

    @Test
    void task_four_verifier() throws InterruptedException {
        // 1. Load initial users
        userPopulator.populate();

        // 2. Send all test transactions
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        // 3. Allow async processing to complete
        Thread.sleep(2000);

        // 4. Fetch Wilbur’s balance
        userRepository.findAll()
                .stream()
                .filter(u -> "wilbur".equalsIgnoreCase(u.getName()))
                .findFirst()
                .ifPresentOrElse(
                        (UserRecord wilbur) -> {
                            float bal = wilbur.getBalance();
                            int floored = (int) Math.floor(bal);
                            logger.info("----------------------------------------------------------");
                            logger.info("Wilbur's final balance (raw) = {}", bal);
                            logger.info("Wilbur's final balance (rounded down) = {}", floored);
                            logger.info("----------------------------------------------------------");
                        },
                        () -> logger.warn("Wilbur not found in user repository!")
                );
    }
}
