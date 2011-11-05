package org.apache.commons.httpclient;

import org.apache.commons.httpclient.ConnectionPoolTimeoutException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public interface HttpConnectionManager {

   void closeIdleConnections(long var1);

   HttpConnection getConnection(HostConfiguration var1);

   HttpConnection getConnection(HostConfiguration var1, long var2) throws HttpException;

   HttpConnection getConnectionWithTimeout(HostConfiguration var1, long var2) throws ConnectionPoolTimeoutException;

   HttpConnectionManagerParams getParams();

   void releaseConnection(HttpConnection var1);

   void setParams(HttpConnectionManagerParams var1);
}
