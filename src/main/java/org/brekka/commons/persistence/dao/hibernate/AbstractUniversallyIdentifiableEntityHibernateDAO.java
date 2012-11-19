package org.brekka.commons.persistence.dao.hibernate;

import java.util.UUID;

import org.brekka.commons.persistence.model.IdentifiableEntity;

public abstract class AbstractUniversallyIdentifiableEntityHibernateDAO<Entity extends IdentifiableEntity<UUID>> 
                extends AbstractIdentifiableEntityHibernateDAO<UUID, Entity> {

    
    @Override
    public UUID create(Entity entity) {
        UUID id = UUID.randomUUID();
        if (entity.getId() == null) {
            entity.setId(id);
        }
        super.create(entity);
        return id;
    }
}
