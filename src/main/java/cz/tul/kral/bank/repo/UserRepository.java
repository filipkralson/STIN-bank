package cz.tul.kral.bank.repo;

import cz.tul.kral.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    @Query("SELECT MAX(u.id) FROM Users u")
    Integer findMaxId();
}
