package com.example.amoy_interest.repository;

import com.example.amoy_interest.entity.Blog;
import com.example.amoy_interest.entity.SensitiveWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensitiveWordRepository extends JpaRepository<SensitiveWord,Integer> {
    @Query(value = "from SensitiveWord")
    List<SensitiveWord> findAllSensitiveWords();

    @Query(value = "from SensitiveWord where keyword = :keyword")
    SensitiveWord findSensitiveWordByKeyword(String keyword);

    @Query(value = "delete from SensitiveWord where keyword = :keyword")
    void deleteByKeyword(String keyword);

}
