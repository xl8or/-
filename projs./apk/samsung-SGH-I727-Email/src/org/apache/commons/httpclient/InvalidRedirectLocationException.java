package org.apache.commons.httpclient;

import org.apache.commons.httpclient.RedirectException;

public class InvalidRedirectLocationException extends RedirectException {

   private final String location;


   public InvalidRedirectLocationException(String var1, String var2) {
      super(var1);
      this.location = var2;
   }

   public InvalidRedirectLocationException(String var1, String var2, Throwable var3) {
      super(var1, var3);
      this.location = var2;
   }

   public String getLocation() {
      return this.location;
   }
}
