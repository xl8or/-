package org.apache.commons.io;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.io.FilenameUtils;

public class FileSystemUtils {

   private static final int INIT_PROBLEM = 255;
   private static final FileSystemUtils INSTANCE = new FileSystemUtils();
   private static final int OS = 0;
   private static final int OTHER = 0;
   private static final int POSIX_UNIX = 3;
   private static final int UNIX = 2;
   private static final int WINDOWS = 1;


   static {
      byte var2;
      label54: {
         label53: {
            int var3;
            try {
               String var0 = System.getProperty("os.name");
               if(var0 == null) {
                  throw new IOException("os.name not found");
               }

               var0 = var0.toLowerCase();
               if(var0.indexOf("windows") != -1) {
                  var2 = 1;
                  break label54;
               }

               if(var0.indexOf("linux") != -1 || var0.indexOf("sun os") != -1 || var0.indexOf("sunos") != -1 || var0.indexOf("solaris") != -1 || var0.indexOf("mpe/ix") != -1 || var0.indexOf("freebsd") != -1 || var0.indexOf("irix") != -1 || var0.indexOf("digital unix") != -1 || var0.indexOf("unix") != -1 || var0.indexOf("mac os x") != -1) {
                  var2 = 2;
                  break label54;
               }

               if(var0.indexOf("hp-ux") != -1) {
                  break label53;
               }

               var3 = var0.indexOf("aix");
            } catch (Exception var4) {
               var2 = -1;
               break label54;
            }

            if(var3 == -1) {
               var2 = 0;
               break label54;
            }
         }

         var2 = 3;
      }

      OS = var2;
   }

   public FileSystemUtils() {}

   public static long freeSpace(String var0) throws IOException {
      FileSystemUtils var1 = INSTANCE;
      int var2 = OS;
      return var1.freeSpaceOS(var0, var2, (boolean)0);
   }

   public static long freeSpaceKb(String var0) throws IOException {
      FileSystemUtils var1 = INSTANCE;
      int var2 = OS;
      return var1.freeSpaceOS(var0, var2, (boolean)1);
   }

   long freeSpaceOS(String var1, int var2, boolean var3) throws IOException {
      if(var1 == null) {
         throw new IllegalArgumentException("Path must not be empty");
      } else {
         long var4;
         switch(var2) {
         case 0:
            throw new IllegalStateException("Unsupported operating system");
         case 1:
            if(var3) {
               var4 = this.freeSpaceWindows(var1) / 1024L;
            } else {
               var4 = this.freeSpaceWindows(var1);
            }
            break;
         case 2:
            var4 = this.freeSpaceUnix(var1, var3, (boolean)0);
            break;
         case 3:
            var4 = this.freeSpaceUnix(var1, var3, (boolean)1);
            break;
         default:
            throw new IllegalStateException("Exception caught when determining operating system");
         }

         return var4;
      }
   }

   long freeSpaceUnix(String var1, boolean var2, boolean var3) throws IOException {
      if(var1.length() == 0) {
         throw new IllegalArgumentException("Path must not be empty");
      } else {
         String var4 = FilenameUtils.normalize(var1);
         String var5 = "-";
         if(var2) {
            var5 = var5 + "k";
         }

         if(var3) {
            var5 = var5 + "P";
         }

         String[] var7;
         if(var5.length() > 1) {
            String[] var6 = new String[]{"df", var5, var4};
            var7 = var6;
         } else {
            String[] var10 = new String[]{"df", var4};
            var7 = var10;
         }

         List var8 = this.performCommand(var7, 3);
         if(var8.size() < 2) {
            String var9 = "Command line \'df\' did not return info as expected for path \'" + var4 + "\'- response was " + var8;
            throw new IOException(var9);
         } else {
            String var11 = (String)var8.get(1);
            StringTokenizer var12 = new StringTokenizer(var11, " ");
            if(var12.countTokens() < 4) {
               if(var12.countTokens() != 1 || var8.size() < 3) {
                  String var17 = "Command line \'df\' did not return data as expected for path \'" + var4 + "\'- check path is valid";
                  throw new IOException(var17);
               }

               String var13 = (String)var8.get(2);
               var12 = new StringTokenizer(var13, " ");
            } else {
               String var18 = var12.nextToken();
            }

            String var14 = var12.nextToken();
            String var15 = var12.nextToken();
            String var16 = var12.nextToken();
            return this.parseBytes(var16, var4);
         }
      }
   }

   long freeSpaceWindows(String var1) throws IOException {
      String var2 = FilenameUtils.normalize(var1);
      if(var2.length() > 2 && var2.charAt(1) == 58) {
         var2 = var2.substring(0, 2);
      }

      String[] var3 = new String[]{"cmd.exe", "/C", null};
      String var4 = "dir /-c " + var2;
      var3[2] = var4;
      List var5 = this.performCommand(var3, Integer.MAX_VALUE);

      for(int var6 = var5.size() - 1; var6 >= 0; var6 += -1) {
         String var7 = (String)var5.get(var6);
         if(var7.length() > 0) {
            return this.parseDir(var7, var2);
         }
      }

      String var8 = "Command line \'dir /-c\' did not return any info for path \'" + var2 + "\'";
      throw new IOException(var8);
   }

   Process openProcess(String[] var1) throws IOException {
      return Runtime.getRuntime().exec(var1);
   }

   long parseBytes(String var1, String var2) throws IOException {
      try {
         long var3 = Long.parseLong(var1);
         if(var3 < 0L) {
            String var5 = "Command line \'df\' did not find free space in response for path \'" + var2 + "\'- check path is valid";
            throw new IOException(var5);
         } else {
            return var3;
         }
      } catch (NumberFormatException var8) {
         String var7 = "Command line \'df\' did not return numeric data as expected for path \'" + var2 + "\'- check path is valid";
         throw new IOException(var7);
      }
   }

   long parseDir(String var1, String var2) throws IOException {
      int var3 = 0;
      int var4 = 0;

      int var5;
      for(var5 = var1.length() - 1; var5 >= 0; var5 += -1) {
         if(Character.isDigit(var1.charAt(var5))) {
            var4 = var5 + 1;
            break;
         }
      }

      while(var5 >= 0) {
         char var6 = var1.charAt(var5);
         if(!Character.isDigit(var6) && var6 != 44 && var6 != 46) {
            var3 = var5 + 1;
            break;
         }

         var5 += -1;
      }

      if(var5 < 0) {
         String var7 = "Command line \'dir /-c\' did not return valid info for path \'" + var2 + "\'";
         throw new IOException(var7);
      } else {
         String var8 = var1.substring(var3, var4);
         StringBuffer var9 = new StringBuffer(var8);
         int var10 = 0;

         while(true) {
            int var11 = var9.length();
            if(var10 >= var11) {
               String var14 = var9.toString();
               return this.parseBytes(var14, var2);
            }

            if(var9.charAt(var10) == 44 || var9.charAt(var10) == 46) {
               int var12 = var10 + -1;
               var9.deleteCharAt(var10);
               var10 = var12;
            }

            ++var10;
         }
      }
   }

   List performCommand(String[] param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
