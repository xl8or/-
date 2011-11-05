package org.apache.commons.httpclient.methods.multipart;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.methods.multipart.PartSource;

public class FilePartSource implements PartSource {

   private File file;
   private String fileName;


   public FilePartSource(File var1) throws FileNotFoundException {
      this.file = null;
      this.fileName = null;
      this.file = var1;
      if(var1 != null) {
         if(!var1.isFile()) {
            throw new FileNotFoundException("File is not a normal file.");
         } else if(!var1.canRead()) {
            throw new FileNotFoundException("File is not readable.");
         } else {
            String var2 = var1.getName();
            this.fileName = var2;
         }
      }
   }

   public FilePartSource(String var1, File var2) throws FileNotFoundException {
      this(var2);
      if(var1 != null) {
         this.fileName = var1;
      }
   }

   public InputStream createInputStream() throws IOException {
      Object var2;
      if(this.file != null) {
         File var1 = this.file;
         var2 = new FileInputStream(var1);
      } else {
         byte[] var3 = new byte[0];
         var2 = new ByteArrayInputStream(var3);
      }

      return (InputStream)var2;
   }

   public String getFileName() {
      String var1;
      if(this.fileName == null) {
         var1 = "noname";
      } else {
         var1 = this.fileName;
      }

      return var1;
   }

   public long getLength() {
      long var1;
      if(this.file != null) {
         var1 = this.file.length();
      } else {
         var1 = 0L;
      }

      return var1;
   }
}
