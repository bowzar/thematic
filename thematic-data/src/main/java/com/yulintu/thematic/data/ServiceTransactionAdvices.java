package com.yulintu.thematic.data;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ServiceTransactionAdvices {

    @Around("@annotation(com.yulintu.thematic.data.Transaction)")
    public Object onAround(ProceedingJoinPoint pjp) {

        ProviderDb provider = null;
        Object[] args = pjp.getArgs();
        try {

          //  Provider p = ProviderUtils.initializeCurrentProvider();
            //if (p instanceof ProviderDb) {
            //    provider = (ProviderDb) p;
               // provider.openConnection();
              //  provider.beginTransaction();
          //  }

            Object val = pjp.proceed(args);
           // if (provider != null)
              //  provider.commitTransaction();

            return val;

        } catch (Throwable e) {
          //  if (provider != null)
           //     provider.rollbackTransaction();

            throw new RuntimeException(e);

        } finally {
           // if (provider != null)
           //     provider.closeConnection();
        }
    }
}
