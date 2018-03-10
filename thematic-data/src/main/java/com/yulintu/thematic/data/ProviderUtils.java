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
    //endregion
}
