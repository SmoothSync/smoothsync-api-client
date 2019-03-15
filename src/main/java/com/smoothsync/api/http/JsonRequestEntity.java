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

import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.types.MediaType;
import org.dmfs.httpessentials.types.StructuredMediaType;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Present;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * A {@link HttpRequestEntity} that contains a {@link JSONObject}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonRequestEntity implements HttpRequestEntity
{
    private final static Optional<MediaType> CONTENT_TYPE = new Present<>(new StructuredMediaType("application", "json"));

    private final JSONObject mData;
    private byte[] mContent;


    public JsonRequestEntity(JSONObject data)
    {
        mData = data;
    }


    @Override
    public Optional<MediaType> contentType()
    {
        return CONTENT_TYPE;
    }


    @Override
    public Optional<Long> contentLength()
    {
        return content().length >= 0 ? new Present<>((long) content().length) : absent();
    }


    @Override
    public void writeContent(OutputStream out) throws IOException
    {
        out.write(content());
    }


    private byte[] content()
    {
        try
        {
            if (mContent == null)
            {
                mContent = mData.toString().getBytes("UTF-8");
            }

            return mContent;
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Runtime doesn't support UTF-8 encoding");
        }
    }
}