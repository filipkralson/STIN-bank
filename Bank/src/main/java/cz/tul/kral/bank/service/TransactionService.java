package cz.tul.kral.bank.service;

import cz.tul.kral.bank.model.Transaction;
import cz.tul.kral.bank.repo.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createUser(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(int transactionId) {
        return transactionRepository.findById(transactionId);
    }
}
