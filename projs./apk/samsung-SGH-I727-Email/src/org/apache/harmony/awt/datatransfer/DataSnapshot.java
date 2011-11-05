package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.RawBitmap;

public class DataSnapshot implements DataProvider {

   private final String[] fileList;
   private final String html;
   private final String[] nativeFormats;
   private final RawBitmap rawBitmap;
   private final Map<Class<?>, byte[]> serializedObjects;
   private final String text;
   private final String url;


   public DataSnapshot(DataProvider var1) {
      String[] var2 = var1.getNativeFormats();
      this.nativeFormats = var2;
      String var3 = var1.getText();
      this.text = var3;
      String[] var4 = var1.getFileList();
      this.fileList = var4;
      String var5 = var1.getURL();
      this.url = var5;
      String var6 = var1.getHTML();
      this.html = var6;
      RawBitmap var7 = var1.getRawBitmap();
      this.rawBitmap = var7;
      Map var8 = Collections.synchronizedMap(new HashMap());
      this.serializedObjects = var8;
      int var9 = 0;

      while(true) {
         int var10 = this.nativeFormats.length;
         if(var9 >= var10) {
            return;
         }

         DataFlavor var11 = null;

         label25: {
            DataFlavor var12;
            try {
               var12 = SystemFlavorMap.decodeDataFlavor(this.nativeFormats[var9]);
            } catch (ClassNotFoundException var17) {
               break label25;
            }

            var11 = var12;
         }

         if(var11 != null) {
            Class var13 = var11.getRepresentationClass();
            byte[] var14 = var1.getSerializedObject(var13);
            if(var14 != null) {
               this.serializedObjects.put(var13, var14);
            }
         }

         ++var9;
      }
   }

   public String[] getFileList() {
      return this.fileList;
   }

   public String getHTML() {
      return this.html;
   }

   public String[] getNativeFormats() {
      return this.nativeFormats;
   }

   public RawBitmap getRawBitmap() {
      return this.rawBitmap;
   }

   public short[] getRawBitmapBuffer16() {
      short[] var1;
      if(this.rawBitmap != null && this.rawBitmap.buffer instanceof short[]) {
         var1 = (short[])this.rawBitmap.buffer;
      } else {
         var1 = null;
      }

      return var1;
   }

   public int[] getRawBitmapBuffer32() {
      int[] var1;
      if(this.rawBitmap != null && this.rawBitmap.buffer instanceof int[]) {
         var1 = (int[])this.rawBitmap.buffer;
      } else {
         var1 = null;
      }

      return var1;
   }

   public byte[] getRawBitmapBuffer8() {
      byte[] var1;
      if(this.rawBitmap != null && this.rawBitmap.buffer instanceof byte[]) {
         var1 = (byte[])this.rawBitmap.buffer;
      } else {
         var1 = null;
      }

      return var1;
   }

   public int[] getRawBitmapHeader() {
      int[] var1;
      if(this.rawBitmap != null) {
         var1 = this.rawBitmap.getHeader();
      } else {
         var1 = null;
      }

      return var1;
   }

   public byte[] getSerializedObject(Class<?> var1) {
      return (byte[])this.serializedObjects.get(var1);
   }

   public byte[] getSerializedObject(String var1) {
      byte[] var3;
      byte[] var4;
      try {
         Class var2 = SystemFlavorMap.decodeDataFlavor(var1).getRepresentationClass();
         var3 = this.getSerializedObject(var2);
      } catch (Exception var6) {
         var4 = null;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   public String getText() {
      return this.text;
   }

   public String getURL() {
      return this.url;
   }

   public boolean isNativeFormatAvailable(String var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(var1.equals("text/plain")) {
         if(this.text != null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else if(var1.equals("application/x-java-file-list")) {
         if(this.fileList != null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else if(var1.equals("application/x-java-url")) {
         if(this.url != null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else if(var1.equals("text/html")) {
         if(this.html != null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else if(var1.equals("image/x-java-image")) {
         if(this.rawBitmap != null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         boolean var6;
         try {
            DataFlavor var3 = SystemFlavorMap.decodeDataFlavor(var1);
            Map var4 = this.serializedObjects;
            Class var5 = var3.getRepresentationClass();
            var6 = var4.containsKey(var5);
         } catch (Exception var8) {
            var2 = false;
            return var2;
         }

         var2 = var6;
      }

      return var2;
   }
}
