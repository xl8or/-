package org.apache.commons.httpclient.methods;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InputStreamRequestEntity implements RequestEntity {

   public static final int CONTENT_LENGTH_AUTO = 254;
   private static final Log LOG = LogFactory.getLog(InputStreamRequestEntity.class);
   private byte[] buffer;
   private InputStream content;
   private long contentLength;
   private String contentType;


   public InputStreamRequestEntity(InputStream var1) {
      this(var1, (String)null);
   }

   public InputStreamRequestEntity(InputStream var1, long var2) {
      this(var1, var2, (String)null);
   }

   public InputStreamRequestEntity(InputStream var1, long var2, String var4) {
      this.buffer = null;
      if(var1 == null) {
         throw new IllegalArgumentException("The content cannot be null");
      } else {
         this.content = var1;
         this.contentLength = var2;
         this.contentType = var4;
      }
   }

   public InputStreamRequestEntity(InputStream var1, String var2) {
      this(var1, 65534L, var2);
   }

   private void bufferContent() {
      // $FF: Couldn't be decompiled
   }

   public InputStream getContent() {
      return this.content;
   }

   public long getContentLength() {
      if(this.contentLength == 65534L && this.buffer == null) {
         this.bufferContent();
      }

      return this.contentLength;
   }

   public String getContentType() {
      return this.contentType;
   }

   public boolean isRepeatable() {
      boolean var1;
      if(this.buffer != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void writeRequest(OutputStream var1) throws IOException {
      if(this.content == null) {
         if(this.buffer != null) {
            byte[] var5 = this.buffer;
            var1.write(var5);
         } else {
            throw new IllegalStateException("Content must be set before entity is written");
         }
      } else {
         byte[] var2 = new byte[4096];
         int var3 = 0;

         while(true) {
            int var4 = this.content.read(var2);
            if(var4 < 0) {
               return;
            }

            var1.write(var2, 0, var4);
            var3 += var4;
         }
      }
   }
}
