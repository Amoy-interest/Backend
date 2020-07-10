package com.example.demo.repository;

import com.example.demo.entity.BlogCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCountRepository extends JpaRepository<BlogCount,Integer> {
}
