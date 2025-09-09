package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.TransactionRecord;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public TransactionService(UserRepository userRepository,
                              TransactionRepository transactionRepository,
                              RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    private double fetchIncentive(Transaction transaction) {
        String url = "http://localhost:8080/incentive";
        IncentiveResponse response =
                restTemplate.postForObject(url, transaction, IncentiveResponse.class);
        return response != null ? response.getAmount() : 0.0;
    }

    @Transactional
    public void processTransaction(Transaction transaction) {
        // Fetch sender & recipient by ID
        UserRecord sender = userRepository.findById(transaction.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sender not found with ID: " + transaction.getSenderId()));

        UserRecord recipient = userRepository.findById(transaction.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Recipient not found with ID: " + transaction.getRecipientId()));

        double amount = transaction.getAmount();

        // Ensure sender has enough balance
        if (sender.getBalance() < amount) {
            System.out.println("❌ Transaction discarded. Insufficient balance for user ID: " + sender.getId());
            return; // discard transaction
        }

        // ✅ Fetch incentive from external API
        double incentive = fetchIncentive(transaction);

        // Update balances
        sender.setBalance((float) (sender.getBalance() - amount));
        recipient.setBalance((float) (recipient.getBalance() + amount + incentive));

        // Save users
        userRepository.save(sender);
        userRepository.save(recipient);

        // Save transaction record (now with incentive)
        TransactionRecord record = new TransactionRecord(amount, sender, recipient, incentive);
        transactionRepository.save(record);

        // Logging
        if (amount > 10000) {
            System.out.println("🚨 High value transaction detected: " + record);
        } else {
            System.out.println("✅ Transaction processed: " + record);
        }
    }

    // DTO for Incentive API response
    private static class IncentiveResponse {
        private double amount;
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
    }
}
