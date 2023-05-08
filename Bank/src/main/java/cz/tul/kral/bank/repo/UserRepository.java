package cz.tul.kral.bank.repo;

import cz.tul.kral.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
}
