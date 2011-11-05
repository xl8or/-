package org.apache.commons.httpclient.methods.multipart;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultipartRequestEntity implements RequestEntity {

   private static byte[] MULTIPART_CHARS = EncodingUtil.getAsciiBytes("-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
   private static final String MULTIPART_FORM_CONTENT_TYPE = "multipart/form-data";
   private static final Log log = LogFactory.getLog(MultipartRequestEntity.class);
   private byte[] multipartBoundary;
   private HttpMethodParams params;
   protected Part[] parts;


   public MultipartRequestEntity(Part[] var1, HttpMethodParams var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("parts cannot be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("params cannot be null");
      } else {
         this.parts = var1;
         this.params = var2;
      }
   }

   private static byte[] generateMultipartBoundary() {
      Random var0 = new Random();
      byte[] var1 = new byte[var0.nextInt(11) + 30];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return var1;
         }

         byte[] var4 = MULTIPART_CHARS;
         int var5 = MULTIPART_CHARS.length;
         int var6 = var0.nextInt(var5);
         byte var7 = var4[var6];
         var1[var2] = var7;
         ++var2;
      }
   }

   public long getContentLength() {
      long var3;
      long var5;
      try {
         Part[] var1 = this.parts;
         byte[] var2 = this.getMultipartBoundary();
         var3 = Part.getLengthOfParts(var1, var2);
      } catch (Exception var8) {
         log.error("An exception occurred while getting the length of the parts", var8);
         var5 = 0L;
         return var5;
      }

      var5 = var3;
      return var5;
   }

   public String getContentType() {
      StringBuffer var1 = new StringBuffer("multipart/form-data");
      StringBuffer var2 = var1.append("; boundary=");
      String var3 = EncodingUtil.getAsciiString(this.getMultipartBoundary());
      var1.append(var3);
      return var1.toString();
   }

   protected byte[] getMultipartBoundary() {
      if(this.multipartBoundary == null) {
         String var1 = (String)this.params.getParameter("http.method.multipart.boundary");
         if(var1 != null) {
            byte[] var2 = EncodingUtil.getAsciiBytes(var1);
            this.multipartBoundary = var2;
         } else {
            byte[] var3 = generateMultipartBoundary();
            this.multipartBoundary = var3;
         }
      }

      return this.multipartBoundary;
   }

   public boolean isRepeatable() {
      int var1 = 0;

      boolean var3;
      while(true) {
         int var2 = this.parts.length;
         if(var1 >= var2) {
            var3 = true;
            break;
         }

         if(!this.parts[var1].isRepeatable()) {
            var3 = false;
            break;
         }

         ++var1;
      }

      return var3;
   }

   public void writeRequest(OutputStream var1) throws IOException {
      Part[] var2 = this.parts;
      byte[] var3 = this.getMultipartBoundary();
      Part.sendParts(var1, var2, var3);
   }
}
