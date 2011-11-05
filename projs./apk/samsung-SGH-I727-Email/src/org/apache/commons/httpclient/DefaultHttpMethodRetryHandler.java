package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.NoHttpResponseException;

public class DefaultHttpMethodRetryHandler implements HttpMethodRetryHandler {

   private static Class SSL_HANDSHAKE_EXCEPTION = null;
   private boolean requestSentRetryEnabled;
   private int retryCount;


   static {
      try {
         SSL_HANDSHAKE_EXCEPTION = Class.forName("javax.net.ssl.SSLHandshakeException");
      } catch (ClassNotFoundException var1) {
         ;
      }
   }

   public DefaultHttpMethodRetryHandler() {
      this(3, (boolean)0);
   }

   public DefaultHttpMethodRetryHandler(int var1, boolean var2) {
      this.retryCount = var1;
      this.requestSentRetryEnabled = var2;
   }

   public int getRetryCount() {
      return this.retryCount;
   }

   public boolean isRequestSentRetryEnabled() {
      return this.requestSentRetryEnabled;
   }

   public boolean retryMethod(HttpMethod var1, IOException var2, int var3) {
      if(var1 == null) {
         throw new IllegalArgumentException("HTTP method may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Exception parameter may not be null");
      } else {
         boolean var4;
         if(var1 instanceof HttpMethodBase && ((HttpMethodBase)var1).isAborted()) {
            var4 = false;
         } else {
            int var5 = this.retryCount;
            if(var3 > var5) {
               var4 = false;
            } else if(var2 instanceof NoHttpResponseException) {
               var4 = true;
            } else if(var2 instanceof InterruptedIOException) {
               var4 = false;
            } else if(var2 instanceof UnknownHostException) {
               var4 = false;
            } else if(var2 instanceof NoRouteToHostException) {
               var4 = false;
            } else if(SSL_HANDSHAKE_EXCEPTION != null && SSL_HANDSHAKE_EXCEPTION.isInstance(var2)) {
               var4 = false;
            } else if(var1.isRequestSent() && !this.requestSentRetryEnabled) {
               var4 = false;
            } else {
               var4 = true;
            }
         }

         return var4;
      }
   }
}
