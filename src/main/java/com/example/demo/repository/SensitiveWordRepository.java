package com.example.demo.repository;

import com.example.demo.entity.SensitiveWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveWordRepository extends JpaRepository<SensitiveWord,Integer> {
}
