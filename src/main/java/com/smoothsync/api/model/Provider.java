/*
 * Copyright (C) 2016 Marten Gajda <marten@dmfs.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.smoothsync.api.model;

import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterators.FilteredIterator;

import java.util.Iterator;


/**
 * Interface of a provider.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public interface Provider
{
    /**
     * An identifier of this provider.
     *
     * @return
     *
     * @throws ProtocolException
     */
    String id() throws ProtocolException;

    /**
     * The (possibly localized) name under which the service is known to the user.
     *
     * @return
     *
     * @throws ProtocolException
     */
    String name() throws ProtocolException;

    /**
     * The domains this provider owns. The domains may contain wildcards in the first segment.
     *
     * @return
     *
     * @throws ProtocolException
     */
    String[] domains() throws ProtocolException;

    /**
     * An Iterator of {@link Link}s that are related to this provider. Use a {@link FilteredIterator} to retrieve only {@link Link}s with a specific relation
     * type.
     *
     * @return
     *
     * @throws ProtocolException
     */
    Iterator<Link> links() throws ProtocolException;

    /**
     * An Iterator of {@link Service}s provider provides. Use a {@link FilteredIterator} to retrieve only {@link Service}s of a specific service type.
     *
     * @return
     *
     * @throws ProtocolException
     */
    Iterator<Service> services() throws ProtocolException;
}
