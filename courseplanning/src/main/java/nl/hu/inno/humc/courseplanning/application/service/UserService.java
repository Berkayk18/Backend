package nl.hu.inno.humc.courseplanning.application.service;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.courseplanning.domain.Email;
import nl.hu.inno.humc.courseplanning.domain.User;
import nl.hu.inno.humc.courseplanning.exceptions.ResourceNotFoundException;
import nl.hu.inno.humc.courseplanning.presentation.dto.UserInfoDTO;
import nl.hu.inno.humc.courseplanning.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username, Email email) {
        User user = new User(username, email);
        userRepository.save(user);
    }

    public void addUser(UUID id, String username, Email email) {
        User user = new User(id, username, email);
        userRepository.save(user);
    }

    public UserInfoDTO getUserInformation(UUID userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found for this id: " + userId));
        return toUserInfoDTO(user);
    }

    private UserInfoDTO toUserInfoDTO(User user) {
        return new UserInfoDTO(user.getName(), user.getEmail().toString());
    }
}
