package org.brekka.commons.persistence.dao;

import java.io.Serializable;


public interface EntityDAO<ID extends Serializable, Entity extends Serializable> {

    Entity retrieveById(ID entityId);
    
    ID create(Entity entity);
    
    void update(Entity entity);
    
    void delete(ID entityId);
}
