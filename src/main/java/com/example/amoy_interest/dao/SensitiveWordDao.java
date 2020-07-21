package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.SensitiveWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SensitiveWordDao {
    List<SensitiveWord> findAllSensitiveWords();
    SensitiveWord findSensitiveWordByKeyword(String keyword);
    SensitiveWord saveSensitiveWord(SensitiveWord sensitiveWord);
    void deleteByKeyword(String keyword);
    Page<SensitiveWord> findList(Pageable pageable);
}
