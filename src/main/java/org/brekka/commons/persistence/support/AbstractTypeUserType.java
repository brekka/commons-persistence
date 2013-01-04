/*
 * Copyright 2013 the original author or authors.
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brekka.commons.persistence.model.EntityType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * Entity type registration with Hibernate supporting registration of enum types.
 *
 * @author Andrew Taylor (andrew@brekka.org)
 */
public abstract class AbstractTypeUserType<T extends EntityType> implements UserType {

    private static final int[] TYPES = { Types.VARCHAR };

    private final Map<String, T> typesMap;

    protected AbstractTypeUserType(Map<String, T> typesMap) {
        this.typesMap = new HashMap<String, T>(typesMap);
    }

    @SuppressWarnings("unchecked")
    protected <Type extends Enum<?> & EntityType> AbstractTypeUserType(List<Class<Type>> enumTypesList) {
        Class<?> expectedType = returnedClass();
        Map<String, T> typesMap = new HashMap<String, T>();
        for (Class<Type> tClass : enumTypesList) {
            if (!tClass.isEnum()) {
                throw new IllegalStateException(String.format(
                        "The class '%s' is not an enum based class", tClass.getName()));
            }
            Type[] enumConstants = tClass.getEnumConstants();
            for (Type type : enumConstants) {
                T t;
                if (expectedType.isAssignableFrom(type.getClass())) {
                    t = (T) type;
                } else {
                    throw new IllegalArgumentException(String.format(
                            "The enum type '%s' does not match the expected type '%s'", 
                            type.getClass().getName(), expectedType.getName()));
                }
                
                T existing = typesMap.put(t.getKey(), t);
                if (existing != null) {
                    throw new IllegalStateException(String.format(
                            "Unable to add type key '%s' for enum '%s', the key is already taken by '%s'",
                            existing.getKey(), t.getClass().getName(), existing.getClass().getName()));
                }
            }
        }
        this.typesMap = typesMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    @Override
    public int[] sqlTypes() {
        return TYPES;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null) {
            return false;
        }
        return x.equals(y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[],
     * org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (value == null) {
            return null;
        }
        T tokenType = typesMap.get(value);
        if (tokenType == null) {
            throw new IllegalArgumentException(String.format("No type found for key '%s'", value));
        }
        return tokenType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int,
     * org.hibernate.engine.spi.SessionImplementor)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
            return;
        }
        T tokenType = (T) value;
        st.setObject(index, tokenType.getKey(), Types.VARCHAR);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return value.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
     */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original.toString();
    }
}
