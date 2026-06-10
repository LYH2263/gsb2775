package com.label2775.resume.repository;

import com.label2775.resume.domain.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}

