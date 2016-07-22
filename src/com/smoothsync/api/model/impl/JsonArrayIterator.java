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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Iterates the objects of a {@link JSONArray}.
 * <p />
 * Note this assumes that the array contains only objects! If an element is not a {@link JSONObject} calling {@link #next()} will fail with an
 * {@link IllegalArgumentException}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonArrayIterator implements Iterator<JSONObject>
{
	private final JSONArray mArray;
	private int mNext;


	public JsonArrayIterator(JSONArray array)
	{
		mArray = array;
	}


	@Override
	public boolean hasNext()
	{
		return mNext < mArray.length();
	}


	@Override
	public JSONObject next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException("No more elements to iterate");
		}
		try
		{
			JSONObject result = mArray.getJSONObject(mNext);
			++mNext;
			return result;
		}
		catch (JSONException e)
		{
			throw new IllegalArgumentException(String.format("Can't retieve element number %d of '%s'", mNext, mArray.toString()), e);
		}
	}


	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("Removing elements is not supported by this iterator.");
	}

}
