/*
 * Copyright 2012 the original author or authors.
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

import java.util.List;

import org.brekka.commons.persistence.model.ListingCriteria;
import org.brekka.commons.persistence.model.OrderByPart;
import org.brekka.commons.persistence.model.OrderByProperty;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 * TODO Description of HibernateUtils
 *
 * @author Andrew Taylor (andrew@brekka.org)
 */
public final class HibernateUtils {
    
    /**
     * 
     */
    private HibernateUtils() {
    }
    
    /**
     * 
     * @param criteria
     * @param listingCriteria
     */
    public static void applyCriteria(Criteria criteria, ListingCriteria listingCriteria) {
        criteria.setFirstResult(listingCriteria.getStartIndex());
        criteria.setMaxResults((listingCriteria.getEndIndex() + 1) - listingCriteria.getStartIndex());
        List<OrderByPart> orderByParts = listingCriteria.getOrderByParts();
        for (OrderByPart orderByPart : orderByParts) {
            if (orderByPart instanceof OrderByProperty) {
                OrderByProperty orderByProperty = (OrderByProperty) orderByPart;
                if (orderByPart.isAscending()) {
                    criteria.addOrder(Order.asc(orderByProperty.getExpression()));
                } else {
                    criteria.addOrder(Order.desc(orderByProperty.getExpression()));
                }
            }
        }
    }
}
