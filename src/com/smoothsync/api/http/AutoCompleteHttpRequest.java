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

import org.dmfs.httpclient.HttpRequest;
import org.dmfs.httpclient.HttpResponse;
import org.dmfs.httpclient.HttpResponseHandler;
import org.dmfs.httpclient.HttpStatus;
import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;
import org.dmfs.httpclient.responsehandlers.FailResponseHandler;

import com.smoothsync.api.model.AutoCompleteResult;
import com.smoothsync.api.requests.AutoCompleteRequest;


/**
 * The {@link HttpRequest} of an {@link AutoCompleteRequest}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class AutoCompleteHttpRequest extends AbstractApiGetRequest<AutoCompleteResult>
{
	private final String mDomainFragment;


	public AutoCompleteHttpRequest(String domainFragment)
	{
		mDomainFragment = domainFragment;
	}


	@Override
	public HttpResponseHandler<AutoCompleteResult> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
	{
		if (!response.status().equals(HttpStatus.OK))
		{
			return FailResponseHandler.getInstance();
		}
		return new AutoCompleteResponseHandler(mDomainFragment);
	}

}
