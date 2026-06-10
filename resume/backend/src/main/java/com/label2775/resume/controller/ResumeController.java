package com.label2775.resume.controller;

import com.label2775.resume.dto.ResumeResponse;
import com.label2775.resume.service.ResumeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/resume")
    public ResumeResponse getResume() {
        return resumeService.getResume();
    }
}

