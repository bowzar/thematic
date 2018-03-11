package com.yulintu.thematic.example.repository.sqlserver;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.example.repository.RepositoryEmployee;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class RepositoryEmployeeSQLServer extends RepositoryEmployee {

    public RepositoryEmployeeSQLServer(Provider provider) {
        super(provider);
    }

    @Override
    public String toString() {
        return super.toString() + "#" + "SQLServer";
    }
}
