package de.htw_berlin.studymatch.backend.repository;

import de.htw_berlin.studymatch.backend.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
}
