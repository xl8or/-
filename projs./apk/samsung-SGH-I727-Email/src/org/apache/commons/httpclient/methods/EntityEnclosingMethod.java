package org.apache.commons.httpclient.methods;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.ChunkedOutputStream;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.ProtocolException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.ExpectContinueMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class EntityEnclosingMethod extends ExpectContinueMethod {

   public static final long CONTENT_LENGTH_AUTO = 254L;
   public static final long CONTENT_LENGTH_CHUNKED = 255L;
   private static final Log LOG = LogFactory.getLog(EntityEnclosingMethod.class);
   private boolean chunked = 0;
   private int repeatCount = 0;
   private long requestContentLength = 65534L;
   private RequestEntity requestEntity;
   private InputStream requestStream = null;
   private String requestString = null;


   public EntityEnclosingMethod() {
      this.setFollowRedirects((boolean)0);
   }

   public EntityEnclosingMethod(String var1) {
      super(var1);
      this.setFollowRedirects((boolean)0);
   }

   protected void addContentLengthRequestHeader(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter EntityEnclosingMethod.addContentLengthRequestHeader(HttpState, HttpConnection)");
      if(this.getRequestHeader("content-length") == null) {
         if(this.getRequestHeader("Transfer-Encoding") == null) {
            long var3 = this.getRequestContentLength();
            if(var3 < 0L) {
               HttpVersion var5 = this.getEffectiveVersion();
               HttpVersion var6 = HttpVersion.HTTP_1_1;
               if(var5.greaterEquals(var6)) {
                  this.addRequestHeader("Transfer-Encoding", "chunked");
               } else {
                  StringBuilder var7 = new StringBuilder();
                  HttpVersion var8 = this.getEffectiveVersion();
                  String var9 = var7.append(var8).append(" does not support chunk encoding").toString();
                  throw new ProtocolException(var9);
               }
            } else {
               String var10 = String.valueOf(var3);
               this.addRequestHeader("Content-Length", var10);
            }
         }
      }
   }

   protected void addRequestHeaders(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter EntityEnclosingMethod.addRequestHeaders(HttpState, HttpConnection)");
      super.addRequestHeaders(var1, var2);
      this.addContentLengthRequestHeader(var1, var2);
      if(this.getRequestHeader("Content-Type") == null) {
         RequestEntity var3 = this.getRequestEntity();
         if(var3 != null) {
            if(var3.getContentType() != null) {
               String var4 = var3.getContentType();
               this.setRequestHeader("Content-Type", var4);
            }
         }
      }
   }

   protected void clearRequestBody() {
      LOG.trace("enter EntityEnclosingMethod.clearRequestBody()");
      this.requestStream = null;
      this.requestString = null;
      this.requestEntity = null;
   }

   protected byte[] generateRequestBody() {
      LOG.trace("enter EntityEnclosingMethod.renerateRequestBody()");
      return null;
   }

   protected RequestEntity generateRequestEntity() {
      byte[] var1 = this.generateRequestBody();
      if(var1 != null) {
         ByteArrayRequestEntity var2 = new ByteArrayRequestEntity(var1);
         this.requestEntity = var2;
      } else if(this.requestStream != null) {
         InputStream var3 = this.requestStream;
         long var4 = this.requestContentLength;
         InputStreamRequestEntity var6 = new InputStreamRequestEntity(var3, var4);
         this.requestEntity = var6;
         this.requestStream = null;
      } else if(this.requestString != null) {
         String var7 = this.getRequestCharSet();

         try {
            String var8 = this.requestString;
            StringRequestEntity var9 = new StringRequestEntity(var8, (String)null, var7);
            this.requestEntity = var9;
         } catch (UnsupportedEncodingException var17) {
            if(LOG.isWarnEnabled()) {
               Log var11 = LOG;
               String var12 = var7 + " not supported";
               var11.warn(var12);
            }

            try {
               String var13 = this.requestString;
               StringRequestEntity var14 = new StringRequestEntity(var13, (String)null, (String)null);
               this.requestEntity = var14;
            } catch (UnsupportedEncodingException var16) {
               ;
            }
         }
      }

      return this.requestEntity;
   }

   public boolean getFollowRedirects() {
      return false;
   }

   public String getRequestCharSet() {
      String var3;
      if(this.getRequestHeader("Content-Type") == null) {
         if(this.requestEntity != null) {
            String var1 = this.requestEntity.getContentType();
            Header var2 = new Header("Content-Type", var1);
            var3 = this.getContentCharSet(var2);
         } else {
            var3 = super.getRequestCharSet();
         }
      } else {
         var3 = super.getRequestCharSet();
      }

      return var3;
   }

   protected long getRequestContentLength() {
      LOG.trace("enter EntityEnclosingMethod.getRequestContentLength()");
      long var1;
      if(!this.hasRequestContent()) {
         var1 = 0L;
      } else if(this.chunked) {
         var1 = 65535L;
      } else {
         if(this.requestEntity == null) {
            RequestEntity var3 = this.generateRequestEntity();
            this.requestEntity = var3;
         }

         if(this.requestEntity == null) {
            var1 = 0L;
         } else {
            var1 = this.requestEntity.getContentLength();
         }
      }

      return var1;
   }

   public RequestEntity getRequestEntity() {
      return this.generateRequestEntity();
   }

   protected boolean hasRequestContent() {
      LOG.trace("enter EntityEnclosingMethod.hasRequestContent()");
      boolean var1;
      if(this.requestEntity == null && this.requestStream == null && this.requestString == null) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void recycle() {
      LOG.trace("enter EntityEnclosingMethod.recycle()");
      this.clearRequestBody();
      this.requestContentLength = 65534L;
      this.repeatCount = 0;
      this.chunked = (boolean)0;
      super.recycle();
   }

   public void setContentChunked(boolean var1) {
      this.chunked = var1;
   }

   public void setFollowRedirects(boolean var1) {
      if(var1) {
         throw new IllegalArgumentException("Entity enclosing requests cannot be redirected without user intervention");
      } else {
         super.setFollowRedirects((boolean)0);
      }
   }

   public void setRequestBody(InputStream var1) {
      LOG.trace("enter EntityEnclosingMethod.setRequestBody(InputStream)");
      this.clearRequestBody();
      this.requestStream = var1;
   }

   public void setRequestBody(String var1) {
      LOG.trace("enter EntityEnclosingMethod.setRequestBody(String)");
      this.clearRequestBody();
      this.requestString = var1;
   }

   public void setRequestContentLength(int var1) {
      LOG.trace("enter EntityEnclosingMethod.setRequestContentLength(int)");
      long var2 = (long)var1;
      this.requestContentLength = var2;
   }

   public void setRequestContentLength(long var1) {
      LOG.trace("enter EntityEnclosingMethod.setRequestContentLength(int)");
      this.requestContentLength = var1;
   }

   public void setRequestEntity(RequestEntity var1) {
      this.clearRequestBody();
      this.requestEntity = var1;
   }

   protected boolean writeRequestBody(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter EntityEnclosingMethod.writeRequestBody(HttpState, HttpConnection)");
      boolean var3;
      if(!this.hasRequestContent()) {
         LOG.debug("Request body has not been specified");
         var3 = true;
      } else {
         if(this.requestEntity == null) {
            RequestEntity var4 = this.generateRequestEntity();
            this.requestEntity = var4;
         }

         if(this.requestEntity == null) {
            LOG.debug("Request body is empty");
            var3 = true;
         } else {
            long var5 = this.getRequestContentLength();
            if(this.repeatCount > 0 && !this.requestEntity.isRepeatable()) {
               throw new ProtocolException("Unbuffered entity enclosing request can not be repeated.");
            }

            int var7 = this.repeatCount + 1;
            this.repeatCount = var7;
            Object var8 = var2.getRequestOutputStream();
            if(var5 < 0L) {
               var8 = new ChunkedOutputStream((OutputStream)var8);
            }

            this.requestEntity.writeRequest((OutputStream)var8);
            if(var8 instanceof ChunkedOutputStream) {
               ((ChunkedOutputStream)var8).finish();
            }

            ((OutputStream)var8).flush();
            LOG.debug("Request body sent");
            var3 = true;
         }
      }

      return var3;
   }
}
