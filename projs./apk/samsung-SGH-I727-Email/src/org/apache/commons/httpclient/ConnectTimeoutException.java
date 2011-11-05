package org.apache.commons.httpclient;

import java.io.InterruptedIOException;
import org.apache.commons.httpclient.util.ExceptionUtil;

public class ConnectTimeoutException extends InterruptedIOException {

   public ConnectTimeoutException() {}

   public ConnectTimeoutException(String var1) {
      super(var1);
   }

   public ConnectTimeoutException(String var1, Throwable var2) {
      super(var1);
      ExceptionUtil.initCause(this, var2);
   }
}
