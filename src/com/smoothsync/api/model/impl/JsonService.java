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

import java.net.URI;

import org.json.JSONObject;

import com.smoothsync.api.model.Service;


/**
 * A {@link Service} that takes the JSON representation of a service entry.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonService implements Service
{

	private final JSONObject mResult;


	public JsonService(JSONObject result)
	{
		mResult = result;
	}


	@Override
	public String name()
	{
		return mResult.optString("name", "");
	}


	@Override
	public String serviceType()
	{
		return mResult.optString("service-type", "");

	}


	@Override
	public URI uri()
	{
		return URI.create(mResult.optString("uri", ""));
	}

}
