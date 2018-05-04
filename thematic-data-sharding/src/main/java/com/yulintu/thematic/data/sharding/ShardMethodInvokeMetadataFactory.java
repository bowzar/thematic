package com.yulintu.thematic.data.sharding;

import org.aspectj.lang.ProceedingJoinPoint;

public interface ShardMethodInvokeMetadataFactory {

    ShardMethodInvokeMetadata generate(ProceedingJoinPoint pjp);
}
