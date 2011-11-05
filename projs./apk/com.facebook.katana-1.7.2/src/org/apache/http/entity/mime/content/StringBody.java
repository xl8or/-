package org.apache.http.entity.mime.content;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.http.entity.mime.content.AbstractContentBody;

public class StringBody extends AbstractContentBody {

   private final Charset charset;
   private final byte[] content;


   public StringBody(String var1) throws UnsupportedEncodingException {
      this(var1, "text/plain", (Charset)null);
   }

   public StringBody(String var1, String var2, Charset var3) throws UnsupportedEncodingException {
      super(var2);
      if(var1 == null) {
         throw new IllegalArgumentException("Text may not be null");
      } else {
         if(var3 == null) {
            var3 = Charset.forName("US-ASCII");
         }

         String var4 = var3.name();
         byte[] var5 = var1.getBytes(var4);
         this.content = var5;
         this.charset = var3;
      }
   }

   public StringBody(String var1, Charset var2) throws UnsupportedEncodingException {
      this(var1, "text/plain", var2);
   }

   public static StringBody create(String var0) throws IllegalArgumentException {
      return create(var0, (String)null, (Charset)null);
   }

   public static StringBody create(String var0, String var1, Charset var2) throws IllegalArgumentException {
      try {
         StringBody var3 = new StringBody(var0, var1, var2);
         return var3;
      } catch (UnsupportedEncodingException var6) {
         String var5 = "Charset " + var2 + " is not supported";
         throw new IllegalArgumentException(var5, var6);
      }
   }

   public static StringBody create(String var0, Charset var1) throws IllegalArgumentException {
      return create(var0, (String)null, var1);
   }

   public String getCharset() {
      return this.charset.name();
   }

   public long getContentLength() {
      return (long)this.content.length;
   }

   public String getFilename() {
      return null;
   }

   public Reader getReader() {
      byte[] var1 = this.content;
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      Charset var3 = this.charset;
      return new InputStreamReader(var2, var3);
   }

   public String getTransferEncoding() {
      return "8bit";
   }

   public void writeTo(OutputStream var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output stream may not be null");
      } else {
         byte[] var2 = this.content;
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
         byte[] var4 = new byte[4096];

         while(true) {
            int var5 = var3.read(var4);
            if(var5 == -1) {
               var1.flush();
               return;
            }

            var1.write(var4, 0, var5);
         }
      }
   }

   @Deprecated
   public void writeTo(OutputStream var1, int var2) throws IOException {
      this.writeTo(var1);
   }
}
