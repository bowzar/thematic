package com.yulintu.thematic.data;

import java.util.List;

public interface ProviderHibernate extends ProviderDb {

    List query(HibernateSessionQuery callback);

    Object execute(HibernateSessionExecute callback);

    Object executeInSession(HibernateSessionExecute callback);

    Object executeInTransaction(HibernateSessionExecute callback);
}
