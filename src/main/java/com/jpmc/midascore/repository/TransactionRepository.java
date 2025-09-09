package com.jpmc.midascore.repository;

import com.jpmc.midascore.foundation.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionRecord, Long> {
    // Custom query methods can be added later if needed
}
