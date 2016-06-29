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

import java.io.IOException;
import java.net.URI;

import org.dmfs.httpclient.HttpRequest;
import org.dmfs.httpclient.HttpRequestExecutor;
import org.dmfs.httpclient.exceptions.ProtocolError;
import org.dmfs.httpclient.exceptions.ProtocolException;
import org.dmfs.httpclient.exceptions.RedirectionException;
import org.dmfs.httpclient.exceptions.UnexpectedStatusException;
import org.dmfs.oauth2.client.BasicOAuth2Client;
import org.dmfs.oauth2.client.OAuth2AccessToken;
import org.dmfs.oauth2.client.OAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.OAuth2AuthorizationRequest;
import org.dmfs.oauth2.client.OAuth2Client;
import org.dmfs.oauth2.client.OAuth2ClientCredentials;
import org.dmfs.rfc5545.Duration;


/**
 * An abstract client of the SmoothSync API.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class AbstractApiClient implements OAuth2Client
{
	private final OAuth2Client mClient;


	public AbstractApiClient(OAuth2AuthorizationProvider authProvider, OAuth2ClientCredentials credentials)
	{
		mClient = new BasicOAuth2Client(authProvider, credentials, null);
	}


	@Override
	public final OAuth2AccessToken accessToken(HttpRequest<OAuth2AccessToken> tokenRequest, HttpRequestExecutor executor) throws RedirectionException,
		UnexpectedStatusException, IOException, ProtocolError, ProtocolException
	{
		return mClient.accessToken(tokenRequest, executor);
	}


	@Override
	public final URI redirectUri()
	{
		throw new UnsupportedOperationException("SmoothSync API doesn't support grant types that use a redirect Uri.");
	}


	@Override
	public final URI authorizationUrl(OAuth2AuthorizationRequest authorizationRequest)
	{
		throw new UnsupportedOperationException("SmoothSync API doesn't support interactive grant types that.");
	}


	@Override
	public final String generatedRandomState()
	{
		return mClient.generatedRandomState();
	}


	@Override
	public final Duration defaultTokenTtl()
	{
		return mClient.defaultTokenTtl();
	}

}
