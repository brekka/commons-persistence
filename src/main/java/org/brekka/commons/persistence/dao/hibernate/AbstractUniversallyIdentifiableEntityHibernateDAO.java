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

package org.brekka.commons.persistence.dao.hibernate;

import java.util.UUID;

import org.brekka.commons.persistence.model.IdentifiableEntity;

public abstract class AbstractUniversallyIdentifiableEntityHibernateDAO<Entity extends IdentifiableEntity<UUID>>
                extends AbstractIdentifiableEntityHibernateDAO<UUID, Entity> {


    @Override
    public UUID create(final Entity entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return super.create(entity);
    }
}
