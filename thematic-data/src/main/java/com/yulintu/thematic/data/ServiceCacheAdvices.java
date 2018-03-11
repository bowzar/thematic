package com.yulintu.thematic.data;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ServiceCacheAdvices {

    @Around("this(com.yulintu.thematic.data.Service)")
    public Object onAround(ProceedingJoinPoint pjp) {

        Provider provider = null;
        Object[] args = pjp.getArgs();
        try {

          //  provider = ProviderUtils.initializeCurrentProvider();
          //  if (provider instanceof ProviderDb) {
           //     ((ProviderHibernate) provider).openConnection();
          //  }

            return pjp.proceed(args);

        } catch (Throwable e) {
            throw new RuntimeException(e);

        } finally {
         //   if (provider instanceof ProviderDb) {
          //      ((ProviderHibernate) provider).closeConnection();
          //  }
        }
    }
}
