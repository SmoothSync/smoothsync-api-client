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

package com.smoothsync.api.dev;

import java.net.URI;

import org.dmfs.httpclient.HttpRequestExecutor;

import com.smoothsync.api.AbstractSmoothSyncApi;


/**
 * A development API that might support upcoming features in an early version.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class DevelopmentApi extends AbstractSmoothSyncApi
{
	private final static URI API_URI = URI.create("https://dev.smoothsync.com/api/v1/");


	public DevelopmentApi(HttpRequestExecutor executor, DevelopmentApiClient apiClient)
	{
		super(executor, apiClient, API_URI);
	}

}
