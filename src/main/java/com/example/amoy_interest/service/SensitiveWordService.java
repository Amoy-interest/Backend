package com.example.amoy_interest.service;

import com.example.amoy_interest.entity.SensitiveWord;

import java.util.List;

public interface SensitiveWordService {
    List<SensitiveWord> getAllSensitiveWords();
    SensitiveWord findSensitiveWordByKeyword(String keyword);
    SensitiveWord addSensitiveWord(SensitiveWord sensitiveWord);
    SensitiveWord updateSensitiveWord(SensitiveWord sensitiveWord);
    void deleteByKeyword(String keyword);
}
