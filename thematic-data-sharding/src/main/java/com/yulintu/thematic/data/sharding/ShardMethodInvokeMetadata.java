package com.yulintu.thematic.data.sharding;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;

@Getter
@Setter
public class ShardMethodInvokeMetadata {

    private String key;
    private ProceedingJoinPoint joinPoint;
    private boolean transactional;
    private String shardType;
    private ShardReduceType reduceType;
    private Class<ShardReducer> reducer;
    private HashMap<String, Object> args = new HashMap<>();
}
