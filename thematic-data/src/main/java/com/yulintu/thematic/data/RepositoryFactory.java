package com.yulintu.thematic.data;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RepositoryFactory {

    //region fields
    private static final Map<Provider, RepositoryFactory> map = new ConcurrentHashMap<>();
    private Map<Class<? extends Repository>, Repository> mapInstances = new ConcurrentHashMap<>();
    private Provider provider;
    private ApplicationContext ac;
    private String configFileTemplate;
    //endregion

    //region ctor
    public RepositoryFactory(Provider provider) {
        this(provider, "classpath:spring.repository.%s.xml");
    }

    public RepositoryFactory(Provider provider, String configFileTemplate) {
        Assert.notNull(provider);
        Assert.hasLength(provider.getType());
        Assert.hasLength(configFileTemplate);

        this.provider = provider;
        this.configFileTemplate = configFileTemplate;

        ac = ClassPathXmlApplicationContextPool.initialize(
                String.format(configFileTemplate, provider.getType()));
    }
    //endregion

    //region methods

    public static RepositoryFactory get(Provider provider) {
        if (map.containsKey(provider))
            return map.get(provider);

        RepositoryFactory factory = new RepositoryFactory(provider);
        map.put(provider, factory);
        return factory;
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
    //endregion
}
