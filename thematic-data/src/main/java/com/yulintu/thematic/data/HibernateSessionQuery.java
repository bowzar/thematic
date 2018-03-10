package com.yulintu.thematic.data;

import org.hibernate.Session;

import java.util.List;

public interface HibernateSessionQuery {

    List query(Session session);
}
