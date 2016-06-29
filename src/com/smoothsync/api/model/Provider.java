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

import java.util.Iterator;

import org.dmfs.httpclient.exceptions.ProtocolException;
import org.dmfs.httpclient.types.Link;
import org.dmfs.iterators.FilteredIterator;


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
	 * @throws ProtocolException
	 */
	public String id() throws ProtocolException;


	/**
	 * The (possibly localized) name under which the service is known to the user.
	 * 
	 * @return
	 * @throws ProtocolException
	 */
	public String name() throws ProtocolException;


	/**
	 * The domains this provider owns. The domains may contain wildcards in the first segment.
	 * 
	 * @return
	 * @throws ProtocolException
	 */
	public String[] domains() throws ProtocolException;


	/**
	 * An Iterator of {@link Link}s that are related to this provider. Use a {@link FilteredIterator} to retrieve only {@link Link}s with a specific relation
	 * type.
	 * 
	 * @return
	 * @throws ProtocolException
	 */
	public Iterator<Link> links() throws ProtocolException;


	/**
	 * An Iterator of {@link Services}s provider provides. Use a {@link FilteredIterator} to retrieve only {@link Services}s of a specific service type.
	 * 
	 * @return
	 * @throws ProtocolException
	 */
	public Iterator<Service> services() throws ProtocolException;
}
