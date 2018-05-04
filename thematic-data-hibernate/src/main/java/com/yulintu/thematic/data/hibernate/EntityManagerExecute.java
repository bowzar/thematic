package com.yulintu.thematic.data.hibernate;

import javax.persistence.EntityManager;

public interface EntityManagerExecute {

    Object execute(EntityManager session);
}
