package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.data.Provider;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;

@Getter
@Setter
public class ShardMethodInvokeResult {

    private Object returnValue;
    private Provider provider;
}
