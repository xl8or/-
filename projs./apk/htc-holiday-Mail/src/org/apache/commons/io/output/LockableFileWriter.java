package org.apache.commons.io.output;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.io.FileUtils;

public class LockableFileWriter extends Writer {

   private static final String LCK = ".lck";
   private final File lockFile;
   private final Writer out;


   public LockableFileWriter(File var1) throws IOException {
      this(var1, (boolean)0, (String)null);
   }

   public LockableFileWriter(File var1, String var2) throws IOException {
      this(var1, var2, (boolean)0, (String)null);
   }

   public LockableFileWriter(File var1, String var2, boolean var3, String var4) throws IOException {
      File var5 = var1.getAbsoluteFile();
      if(var5.getParentFile() != null) {
         FileUtils.forceMkdir(var5.getParentFile());
      }

      if(var5.isDirectory()) {
         throw new IOException("File specified is a directory");
      } else {
         if(var4 == null) {
            var4 = System.getProperty("java.io.tmpdir");
         }

         File var6 = new File(var4);
         FileUtils.forceMkdir(var6);
         this.testLockDir(var6);
         StringBuilder var7 = new StringBuilder();
         String var8 = var5.getName();
         String var9 = var7.append(var8).append(".lck").toString();
         File var10 = new File(var6, var9);
         this.lockFile = var10;
         this.createLock();
         Writer var11 = this.initWriter(var5, var2, var3);
         this.out = var11;
      }
   }

   public LockableFileWriter(File var1, boolean var2) throws IOException {
      this(var1, var2, (String)null);
   }

   public LockableFileWriter(File var1, boolean var2, String var3) throws IOException {
      this(var1, (String)null, var2, var3);
   }

   public LockableFileWriter(String var1) throws IOException {
      this(var1, (boolean)0, (String)null);
   }

   public LockableFileWriter(String var1, boolean var2) throws IOException {
      this(var1, var2, (String)null);
   }

   public LockableFileWriter(String var1, boolean var2, String var3) throws IOException {
      File var4 = new File(var1);
      this(var4, var2, var3);
   }

   private void createLock() throws IOException {
      synchronized(LockableFileWriter.class) {
         if(!this.lockFile.createNewFile()) {
            StringBuilder var1 = (new StringBuilder()).append("Can\'t write file, lock ");
            String var2 = this.lockFile.getAbsolutePath();
            String var3 = var1.append(var2).append(" exists").toString();
            throw new IOException(var3);
         } else {
            this.lockFile.deleteOnExit();
         }
      }
   }

   private Writer initWriter(File param1, String param2, boolean param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void testLockDir(File var1) throws IOException {
      if(!var1.exists()) {
         StringBuilder var2 = (new StringBuilder()).append("Could not find lockDir: ");
         String var3 = var1.getAbsolutePath();
         String var4 = var2.append(var3).toString();
         throw new IOException(var4);
      } else if(!var1.canWrite()) {
         StringBuilder var5 = (new StringBuilder()).append("Could not write to lockDir: ");
         String var6 = var1.getAbsolutePath();
         String var7 = var5.append(var6).toString();
         throw new IOException(var7);
      }
   }

   public void close() throws IOException {
      boolean var5 = false;

      try {
         var5 = true;
         this.out.close();
         var5 = false;
      } finally {
         if(var5) {
            boolean var3 = this.lockFile.delete();
         }
      }

      boolean var1 = this.lockFile.delete();
   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void write(int var1) throws IOException {
      this.out.write(var1);
   }

   public void write(String var1) throws IOException {
      this.out.write(var1);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }

   public void write(char[] var1) throws IOException {
      this.out.write(var1);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }
}
