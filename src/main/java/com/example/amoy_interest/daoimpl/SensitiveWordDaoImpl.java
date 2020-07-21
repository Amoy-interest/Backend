package com.example.amoy_interest.daoimpl;

import com.example.amoy_interest.dao.SensitiveWordDao;
import com.example.amoy_interest.entity.SensitiveWord;
import com.example.amoy_interest.repository.SensitiveWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensitiveWordDaoImpl implements SensitiveWordDao {
    @Autowired
    private SensitiveWordRepository sensitiveWordRepository;

    @Override
    public List<SensitiveWord> findAllSensitiveWords() {
        return sensitiveWordRepository.findAllSensitiveWords();
    }

    @Override
    public SensitiveWord findSensitiveWordByKeyword(String keyword) {
        return sensitiveWordRepository.findSensitiveWordByKeyword(keyword);
    }

    @Override
    public SensitiveWord saveSensitiveWord(SensitiveWord sensitiveWord) {
        return sensitiveWordRepository.saveAndFlush(sensitiveWord);
    }

    @Override
    public void deleteByKeyword(String keyword) {
        sensitiveWordRepository.deleteByKeyword(keyword);
    }
}

