package de.htw_berlin.studymatch.controller;

import de.htw_berlin.studymatch.backend.controller.SubjectController;
import de.htw_berlin.studymatch.backend.controller.dto.SubjectResponse;
import de.htw_berlin.studymatch.backend.security.JwtAuthenticationFilter;
import de.htw_berlin.studymatch.backend.security.JwtService;
import de.htw_berlin.studymatch.backend.service.ImplUserDetailsService;
import de.htw_berlin.studymatch.backend.service.SubjectService;
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

@WebMvcTest(SubjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubjectService subjectService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private ImplUserDetailsService userDetailsService;

    @Test
    @DisplayName("GET /api/subjects - Should return all subjects")
    void testGetAllSubjects() throws Exception {

        List<SubjectResponse> subjects = List.of(
                new SubjectResponse(1, "Programmierung"),
                new SubjectResponse(2, "Datenbanken")
        );
        when(subjectService.getAllSubjects()).thenReturn(subjects);

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Programmierung"));
    }
}
