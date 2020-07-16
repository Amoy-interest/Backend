package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.SensitiveWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveWordRepository extends JpaRepository<SensitiveWord,Integer> {
}
