package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.SensitiveWordDao;
import com.example.amoy_interest.entity.SensitiveWord;
import com.example.amoy_interest.service.SensitiveWordService;
import com.example.amoy_interest.utils.sensitivefilter2.Finder;
import com.example.amoy_interest.utils.sensitivefilter2.FinderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Finder.addSensitiveWords(sensitiveWord.getKeyword());//可以用消息队列完成
        return sensitiveWordDao.saveSensitiveWord(sensitiveWord);
    }

    @Override
    @Transactional(readOnly = false)
    public SensitiveWord updateSensitiveWord(String oldWord,String newWord) {
        sensitiveWordDao.deleteByKeyword(oldWord);
        SensitiveWord sensitiveWord = new SensitiveWord(newWord);
        Finder.removeSensitiveWords(oldWord);
        Finder.addSensitiveWords(newWord);
        return sensitiveWordDao.saveSensitiveWord(sensitiveWord);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByKeyword(String keyword) {
        Finder.removeSensitiveWords(keyword);
        sensitiveWordDao.deleteByKeyword(keyword);
    }

    @Override
    public SensitiveWord findSensitiveWordByKeyword(String keyword) {
        return sensitiveWordDao.findSensitiveWordByKeyword(keyword);
    }
    @Override
    public Page<SensitiveWord> findSensitiveWordsPageByKeyword(String keyword,Integer pageNum,Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return sensitiveWordDao.findPageByKeyword(keyword,pageable);
    }

    @Override
    public Page<SensitiveWord> getSensitiveWordsPage(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return sensitiveWordDao.findPage(pageable);
    }
}
