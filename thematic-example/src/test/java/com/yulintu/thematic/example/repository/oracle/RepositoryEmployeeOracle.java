package com.yulintu.thematic.example.repository.oracle;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.example.repository.RepositoryEmployee;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class RepositoryEmployeeOracle extends RepositoryEmployee {

    public RepositoryEmployeeOracle(Provider provider) {
        super(provider);
    }

    @Override
    public String toString() {
        return super.toString() + "#" + "Oracle";
    }
}
