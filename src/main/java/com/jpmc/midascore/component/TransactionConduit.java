package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.TransactionRecord;
import com.jpmc.midascore.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionConduit {
    private final TransactionRepository transactionRepository;

    public TransactionConduit(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(TransactionRecord transactionRecord) {
        transactionRepository.save(transactionRecord);
    }
}
