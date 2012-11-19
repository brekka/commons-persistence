package org.brekka.commons.persistence.model;

import java.io.Serializable;

public interface IdentifiableEntity<T extends Serializable> extends Serializable {
    
    T getId();

    void setId(T id);
}
