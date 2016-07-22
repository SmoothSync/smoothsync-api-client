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

import org.dmfs.httpessentials.callbacks.FollowSecureRedirectCallback;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.exceptions.RedirectionException;
import org.dmfs.httpessentials.exceptions.UnexpectedStatusException;

import com.smoothsync.api.SmoothSyncApiRequest;
import com.smoothsync.api.http.AutoCompleteHttpRequest;
import com.smoothsync.api.model.AutoCompleteResult;


/**
 * A request to get suggestions on how to complete a given domain fragment.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class AutoComplete implements SmoothSyncApiRequest<AutoCompleteResult>
{
	public final String mDomainFragment;


	public AutoComplete(String domainFragment)
	{
		mDomainFragment = domainFragment;
	}


	@Override
	public AutoCompleteResult result(HttpRequestExecutor executor, URI baseUri) throws RedirectionException, UnexpectedStatusException, IOException,
		ProtocolError, ProtocolException
	{
		return executor.execute(baseUri.resolve("autocomplete?domain=" + mDomainFragment), new AutoCompleteHttpRequest(mDomainFragment),
			FollowSecureRedirectCallback.getInstance());
	}
}
