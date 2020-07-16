package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.SensitiveWordDao;
import com.example.amoy_interest.entity.SensitiveWord;
import com.example.amoy_interest.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensitiveWordServiceImpl implements SensitiveWordService {

    @Autowired
    private SensitiveWordDao sensitiveWordDao;

    @Override
    public List<SensitiveWord> getAllSensitiveWords() {
        return sensitiveWordDao.findAllSensitiveWords();
    }

    @Override
    public SensitiveWord addSensitiveWord(SensitiveWord sensitiveWord) {
        return sensitiveWordDao.saveSensitiveWord(sensitiveWord);
    }

    @Override
    public SensitiveWord updateSensitiveWord(SensitiveWord sensitiveWord) {
        return sensitiveWordDao.saveSensitiveWord(sensitiveWord);
    }

    @Override
    public void deleteByKeyword(String keyword) {
        sensitiveWordDao.deleteByKeyword(keyword);
    }

    @Override
    public SensitiveWord findSensitiveWordByKeyword(String keyword) {
        return sensitiveWordDao.findSensitiveWordByKeyword(keyword);
    }
}
