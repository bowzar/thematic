package com.yulintu.thematic.data;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;

public class ProviderUtils {

    //region fields
    private static final ThreadLocal<Provider> tl = new ThreadLocal<>();

    @Getter
    @Setter
    private  static  String providerName = null;
    //endregion

    //region methods
    public static Provider getCurrentProvider() {
        return tl.get();
    }

    public static void setCurrentProvider(Provider provider) {
        tl.set(provider);
    }

    public static Provider initializeCurrentProvider() {

        Provider provider = getCurrentProvider();
        if (provider == null) {
            setCurrentProvider(
                    provider = Strings.isNullOrEmpty( providerName)?
                            GlobalApplicationContext.getApplicationContext().getBean(Provider.class):
                            GlobalApplicationContext.getApplicationContext().getBean(providerName, Provider.class));
        }

        return provider;
    }

    public static void clearCurrentProvider() {
        setCurrentProvider(null);
    }
    //endregion
}
