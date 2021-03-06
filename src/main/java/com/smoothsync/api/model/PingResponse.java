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

package com.smoothsync.api.model;

import com.smoothsync.api.requests.Ping;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.rfc5545.DateTime;


/**
 * The response to a {@link Ping}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public interface PingResponse
{
    DateTime firstContactDateTime() throws ProtocolException;

    DateTime lastContactDateTime() throws ProtocolException;

    DateTime sponsoredUntil() throws ProtocolException;

    /**
     * The provider this response belongs to.
     *
     * @return A {@link Provider}.
     *
     * @throws ProtocolException
     */
    Provider provider() throws ProtocolException;
}
