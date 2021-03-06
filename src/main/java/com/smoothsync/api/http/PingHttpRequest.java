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

import com.smoothsync.api.model.Instance;
import com.smoothsync.api.model.PingResponse;
import com.smoothsync.api.model.Provider;
import com.smoothsync.api.model.impl.JsonPingResponse;
import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.EmptyHeaders;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.responsehandlers.FailResponseHandler;

import java.io.IOException;


/**
 * Implements a ping request. Ping requests should be sent once a month to inform the service that this installation is still active.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class PingHttpRequest implements HttpRequest<PingResponse>
{
    private final Instance mInstance;


    public PingHttpRequest(Instance instance)
    {
        mInstance = instance;
    }


    @Override
    public HttpMethod method()
    {
        return HttpMethod.POST;
    }


    @Override
    public Headers headers()
    {
        return EmptyHeaders.INSTANCE;
    }


    @Override
    public HttpRequestEntity requestEntity()
    {
        return new JsonRequestEntity(mInstance.toJson());
    }


    @Override
    public HttpResponseHandler<PingResponse> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        if (HttpStatus.OK.equals(response.status()))
        {
            return new PingResponseHttpResponseHandler(mInstance.provider());
        }
        return FailResponseHandler.getInstance();
    }


    private final static class PingResponseHttpResponseHandler implements HttpResponseHandler<PingResponse>
    {
        private final Provider mProvider;


        private PingResponseHttpResponseHandler(Provider provider)
        {
            mProvider = provider;
        }


        @Override
        public PingResponse handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
        {
            return new JsonPingResponse(JsonObjectResponseHandler.INSTANCE.handleResponse(response), mProvider);
        }
    }
}
