package org.brekka.commons.persistence.dao.hibernate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.persistence.LockModeType;

import org.brekka.commons.persistence.dao.EntityDAO;
import org.brekka.commons.persistence.model.IdentifiableEntity;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

public abstract class AbstractIdentifiableEntityHibernateDAO<ID extends Serializable, Entity extends IdentifiableEntity<ID>> implements EntityDAO<ID, Entity> {

    @Override
    public Entity retrieveById(ID entityId) {
        return retrieveById(entityId, LockModeType.NONE, -1, null);
    }
    
    /* (non-Javadoc)
     * @see org.brekka.commons.persistence.dao.EntityDAO#retrieveById(java.io.Serializable, javax.persistence.LockModeType)
     */
    @Override
    public Entity retrieveById(ID entityId, LockModeType lockModeType) {
        return retrieveById(entityId, lockModeType, -1, null);
    }
    
    /* (non-Javadoc)
     * @see org.brekka.commons.persistence.dao.EntityDAO#retrieveById(java.io.Serializable, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Entity retrieveById(ID entityId, LockModeType lockModeType, int timeout, TimeUnit timeUnit) {
        Session session = getCurrentSession();
        LockMode lockMode = getLockMode(lockModeType);
        LockOptions lockOptions = new LockOptions(lockMode);
        if (timeout > -1) {
            lockOptions.setTimeOut((int) timeUnit.toMillis(timeout));
        }
        return (Entity) session.get(type(), entityId, lockOptions);
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
    
    /**
     * Borrowed from org.hibernate.ejb.util.LockModeTypeHelper
     * @param lockMode
     * @return
     */
    public static LockMode getLockMode(LockModeType lockMode) {
        switch ( lockMode ) {
            case READ:
            case OPTIMISTIC: {
                return LockMode.OPTIMISTIC;
            }
            case OPTIMISTIC_FORCE_INCREMENT:
            case WRITE: {
                return LockMode.OPTIMISTIC_FORCE_INCREMENT;
            }
            case PESSIMISTIC_READ: {
                return LockMode.PESSIMISTIC_READ;
            }
            case PESSIMISTIC_WRITE: {
                return LockMode.PESSIMISTIC_WRITE;
            }
            case PESSIMISTIC_FORCE_INCREMENT: {
                return LockMode.PESSIMISTIC_FORCE_INCREMENT;
            }
            case NONE: {
                return LockMode.NONE;
            }
            default: {
                throw new IllegalStateException( "Unknown LockModeType: " + lockMode );
            }
        }
    }

}
