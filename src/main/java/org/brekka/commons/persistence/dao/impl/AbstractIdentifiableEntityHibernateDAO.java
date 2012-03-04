package org.brekka.commons.persistence.dao.impl;

import java.util.UUID;

import org.brekka.commons.persistence.dao.EntityDAO;
import org.brekka.commons.persistence.model.IdentifiableEntity;
import org.hibernate.Session;

public abstract class AbstractIdentifiableEntityHibernateDAO<Entity extends IdentifiableEntity> implements EntityDAO<UUID, Entity> {

    @SuppressWarnings("unchecked")
    @Override
    public Entity retrieveById(UUID entityId) {
        Session session = getCurrentSession();
        return (Entity) session.get(type(), entityId);
    }
    
    @Override
    public UUID create(Entity entity) {
        UUID id = UUID.randomUUID();
        if (entity.getId() != null) {
            entity.setId(id);
        }
        Session session = getCurrentSession();
        session.save(entity);
        return id;
    }

    @Override
    public void update(Entity entity) {
        Session session = getCurrentSession();
        session.update(entity);
    }

    @Override
    public void delete(UUID entityId) {
        Session session = getCurrentSession();
        Object toDelete = session.get(type(), entityId);
        session.delete(toDelete);
    }
    
    protected abstract Class<Entity> type();

    protected abstract Session getCurrentSession();
}
