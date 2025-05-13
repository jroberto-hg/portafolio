package com.example.medicina.repositories;

import com.example.medicina.models.ConsultoriosModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultoriosRepositories extends JpaRepository<ConsultoriosModels, Long> {
}
