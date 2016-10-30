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

package com.smoothsync.api.model.impl;

import com.smoothsync.api.model.Instance;
import com.smoothsync.api.model.Provider;
import net.iharder.Base64;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


/**
 * A concrete {@link Instance}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class BasicInstance implements Instance
{
    private final static int ITERATIONS = 1193;
    private final static int KEY_LENGHT = 256; // bits
    private final static String ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * We use a fixed static salt to create the instance hash value. That's important to get the same hash for every instance of the same account.
     */
    private final static byte[] SALT = new byte[] { 0x53, 0x6d, 0x6f, 0x6f, 0x74, 0x68, 0x53, 0x79, 0x6e, 0x63 };

    private final Provider mProvider;
    private final String mClientIdentifier;
    private final String mAccountIdentifier;
    private final String mReferrer;


    public BasicInstance(Provider provider, String clientCredentials, String accountIdentifier)
    {
        this(provider, clientCredentials, accountIdentifier, null);
    }


    public BasicInstance(Provider provider, String clientIdentifier, String accountIdentifier, String referrer)
    {
        mProvider = provider;
        mClientIdentifier = clientIdentifier;
        mAccountIdentifier = accountIdentifier;
        mReferrer = referrer;
    }


    private static byte[] hashIdentifier(String instance) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new PBEKeySpec(instance.toCharArray(), SALT, ITERATIONS, KEY_LENGHT)).getEncoded();
    }


    @Override
    public JSONObject toJson()
    {
        try
        {
            JSONObject result = new JSONObject();
            result.put("provider-id", mProvider.id());
            if (mReferrer != null)
            {
                result.put("referrer", mReferrer);
            }
            // we hash the account identifier before sending it to the API to maintain the user's privacy as much as possible
            result.put("instance-id", Base64.encodeBytes(hashIdentifier(String.format("%s%s%s", mProvider.id(), mClientIdentifier, mAccountIdentifier))));
            return result;
        }
        catch (NoSuchAlgorithmException | JSONException | ProtocolException | InvalidKeySpecException e)
        {
            throw new RuntimeException("Can't create Instance JSON object", e);
        }
    }

}
