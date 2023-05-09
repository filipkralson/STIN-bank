package cz.tul.kral.bank.repo;

import cz.tul.kral.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(int id);
}
