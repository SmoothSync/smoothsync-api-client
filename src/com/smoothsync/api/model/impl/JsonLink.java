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
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.dmfs.httpclient.parameters.Parameter;
import org.dmfs.httpclient.parameters.ParameterType;
import org.dmfs.httpclient.types.Link;
import org.dmfs.httpclient.types.MediaType;
import org.dmfs.httpclient.types.StringMediaType;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Implementation of a {@link Link}.
 * 
 * @see <a href="http://tools.ietf.org/html/rfc7033#section-4.4.4">RFC 7033, section 4.4.4</a>
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class JsonLink implements Link
{
	private final JSONObject mJsonObject;


	/**
	 * Creates a Link from a given {@link JSONObject} that contains the information of the link.
	 * 
	 * @param jsonObject
	 *            The {@link JSONObject} that contains the link data.
	 */
	public JsonLink(JSONObject jsonObject)
	{
		mJsonObject = jsonObject;
	}


	@Override
	public <T> Parameter<T> firstParameter(ParameterType<T> parameterType, T defaultValue)
	{
		return parameterType.entity(defaultValue);
	}


	@Override
	public <T> Iterator<Parameter<T>> parameters(ParameterType<T> parameterType)
	{
		return Collections.emptyIterator();
	}


	@Override
	public <T> boolean hasParameter(ParameterType<T> parameterType)
	{
		return false;
	}


	@Override
	public URI target()
	{
		if (!mJsonObject.has("href"))
		{
			return null;
		}
		try
		{
			return URI.create(mJsonObject.getString("href"));
		}
		catch (JSONException e)
		{
			throw new IllegalArgumentException(String.format("Can't read href from %s", mJsonObject.toString()), e);
		}
	}


	@Override
	public URI context(URI defaultContext)
	{
		return defaultContext;
	}


	@Override
	public Set<Locale> languages()
	{
		return Collections.emptySet();
	}


	@Override
	public String title()
	{
		if (!mJsonObject.has("titles"))
		{
			return null;
		}

		JSONObject titles;
		try
		{
			titles = mJsonObject.getJSONObject("titles");
			@SuppressWarnings("unchecked")
			Iterator<String> keyIterator = titles.keys();
			String defaultLang = Locale.getDefault().getLanguage();
			while (keyIterator.hasNext())
			{
				String key = keyIterator.next();
				if (defaultLang.equals(Locale.forLanguageTag(key).getLanguage()))
				{
					return titles.getString(key);
				}
			}
		}
		catch (JSONException e)
		{
			throw new IllegalArgumentException(String.format("Can't read titles from %s", mJsonObject.toString()), e);
		}

		return titles.optString("und", null);
	}


	@Override
	public MediaType mediaType()
	{
		if (!mJsonObject.has("type"))
		{
			return null;
		}
		try
		{
			return new StringMediaType(mJsonObject.getString("type"));
		}
		catch (JSONException e)
		{
			throw new IllegalArgumentException(String.format("Can't read type from %s", mJsonObject.toString()), e);
		}
	}


	@Override
	public Set<String> relationTypes()
	{
		if (!mJsonObject.has("rel"))
		{
			return null;
		}
		try
		{
			return Collections.singleton(mJsonObject.getString("rel"));
		}
		catch (JSONException e)
		{
			throw new IllegalArgumentException(String.format("Can't read rel from %s", mJsonObject.toString()), e);
		}
	}


	@Override
	public Set<String> reverseRelationTypes()
	{
		return Collections.emptySet();
	}

}
