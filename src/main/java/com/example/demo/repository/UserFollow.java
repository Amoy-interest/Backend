package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //此处有遗留问题
public interface UserFollow extends JpaRepository<UserFollow,Integer> {
}
