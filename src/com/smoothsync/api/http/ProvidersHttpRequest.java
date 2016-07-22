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
import java.util.ArrayList;
import java.util.List;

import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.responsehandlers.FailResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smoothsync.api.model.Provider;
import com.smoothsync.api.model.impl.JsonProvider;


/**
 * An {@link HttpRequest} for all API requests that return a list of {@link Provider}s.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ProvidersHttpRequest extends AbstractApiGetRequest<List<Provider>>
{
	public final static ProvidersHttpRequest INSTANCE = new ProvidersHttpRequest();

	private final static HttpResponseHandler<List<Provider>> RESPONSE_HANDLER = new HttpResponseHandler<List<Provider>>()
	{
		@Override
		public List<Provider> handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
		{
			JSONObject data = JsonObjectResponseHandler.INSTANCE.handleResponse(response);
			try
			{
				JSONArray providers = data.getJSONArray("providers");

				List<Provider> result = new ArrayList<Provider>(providers.length());

				for (int i = 0, count = providers.length(); i < count; ++i)
				{
					result.add(new JsonProvider(providers.getJSONObject(i)));
				}
				return result;
			}
			catch (JSONException e)
			{
				throw new ProtocolException(String.format("can't parse response JSON '%s'", data.toString()), e);
			}
		}
	};


	@Override
	public HttpResponseHandler<List<Provider>> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
	{
		if (!HttpStatus.OK.equals(response.status()))
		{
			return FailResponseHandler.getInstance();
		}
		return RESPONSE_HANDLER;
	}

}
