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

package org.brekka.commons.persistence.model;

import java.io.Serializable;

/**
 * A part of an order-by where the column name is identified by a property name (that will be resolved into an actual
 * column name by the ORM).
 * 
 * @author Andrew Taylor (andrew@brekka.org)
 */
public class OrderByProperty implements Serializable, OrderByPart {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -6500741027487383883L;

    private final String property;

    private final boolean ascending;

    /**
     * @param property
     * @param ascending
     */
    public OrderByProperty(String property, boolean ascending) {
        this.property = property;
        this.ascending = ascending;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.brekka.commons.persistence.model.OrderByPart#getProperty()
     */
    @Override
    public String getExpression() {
        return property;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.brekka.commons.persistence.model.OrderByPart#isAscending()
     */
    @Override
    public boolean isAscending() {
        return ascending;
    }
}
