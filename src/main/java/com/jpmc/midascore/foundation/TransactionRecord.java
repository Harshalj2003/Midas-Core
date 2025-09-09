package com.jpmc.midascore.foundation;

import com.jpmc.midascore.entity.UserRecord;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private Double incentiveAmount;  // ✅ new field

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserRecord recipient;

    public TransactionRecord() {}

    // Constructor with incentive
    public TransactionRecord(Double amount, UserRecord sender, UserRecord recipient, Double incentiveAmount) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
        this.incentiveAmount = incentiveAmount;
        this.timestamp = LocalDateTime.now();
    }

    // Existing constructor kept for backward-compatibility
    public TransactionRecord(Double amount, UserRecord sender, UserRecord recipient) {
        this(amount, sender, recipient, 0.0);
    }

    // --- getters & setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getIncentiveAmount() {
        return incentiveAmount;
    }

    public void setIncentiveAmount(Double incentiveAmount) {
        this.incentiveAmount = incentiveAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "id=" + id +
                ", amount=" + amount +
                ", incentiveAmount=" + incentiveAmount +
                ", timestamp=" + timestamp +
                ", senderId=" + (sender != null ? sender.getId() : "null") +
                ", recipientId=" + (recipient != null ? recipient.getId() : "null") +
                '}';
    }
}
