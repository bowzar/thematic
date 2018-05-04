package com.yulintu.thematic.data;

import com.google.common.base.Strings;
import com.yulintu.thematic.AssertUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RepositoryFactory {

    //region fields
    private static final Map<Provider, RepositoryFactory> map = new ConcurrentHashMap<>();
    private Map<Class<? extends Repository>, Repository> mapInstances = new ConcurrentHashMap<>();
    private Provider provider;
    private ApplicationContext ac;
    //endregion

    //region ctor
    public RepositoryFactory(Provider provider) {
        AssertUtils.notNull(provider, "provider");
        AssertUtils.ifTrue(Strings.isNullOrEmpty(provider.getType()), "必须指定 ProviderType");

        this.provider = provider;

        ac = tryLoad(String.format("file:spring.repository.%s.xml", provider.getType()));
        if (ac == null)
            ac = tryLoad(String.format("file:config/spring.repository.%s.xml", provider.getType()));
        if (ac == null)
            ac = tryLoad(String.format("classpath:spring.repository.%s.xml", provider.getType()));
    }
    //endregion

    //region methods

    public static RepositoryFactory get(Provider provider) {
        if (map.containsKey(provider)) {
            RepositoryFactory factory = map.get(provider);
            if (factory != null)
                return factory;
        }

        RepositoryFactory factory = new RepositoryFactory(provider);
        map.put(provider, factory);
        return factory;
    }

    public static void clear(Provider provider) {
        if (map.containsKey(provider)) {
            RepositoryFactory factory = map.get(provider);
            map.remove(provider);

            if (factory != null)
                factory.mapInstances.clear();
        }
    }

    public <T extends Repository> T create(Class<? extends T> type) {

        return ac.getBean(type, provider);
    }

    public <T extends Repository> T create(Class<? extends T> type, boolean singleton) {

        if (singleton && mapInstances.containsKey(type))
            return (T) mapInstances.get(type);

        T bean = ac.getBean(type, provider);
        if (singleton)
            mapInstances.put(type, bean);

        return bean;
    }

    private ApplicationContext tryLoad(String fileName) {

        try {
            return ClassPathXmlApplicationContextPool.initialize(fileName);

        } catch (Throwable e) {
            return null;
        }
    }
    //endregion
}
