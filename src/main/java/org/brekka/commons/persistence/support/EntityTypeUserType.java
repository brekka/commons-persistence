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

import java.util.Map;

import org.brekka.commons.persistence.model.EntityType;

/**
 * Map any entity user type defined within the typesMap.
 *
 * @author Andrew Taylor (andrew@brekka.org)
 */
public class EntityTypeUserType extends AbstractTypeUserType<EntityType> {

    public EntityTypeUserType(Map<String, EntityType> typesMap) {
        super(typesMap);
    }
    
    /* (non-Javadoc)
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    @Override
    public Class<EntityType> returnedClass() {
        return EntityType.class;
    }
}
