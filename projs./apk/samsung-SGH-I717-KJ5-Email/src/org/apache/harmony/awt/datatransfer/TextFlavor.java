package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class TextFlavor {

   public static final Class[] charsetTextClasses;
   public static final Class[] unicodeTextClasses;


   static {
      Class[] var0 = new Class[]{String.class, Reader.class, CharBuffer.class, char[].class};
      unicodeTextClasses = var0;
      Class[] var1 = new Class[]{InputStream.class, ByteBuffer.class, byte[].class};
      charsetTextClasses = var1;
   }

   public TextFlavor() {}

   public static void addCharsetClasses(SystemFlavorMap var0, String var1, String var2, String var3) {
      int var4 = 0;

      while(true) {
         int var5 = charsetTextClasses.length;
         if(var4 >= var5) {
            return;
         }

         String var6 = "text/" + var2;
         StringBuilder var7 = new StringBuilder(";class=\"");
         String var8 = charsetTextClasses[var4].getName();
         String var9 = var7.append(var8).append("\"").append(";charset=\"").append(var3).append("\"").toString();
         String var10 = String.valueOf(var6);
         String var11 = var10 + var9;
         DataFlavor var12 = new DataFlavor(var11, var6);
         var0.addFlavorForUnencodedNative(var1, var12);
         var0.addUnencodedNativeForFlavor(var12, var1);
         ++var4;
      }
   }

   public static void addUnicodeClasses(SystemFlavorMap var0, String var1, String var2) {
      int var3 = 0;

      while(true) {
         int var4 = unicodeTextClasses.length;
         if(var3 >= var4) {
            return;
         }

         String var5 = "text/" + var2;
         StringBuilder var6 = new StringBuilder(";class=\"");
         String var7 = unicodeTextClasses[var3].getName();
         String var8 = var6.append(var7).append("\"").toString();
         String var9 = String.valueOf(var5);
         String var10 = var9 + var8;
         DataFlavor var11 = new DataFlavor(var10, var5);
         var0.addFlavorForUnencodedNative(var1, var11);
         var0.addUnencodedNativeForFlavor(var11, var1);
         ++var3;
      }
   }
}
