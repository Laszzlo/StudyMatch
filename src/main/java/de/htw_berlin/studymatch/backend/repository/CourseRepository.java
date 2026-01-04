package de.htw_berlin.studymatch.backend.repository;

import de.htw_berlin.studymatch.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
