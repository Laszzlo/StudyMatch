package de.htw_berlin.studymatch.backend.repository;

import de.htw_berlin.studymatch.backend.model.Match;
import de.htw_berlin.studymatch.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.userA.id = :userId OR m.userB.id = :userId")
    List<Match> findAllByUserId(@Param("userId") Long userId);
    @Query("SELECT CASE WHEN m.userA.id = :userId THEN m.userB.id ELSE m.userA.id END " +
            "FROM Match m WHERE m.userA.id = :userId OR m.userB.id = :userId")
    List<Long> findAllMatchedUserIdsByUserId(@Param("userId") Long userId);
}
