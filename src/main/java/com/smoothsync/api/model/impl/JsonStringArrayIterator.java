/*
 * Copyright 2016 Marten Gajda <marten@dmfs.org>
 *
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
 */

package com.smoothsync.api.model.impl;

import org.dmfs.iterators.AbstractBaseIterator;
import org.json.JSONArray;

import java.util.NoSuchElementException;


/**
 * Iterates the Strings of a {@link JSONArray}.
 * <p/>
 * Note this assumes that the array contains only Strings! If an element is not a String calling {@link #next()} will return {@code null}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonStringArrayIterator extends AbstractBaseIterator<String>
{
    private final JSONArray mArray;
    private int mNext;


    public JsonStringArrayIterator(JSONArray array)
    {
        mArray = array;
    }


    @Override
    public boolean hasNext()
    {
        return mNext < mArray.length();
    }


    @Override
    public String next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No more elements to iterate");
        }
        String result = mArray.optString(mNext);
        ++mNext;
        return result;
    }
}
