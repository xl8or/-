package org.apache.http.entity.mime.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.mime.content.AbstractContentBody;

public class FileBody extends AbstractContentBody {

   private final String charset;
   private final File file;
   private final String filename;


   public FileBody(File var1) {
      this(var1, "application/octet-stream");
   }

   public FileBody(File var1, String var2) {
      this(var1, var2, (String)null);
   }

   public FileBody(File var1, String var2, String var3) {
      this(var1, (String)null, var2, var3);
   }

   public FileBody(File var1, String var2, String var3, String var4) {
      super(var3);
      if(var1 == null) {
         throw new IllegalArgumentException("File may not be null");
      } else {
         this.file = var1;
         if(var2 != null) {
            this.filename = var2;
         } else {
            String var5 = var1.getName();
            this.filename = var5;
         }

         this.charset = var4;
      }
   }

   public String getCharset() {
      return this.charset;
   }

   public long getContentLength() {
      return this.file.length();
   }

   public File getFile() {
      return this.file;
   }

   public String getFilename() {
      return this.filename;
   }

   public InputStream getInputStream() throws IOException {
      File var1 = this.file;
      return new FileInputStream(var1);
   }

   public String getTransferEncoding() {
      return "binary";
   }

   public void writeTo(OutputStream var1) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Output stream may not be null");
      } else {
         File var2 = this.file;
         FileInputStream var3 = new FileInputStream(var2);
         short var4 = 4096;

         try {
            byte[] var5 = new byte[var4];

            while(true) {
               int var6 = var3.read(var5);
               if(var6 == -1) {
                  var1.flush();
                  return;
               }

               var1.write(var5, 0, var6);
            }
         } finally {
            var3.close();
         }
      }
   }

   @Deprecated
   public void writeTo(OutputStream var1, int var2) throws IOException {
      this.writeTo(var1);
   }
}
