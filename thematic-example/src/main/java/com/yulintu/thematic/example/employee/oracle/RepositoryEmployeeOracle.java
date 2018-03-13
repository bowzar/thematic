package com.yulintu.thematic.example.employee.oracle;

import com.yulintu.thematic.data.DbTransaction;
import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.example.employee.RepositoryEmployeeImpl;
import com.yulintu.thematic.example.employee.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("prototype")
public class RepositoryEmployeeOracle extends RepositoryEmployeeImpl {

    public RepositoryEmployeeOracle(Provider provider) {
        super(provider);
    }

    @Override
    public String toString() {
        return super.toString() + "#" + "Oracle";
    }
}
