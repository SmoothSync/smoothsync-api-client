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

import java.io.IOException;

import org.dmfs.httpclient.HttpMethod;
import org.dmfs.httpclient.HttpRequest;
import org.dmfs.httpclient.HttpRequestEntity;
import org.dmfs.httpclient.HttpResponse;
import org.dmfs.httpclient.HttpResponseHandler;
import org.dmfs.httpclient.HttpStatus;
import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;
import org.dmfs.httpclient.headers.EmptyHeaders;
import org.dmfs.httpclient.headers.Headers;
import org.dmfs.httpclient.responsehandlers.FailResponseHandler;

import com.smoothsync.api.model.Instance;
import com.smoothsync.api.model.PingResponse;
import com.smoothsync.api.model.impl.JsonPingResponse;


/**
 * Implements a ping request. Ping requests should be sent once a month to inform the service that this installation is still active.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class PingHttpRequest implements HttpRequest<PingResponse>
{
	private final static HttpResponseHandler<PingResponse> RESPONSE_HANDLER = new HttpResponseHandler<PingResponse>()
	{
		@Override
		public PingResponse handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
		{
			return new JsonPingResponse(JsonObjectResponseHandler.INSTANCE.handleResponse(response));
		}
	};

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
			return RESPONSE_HANDLER;
		}
		return FailResponseHandler.getInstance();
	}

}
