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

import org.hibernate.transform.BasicTransformerAdapter;

/**
 * @author Andrew Taylor (andrew@brekka.org)
 *
 */
public class FirstResultTransformer extends BasicTransformerAdapter {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8591535664900083479L;

    public static final FirstResultTransformer INSTANCE = new FirstResultTransformer();

    private FirstResultTransformer() {

    }

    /* (non-Javadoc)
     * @see org.hibernate.transform.BasicTransformerAdapter#transformTuple(java.lang.Object[], java.lang.String[])
     */
    @Override
    public Object transformTuple(final Object[] tuple, final String[] aliases) {
        return tuple.length > 0 ? tuple[0] : null;
    }
}
