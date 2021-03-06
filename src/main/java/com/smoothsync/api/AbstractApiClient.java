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

import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.exceptions.RedirectionException;
import org.dmfs.httpessentials.exceptions.UnexpectedStatusException;
import org.dmfs.oauth2.client.BasicOAuth2Client;
import org.dmfs.oauth2.client.OAuth2AccessToken;
import org.dmfs.oauth2.client.OAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.OAuth2AuthorizationRequest;
import org.dmfs.oauth2.client.OAuth2Client;
import org.dmfs.oauth2.client.OAuth2ClientCredentials;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc5545.Duration;

import java.io.IOException;
import java.net.URI;


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
    public final Uri redirectUri()
    {
        throw new UnsupportedOperationException("SmoothSync API doesn't support grant types that use a redirect Uri.");
    }


    @Override
    public final URI authorizationUrl(OAuth2AuthorizationRequest authorizationRequest)
    {
        throw new UnsupportedOperationException("SmoothSync API doesn't support interactive grant types that.");
    }


    @Override
    public CharSequence randomChars()
    {
        return mClient.randomChars();
    }


    @Override
    public final Duration defaultTokenTtl()
    {
        return mClient.defaultTokenTtl();
    }

}
