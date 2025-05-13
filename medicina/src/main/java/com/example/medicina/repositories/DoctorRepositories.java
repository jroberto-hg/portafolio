package com.example.medicina.repositories;

import com.example.medicina.models.DoctoresModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepositories extends JpaRepository<DoctoresModels, Long> {
}
