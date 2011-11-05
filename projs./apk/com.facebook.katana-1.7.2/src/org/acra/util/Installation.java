package org.acra.util;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class Installation {

   private static final String INSTALLATION = "ACRA-INSTALLATION";
   private static String sID = null;


   public Installation() {}

   public static String id(Context var0) {
      synchronized(Installation.class){}

      String var3;
      try {
         if(sID == null) {
            File var1 = var0.getFilesDir();
            File var2 = new File(var1, "ACRA-INSTALLATION");

            try {
               if(!var2.exists()) {
                  writeInstallationFile(var2);
               }

               sID = readInstallationFile(var2);
            } catch (Exception var8) {
               throw new RuntimeException(var8);
            }
         }

         var3 = sID;
      } finally {
         ;
      }

      return var3;
   }

   private static String readInstallationFile(File var0) throws IOException {
      RandomAccessFile var1 = new RandomAccessFile(var0, "r");
      byte[] var2 = new byte[(int)var1.length()];

      try {
         var1.readFully(var2);
      } finally {
         var1.close();
      }

      return new String(var2);
   }

   private static void writeInstallationFile(File var0) throws IOException {
      FileOutputStream var1 = new FileOutputStream(var0);

      try {
         byte[] var2 = UUID.randomUUID().toString().getBytes();
         var1.write(var2);
      } finally {
         var1.close();
      }

   }
}
