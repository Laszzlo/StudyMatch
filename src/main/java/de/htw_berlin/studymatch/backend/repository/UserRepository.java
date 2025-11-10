package de.htw_berlin.studymatch.backend.repository;
import de.htw_berlin.studymatch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(Long uid);
    List<User> findAll();
}
