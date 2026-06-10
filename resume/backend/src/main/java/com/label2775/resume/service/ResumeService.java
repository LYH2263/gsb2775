package com.label2775.resume.service;

import com.label2775.resume.domain.Education;
import com.label2775.resume.domain.Experience;
import com.label2775.resume.domain.Profile;
import com.label2775.resume.domain.Project;
import com.label2775.resume.domain.Skill;
import com.label2775.resume.dto.EducationDto;
import com.label2775.resume.dto.ExperienceDto;
import com.label2775.resume.dto.ProfileDto;
import com.label2775.resume.dto.ProjectDto;
import com.label2775.resume.dto.ResumeResponse;
import com.label2775.resume.dto.SkillDto;
import com.label2775.resume.repository.EducationRepository;
import com.label2775.resume.repository.ExperienceRepository;
import com.label2775.resume.repository.ProfileRepository;
import com.label2775.resume.repository.ProjectRepository;
import com.label2775.resume.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ResumeService {

    private final ProfileRepository profileRepository;
    private final EducationRepository educationRepository;
    private final ExperienceRepository experienceRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public ResumeService(ProfileRepository profileRepository,
                         EducationRepository educationRepository,
                         ExperienceRepository experienceRepository,
                         ProjectRepository projectRepository,
                         SkillRepository skillRepository) {
        this.profileRepository = profileRepository;
        this.educationRepository = educationRepository;
        this.experienceRepository = experienceRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    public ResumeResponse getResume() {
        ResumeResponse response = new ResumeResponse();

        Optional<Profile> profileOpt = profileRepository.findAll().stream().findFirst();
        profileOpt.ifPresent(profile -> response.setProfile(toProfileDto(profile)));

        List<Education> educations = educationRepository.findAllByOrderByOrderIndexAscIdAsc();
        response.setEducations(educations.stream().map(this::toEducationDto).collect(Collectors.toList()));

        List<Experience> experiences = experienceRepository.findAll();
        response.setExperiences(experiences.stream().map(this::toExperienceDto).collect(Collectors.toList()));

        List<Project> projects = projectRepository.findAll();
        response.setProjects(projects.stream().map(this::toProjectDto).collect(Collectors.toList()));

        List<Skill> skills = skillRepository.findAll();
        response.setSkills(skills.stream().map(this::toSkillDto).collect(Collectors.toList()));

        return response;
    }

    private ProfileDto toProfileDto(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setName(profile.getName());
        dto.setTitle(profile.getTitle());
        dto.setLocation(profile.getLocation());
        dto.setEmail(profile.getEmail());
        dto.setPhone(profile.getPhone());
        dto.setGithubUrl(profile.getGithubUrl());
        dto.setWebsiteUrl(profile.getWebsiteUrl());
        dto.setSummary(profile.getSummary());
        return dto;
    }

    private EducationDto toEducationDto(Education education) {
        EducationDto dto = new EducationDto();
        dto.setSchool(education.getSchool());
        dto.setDegree(education.getDegree());
        dto.setMajor(education.getMajor());
        dto.setStartDate(education.getStartDate());
        dto.setEndDate(education.getEndDate());
        dto.setDescription(education.getDescription());
        return dto;
    }

    private ExperienceDto toExperienceDto(Experience experience) {
        ExperienceDto dto = new ExperienceDto();
        dto.setCompany(experience.getCompany());
        dto.setPosition(experience.getPosition());
        dto.setLocation(experience.getLocation());
        dto.setStartDate(experience.getStartDate());
        dto.setEndDate(experience.getEndDate());
        dto.setDescription(experience.getDescription());
        return dto;
    }

    private ProjectDto toProjectDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setName(project.getName());
        dto.setRole(project.getRole());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setDescription(project.getDescription());
        dto.setGithubUrl(project.getGithubUrl());
        dto.setDemoUrl(project.getDemoUrl());
        dto.setTechStack(project.getTechStack());
        return dto;
    }

    private SkillDto toSkillDto(Skill skill) {
        SkillDto dto = new SkillDto();
        dto.setName(skill.getName());
        dto.setCategory(skill.getCategory());
        dto.setLevel(skill.getLevel());
        dto.setDescription(skill.getDescription());
        return dto;
    }
}

