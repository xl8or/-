package org.apache.commons.httpclient;

import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpRecoverableException;
import org.apache.commons.httpclient.MethodRetryHandler;

public class DefaultMethodRetryHandler implements MethodRetryHandler {

   private boolean requestSentRetryEnabled = 0;
   private int retryCount = 3;


   public DefaultMethodRetryHandler() {}

   public int getRetryCount() {
      return this.retryCount;
   }

   public boolean isRequestSentRetryEnabled() {
      return this.requestSentRetryEnabled;
   }

   public boolean retryMethod(HttpMethod var1, HttpConnection var2, HttpRecoverableException var3, int var4, boolean var5) {
      boolean var7;
      if(!var5 || this.requestSentRetryEnabled) {
         int var6 = this.retryCount;
         if(var4 <= var6) {
            var7 = true;
            return var7;
         }
      }

      var7 = false;
      return var7;
   }

   public void setRequestSentRetryEnabled(boolean var1) {
      this.requestSentRetryEnabled = var1;
   }

   public void setRetryCount(int var1) {
      this.retryCount = var1;
   }
}
