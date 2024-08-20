package nl.hu.inno.humc.monoliet.courseplanning.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.monoliet.courseplanning.domain.Email;
import nl.hu.inno.humc.monoliet.courseplanning.domain.User;
import nl.hu.inno.humc.monoliet.courseplanning.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public void createUser(String username, Email email) {
        User user = new User(username, email);
        userRepository.save(user);
    }
}
