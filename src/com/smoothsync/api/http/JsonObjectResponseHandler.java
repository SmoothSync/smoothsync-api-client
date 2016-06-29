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

import org.dmfs.httpclient.HttpResponse;
import org.dmfs.httpclient.HttpResponseHandler;
import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;
import org.dmfs.httpclient.responsehandlers.StringResponseHandler;
import org.dmfs.httpclient.types.MediaType;
import org.dmfs.httpclient.types.StructuredMediaType;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * An {@link HttpResponseHandler} that parses the response as a JSON object and returns it.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class JsonObjectResponseHandler implements HttpResponseHandler<JSONObject>
{
	public final static JsonObjectResponseHandler INSTANCE = new JsonObjectResponseHandler();

	private final static MediaType APPLICATION_JSON = new StructuredMediaType("application", "json");


	@Override
	public JSONObject handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
	{
		if (!APPLICATION_JSON.equals(response.responseEntity().contentType()))
		{
			throw new ProtocolException(String.format("expected content type %s, but got content type %s", APPLICATION_JSON, response.responseEntity()
				.contentType()));
		}

		try
		{
			return new JSONObject(new StringResponseHandler("UTF-8").handleResponse(response));
		}
		catch (JSONException e)
		{
			throw new ProtocolException("Can't decode JSON response", e);
		}
	}

}
