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

package org.brekka.commons.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Identifies entities that will be around for a long time, ie are lively to be updated at some
 * point during their (long) lifetime.
 * 
 * @author Andrew Taylor (andrew@brekka.org)
 */
@MappedSuperclass
public abstract class LongevousEntity<ID extends Serializable> implements IdentifiableEntity<ID> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 4541242370565729473L;

    /**
     * The moment this entity was created
     */
    @Column(name="`Created`", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    /**
     * When was this entity last modified
     */
    @Column(name="`Modified`", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;


    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }
    
    /**
     * @param created the created to set
     */
    void setCreated(Date created) {
        this.created = created;
    }
    
    /**
     * @param modified the modified to set
     */
    void setModified(Date modified) {
        this.modified = modified;
    }
}
