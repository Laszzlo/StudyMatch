package de.htw_berlin.studymatch.backend.service;


import de.htw_berlin.studymatch.backend.controller.dto.SubjectResponse;
import de.htw_berlin.studymatch.backend.model.Subject;
import de.htw_berlin.studymatch.backend.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    private SubjectResponse subjectResponse(Subject subject){
        return new SubjectResponse(
                subject.getId(),
                subject.getName()
        );
    }

    public List<SubjectResponse> getAllSubjects(){
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(this::subjectResponse).toList();
    }
}
