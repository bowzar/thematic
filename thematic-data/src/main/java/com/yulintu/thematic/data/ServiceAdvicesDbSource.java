package com.yulintu.thematic.data;

import com.yulintu.thematic.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ServiceAdvicesDbSource {

    @Around("this(com.yulintu.thematic.data.Service)")
    public Object onAround(ProceedingJoinPoint pjp) {

        Provider provider = null;
        Object[] args = pjp.getArgs();
        try {

            provider = ProviderUtils.initializeCurrentProvider();
//          if (provider instanceof ProviderDb) {
//              ((ProviderDb) provider).openConnection();
//          }

            return pjp.proceed(args);

        } catch (Throwable e) {
            if (provider != null) {
                ProviderUtils.clearCurrentProvider();
                RepositoryFactory.clear(provider);
            }

            ExceptionUtils.throwRuntimeException(e);
            return null;
        } /*finally {
            if (provider instanceof ProviderDb) {
                ((ProviderDb) provider).closeConnection();
            }
        }*/
    }
}
