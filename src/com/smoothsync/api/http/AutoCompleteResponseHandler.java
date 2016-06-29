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

import java.io.IOException;

import org.dmfs.httpclient.HttpResponse;
import org.dmfs.httpclient.HttpResponseHandler;
import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;

import com.smoothsync.api.model.AutoCompleteResult;
import com.smoothsync.api.model.impl.JsonAutoCompleteResult;


/**
 * An {@link HttpResponseHandler} to handle results of {@link AutoCompleteHttpRequest}s.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class AutoCompleteResponseHandler implements HttpResponseHandler<AutoCompleteResult>
{

	private final String mDomainFragment;


	public AutoCompleteResponseHandler(String domainFragment)
	{
		mDomainFragment = domainFragment;
	}


	@Override
	public AutoCompleteResult handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
	{
		return new JsonAutoCompleteResult(mDomainFragment, JsonObjectResponseHandler.INSTANCE.handleResponse(response));
	}

}
