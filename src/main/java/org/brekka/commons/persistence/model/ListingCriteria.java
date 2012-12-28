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
import java.util.List;

/**
 * Standard list criteria.
 *
 * @author Andrew Taylor (andrew@brekka.org)
 */
public final class ListingCriteria implements Serializable {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 4507631050591157529L;
    
    private final int startIndex;
    private final int endIndex;
    private final List<OrderByPart> orderByParts;
    /**
     * @param startIndex
     * @param endIndex
     * @param orderByParts
     */
    public ListingCriteria(int startIndex, int endIndex, List<OrderByPart> orderByParts) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.orderByParts = orderByParts;
    }
    /**
     * @return the startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }
    /**
     * @return the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }
    /**
     * @return the orderByParts
     */
    public List<OrderByPart> getOrderByParts() {
        return orderByParts;
    }
}
