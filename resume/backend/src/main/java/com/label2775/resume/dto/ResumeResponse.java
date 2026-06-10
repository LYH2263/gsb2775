package com.label2775.resume.dto;

import java.util.List;

public class ResumeResponse {

    private ProfileDto profile;
    private List<EducationDto> educations;
    private List<ExperienceDto> experiences;
    private List<ProjectDto> projects;
    private List<SkillDto> skills;

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }

    public List<EducationDto> getEducations() {
        return educations;
    }

    public void setEducations(List<EducationDto> educations) {
        this.educations = educations;
    }

    public List<ExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceDto> experiences) {
        this.experiences = experiences;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }
}

