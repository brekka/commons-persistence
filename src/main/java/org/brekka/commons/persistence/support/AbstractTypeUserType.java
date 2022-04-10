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
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

/**
 * Entity type registration with Hibernate supporting registration of enum types.
 */
public abstract class AbstractTypeUserType<T extends EntityType> implements UserType {

    private static final int[] TYPES = { Types.VARCHAR };

    private final Map<String, T> typesMap;

    protected AbstractTypeUserType(final Map<String, T> typesMap) {
        this.typesMap = new HashMap<>(typesMap);
    }

    @SuppressWarnings("unchecked")
    protected <Type extends Enum<?> & EntityType> AbstractTypeUserType(final List<Class<Type>> enumTypesList) {
        Class<?> expectedType = returnedClass();
        Map<String, T> tMap = new HashMap<>();
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

                T existing = tMap.put(t.getKey(), t);
                if (existing != null) {
                    throw new IllegalStateException(String.format(
                            "Unable to add type key '%s' for enum '%s', the key is already taken by '%s'",
                            existing.getKey(), t.getClass().getName(), existing.getClass().getName()));
                }
            }
        }
        this.typesMap = tMap;
    }

    @Override
    public int[] sqlTypes() {
        return TYPES;
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        if (x == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor session, final Object owner)
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

    @Override
    @SuppressWarnings("unchecked")
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
            return;
        }
        T tokenType = (T) value;
        st.setObject(index, tokenType.getKey(), Types.VARCHAR);
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return value.toString();
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original.toString();
    }
}
