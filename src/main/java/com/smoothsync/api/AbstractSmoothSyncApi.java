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
import org.dmfs.httpessentials.executors.useragent.Branded;
import org.dmfs.httpessentials.types.CommentedProduct;
import org.dmfs.jems.optional.Optional;
import org.dmfs.oauth2.client.OAuth2AccessToken;
import org.dmfs.oauth2.client.OAuth2Client;
import org.dmfs.oauth2.client.OAuth2Grant;
import org.dmfs.oauth2.client.OAuth2Scope;
import org.dmfs.oauth2.client.grants.ClientCredentialsGrant;
import org.dmfs.oauth2.client.http.decorators.BearerAuthenticatedRequest;
import org.dmfs.oauth2.client.scope.EmptyScope;
import org.dmfs.rfc5545.DateTime;

import java.io.IOException;
import java.net.URI;


/**
 * An abstract SmoothSync API.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public abstract class AbstractSmoothSyncApi implements SmoothSyncApi
{
    private final URI mBaseUri;
    private final HttpRequestExecutor mExecutor;
    private final OAuth2AccessToken mAccessToken;


    public AbstractSmoothSyncApi(final HttpRequestExecutor executor, final OAuth2Client client, final URI baseUri)
    {
        final HttpRequestExecutor brandedExecutor = new Branded(executor, new CommentedProduct(BuildConfig.NAME, BuildConfig.VERSION, "gzip"));
        mAccessToken = new LazyAccessToken(new ClientCredentialsGrant(client, EmptyScope.INSTANCE), brandedExecutor);
        mExecutor = new HttpRequestExecutor()
        {

            @Override
            public <T> T execute(URI uri, HttpRequest<T> request) throws IOException, ProtocolError, ProtocolException, RedirectionException,
                    UnexpectedStatusException
            {
                verifyUri(uri);
                return brandedExecutor.execute(uri, new BearerAuthenticatedRequest<T>(request, mAccessToken));
            }


            private void verifyUri(final URI uri)
            {
                if (!"https".equals(uri.getScheme()))
                {
                    throw new IllegalArgumentException(String.format("URI scheme must be 'https': %s", uri.toASCIIString()));
                }
                if (!mBaseUri.getAuthority().equals(uri.getAuthority()))
                {
                    throw new IllegalArgumentException(String.format("Host %s doesn't match SmoothSync API host %s", uri.getAuthority(),
                            mBaseUri.getAuthority()));
                }
            }
        };
        mBaseUri = baseUri;
    }


    @Override
    public final <T> T resultOf(SmoothSyncApiRequest<T> request) throws RedirectionException, UnexpectedStatusException, IOException, ProtocolError,
            ProtocolException
    {
        return request.result(mExecutor, mBaseUri);
    }


    /**
     * An access token that's not requested from the server until it's actually used.
     *
     * @author Marten Gajda <marten@dmfs.org>
     */
    private final static class LazyAccessToken implements OAuth2AccessToken
    {

        private final OAuth2Grant mGrant;
        private final HttpRequestExecutor mExecutor;
        private OAuth2AccessToken mAccessToken;


        public LazyAccessToken(OAuth2Grant grant, HttpRequestExecutor executor)
        {
            mGrant = grant;
            mExecutor = executor;
        }


        private OAuth2AccessToken oauth2AccessToken() throws ProtocolException
        {
            if (mAccessToken == null || DateTime.now().after(mAccessToken.expirationDate()))
            {
                try
                {
                    mAccessToken = mGrant.accessToken(mExecutor);
                }
                catch (IOException | ProtocolError e)
                {
                    throw new ProtocolException("Can't retrieve accesstoken.", e);
                }
            }
            return mAccessToken;
        }


        @Override
        public CharSequence accessToken() throws ProtocolException
        {
            return oauth2AccessToken().accessToken();
        }


        @Override
        public CharSequence tokenType() throws ProtocolException
        {
            return oauth2AccessToken().tokenType();
        }


        @Override
        public boolean hasRefreshToken()
        {
            try
            {
                return oauth2AccessToken().hasRefreshToken();
            }
            catch (ProtocolException e)
            {
                return false;
            }
        }


        @Override
        public CharSequence refreshToken() throws ProtocolException
        {
            return oauth2AccessToken().refreshToken();
        }


        @Override
        public DateTime expirationDate() throws ProtocolException
        {
            return oauth2AccessToken().expirationDate();
        }


        @Override
        public OAuth2Scope scope() throws ProtocolException
        {
            return oauth2AccessToken().scope();
        }


        @Override
        public Optional<CharSequence> extraParameter(String parameterName)
        {
            try
            {
                return oauth2AccessToken().extraParameter(parameterName);
            }
            catch (ProtocolException e)
            {
                throw new RuntimeException("Unexpected ProtocolException", e);
            }
        }

    }
}
