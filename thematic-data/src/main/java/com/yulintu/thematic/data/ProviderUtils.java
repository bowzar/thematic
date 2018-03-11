package com.yulintu.thematic.data;

public class ProviderUtils {

    //region fields
    private static final ThreadLocal<Provider> tl = new ThreadLocal<>();
    //endregion

    //region methods
    public static Provider getCurrentProvider() {
        return tl.get();
    }

    public static void setCurrentProvider(Provider provider) {
        tl.set(provider);
    }

    public  static  Provider  initializeCurrentProvider(){

        Provider provider = getCurrentProvider();
        if (provider == null) {
            setCurrentProvider(
                    provider = GlobalApplicationContext.getApplicationContext().getBean(Provider.class));
        }

        return provider;
    }
    //endregion
}
