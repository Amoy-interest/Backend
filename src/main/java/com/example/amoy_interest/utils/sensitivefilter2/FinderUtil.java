package com.example.amoy_interest.utils.sensitivefilter2;

import com.example.amoy_interest.utils.common.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;


/**
 * 敏感词过滤对外提供方法类
 *
 * @author hymer
 *
 */
@Component
@Slf4j
public class FinderUtil {

	public static final char ADD_FLAG = '+'; // 新增标记
	public static final char REMOVE_FLAG = '-'; // 删除标记

	private static DBProperties config;
	@Autowired
	private DBProperties config0;
//	static {
//		try {
//			initialize();
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//		}
//	}
	@PostConstruct
	public void init() {
		FinderUtil.config = config0;
		try {
			initialize();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	/**
	 * 初始化敏感词
	 */
	public static void initialize() {
//		PropertiesUtil.readProperties("config.properties");
//		String className = PropertiesUtil.getProperty("jdbcDriverClassName");
//		String url = PropertiesUtil.getProperty("jdbcUrl");
//		String username = PropertiesUtil.getProperty("jdbcUsername");
//		String password = PropertiesUtil.getProperty("jdbcPassword");
		String url = config.getUrl();
		String username = config.getUsername();
		String password = config.getPassword();
		String className = config.getClassName();
		System.out.println(className+url+username+password);
		DBWordProvider p = new DBWordProvider(className, url, username, password);
//		DBWordProvider p = new DBWordProvider();
		String[] words = p.loadWords();
		// 初始导入
		Finder.addSensitiveWords(words);
//		String mqurl = util.getProperty("url");
//		String topic = util.getProperty("topic");
//		// 监听词库变化接收器启动
//		new Thread(new WordLibListener(mqurl, topic)).start();
	}

	/**
	 *
	 * 找出文本中的敏感词
	 *
	 * @author hymer
	 * @param text
	 * @return
	 */
	public static Set<String> find(String text) {
		return Finder.find(text);
	}

	/**
	 * 替换文本中的敏感词
	 *
	 * @param text
	 *            含敏感词的文本
	 * @return
	 */
	public static String replace(String text) {
		return Finder.replace(text);
	}

	/**
	 * 替换文本中的敏感词
	 *
	 * @param text
	 *            含敏感词的文本
	 * @param replacement
	 *            替换字符
	 * @return
	 */
	public static String replace(String text, Character replacement) {
		return Finder.replace(text, replacement);
	}

	/**
	 *
	 * 过滤文本，并标记出敏感词，默认使用HTML中红色font标记
	 *
	 * @author hymer
	 * @param text
	 * @return
	 */
	public static String filter(String text) {
		return Finder.filter(text);
	}

	/**
	 *
	 * 过滤文本，并标记出敏感词
	 *
	 * @author hymer
	 * @param text
	 * @param startTag
	 * @param endTag
	 * @return
	 */
	public static String filter(String text, String startTag, String endTag) {
		return Finder.filter(text, startTag, endTag);
	}

}
