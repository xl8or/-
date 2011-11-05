package org.apache.commons.httpclient.methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.httpclient.methods.RequestEntity;

public class FileRequestEntity implements RequestEntity {

   final String contentType;
   final File file;


   public FileRequestEntity(File var1, String var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("File may not be null");
      } else {
         this.file = var1;
         this.contentType = var2;
      }
   }

   public long getContentLength() {
      return this.file.length();
   }

   public String getContentType() {
      return this.contentType;
   }

   public boolean isRepeatable() {
      return true;
   }

   public void writeRequest(OutputStream var1) throws IOException {
      byte[] var2 = new byte[4096];
      File var3 = this.file;
      FileInputStream var4 = new FileInputStream(var3);

      try {
         while(true) {
            int var5 = var4.read(var2);
            if(var5 < 0) {
               return;
            }

            var1.write(var2, 0, var5);
         }
      } finally {
         var4.close();
      }
   }
}
