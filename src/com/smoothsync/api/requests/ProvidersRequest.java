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

package com.smoothsync.api.requests;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.dmfs.httpclient.HttpRequestExecutor;
import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;
import org.dmfs.httpclient.exceptions.RedirectionException;
import org.dmfs.httpclient.exceptions.UnexpectedStatusException;

import com.smoothsync.api.SmoothSyncApiRequest;
import com.smoothsync.api.http.ProvidersHttpRequest;
import com.smoothsync.api.model.Provider;


/**
 * Retrieves all providers this client has access to.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class ProvidersRequest implements SmoothSyncApiRequest<List<Provider>>
{

	@Override
	public List<Provider> result(HttpRequestExecutor executor, URI baseUri) throws RedirectionException, UnexpectedStatusException, IOException,
		ProtocolError, ProtocolException
	{
		return executor.execute(baseUri.resolve("providers/?only_custom_domain=true"), ProvidersHttpRequest.INSTANCE);
	}
}
