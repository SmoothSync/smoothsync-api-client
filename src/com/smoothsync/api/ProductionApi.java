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

package com.smoothsync.api;

import java.net.URI;

import org.dmfs.httpessentials.client.HttpRequestExecutor;


/**
 * Represents the SmoothSync production API.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ProductionApi extends AbstractSmoothSyncApi
{
	private final static URI API_URI = URI.create("https://api.smoothsync.com/api/v1/");


	public ProductionApi(final HttpRequestExecutor executor, final ProductionApiClient apiClient)
	{
		super(executor, apiClient, API_URI);
	}
}
