package org.apache.http.entity.mime.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.mime.content.AbstractContentBody;

public class InputStreamBody extends AbstractContentBody {

   private final String filename;
   private final InputStream in;


   public InputStreamBody(InputStream var1, String var2) {
      this(var1, "application/octet-stream", var2);
   }

   public InputStreamBody(InputStream var1, String var2, String var3) {
      super(var2);
      if(var1 == null) {
         throw new IllegalArgumentException("Input stream may not be null");
      } else {
         this.in = var1;
         this.filename = var3;
      }
   }

   public String getCharset() {
      return null;
   }

   public long getContentLength() {
      return 65535L;
   }

   public String getFilename() {
      return this.filename;
   }

   public InputStream getInputStream() {
      return this.in;
   }

   public String getTransferEncoding() {
      return "binary";
   }

   public void writeTo(OutputStream var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output stream may not be null");
      } else {
         short var2 = 4096;

         try {
            byte[] var3 = new byte[var2];

            while(true) {
               int var4 = this.in.read(var3);
               if(var4 == -1) {
                  var1.flush();
                  return;
               }

               var1.write(var3, 0, var4);
            }
         } finally {
            this.in.close();
         }
      }
   }

   @Deprecated
   public void writeTo(OutputStream var1, int var2) throws IOException {
      this.writeTo(var1);
   }
}
