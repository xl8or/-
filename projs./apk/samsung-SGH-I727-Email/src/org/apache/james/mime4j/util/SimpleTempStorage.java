package org.apache.james.mime4j.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.james.mime4j.Log;
import org.apache.james.mime4j.LogFactory;
import org.apache.james.mime4j.util.TempFile;
import org.apache.james.mime4j.util.TempPath;
import org.apache.james.mime4j.util.TempStorage;

public class SimpleTempStorage extends TempStorage {

   private static Log log = LogFactory.getLog(SimpleTempStorage.class);
   private Random random;
   private TempPath rootPath = null;


   public SimpleTempStorage() {
      Random var1 = new Random();
      this.random = var1;
      String var2 = System.getProperty("java.io.tmpdir");
      SimpleTempStorage.SimpleTempPath var3 = new SimpleTempStorage.SimpleTempPath(var2, (SimpleTempStorage.1)null);
      this.rootPath = var3;
   }

   private TempFile createTempFile(TempPath param1, String param2, String param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private TempPath createTempPath(TempPath var1, String var2) throws IOException {
      if(var2 == null) {
         var2 = "";
      }

      int var3 = 1000;

      File var8;
      do {
         long var4 = Math.abs(this.random.nextLong());
         String var6 = var1.getAbsolutePath();
         String var7 = var2 + var4;
         var8 = new File(var6, var7);
         var3 += -1;
      } while(var8.exists() && var3 > 0);

      if(!var8.exists() && var8.mkdirs()) {
         return new SimpleTempStorage.SimpleTempPath(var8, (SimpleTempStorage.1)null);
      } else {
         Log var9 = log;
         StringBuilder var10 = (new StringBuilder()).append("Unable to mkdirs on ");
         String var11 = var8.getAbsolutePath();
         String var12 = var10.append(var11).toString();
         var9.error(var12);
         StringBuilder var13 = (new StringBuilder()).append("Creating dir \'");
         String var14 = var8.getAbsolutePath();
         String var15 = var13.append(var14).append("\' failed.").toString();
         throw new IOException(var15);
      }
   }

   public TempPath getRootTempPath() {
      return this.rootPath;
   }

   private class SimpleTempPath implements TempPath {

      private File path;


      private SimpleTempPath(File var2) {
         this.path = null;
         this.path = var2;
      }

      // $FF: synthetic method
      SimpleTempPath(File var2, SimpleTempStorage.1 var3) {
         this(var2);
      }

      private SimpleTempPath(String var2) {
         this.path = null;
         File var3 = new File(var2);
         this.path = var3;
      }

      // $FF: synthetic method
      SimpleTempPath(String var2, SimpleTempStorage.1 var3) {
         this(var2);
      }

      public TempFile createTempFile() throws IOException {
         return SimpleTempStorage.this.createTempFile(this, (String)null, (String)null);
      }

      public TempFile createTempFile(String var1, String var2) throws IOException {
         return SimpleTempStorage.this.createTempFile(this, var1, var2);
      }

      public TempFile createTempFile(String var1, String var2, boolean var3) throws IOException {
         return SimpleTempStorage.this.createTempFile(this, var1, var2);
      }

      public TempPath createTempPath() throws IOException {
         return SimpleTempStorage.this.createTempPath(this, (String)null);
      }

      public TempPath createTempPath(String var1) throws IOException {
         return SimpleTempStorage.this.createTempPath(this, var1);
      }

      public void delete() {}

      public String getAbsolutePath() {
         return this.path.getAbsolutePath();
      }
   }

   private class SimpleTempFile implements TempFile {

      private File file;


      private SimpleTempFile(File var2) {
         this.file = null;
         this.file = var2;
         this.file.deleteOnExit();
      }

      // $FF: synthetic method
      SimpleTempFile(File var2, SimpleTempStorage.1 var3) {
         this(var2);
      }

      public void delete() {}

      public String getAbsolutePath() {
         return this.file.getAbsolutePath();
      }

      public InputStream getInputStream() throws IOException {
         File var1 = this.file;
         FileInputStream var2 = new FileInputStream(var1);
         return new BufferedInputStream(var2);
      }

      public OutputStream getOutputStream() throws IOException {
         File var1 = this.file;
         FileOutputStream var2 = new FileOutputStream(var1);
         return new BufferedOutputStream(var2);
      }

      public boolean isInMemory() {
         return false;
      }

      public long length() {
         return this.file.length();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
