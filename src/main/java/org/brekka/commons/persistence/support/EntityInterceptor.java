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
            return setProperty("modified", new Date(), currentState, propertyNames, true);
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
            return setProperty("created", new Date(), state, propertyNames, false)
                 | setProperty("modified", new Date(), state, propertyNames, false);
        }
        return false;
    }

    /**
     * @param string
     * @param date
     * @param state
     * @param propertyNames
     */
    protected boolean setProperty(String propertyName, Object toValue, Object[] state, String[] propertyNames, boolean overwrite) {
        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyNames[i].equals(propertyName)) {
                if (overwrite || state[i] == null) {
                    state[i] = toValue;
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
