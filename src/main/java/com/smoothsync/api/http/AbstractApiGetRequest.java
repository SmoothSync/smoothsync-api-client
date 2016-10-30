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

package com.smoothsync.api.http;

import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.entities.EmptyHttpRequestEntity;
import org.dmfs.httpessentials.headers.EmptyHeaders;
import org.dmfs.httpessentials.headers.Headers;


/**
 * An abstract base class for most API request types.
 *
 * @param <T>
 *         The type of the expected response.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class AbstractApiGetRequest<T> implements HttpRequest<T>
{
    @Override
    public final HttpMethod method()
    {
        // all requests are GET requests
        return HttpMethod.GET;
    }


    @Override
    public final Headers headers()
    {
        // we don't use any headers
        return EmptyHeaders.INSTANCE;
    }


    @Override
    public final HttpRequestEntity requestEntity()
    {
        // get requests don't contain a request payload
        return EmptyHttpRequestEntity.INSTANCE;
    }

}
