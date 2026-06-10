package com.label2775.resume.repository;

import com.label2775.resume.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findAllByOrderByOrderIndexAscIdAsc();
}

