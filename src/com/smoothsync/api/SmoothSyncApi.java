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

package com.smoothsync.api;

import java.io.IOException;

import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;


/**
 * Interface of the SmoothSync API.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public interface SmoothSyncApi
{
	/**
	 * Executes the given SmoothSyncApiRequest against the SmoothSync API and returns the result.
	 * 
	 * @param request
	 *            A {@link SmoothSyncApiRequest} to execute.
	 * @return The result of the request.
	 * @throws IOException
	 * @throws ProtocolError
	 * @throws ProtocolException
	 */
	public <T> T resultOf(SmoothSyncApiRequest<T> request) throws IOException, ProtocolError, ProtocolException;
}
