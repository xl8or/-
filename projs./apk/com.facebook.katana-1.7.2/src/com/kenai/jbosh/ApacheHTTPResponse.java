package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.BOSHClientConfig;
import com.kenai.jbosh.BOSHException;
import com.kenai.jbosh.CMSessionParams;
import com.kenai.jbosh.GZIPCodec;
import com.kenai.jbosh.HTTPResponse;
import com.kenai.jbosh.ZLIBCodec;
import java.util.concurrent.locks.Lock;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HttpContext;

final class ApacheHTTPResponse implements HTTPResponse {

   private static final String ACCEPT_ENCODING = "Accept-Encoding";
   private static final String ACCEPT_ENCODING_VAL;
   private static final String CHARSET = "UTF-8";
   private static final String CONTENT_TYPE = "text/xml; charset=utf-8";
   private AbstractBody body;
   private final HttpClient client;
   private final HttpContext context;
   private final Lock lock;
   private final HttpPost post;
   private boolean sent;
   private int statusCode;
   private BOSHException toThrow;


   static {
      StringBuilder var0 = new StringBuilder();
      String var1 = ZLIBCodec.getID();
      StringBuilder var2 = var0.append(var1).append(", ");
      String var3 = GZIPCodec.getID();
      ACCEPT_ENCODING_VAL = var2.append(var3).toString();
   }

   ApacheHTTPResponse(HttpClient param1, BOSHClientConfig param2, CMSessionParams param3, AbstractBody param4) {
      // $FF: Couldn't be decompiled
   }

   private void awaitResponse() throws BOSHException {
      // $FF: Couldn't be decompiled
   }

   public void abort() {
      if(this.post != null) {
         this.post.abort();
         BOSHException var1 = new BOSHException("HTTP request aborted");
         this.toThrow = var1;
      }
   }

   public AbstractBody getBody() throws InterruptedException, BOSHException {
      if(this.toThrow != null) {
         throw this.toThrow;
      } else {
         this.lock.lock();

         try {
            if(!this.sent) {
               this.awaitResponse();
            }
         } finally {
            this.lock.unlock();
         }

         return this.body;
      }
   }

   public int getHTTPStatus() throws InterruptedException, BOSHException {
      if(this.toThrow != null) {
         throw this.toThrow;
      } else {
         this.lock.lock();

         try {
            if(!this.sent) {
               this.awaitResponse();
            }
         } finally {
            this.lock.unlock();
         }

         return this.statusCode;
      }
   }
}
