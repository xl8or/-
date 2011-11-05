package org.apache.commons.httpclient;

import org.apache.commons.httpclient.ProtocolException;

public class RedirectException extends ProtocolException {

   public RedirectException() {}

   public RedirectException(String var1) {
      super(var1);
   }

   public RedirectException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
