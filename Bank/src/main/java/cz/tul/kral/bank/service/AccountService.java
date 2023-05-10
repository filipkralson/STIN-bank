package cz.tul.kral.bank.service;

import cz.tul.kral.bank.model.Account;
import cz.tul.kral.bank.repo.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    public Account getAccountById(int accountId) {
        return accountRepository.findById(accountId);
    }
}
