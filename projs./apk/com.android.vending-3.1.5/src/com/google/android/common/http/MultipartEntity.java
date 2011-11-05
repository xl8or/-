package com.google.android.common.http;

import com.google.android.common.http.Part;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;

public class MultipartEntity extends AbstractHttpEntity {

   public static final String MULTIPART_BOUNDARY = "http.method.multipart.boundary";
   private static byte[] MULTIPART_CHARS = EncodingUtils.getAsciiBytes("-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
   private static final String MULTIPART_FORM_CONTENT_TYPE = "multipart/form-data";
   private boolean contentConsumed = 0;
   private byte[] multipartBoundary;
   private HttpParams params;
   protected Part[] parts;


   public MultipartEntity(Part[] var1) {
      this.setContentType("multipart/form-data");
      if(var1 == null) {
         throw new IllegalArgumentException("parts cannot be null");
      } else {
         this.parts = var1;
         this.params = null;
      }
   }

   public MultipartEntity(Part[] var1, HttpParams var2) {
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

   public InputStream getContent() throws IOException, IllegalStateException {
      if(!this.isRepeatable() && this.contentConsumed) {
         throw new IllegalStateException("Content has been consumed");
      } else {
         this.contentConsumed = (boolean)1;
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         Part[] var2 = this.parts;
         byte[] var3 = this.multipartBoundary;
         Part.sendParts(var1, var2, var3);
         byte[] var4 = var1.toByteArray();
         return new ByteArrayInputStream(var4);
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
         var5 = 0L;
         return var5;
      }

      var5 = var3;
      return var5;
   }

   public Header getContentType() {
      StringBuffer var1 = new StringBuffer("multipart/form-data");
      StringBuffer var2 = var1.append("; boundary=");
      String var3 = EncodingUtils.getAsciiString(this.getMultipartBoundary());
      var1.append(var3);
      String var5 = var1.toString();
      return new BasicHeader("Content-Type", var5);
   }

   protected byte[] getMultipartBoundary() {
      if(this.multipartBoundary == null) {
         String var1 = null;
         if(this.params != null) {
            var1 = (String)this.params.getParameter("http.method.multipart.boundary");
         }

         if(var1 != null) {
            byte[] var2 = EncodingUtils.getAsciiBytes(var1);
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

   public boolean isStreaming() {
      return false;
   }

   public void writeTo(OutputStream var1) throws IOException {
      Part[] var2 = this.parts;
      byte[] var3 = this.getMultipartBoundary();
      Part.sendParts(var1, var2, var3);
   }
}
