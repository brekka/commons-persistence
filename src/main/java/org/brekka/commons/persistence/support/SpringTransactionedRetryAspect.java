/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brekka.commons.persistence.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.brekka.commons.utils.AbstractRetryAspect;
import org.springframework.core.Ordered;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Handle retry of transactions that throw the configured exception types.
 *
 * @author Andrew Taylor (andrew@brekka.org)
 */
@Aspect
public class SpringTransactionedRetryAspect extends AbstractRetryAspect implements Ordered {

    /**
     * Default order
     */
    private static final int DEFAULT_ORDER = 100;

    /**
     * the order
     */
    private int order = DEFAULT_ORDER;
    
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object retryOnConcurrentError(ProceedingJoinPoint pjp) throws Throwable {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // Already in a transaction
            return pjp.proceed();
        }
        return attemptWithRetry(pjp);
    }
    

    /* (non-Javadoc)
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return order;
    }
    
    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
