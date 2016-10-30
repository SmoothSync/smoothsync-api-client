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

import com.smoothsync.api.model.AutoCompleteResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Implementation of {@link AutoCompleteResult}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonAutoCompleteResult implements AutoCompleteResult
{

    private final JSONObject mResult;
    private final String mQueryDomainFragment;


    public JsonAutoCompleteResult(String queryDomainFragment, JSONObject result)
    {
        mQueryDomainFragment = queryDomainFragment;
        mResult = result;
    }


    @Override
    public String domainFragment()
    {
        return mQueryDomainFragment;
    }


    @Override
    public String[] autoComplete()
    {
        // convert the JSONArray to an array of Strings
        JSONArray autoComplete = mResult.optJSONArray("auto-complete");
        String[] result = new String[autoComplete.length()];
        for (int i = 0, count = autoComplete.length(); i < count; ++i)
        {
            try
            {
                result[i] = autoComplete.getString(i);
            }
            catch (JSONException e)
            {
                throw new IllegalArgumentException(String.format("can't read element %d from JSONArray '%s'", i, autoComplete.toString()), e);
            }
        }
        return result;
    }

}
