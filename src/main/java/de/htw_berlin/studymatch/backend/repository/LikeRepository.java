package de.htw_berlin.studymatch.backend.repository;

import de.htw_berlin.studymatch.backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByToUserId(Long userId);
    List<Like> findAllByFromUserId(Long userId);
    boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
    void deleteLikeByFromUser_IdAndToUser_Id(Long fromUserId, Long toUserId);
    List<Like> findAll();
    @Query("SELECT l.toUser.id FROM Like l WHERE l.fromUser.id = :userId")
    List<Long> findAllToUserIdsByFromUserId(@Param("userId") Long userId);
}
