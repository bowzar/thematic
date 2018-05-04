package com.yulintu.thematic.data.sharding;

class ShardingTransactionUtils {

    //region fields
    private static final ThreadLocal<Boolean> tt = new ThreadLocal<>();
    //endregion

    //region methods
    public static boolean isNeedTransaction() {
        return tt.get();
    }

    public static void setNeedTransaction(boolean val) {
        tt.set(val);
    }
    //endregion
}
