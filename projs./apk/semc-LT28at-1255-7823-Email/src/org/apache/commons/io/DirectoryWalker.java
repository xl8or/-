package org.apache.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public abstract class DirectoryWalker {

   private final int depthLimit;
   private final FileFilter filter;


   protected DirectoryWalker() {
      this((FileFilter)null, -1);
   }

   protected DirectoryWalker(FileFilter var1, int var2) {
      this.filter = var1;
      this.depthLimit = var2;
   }

   protected DirectoryWalker(IOFileFilter var1, IOFileFilter var2, int var3) {
      if(var1 == null && var2 == null) {
         this.filter = null;
      } else {
         if(var1 == null) {
            var1 = TrueFileFilter.TRUE;
         }

         if(var2 == null) {
            var2 = TrueFileFilter.TRUE;
         }

         IOFileFilter var4 = FileFilterUtils.makeDirectoryOnly(var1);
         IOFileFilter var5 = FileFilterUtils.makeFileOnly(var2);
         IOFileFilter var6 = FileFilterUtils.orFileFilter(var4, var5);
         this.filter = var6;
      }

      this.depthLimit = var3;
   }

   private void walk(File var1, int var2, Collection var3) throws IOException {
      this.checkIfCancelled(var1, var2, var3);
      if(this.handleDirectory(var1, var2, var3)) {
         label37: {
            this.handleDirectoryStart(var1, var2, var3);
            int var4 = var2 + 1;
            if(this.depthLimit >= 0) {
               int var5 = this.depthLimit;
               if(var4 > var5) {
                  break label37;
               }
            }

            this.checkIfCancelled(var1, var2, var3);
            File[] var6;
            if(this.filter == null) {
               var6 = var1.listFiles();
            } else {
               FileFilter var7 = this.filter;
               var6 = var1.listFiles(var7);
            }

            if(var6 == null) {
               this.handleRestricted(var1, var4, var3);
            } else {
               int var8 = 0;

               while(true) {
                  int var9 = var6.length;
                  if(var8 >= var9) {
                     break;
                  }

                  File var10 = var6[var8];
                  if(var10.isDirectory()) {
                     this.walk(var10, var4, var3);
                  } else {
                     this.checkIfCancelled(var10, var4, var3);
                     this.handleFile(var10, var4, var3);
                     this.checkIfCancelled(var10, var4, var3);
                  }

                  ++var8;
               }
            }
         }

         this.handleDirectoryEnd(var1, var2, var3);
      }

      this.checkIfCancelled(var1, var2, var3);
   }

   protected final void checkIfCancelled(File var1, int var2, Collection var3) throws IOException {
      if(this.handleIsCancelled(var1, var2, var3)) {
         throw new DirectoryWalker.CancelException(var1, var2);
      }
   }

   protected void handleCancelled(File var1, Collection var2, DirectoryWalker.CancelException var3) throws IOException {
      throw var3;
   }

   protected boolean handleDirectory(File var1, int var2, Collection var3) throws IOException {
      return true;
   }

   protected void handleDirectoryEnd(File var1, int var2, Collection var3) throws IOException {}

   protected void handleDirectoryStart(File var1, int var2, Collection var3) throws IOException {}

   protected void handleEnd(Collection var1) throws IOException {}

   protected void handleFile(File var1, int var2, Collection var3) throws IOException {}

   protected boolean handleIsCancelled(File var1, int var2, Collection var3) throws IOException {
      return false;
   }

   protected void handleRestricted(File var1, int var2, Collection var3) throws IOException {}

   protected void handleStart(File var1, Collection var2) throws IOException {}

   protected final void walk(File var1, Collection var2) throws IOException {
      if(var1 == null) {
         throw new NullPointerException("Start Directory is null");
      } else {
         try {
            this.handleStart(var1, var2);
            this.walk(var1, 0, var2);
            this.handleEnd(var2);
         } catch (DirectoryWalker.CancelException var4) {
            this.handleCancelled(var1, var2, var4);
         }
      }
   }

   public static class CancelException extends IOException {

      private static final long serialVersionUID = 1347339620135041008L;
      private int depth;
      private File file;


      public CancelException(File var1, int var2) {
         this("Operation Cancelled", var1, var2);
      }

      public CancelException(String var1, File var2, int var3) {
         super(var1);
         this.depth = -1;
         this.file = var2;
         this.depth = var3;
      }

      public int getDepth() {
         return this.depth;
      }

      public File getFile() {
         return this.file;
      }
   }
}
