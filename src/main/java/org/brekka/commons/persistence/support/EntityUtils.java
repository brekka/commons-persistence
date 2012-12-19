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

import java.io.Serializable;

import org.brekka.commons.persistence.model.IdentifiableEntity;

/**
 * Utilities for working with entities.
 *
 * @author Andrew Taylor (andrew@brekka.org)
 */
public final class EntityUtils {

    /**
     * Non-cons
     */
    private EntityUtils() {
    }
    
    /**
     * Determine whether two entity instances are the same by their primary key.
     * 
     * @param left entity on the left
     * @param right entity on the right
     * @return true only if both entities have non-null ID's and they are equal as determined by the equals method of the id type.
     */
    public static <ID extends Serializable> boolean identityEquals(IdentifiableEntity<ID> left, IdentifiableEntity<ID> right) {
        if (left != null 
                && right != null) {
            ID leftId = left.getId();
            ID rightId = right.getId();
            if (leftId != null && rightId != null) {
                return leftId.equals(rightId);
            }
        }
        return false;
    }
}
