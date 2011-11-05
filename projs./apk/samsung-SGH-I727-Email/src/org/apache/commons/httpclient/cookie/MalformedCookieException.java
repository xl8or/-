package org.apache.commons.httpclient.cookie;

import org.apache.commons.httpclient.ProtocolException;

public class MalformedCookieException extends ProtocolException {

   public MalformedCookieException() {}

   public MalformedCookieException(String var1) {
      super(var1);
   }

   public MalformedCookieException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
