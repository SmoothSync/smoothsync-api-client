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

import java.net.URI;


/**
 * The interface of a service.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public interface Service
{
	/**
	 * A user readable (possibly localized) name of a service.
	 * 
	 * @return
	 */
	public String name();


	/**
	 * The type of the service.
	 * 
	 * @return A service type token or URI.
	 */
	public String serviceType();


	/**
	 * The (base-){@link URI} of the service.
	 * 
	 * @return A {@link URI}.
	 */
	public URI uri();
}
