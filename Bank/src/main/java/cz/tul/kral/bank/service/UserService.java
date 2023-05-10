package cz.tul.kral.bank.service;

import cz.tul.kral.bank.model.User;
import cz.tul.kral.bank.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId);
    }
}
