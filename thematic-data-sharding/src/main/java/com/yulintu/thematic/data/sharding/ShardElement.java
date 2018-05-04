package com.yulintu.thematic.data.sharding;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class ShardElement {

    private String name;
    private String group;
    private String provider;
    private String connectionString;
    private Properties properties = new Properties();
}
