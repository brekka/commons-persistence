package org.brekka.commons.persistence.dao.hibernate;

import java.io.Serializable;

import org.brekka.commons.persistence.dao.EntityDAO;
import org.brekka.commons.persistence.model.IdentifiableEntity;
import org.hibernate.Session;

public abstract class AbstractIdentifiableEntityHibernateDAO<ID extends Serializable, Entity extends IdentifiableEntity<ID>> implements EntityDAO<ID, Entity> {

    @SuppressWarnings("unchecked")
    @Override
    public Entity retrieveById(ID entityId) {
        Session session = getCurrentSession();
        return (Entity) session.get(type(), entityId);
    }
    
    @Override
    public ID create(Entity entity) {
        Session session = getCurrentSession();
        session.save(entity);
        return entity.getId();
    }

    @Override
    public void update(Entity entity) {
        Session session = getCurrentSession();
        session.update(entity);
    }

    @Override
    public void delete(ID entityId) {
        Session session = getCurrentSession();
        Object toDelete = session.get(type(), entityId);
        session.delete(toDelete);
    }
    
    protected abstract Class<Entity> type();

    protected abstract Session getCurrentSession();
}
