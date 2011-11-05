package org.apache.commons.httpclient;

import org.apache.commons.httpclient.RedirectException;

public class CircularRedirectException extends RedirectException {

   public CircularRedirectException() {}

   public CircularRedirectException(String var1) {
      super(var1);
   }

   public CircularRedirectException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
