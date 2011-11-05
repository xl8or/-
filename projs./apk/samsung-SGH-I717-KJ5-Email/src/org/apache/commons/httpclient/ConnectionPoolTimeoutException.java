package org.apache.commons.httpclient;

import org.apache.commons.httpclient.ConnectTimeoutException;

public class ConnectionPoolTimeoutException extends ConnectTimeoutException {

   public ConnectionPoolTimeoutException() {}

   public ConnectionPoolTimeoutException(String var1) {
      super(var1);
   }

   public ConnectionPoolTimeoutException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
