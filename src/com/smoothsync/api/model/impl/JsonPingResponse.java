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

package com.smoothsync.api.model.impl;

import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.rfc5545.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import com.smoothsync.api.model.PingResponse;


/**
 * A {@link PingResponse} that takes a JSON object.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonPingResponse implements PingResponse
{
	private final JSONObject mJsonPingResponse;


	public JsonPingResponse(JSONObject jsonPingResponse)
	{
		mJsonPingResponse = jsonPingResponse;
	}


	@Override
	public DateTime firstContactDateTime() throws ProtocolException
	{
		{
			try
			{
				return DateTime.parse(mJsonPingResponse.getString("first-contact"));
			}
			catch (JSONException e)
			{
				throw new ProtocolException(String.format("could not load 'first-contact' from JSON\n%s", mJsonPingResponse.toString()), e);
			}
		}
	}


	@Override
	public DateTime sponsoredUntil() throws ProtocolException
	{
		try
		{
			return DateTime.parse(mJsonPingResponse.getString("sponsored-until"));
		}
		catch (JSONException e)
		{
			throw new ProtocolException(String.format("could not load 'sponsored-until' from JSON\n%s", mJsonPingResponse.toString()), e);
		}
	}


	@Override
	public String toString()
	{
		return mJsonPingResponse.toString();
	}

}
