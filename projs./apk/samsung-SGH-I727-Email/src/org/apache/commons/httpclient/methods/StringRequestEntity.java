package org.apache.commons.httpclient.methods;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.RequestEntity;

public class StringRequestEntity implements RequestEntity {

   private String charset;
   private byte[] content;
   private String contentType;


   public StringRequestEntity(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("The content cannot be null");
      } else {
         this.contentType = null;
         this.charset = null;
         byte[] var2 = var1.getBytes();
         this.content = var2;
      }
   }

   public StringRequestEntity(String var1, String var2, String var3) throws UnsupportedEncodingException {
      if(var1 == null) {
         throw new IllegalArgumentException("The content cannot be null");
      } else {
         this.contentType = var2;
         this.charset = var3;
         if(var2 != null) {
            HeaderElement[] var4 = HeaderElement.parseElements(var2);
            NameValuePair var5 = null;
            int var6 = 0;

            while(true) {
               int var7 = var4.length;
               if(var6 >= var7) {
                  break;
               }

               var5 = var4[var6].getParameterByName("charset");
               if(var5 != null) {
                  break;
               }

               ++var6;
            }

            if(var3 == null && var5 != null) {
               String var8 = var5.getValue();
               this.charset = var8;
            } else if(var3 != null && var5 == null) {
               String var11 = var2 + "; charset=" + var3;
               this.contentType = var11;
            }
         }

         if(this.charset != null) {
            String var9 = this.charset;
            byte[] var10 = var1.getBytes(var9);
            this.content = var10;
         } else {
            byte[] var12 = var1.getBytes();
            this.content = var12;
         }
      }
   }

   public String getCharset() {
      return this.charset;
   }

   public String getContent() {
      String var3;
      if(this.charset != null) {
         try {
            byte[] var1 = this.content;
            String var2 = this.charset;
            var3 = new String(var1, var2);
         } catch (UnsupportedEncodingException var7) {
            byte[] var5 = this.content;
            var3 = new String(var5);
         }
      } else {
         byte[] var6 = this.content;
         var3 = new String(var6);
      }

      return var3;
   }

   public long getContentLength() {
      return (long)this.content.length;
   }

   public String getContentType() {
      return this.contentType;
   }

   public boolean isRepeatable() {
      return true;
   }

   public void writeRequest(OutputStream var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output stream may not be null");
      } else {
         byte[] var2 = this.content;
         var1.write(var2);
         var1.flush();
      }
   }
}
