package de.htw_berlin.studymatch.backend.controller;


import de.htw_berlin.studymatch.backend.controller.dto.LanguageResponse;
import de.htw_berlin.studymatch.backend.service.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/languages")
@AllArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping()
    public ResponseEntity<List<LanguageResponse>> getAllLanguages(){
        return ResponseEntity.ok(languageService.getAllLanguages());
    }
}
