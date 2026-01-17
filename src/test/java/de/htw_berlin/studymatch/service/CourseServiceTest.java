package de.htw_berlin.studymatch.service;

import de.htw_berlin.studymatch.backend.controller.dto.CourseResponse;
import de.htw_berlin.studymatch.backend.model.Course;
import de.htw_berlin.studymatch.backend.repository.CourseRepository;
import de.htw_berlin.studymatch.backend.service.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockitoBean
    private CourseRepository courseRepository;


    @Test
    @DisplayName("Should return all courses.")
    void testGetAllCourses(){
        Course c1 = new Course(1, "Informatik");
        Course c2 = new Course(2, "Mathematik");
        doReturn(List.of(c1, c2)).when(courseRepository).findAll();

        List<CourseResponse> courses = courseService.getAllCourses();

        Assertions.assertSame("Informatik", courses.getFirst().name());
        Assertions.assertEquals(2, courses.size());
    }
}
