package org.apache.http.entity.mime.content;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.entity.mime.content.AbstractContentBody;

public class ByteArrayBody extends AbstractContentBody {

   private final byte[] data;
   private final String filename;


   public ByteArrayBody(byte[] var1, String var2) {
      this(var1, "application/octet-stream", var2);
   }

   public ByteArrayBody(byte[] var1, String var2, String var3) {
      super(var2);
      if(var1 == null) {
         throw new IllegalArgumentException("byte[] may not be null");
      } else {
         this.data = var1;
         this.filename = var3;
      }
   }

   public String getCharset() {
      return null;
   }

   public long getContentLength() {
      return (long)this.data.length;
   }

   public String getFilename() {
      return this.filename;
   }

   public String getTransferEncoding() {
      return "binary";
   }

   public void writeTo(OutputStream var1) throws IOException {
      byte[] var2 = this.data;
      var1.write(var2);
   }
}
