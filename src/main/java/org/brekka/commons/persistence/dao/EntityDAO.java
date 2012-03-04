package org.brekka.commons.persistence.dao;


public interface EntityDAO<ID, Entity> {

    Entity retrieveById(ID entityId);
    
    ID create(Entity entity);
    
    void update(Entity entity);
    
    void delete(ID entityId);
}
