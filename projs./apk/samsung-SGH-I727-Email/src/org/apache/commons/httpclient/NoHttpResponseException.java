package org.apache.commons.httpclient;

import java.io.IOException;
import org.apache.commons.httpclient.util.ExceptionUtil;

public class NoHttpResponseException extends IOException {

   public NoHttpResponseException() {}

   public NoHttpResponseException(String var1) {
      super(var1);
   }

   public NoHttpResponseException(String var1, Throwable var2) {
      super(var1);
      ExceptionUtil.initCause(this, var2);
   }
}
