package de.htw_berlin.studymatch.backend.repository;

import de.htw_berlin.studymatch.backend.model.Profile;
import de.htw_berlin.studymatch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser_Id(Long userId);
    Optional<Profile> findByUser_Username(String userUsername);
    Optional<Profile> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.id != :currentUserId ORDER BY RANDOM()")
    List<Profile> findAllByIdNotOrderByRandom(@Param("id") Long id);

    List<Profile> findAllByProfileCompleteTrue();
}
