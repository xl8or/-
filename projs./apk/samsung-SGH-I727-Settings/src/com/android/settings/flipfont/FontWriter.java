package com.android.settings.flipfont;

import android.content.Context;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class FontWriter {

   public static final String FONT_DIRECTORY = "fonts";
   public static final String LOC_DIRECTORY = "/data/data/com.android.settings/app_fonts";
   public static final String MONOSPACE_LOC_NAME = "monospace.loc";
   public static final String SANS_LOC_NAME = "sans.loc";
   public static final String SERIF_LOC_NAME = "serif.loc";
   private static String TAG = "FontWriter";
   BufferedOutputStream bos = null;
   FileOutputStream fOut = null;
   OutputStreamWriter osw = null;


   public FontWriter() {}

   private boolean deleteFolder(File var1, String var2) {
      File var3 = new File(var1, var2);
      String[] var4 = var3.list();
      boolean var9;
      if(var4 != null) {
         int var5 = 0;

         while(true) {
            int var6 = var4.length;
            if(var5 >= var6) {
               var9 = var3.delete();
               break;
            }

            String var7 = var4[var5];
            boolean var8 = (new File(var3, var7)).delete();
            ++var5;
         }
      } else {
         var9 = false;
      }

      return var9;
   }

   public void changeFilePermission(String var1) {
      try {
         File var2 = new File(var1);
         boolean var3 = var2.setExecutable((boolean)1, (boolean)0);
         boolean var4 = var2.setWritable((boolean)1, (boolean)0);
         boolean var5 = var2.setReadable((boolean)1, (boolean)0);
      } catch (SecurityException var10) {
         String var7 = TAG;
         String var8 = "changeFilePermission : File permission error, " + var10;
         Log.e(var7, var8);
      }
   }

   public void copyFontFile(File var1, InputStream var2, String var3) {
      InputStream var4 = var2;
      File var5 = var1;
      String var6 = var3;
      boolean var50 = false;

      String var9;
      label207: {
         label208: {
            try {
               var50 = true;
               File var7 = new File(var5, var6);
               boolean var8 = var7.createNewFile();
               var9 = var7.getAbsolutePath();
               FileOutputStream var10 = new FileOutputStream(var7);
               this.fOut = var10;
               FileOutputStream var11 = this.fOut;
               BufferedOutputStream var12 = new BufferedOutputStream(var11);
               this.bos = var12;
               byte[] var13 = new byte[1024];

               while(true) {
                  int var14 = var4.read(var13);
                  if(var14 <= 0) {
                     this.bos.flush();
                     this.fOut.flush();
                     this.bos.close();
                     var50 = false;
                     break label208;
                  }

                  this.bos.write(var13, 0, var14);
               }
            } catch (Exception var60) {
               File var16 = new File(var5, var6);
               if(var16.length() == 0L) {
                  boolean var17 = var16.delete();
               }

               var60.printStackTrace();
               var50 = false;
            } finally {
               if(var50) {
                  if(var4 != null) {
                     try {
                        var4.close();
                     } catch (IOException var53) {
                        int var34 = Log.e(TAG, "copyFontFile : myInputStream.close() error");
                     }
                  }

                  try {
                     if(this.fOut != null) {
                        this.fOut.close();
                     }
                  } catch (IOException var52) {
                     int var36 = Log.e(TAG, "copyFontFile : fOut.close() error");
                  }

                  try {
                     if(this.bos != null) {
                        this.bos.close();
                     }
                  } catch (IOException var51) {
                     int var38 = Log.e(TAG, "copyFontFile : bos.close() error");
                  }

               }
            }

            if(var2 != null) {
               try {
                  var4.close();
               } catch (IOException var59) {
                  int var27 = Log.e(TAG, "copyFontFile : myInputStream.close() error");
               }
            }

            try {
               if(this.fOut != null) {
                  this.fOut.close();
               }
            } catch (IOException var58) {
               int var29 = Log.e(TAG, "copyFontFile : fOut.close() error");
            }

            try {
               if(this.bos != null) {
                  this.bos.close();
               }
            } catch (IOException var57) {
               int var31 = Log.e(TAG, "copyFontFile : bos.close() error");
            }
            break label207;
         }

         if(var4 != null) {
            try {
               var4.close();
            } catch (IOException var56) {
               int var23 = Log.e(TAG, "copyFontFile : myInputStream.close() error");
            }
         }

         try {
            if(this.fOut != null) {
               this.fOut.close();
            }
         } catch (IOException var55) {
            int var25 = Log.e(TAG, "copyFontFile : fOut.close() error");
         }

         try {
            if(this.bos != null) {
               this.bos.close();
            }
         } catch (IOException var54) {
            int var21 = Log.e(TAG, "copyFontFile : bos.close() error");
         }
      }

      this.changeFilePermission(var9);
      File var18 = new File(var1, var3);
      if(var18.length() == 0L) {
         boolean var19 = var18.delete();
      }
   }

   public File createFontDirectory(Context var1, String var2) {
      File var3 = var1.getDir("fonts", 1);
      File var4 = new File(var3, var2);
      String[] var5 = var3.list();
      int var6 = 0;

      while(true) {
         int var7 = var5.length;
         if(var6 >= var7) {
            boolean var10 = var4.mkdir();
            byte var11 = 1;
            byte var12 = 0;

            try {
               var4.setExecutable((boolean)var11, (boolean)var12);
               boolean var14 = var4.setReadable((boolean)1, (boolean)0);
               boolean var15 = var4.setWritable((boolean)1, (boolean)0);
            } catch (SecurityException var20) {
               String var17 = TAG;
               String var18 = "writeLoc : File permission error, " + var20;
               Log.e(var17, var18);
            }

            return var4;
         }

         String var8 = var5[var6];
         this.deleteFolder(var3, var8);
         ++var6;
      }
   }

   public void writeLoc(Context var1, String var2, String var3) {
      boolean var34 = false;

      String var6;
      label124: {
         label125: {
            try {
               var34 = true;
               File var4 = new File("/data/data/com.android.settings/app_fonts", var2);
               boolean var5 = var4.createNewFile();
               var6 = var4.getAbsolutePath();
               FileOutputStream var7 = new FileOutputStream(var4);
               this.fOut = var7;
               FileOutputStream var8 = this.fOut;
               OutputStreamWriter var9 = new OutputStreamWriter(var8);
               this.osw = var9;
               OutputStreamWriter var10 = this.osw;
               String var11 = var3 + "\n";
               var10.write(var11);
               this.osw.flush();
               this.fOut.flush();
               var34 = false;
               break label125;
            } catch (Exception var41) {
               var41.printStackTrace();
               var34 = false;
            } finally {
               if(var34) {
                  try {
                     if(this.osw != null) {
                        this.osw.close();
                     }
                  } catch (IOException var36) {
                     int var23 = Log.e(TAG, "writeLoc : osw.close() error");
                  }

                  try {
                     if(this.fOut != null) {
                        this.fOut.close();
                     }
                  } catch (IOException var35) {
                     int var25 = Log.e(TAG, "writeLoc : fOut.close() error");
                  }

               }
            }

            try {
               if(this.osw != null) {
                  this.osw.close();
               }
            } catch (IOException var38) {
               int var20 = Log.e(TAG, "writeLoc : osw.close() error");
            }

            try {
               if(this.fOut != null) {
                  this.fOut.close();
               }
            } catch (IOException var37) {
               int var18 = Log.e(TAG, "writeLoc : fOut.close() error");
            }
            break label124;
         }

         try {
            if(this.osw != null) {
               this.osw.close();
            }
         } catch (IOException var40) {
            int var13 = Log.e(TAG, "writeLoc : osw.close() error");
         }

         try {
            if(this.fOut != null) {
               this.fOut.close();
            }
         } catch (IOException var39) {
            int var15 = Log.e(TAG, "writeLoc : fOut.close() error");
         }
      }

      this.changeFilePermission(var6);
   }
}
