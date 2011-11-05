package com.google.android.common.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public abstract class Part {

   protected static final String BOUNDARY = "----------------314159265358979323846";
   protected static final byte[] BOUNDARY_BYTES = EncodingUtils.getAsciiBytes("----------------314159265358979323846");
   protected static final String CHARSET = "; charset=";
   protected static final byte[] CHARSET_BYTES = EncodingUtils.getAsciiBytes("; charset=");
   protected static final String CONTENT_DISPOSITION = "Content-Disposition: form-data; name=";
   protected static final byte[] CONTENT_DISPOSITION_BYTES = EncodingUtils.getAsciiBytes("Content-Disposition: form-data; name=");
   protected static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: ";
   protected static final byte[] CONTENT_TRANSFER_ENCODING_BYTES = EncodingUtils.getAsciiBytes("Content-Transfer-Encoding: ");
   protected static final String CONTENT_TYPE = "Content-Type: ";
   protected static final byte[] CONTENT_TYPE_BYTES = EncodingUtils.getAsciiBytes("Content-Type: ");
   protected static final String CRLF = "\r\n";
   protected static final byte[] CRLF_BYTES = EncodingUtils.getAsciiBytes("\r\n");
   private static final byte[] DEFAULT_BOUNDARY_BYTES = BOUNDARY_BYTES;
   protected static final String EXTRA = "--";
   protected static final byte[] EXTRA_BYTES = EncodingUtils.getAsciiBytes("--");
   protected static final String QUOTE = "\"";
   protected static final byte[] QUOTE_BYTES = EncodingUtils.getAsciiBytes("\"");
   private byte[] boundaryBytes;


   public Part() {}

   public static String getBoundary() {
      return "----------------314159265358979323846";
   }

   public static long getLengthOfParts(Part[] var0) throws IOException {
      byte[] var1 = DEFAULT_BOUNDARY_BYTES;
      return getLengthOfParts(var0, var1);
   }

   public static long getLengthOfParts(Part[] var0, byte[] var1) throws IOException {
      if(var0 == null) {
         throw new IllegalArgumentException("Parts may not be null");
      } else {
         long var2 = 0L;
         int var4 = 0;

         long var8;
         while(true) {
            int var5 = var0.length;
            if(var4 >= var5) {
               long var10 = (long)EXTRA_BYTES.length;
               long var12 = var2 + var10;
               long var14 = (long)var1.length;
               long var16 = var12 + var14;
               long var18 = (long)EXTRA_BYTES.length;
               long var20 = var16 + var18;
               long var22 = (long)CRLF_BYTES.length;
               var8 = var20 + var22;
               break;
            }

            var0[var4].setPartBoundary(var1);
            long var6 = var0[var4].length();
            if(var6 < 0L) {
               var8 = 65535L;
               break;
            }

            var2 += var6;
            ++var4;
         }

         return var8;
      }
   }

   public static void sendParts(OutputStream var0, Part[] var1) throws IOException {
      byte[] var2 = DEFAULT_BOUNDARY_BYTES;
      sendParts(var0, var1, var2);
   }

   public static void sendParts(OutputStream var0, Part[] var1, byte[] var2) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Parts may not be null");
      } else if(var2 != null && var2.length != 0) {
         int var3 = 0;

         while(true) {
            int var4 = var1.length;
            if(var3 >= var4) {
               byte[] var5 = EXTRA_BYTES;
               var0.write(var5);
               var0.write(var2);
               byte[] var6 = EXTRA_BYTES;
               var0.write(var6);
               byte[] var7 = CRLF_BYTES;
               var0.write(var7);
               return;
            }

            var1[var3].setPartBoundary(var2);
            var1[var3].send(var0);
            ++var3;
         }
      } else {
         throw new IllegalArgumentException("partBoundary may not be empty");
      }
   }

   public abstract String getCharSet();

   public abstract String getContentType();

   public abstract String getName();

   protected byte[] getPartBoundary() {
      byte[] var1;
      if(this.boundaryBytes == null) {
         var1 = DEFAULT_BOUNDARY_BYTES;
      } else {
         var1 = this.boundaryBytes;
      }

      return var1;
   }

   public abstract String getTransferEncoding();

   public boolean isRepeatable() {
      return true;
   }

   public long length() throws IOException {
      long var1;
      if(this.lengthOfData() < 0L) {
         var1 = 65535L;
      } else {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         this.sendStart(var3);
         this.sendDispositionHeader(var3);
         this.sendContentTypeHeader(var3);
         this.sendTransferEncodingHeader(var3);
         this.sendEndOfHeader(var3);
         this.sendEnd(var3);
         long var4 = (long)var3.size();
         long var6 = this.lengthOfData();
         var1 = var4 + var6;
      }

      return var1;
   }

   protected abstract long lengthOfData() throws IOException;

   public void send(OutputStream var1) throws IOException {
      this.sendStart(var1);
      this.sendDispositionHeader(var1);
      this.sendContentTypeHeader(var1);
      this.sendTransferEncodingHeader(var1);
      this.sendEndOfHeader(var1);
      this.sendData(var1);
      this.sendEnd(var1);
   }

   protected void sendContentTypeHeader(OutputStream var1) throws IOException {
      String var2 = this.getContentType();
      if(var2 != null) {
         byte[] var3 = CRLF_BYTES;
         var1.write(var3);
         byte[] var4 = CONTENT_TYPE_BYTES;
         var1.write(var4);
         byte[] var5 = EncodingUtils.getAsciiBytes(var2);
         var1.write(var5);
         String var6 = this.getCharSet();
         if(var6 != null) {
            byte[] var7 = CHARSET_BYTES;
            var1.write(var7);
            byte[] var8 = EncodingUtils.getAsciiBytes(var6);
            var1.write(var8);
         }
      }
   }

   protected abstract void sendData(OutputStream var1) throws IOException;

   protected void sendDispositionHeader(OutputStream var1) throws IOException {
      byte[] var2 = CONTENT_DISPOSITION_BYTES;
      var1.write(var2);
      byte[] var3 = QUOTE_BYTES;
      var1.write(var3);
      byte[] var4 = EncodingUtils.getAsciiBytes(this.getName());
      var1.write(var4);
      byte[] var5 = QUOTE_BYTES;
      var1.write(var5);
   }

   protected void sendEnd(OutputStream var1) throws IOException {
      byte[] var2 = CRLF_BYTES;
      var1.write(var2);
   }

   protected void sendEndOfHeader(OutputStream var1) throws IOException {
      byte[] var2 = CRLF_BYTES;
      var1.write(var2);
      byte[] var3 = CRLF_BYTES;
      var1.write(var3);
   }

   protected void sendStart(OutputStream var1) throws IOException {
      byte[] var2 = EXTRA_BYTES;
      var1.write(var2);
      byte[] var3 = this.getPartBoundary();
      var1.write(var3);
      byte[] var4 = CRLF_BYTES;
      var1.write(var4);
   }

   protected void sendTransferEncodingHeader(OutputStream var1) throws IOException {
      String var2 = this.getTransferEncoding();
      if(var2 != null) {
         byte[] var3 = CRLF_BYTES;
         var1.write(var3);
         byte[] var4 = CONTENT_TRANSFER_ENCODING_BYTES;
         var1.write(var4);
         byte[] var5 = EncodingUtils.getAsciiBytes(var2);
         var1.write(var5);
      }
   }

   void setPartBoundary(byte[] var1) {
      this.boundaryBytes = var1;
   }

   public String toString() {
      return this.getName();
   }
}
