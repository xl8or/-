package org.apache.commons.httpclient;

import org.apache.commons.httpclient.HttpException;

public class ProtocolException extends HttpException {

   public ProtocolException() {}

   public ProtocolException(String var1) {
      super(var1);
   }

   public ProtocolException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
