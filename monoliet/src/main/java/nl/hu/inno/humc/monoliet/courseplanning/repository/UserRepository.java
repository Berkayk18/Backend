package nl.hu.inno.humc.monoliet.courseplanning.repository;

import nl.hu.inno.humc.monoliet.courseplanning.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
