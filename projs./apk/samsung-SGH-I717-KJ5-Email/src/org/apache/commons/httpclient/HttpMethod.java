package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.auth.AuthState;
import org.apache.commons.httpclient.params.HttpMethodParams;

public interface HttpMethod {

   void abort();

   void addRequestHeader(String var1, String var2);

   void addRequestHeader(Header var1);

   void addResponseFooter(Header var1);

   int execute(HttpState var1, HttpConnection var2) throws HttpException, IOException;

   boolean getDoAuthentication();

   boolean getFollowRedirects();

   AuthState getHostAuthState();

   HostConfiguration getHostConfiguration();

   String getName();

   HttpMethodParams getParams();

   String getPath();

   AuthState getProxyAuthState();

   String getQueryString();

   Header getRequestHeader(String var1);

   Header[] getRequestHeaders();

   Header[] getRequestHeaders(String var1);

   byte[] getResponseBody() throws IOException;

   InputStream getResponseBodyAsStream() throws IOException;

   String getResponseBodyAsString() throws IOException;

   Header getResponseFooter(String var1);

   Header[] getResponseFooters();

   Header getResponseHeader(String var1);

   Header[] getResponseHeaders();

   Header[] getResponseHeaders(String var1);

   int getStatusCode();

   StatusLine getStatusLine();

   String getStatusText();

   URI getURI() throws URIException;

   boolean hasBeenUsed();

   boolean isRequestSent();

   boolean isStrictMode();

   void recycle();

   void releaseConnection();

   void removeRequestHeader(String var1);

   void removeRequestHeader(Header var1);

   void setDoAuthentication(boolean var1);

   void setFollowRedirects(boolean var1);

   void setParams(HttpMethodParams var1);

   void setPath(String var1);

   void setQueryString(String var1);

   void setQueryString(NameValuePair[] var1);

   void setRequestHeader(String var1, String var2);

   void setRequestHeader(Header var1);

   void setStrictMode(boolean var1);

   void setURI(URI var1) throws URIException;

   boolean validate();
}
