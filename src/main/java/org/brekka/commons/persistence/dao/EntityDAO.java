package org.brekka.commons.persistence.dao;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.persistence.LockModeType;


public interface EntityDAO<ID extends Serializable, Entity extends Serializable> {

    Entity retrieveById(ID entityId);
    
    Entity retrieveById(ID entityId, LockModeType lockMode);
    
    Entity retrieveById(ID entityId, LockModeType lockMode, int timeout, TimeUnit timeUnit);
    
    ID create(Entity entity);
    
    void update(Entity entity);
    
    void delete(ID entityId);
}
