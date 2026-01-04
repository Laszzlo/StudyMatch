package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.CourseResponse;
import de.htw_berlin.studymatch.backend.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
