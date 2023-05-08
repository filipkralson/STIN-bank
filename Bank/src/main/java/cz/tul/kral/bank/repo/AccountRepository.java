package cz.tul.kral.bank.repo;

import cz.tul.kral.bank.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findById(int id);
}
