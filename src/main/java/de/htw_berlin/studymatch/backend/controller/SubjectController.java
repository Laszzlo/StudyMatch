package de.htw_berlin.studymatch.backend.controller;

import de.htw_berlin.studymatch.backend.controller.dto.SubjectResponse;
import de.htw_berlin.studymatch.backend.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects(){
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
}
