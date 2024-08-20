package nl.hu.inno.humc.courseplanning.repository;

import nl.hu.inno.humc.courseplanning.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    User findByName(String name);
    Optional<User> findById(UUID id);
}
