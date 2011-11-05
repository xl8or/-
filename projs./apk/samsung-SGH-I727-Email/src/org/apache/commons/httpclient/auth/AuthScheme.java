package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.MalformedChallengeException;

public interface AuthScheme {

   String authenticate(Credentials var1, String var2, String var3) throws AuthenticationException;

   String authenticate(Credentials var1, HttpMethod var2) throws AuthenticationException;

   String getID();

   String getParameter(String var1);

   String getRealm();

   String getSchemeName();

   boolean isComplete();

   boolean isConnectionBased();

   void processChallenge(String var1) throws MalformedChallengeException;
}
