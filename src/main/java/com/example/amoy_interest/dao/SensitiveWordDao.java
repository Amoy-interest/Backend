package com.example.amoy_interest.dao;

import com.example.amoy_interest.entity.SensitiveWord;

import java.util.List;

public interface SensitiveWordDao {
    List<SensitiveWord> findAllSensitiveWords();
    SensitiveWord findSensitiveWordByKeyword(String keyword);
    SensitiveWord saveSensitiveWord(SensitiveWord sensitiveWord);
    void deleteByKeyword(String keyword);
}
