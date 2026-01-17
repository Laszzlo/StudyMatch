package de.htw_berlin.studymatch.controller;

import de.htw_berlin.studymatch.backend.controller.LanguageController;
import de.htw_berlin.studymatch.backend.controller.dto.LanguageResponse;
import de.htw_berlin.studymatch.backend.security.JwtAuthenticationFilter;
import de.htw_berlin.studymatch.backend.security.JwtService;
import de.htw_berlin.studymatch.backend.service.ImplUserDetailsService;
import de.htw_berlin.studymatch.backend.service.LanguageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LanguageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LanguageService languageService;
    @MockitoBean
    private JwtService jwtService;  // ← Hinzufügen!

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;  // ← Hinzufügen!

    @MockitoBean
    private ImplUserDetailsService userDetailsService;  // ← Hinzufügen!

    @Test
    @DisplayName("GET /api/languages - Should return all languages")
    void testGetAllLanguages() throws Exception {

        List<LanguageResponse> languages = List.of(
                new LanguageResponse(1, "Deutsch"),
                new LanguageResponse(2, "Englisch")
        );
        when(languageService.getAllLanguages()).thenReturn(languages);

        mockMvc.perform(get("/api/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Deutsch"));
    }
}
