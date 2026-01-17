package de.htw_berlin.studymatch.controller;

import de.htw_berlin.studymatch.backend.controller.CourseController;
import de.htw_berlin.studymatch.backend.controller.dto.CourseResponse;
import de.htw_berlin.studymatch.backend.security.JwtAuthenticationFilter;
import de.htw_berlin.studymatch.backend.security.JwtService;
import de.htw_berlin.studymatch.backend.service.CourseService;
import de.htw_berlin.studymatch.backend.service.ImplUserDetailsService;
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

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private ImplUserDetailsService userDetailsService;

    @Test
    @DisplayName("GET /api/courses - Should return all courses")
    void testGetAllCourses() throws Exception {
        // Arrange
        List<CourseResponse> courses = List.of(
                new CourseResponse(1, "Informatik"),
                new CourseResponse(2, "Mathematik")
        );
        when(courseService.getAllCourses()).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Informatik"))
                .andExpect(jsonPath("$[1].name").value("Mathematik"));
    }
}
