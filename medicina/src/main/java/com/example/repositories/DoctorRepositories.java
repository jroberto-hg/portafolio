package com.example.repositories;

import com.example.models.DoctoresModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepositories extends JpaRepository<DoctoresModels, Long> {
}
