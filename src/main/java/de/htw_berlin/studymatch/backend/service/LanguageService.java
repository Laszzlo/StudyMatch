package de.htw_berlin.studymatch.backend.service;

import de.htw_berlin.studymatch.backend.controller.dto.LanguageResponse;
import de.htw_berlin.studymatch.backend.model.Language;
import de.htw_berlin.studymatch.backend.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    private LanguageResponse languageResponse(Language language){
        return new LanguageResponse(
                language.getId(),
                language.getName()
        );
    }

    public List<LanguageResponse> getAllLanguages(){
        List<Language> languages = languageRepository.findAll();
        return languages.stream().map(this::languageResponse).toList();
    }
}
