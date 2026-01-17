package de.htw_berlin.studymatch.service;

import de.htw_berlin.studymatch.backend.controller.dto.SubjectResponse;
import de.htw_berlin.studymatch.backend.model.Subject;
import de.htw_berlin.studymatch.backend.repository.SubjectRepository;
import de.htw_berlin.studymatch.backend.service.SubjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class SubjectServiceTest {

    @Autowired
    private SubjectService subjectService;

    @MockitoBean
    private SubjectRepository subjectRepository;

    @Test
    @DisplayName("Should return all subjects.")
    void testGetAllSubjects() {
        Subject s1 = new Subject(1, "Mathematik");
        Subject s2 = new Subject(2, "Programmierung");
        Subject s3 = new Subject(3, "Datenbanken");
        doReturn(List.of(s1, s2, s3)).when(subjectRepository).findAll();

        List<SubjectResponse> subjects = subjectService.getAllSubjects();

        Assertions.assertEquals(3, subjects.size());
        Assertions.assertEquals("Mathematik", subjects.get(0).name());
        Assertions.assertEquals("Programmierung", subjects.get(1).name());
    }
}
