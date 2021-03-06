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

import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;

import java.io.IOException;
import java.net.URI;


/**
 * Interface of a request to the SmoothSync API.
 *
 * @param <T>
 *         The type of the expected response.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public interface SmoothSyncApiRequest<T>
{
    /**
     * Returns the result of this request.
     *
     * @param executor
     *         An {@link HttpRequestExecutor} to execute the request.
     * @param baseUri
     *         The base {@link URI} of the API.
     *
     * @return The result.
     *
     * @throws IOException
     * @throws ProtocolError
     * @throws ProtocolException
     */
    T result(HttpRequestExecutor executor, URI baseUri) throws IOException, ProtocolError, ProtocolException;
}
