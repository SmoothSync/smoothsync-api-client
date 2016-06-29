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

/**
 * The result of an auto-complete request.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public interface AutoCompleteResult
{
	/**
	 * The domain fragment that had been requested.
	 * 
	 * @return
	 */
	public String domainFragment();


	/**
	 * A set of domains (possibly containing wildcards) that the server matched to the requested domain fragment.
	 * 
	 * @return An array of Strings, never <code>null</code>, may be empty.
	 */
	public String[] autoComplete();
}
