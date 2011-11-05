package javax.activation;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

public abstract class FileTypeMap {

   private static FileTypeMap defaultMap;


   public FileTypeMap() {}

   public static FileTypeMap getDefaultFileTypeMap() {
      if(defaultMap == null) {
         defaultMap = new MimetypesFileTypeMap();
      }

      return defaultMap;
   }

   public static void setDefaultFileTypeMap(FileTypeMap var0) {
      SecurityManager var1 = System.getSecurityManager();
      if(var1 != null) {
         try {
            var1.checkSetFactory();
         } catch (SecurityException var4) {
            if(var0 != null) {
               ClassLoader var2 = FileTypeMap.class.getClassLoader();
               ClassLoader var3 = var0.getClass().getClassLoader();
               if(var2 != var3) {
                  throw var4;
               }
            }
         }
      }

      defaultMap = var0;
   }

   public abstract String getContentType(File var1);

   public abstract String getContentType(String var1);
}
