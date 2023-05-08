package cz.tul.kral.bank.repo;

import cz.tul.kral.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(int id);
}
