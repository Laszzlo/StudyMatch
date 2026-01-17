package de.htw_berlin.studymatch.service;

import de.htw_berlin.studymatch.backend.controller.dto.LanguageResponse;
import de.htw_berlin.studymatch.backend.model.Language;
import de.htw_berlin.studymatch.backend.repository.LanguageRepository;
import de.htw_berlin.studymatch.backend.service.LanguageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class LanguageServiceTest {

    @Autowired
    private LanguageService languageService;

    @MockitoBean
    private LanguageRepository languageRepository;

    @Test
    @DisplayName("Should return all languages.")
    void testGetAllLanguages() {
        Language l1 = new Language(1, "Deutsch");
        Language l2 = new Language(2, "Englisch");
        Language l3 = new Language(3, "Spanisch");
        doReturn(List.of(l1, l2, l3)).when(languageRepository).findAll();

        List<LanguageResponse> languages = languageService.getAllLanguages();

        Assertions.assertEquals(3, languages.size());
        Assertions.assertEquals("Deutsch", languages.get(0).name());
        Assertions.assertEquals("Englisch", languages.get(1).name());
    }
}
