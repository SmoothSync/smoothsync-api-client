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

import com.smoothsync.api.model.Provider;
import com.smoothsync.api.model.Service;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.iterator.decorators.Mapped;
import org.dmfs.rfc5545.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Iterator;


/**
 * A {@link Provider} implementation.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonProvider implements Provider
{

    private final JSONObject mResult;


    public JsonProvider(JSONObject result)
    {
        mResult = result;
    }


    @Override
    public String id()
    {
        return mResult.optString("id", "");
    }


    @Override
    public String name()
    {
        return mResult.optString("name", "");
    }


    @Override
    public String[] domains()
    {
        JSONArray domains = mResult.optJSONArray("domains");
        String[] result = new String[domains.length()];
        for (int i = 0, count = domains.length(); i < count; ++i)
        {
            try
            {
                result[i] = domains.getString(i);
            }
            catch (JSONException e)
            {
                throw new RuntimeException(String.format("can't read value %d from JSONArray '%s'", i, domains.toString()), e);
            }
        }
        return result;
    }


    @Override
    public Iterator<Link> links() throws ProtocolException
    {
        return iterate("links", JsonLink::new);

    }


    @Override
    public Iterator<Service> services() throws ProtocolException
    {
        return iterate("services", JsonService::new);
    }


    @Override
    public DateTime lastModified()
    {
        return DateTime.parse(mResult.optString("last-modified").replaceAll("[-:]", ""));
    }


    private <T> Iterator<T> iterate(String field, Function<JSONObject, T> function) throws ProtocolException
    {
        if (!mResult.has(field))
        {
            return Collections.emptyIterator();
        }

        try
        {
            return new Mapped<>(function, new JsonObjectArrayIterator(mResult.getJSONArray(field)));
        }
        catch (JSONException e)
        {
            throw new ProtocolException(String.format("can't read JSONArray '%s' from '%s'", field, mResult.toString()), e);
        }
    }
}
