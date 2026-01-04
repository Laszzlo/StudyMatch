package de.htw_berlin.studymatch.backend.service;


import de.htw_berlin.studymatch.backend.controller.dto.CourseResponse;
import de.htw_berlin.studymatch.backend.model.Course;
import de.htw_berlin.studymatch.backend.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    private CourseResponse courseResponse(Course course){
        return new CourseResponse(
                course.getId(),
                course.getName()
        );
    }

    public List<CourseResponse> getAllCourses(){
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::courseResponse).toList();
    }
}
