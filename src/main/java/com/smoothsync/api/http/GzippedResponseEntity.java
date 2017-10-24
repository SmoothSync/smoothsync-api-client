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

package com.smoothsync.api.http;

import org.dmfs.httpessentials.client.HttpResponseEntity;
import org.dmfs.httpessentials.types.MediaType;
import org.dmfs.optional.Optional;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import static org.dmfs.optional.Absent.absent;


/**
 * An {@link HttpResponseEntity} that unzips the transferred content.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public class GzippedResponseEntity implements HttpResponseEntity
{
    private final HttpResponseEntity mDecoratedResponseEntity;


    public GzippedResponseEntity(final HttpResponseEntity decoratedResponseEntity)
    {
        mDecoratedResponseEntity = decoratedResponseEntity;
    }


    @Override
    public Optional<MediaType> contentType()
    {
        // return the original content type
        return mDecoratedResponseEntity.contentType();
    }


    @Override
    public Optional<Long> contentLength()
    {
        // we don't know the actual content-length
        return absent();
    }


    @Override
    public InputStream contentStream() throws IOException
    {
        return new GZIPInputStream(mDecoratedResponseEntity.contentStream());
    }
}
