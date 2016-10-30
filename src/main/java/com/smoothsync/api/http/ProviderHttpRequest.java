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

import com.smoothsync.api.model.Provider;
import com.smoothsync.api.model.impl.JsonProvider;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.responsehandlers.FailResponseHandler;
import org.json.JSONObject;

import java.io.IOException;


/**
 * An {@link HttpRequest} for all API requests that return a single provider.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ProviderHttpRequest extends AbstractApiGetRequest<Provider>
{
    public final static ProviderHttpRequest INSTANCE = new ProviderHttpRequest();

    private final static HttpResponseHandler<Provider> RESPONSE_HANDLER = new HttpResponseHandler<Provider>()
    {
        @Override
        public Provider handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
        {
            JSONObject data = JsonObjectResponseHandler.INSTANCE.handleResponse(response);
            return new JsonProvider(data);
        }
    };


    @Override
    public HttpResponseHandler<Provider> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        if (!HttpStatus.OK.equals(response.status()))
        {
            return FailResponseHandler.getInstance();
        }
        return RESPONSE_HANDLER;
    }

}
