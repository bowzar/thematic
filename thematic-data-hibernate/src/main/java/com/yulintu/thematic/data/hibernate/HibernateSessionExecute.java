package com.yulintu.thematic.data.hibernate;

import org.hibernate.Session;

import java.util.List;

public interface HibernateSessionExecute {

    Object execute(Session session);
}
