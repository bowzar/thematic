package com.yulintu.thematic.data;

public class ProviderDbImpl extends ProviderImpl implements ProviderDb {

    //region fields
    private int cntConnection;
    private int cntTransaction;
    private final Object objSync = new Object();
    //endregion

    //region ctor
    public ProviderDbImpl(String connectionString) {
        super(connectionString);
    }
    //endregion

    //region methods
    @Override
    protected void initialize(String connectionString) {
        super.initialize(connectionString);
    }

    @Override
    public final boolean openConnection() {

        synchronized (objSync) {

            if (cntConnection == 0) {

                boolean val = onOpenConnection();
                cntConnection++;
                return val;
            }

            cntConnection++;
        }

        return false;
    }

    protected boolean onOpenConnection() {
        return false;
    }

    @Override
    public final boolean closeConnection() {

        synchronized (objSync) {

            if (cntConnection == 1) {
                boolean val = onCloseConnection();
                cntConnection--;
                return val;
            }

            cntConnection--;
        }

        return false;
    }

    protected boolean onCloseConnection() {
        return false;
    }

    @Override
    public final boolean beginTransaction() {

        synchronized (objSync) {

            if (cntTransaction == 0) {
                boolean val = onBeginTransaction();
                cntTransaction++;
                return val;
            }

            cntTransaction++;
        }

        return false;
    }

    protected boolean onBeginTransaction() {
        return false;
    }

    @Override
    public final boolean commitTransaction() {

        synchronized (objSync) {

            if (cntTransaction == 1) {
                boolean val = onCommitTransaction();
                cntTransaction--;
                return val;
            }

            cntTransaction--;
        }

        return false;
    }

    protected boolean onCommitTransaction() {
        return false;
    }

    @Override
    public final boolean rollbackTransaction() {

        synchronized (objSync) {

            if (cntTransaction == 1) {
                boolean val = onRollbackTransaction();
                cntTransaction--;
                return val;
            }

            cntTransaction--;
        }

        return false;
    }

    protected boolean onRollbackTransaction() {
        return false;
    }
    //endregion
}
