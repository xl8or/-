package org.apache.commons.httpclient.methods;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.httpclient.methods.RequestEntity;

public class ByteArrayRequestEntity implements RequestEntity {

   private byte[] content;
   private String contentType;


   public ByteArrayRequestEntity(byte[] var1) {
      this(var1, (String)null);
   }

   public ByteArrayRequestEntity(byte[] var1, String var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("The content cannot be null");
      } else {
         this.content = var1;
         this.contentType = var2;
      }
   }

   public byte[] getContent() {
      return this.content;
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
      byte[] var2 = this.content;
      var1.write(var2);
   }
}
