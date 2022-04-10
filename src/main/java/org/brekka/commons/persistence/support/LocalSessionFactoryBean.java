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


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;


/**
 * Enhancement to the Spring {@link org.springframework.orm.hibernate5.LocalSessionFactoryBean}
 * to provide a way to access the Hibernate configuration.
 */
public class LocalSessionFactoryBean extends org.springframework.orm.hibernate5.LocalSessionFactoryBean {

    private List<ConfigurationPostProcessor> configurationPostProcessors;

    @Override
    protected SessionFactory buildSessionFactory(final LocalSessionFactoryBuilder sfb) {
        for (ConfigurationPostProcessor postProcessor : configurationPostProcessors) {
            postProcessor.postProcess(sfb);
        }
        return super.buildSessionFactory(sfb);
    }

    public void setConfigurationPostProcessors(final List<ConfigurationPostProcessor> configurationPostProcessors) {
        this.configurationPostProcessors = configurationPostProcessors;
    }
}
