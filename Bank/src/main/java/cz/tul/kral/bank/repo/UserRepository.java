package cz.tul.kral.bank.repo;

import cz.tul.kral.bank.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(int id);
}
