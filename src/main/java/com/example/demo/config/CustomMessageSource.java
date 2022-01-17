package com.example.demo.config;

import cn.hutool.core.map.MapUtil;
import com.example.demo.pojo.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("messageSource")
public class CustomMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    ResourceLoader resourceLoader;

    @Autowired
    MessageService messageService;

    private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        this.reload();
    }

    /**
     * 重新将数据库中的国际化配置加载
     */
    public void reload() {
        LOCAL_CACHE.clear();
        LOCAL_CACHE.putAll(loadAllMessageResourcesFromDataBase());
    }

    /**
     * 从数据库中获取所有国际化配置 这边可以根据自己数据库表结构进行相应的业务实现
     * 对应的语言能够取出来对应的值就行了 无需一定要按照这个方法来
     */
    public Map<String, Map<String, String>> loadAllMessageResourcesFromDataBase() {
        List<Message> messagesList = messageService.queryAllMessage();

        if (!messagesList.isEmpty()){
            final Map<String, String> zhCnMessageResources = new HashMap<>(messagesList.size());
            final Map<String, String> enUsMessageResources = new HashMap<>(messagesList.size());
            final Map<String, String> idIdMessageResources = new HashMap<>(messagesList.size());

            for (Message message : messagesList) {
                String messageBasename = message.getBasename();

                zhCnMessageResources.put(messageBasename, message.getChinese());
                enUsMessageResources.put(messageBasename, message.getEnglish());
                idIdMessageResources.put(messageBasename, message.getIndonesian());
            }

            LOCAL_CACHE.put("zh", zhCnMessageResources);
            LOCAL_CACHE.put("en", enUsMessageResources);
            LOCAL_CACHE.put("in", idIdMessageResources);
        }

        return MapUtil.newHashMap();
    }

    /**
     * 从缓存中取出国际化配置对应的数据 或者从父级获取
     *
     * @param code
     * @param locale
     * @return
     */
    public String getSourceFromCache(String code, Locale locale) {
        String language = locale.getLanguage();
        Map<String, String> props = LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            return props.get(code);
        } else {
            try {
                if (null != this.getParentMessageSource()) {
                    return this.getParentMessageSource().getMessage(String.valueOf(code), null, locale);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
            return code;
        }
    }

    public String getSourceFromCache(String code, Object[] args, Locale locale) {
        String language = locale.getLanguage();
        Map<String, String> props = LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            MessageFormat messageFormat = resolveCode(code, locale);

            if (messageFormat != null){
                synchronized (messageFormat){
                    return messageFormat.format(args);
                }
            }
            return props.get(code);
        } else {
            try {
                if (null != this.getParentMessageSource()) {
                    return this.getParentMessageSource().getMessage(String.valueOf(code), args, locale);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
            return code;
        }
    }

    // 下面三个重写的方法是比较重要的
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader == null ? new DefaultResourceLoader() : resourceLoader);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getSourceFromCache(code, locale);
        MessageFormat messageFormat = new MessageFormat(msg, locale);
        return messageFormat;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getSourceFromCache(code, locale);
    }
}
