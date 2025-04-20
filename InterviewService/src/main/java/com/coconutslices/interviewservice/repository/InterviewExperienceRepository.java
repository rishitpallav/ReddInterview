package com.coconutslices.interviewservice.repository;

import com.coconutslices.interviewservice.model.InterviewExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewExperienceRepository extends JpaRepository<InterviewExperience, Long> {
}
