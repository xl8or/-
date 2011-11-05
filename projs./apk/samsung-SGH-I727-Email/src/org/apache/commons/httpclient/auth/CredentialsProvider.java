package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;

public interface CredentialsProvider {

   String PROVIDER = "http.authentication.credential-provider";


   Credentials getCredentials(AuthScheme var1, String var2, int var3, boolean var4) throws CredentialsNotAvailableException;
}
