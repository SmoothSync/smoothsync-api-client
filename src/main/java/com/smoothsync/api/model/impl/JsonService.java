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

import com.smoothsync.api.model.Service;
import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Iterator;


/**
 * A {@link Service} that takes the JSON representation of a service entry.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class JsonService implements Service
{

    private final JSONObject mResult;


    public JsonService(JSONObject result)
    {
        mResult = result;
    }


    @Override
    public String name()
    {
        return mResult.optString("name", "");
    }


    @Override
    public String serviceType()
    {
        return mResult.optString("service-type", "");

    }


    @Override
    public URI uri()
    {
        return URI.create(mResult.optString("uri", ""));
    }


    @Override
    public KeyStore keyStore()
    {
        JSONArray array = mResult.optJSONArray("com-smoothsync-certificates");
        if (array == null)
        {
            return null;
        }
        try
        {
            final CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            Iterator<Certificate> certificateIterator = new ConvertedIterator<>(new JsonStringArrayIterator(array),
                    new AbstractConvertedIterator.Converter<Certificate, String>()
                    {
                        @Override
                        public Certificate convert(String element)
                        {
                            try
                            {
                                return certificateFactory.generateCertificate(new ByteArrayInputStream(element.getBytes("UTF-8")));
                            }
                            catch (CertificateException e)
                            {
                                throw new IllegalArgumentException(String.format("Can't decode certificate %s", element), e);
                            }
                            catch (UnsupportedEncodingException e)
                            {
                                throw new RuntimeException("UTF-8 not supported by runtime", e);
                            }
                        }
                    });
            StringBuilder aliasBuilder = new StringBuilder("alias");
            int count = 0;
            while (certificateIterator.hasNext())
            {
                aliasBuilder.append(count++);
                keyStore.setCertificateEntry(aliasBuilder.toString(), certificateIterator.next());
                aliasBuilder.setLength(5);
            }
            return count == 0 ? null : keyStore;
        }
        catch (KeyStoreException | CertificateException | NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Initialize KeyStore", e);
        }
        catch (IOException e)
        {
            throw new RuntimeException("IOException without doing real I/O.", e);
        }
    }

}
