package org.apache.commons.io.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.ThresholdingOutputStream;

public class DeferredFileOutputStream extends ThresholdingOutputStream {

   private boolean closed;
   private OutputStream currentOutputStream;
   private File directory;
   private ByteArrayOutputStream memoryOutputStream;
   private File outputFile;
   private String prefix;
   private String suffix;


   public DeferredFileOutputStream(int var1, File var2) {
      super(var1);
      this.closed = (boolean)0;
      this.outputFile = var2;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.memoryOutputStream = var3;
      ByteArrayOutputStream var4 = this.memoryOutputStream;
      this.currentOutputStream = var4;
   }

   public DeferredFileOutputStream(int var1, String var2, String var3, File var4) {
      File var5 = (File)false;
      this(var1, var5);
      if(var2 == null) {
         throw new IllegalArgumentException("Temporary file prefix is missing");
      } else {
         this.prefix = var2;
         this.suffix = var3;
         this.directory = var4;
      }
   }

   public void close() throws IOException {
      super.close();
      this.closed = (boolean)1;
   }

   public byte[] getData() {
      byte[] var1;
      if(this.memoryOutputStream != null) {
         var1 = this.memoryOutputStream.toByteArray();
      } else {
         var1 = null;
      }

      return var1;
   }

   public File getFile() {
      return this.outputFile;
   }

   protected OutputStream getStream() throws IOException {
      return this.currentOutputStream;
   }

   public boolean isInMemory() {
      boolean var1;
      if(!this.isThresholdExceeded()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void thresholdReached() throws IOException {
      if(this.prefix != null) {
         String var1 = this.prefix;
         String var2 = this.suffix;
         File var3 = this.directory;
         File var4 = File.createTempFile(var1, var2, var3);
         this.outputFile = var4;
      }

      File var5 = this.outputFile;
      FileOutputStream var6 = new FileOutputStream(var5);
      this.memoryOutputStream.writeTo(var6);
      this.currentOutputStream = var6;
      this.memoryOutputStream = null;
   }

   public void writeTo(OutputStream var1) throws IOException {
      if(!this.closed) {
         throw new IOException("Stream not closed");
      } else if(this.isInMemory()) {
         this.memoryOutputStream.writeTo(var1);
      } else {
         File var2 = this.outputFile;
         FileInputStream var3 = new FileInputStream(var2);

         try {
            IOUtils.copy((InputStream)var3, var1);
         } finally {
            IOUtils.closeQuietly((InputStream)var3);
         }

      }
   }
}
