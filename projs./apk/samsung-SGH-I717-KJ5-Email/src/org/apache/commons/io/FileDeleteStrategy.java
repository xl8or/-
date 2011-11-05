package org.apache.commons.io;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileDeleteStrategy {

   public static final FileDeleteStrategy FORCE = new FileDeleteStrategy.ForceFileDeleteStrategy();
   public static final FileDeleteStrategy NORMAL = new FileDeleteStrategy("Normal");
   private final String name;


   protected FileDeleteStrategy(String var1) {
      this.name = var1;
   }

   public void delete(File var1) throws IOException {
      if(var1.exists()) {
         if(!this.doDelete(var1)) {
            String var2 = "Deletion failed: " + var1;
            throw new IOException(var2);
         }
      }
   }

   public boolean deleteQuietly(File var1) {
      boolean var2;
      if(var1 != null && var1.exists()) {
         boolean var3;
         try {
            var3 = this.doDelete(var1);
         } catch (IOException var5) {
            var2 = false;
            return var2;
         }

         var2 = var3;
      } else {
         var2 = true;
      }

      return var2;
   }

   protected boolean doDelete(File var1) throws IOException {
      return var1.delete();
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("FileDeleteStrategy[");
      String var2 = this.name;
      return var1.append(var2).append("]").toString();
   }

   static class ForceFileDeleteStrategy extends FileDeleteStrategy {

      ForceFileDeleteStrategy() {
         super("Force");
      }

      protected boolean doDelete(File var1) throws IOException {
         FileUtils.forceDelete(var1);
         return true;
      }
   }
}
