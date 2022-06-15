package com.feng.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author fhn
 * @create 2021/7/23
 * @software idea
 */
public class PropertiesUtil {

    /**
     * 缓存map的初始容量
     */
    static final int INITIAL_CAPACITY = 16;

    /**
     * 配置文件的缓存目录，对于已经读取到的properties做缓存
     */
    private static final Map<String, Properties> cacheProperties = new HashMap<>(INITIAL_CAPACITY);


    /**
     * 供外界调用的方法，获取配置的属性值
     *
     * @param configFileName 配值文件的名称
     * @param key            key值
     * @return 返回的value
     */
    public static Object getValue(String configFileName, String key) {
        return getProperties(configFileName).getProperty(key);
    }

    /**
     * 私有方法，加载config文件并且放进cache中去
     *
     * @param configFileName 配值文件名
     * @return 返回properties对象
     */
    private static Properties getProperties(String configFileName) {
        if (cacheProperties.get(configFileName) != null) {
            return cacheProperties.get(configFileName);
        }
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName));
            cacheProperties.put(configFileName, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
