/**
 * 
 */
package org.brekka.commons.persistence.support;

import java.io.Serializable;
import java.util.Date;

import org.brekka.commons.persistence.model.LongevousEntity;
import org.brekka.commons.persistence.model.SnapshotEntity;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * @author Andrew Taylor (andrew@brekka.org)
 *
 */
public class EntityInterceptor extends EmptyInterceptor {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 2498606006650979836L;

    /* (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) {
        if (entity instanceof LongevousEntity) {
            setProperty("modified", new Date(), currentState, propertyNames);
            return true;
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof SnapshotEntity
                || entity instanceof LongevousEntity) {
            setProperty("created", new Date(), state, propertyNames);
            setProperty("modified", new Date(), state, propertyNames);
            return true;
        }
        return false;
    }

    /**
     * @param string
     * @param date
     * @param state
     * @param propertyNames
     */
    private void setProperty(String propertyName, Object toValue, Object[] state, String[] propertyNames) {
        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyNames[i].equals(propertyName)) {
                state[i] = toValue;
                break;
            }
        }
    }
    
    
}
