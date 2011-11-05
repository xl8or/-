package org.apache.harmony.awt.datatransfer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.RawBitmap;

public class DataSource implements DataProvider {

   protected final Transferable contents;
   private DataFlavor[] flavors;
   private List<String> nativeFormats;


   public DataSource(Transferable var1) {
      this.contents = var1;
   }

   private RawBitmap getImageBitmap(Image var1) {
      RawBitmap var3;
      if(var1 instanceof BufferedImage) {
         BufferedImage var2 = (BufferedImage)var1;
         if(var2.getType() == 1) {
            var3 = this.getImageBitmap32(var2);
            return var3;
         }
      }

      int var4 = var1.getWidth((ImageObserver)null);
      int var5 = var1.getHeight((ImageObserver)null);
      if(var4 > 0 && var5 > 0) {
         BufferedImage var6 = new BufferedImage(var4, var5, 1);
         Graphics var7 = var6.getGraphics();
         var7.drawImage(var1, 0, 0, (ImageObserver)null);
         var7.dispose();
         var3 = this.getImageBitmap32(var6);
      } else {
         var3 = null;
      }

      return var3;
   }

   private RawBitmap getImageBitmap32(BufferedImage var1) {
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      int[] var4 = new int[var2 * var3];
      DataBufferInt var5 = (DataBufferInt)var1.getRaster().getDataBuffer();
      int var6 = 0;
      int var7 = var5.getNumBanks();
      int[] var8 = var5.getOffsets();

      for(int var9 = 0; var9 < var7; ++var9) {
         int[] var13 = var5.getData(var9);
         int var14 = var8[var9];
         int var15 = var13.length;
         int var16 = var8[var9];
         int var17 = var15 - var16;
         System.arraycopy(var13, var14, var4, var6, var17);
         int var18 = var13.length;
         int var19 = var8[var9];
         int var20 = var18 - var19;
         var6 += var20;
      }

      int var10 = var1.getWidth();
      int var11 = var1.getHeight();
      int var12 = var1.getWidth();
      return new RawBitmap(var10, var11, var12, 32, 16711680, '\uff00', 255, var4);
   }

   private static List<String> getNativesForFlavors(DataFlavor[] var0) {
      ArrayList var1 = new ArrayList();
      SystemFlavorMap var2 = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var1;
         }

         DataFlavor var5 = var0[var3];
         Iterator var6 = var2.getNativesForFlavor(var5).iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            if(!var1.contains(var7)) {
               var1.add(var7);
            }
         }

         ++var3;
      }
   }

   private String getText(boolean var1) {
      DataFlavor[] var2 = this.contents.getTransferDataFlavors();
      int var3 = 0;

      String var5;
      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            var5 = null;
            break;
         }

         DataFlavor var6 = var2[var3];
         if(var6.isFlavorTextType() && (!var1 || this.isHtmlFlavor(var6))) {
            label43: {
               String var10;
               try {
                  Class var7 = var6.getRepresentationClass();
                  if(String.class.isAssignableFrom(var7)) {
                     String var13 = (String)this.contents.getTransferData(var6);
                     var5 = var13;
                     break;
                  }

                  Transferable var8 = this.contents;
                  Reader var9 = var6.getReaderForText(var8);
                  var10 = this.getTextFromReader(var9);
               } catch (Exception var12) {
                  break label43;
               }

               var5 = var10;
               break;
            }
         }

         ++var3;
      }

      return var5;
   }

   private String getTextFromReader(Reader var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      char[] var3 = new char[1024];

      while(true) {
         int var4 = var1.read(var3);
         if(var4 <= 0) {
            return var2.toString();
         }

         var2.append(var3, 0, var4);
      }
   }

   private boolean isHtmlFlavor(DataFlavor var1) {
      String var2 = var1.getSubType();
      return "html".equalsIgnoreCase(var2);
   }

   protected DataFlavor[] getDataFlavors() {
      if(this.flavors == null) {
         DataFlavor[] var1 = this.contents.getTransferDataFlavors();
         this.flavors = var1;
      }

      return this.flavors;
   }

   public String[] getFileList() {
      String[] var5;
      String[] var8;
      try {
         Transferable var1 = this.contents;
         DataFlavor var2 = DataFlavor.javaFileListFlavor;
         List var3 = (List)var1.getTransferData(var2);
         String[] var4 = new String[var3.size()];
         var8 = (String[])var3.toArray(var4);
      } catch (Exception var7) {
         var5 = null;
         return var5;
      }

      var5 = var8;
      return var5;
   }

   public String getHTML() {
      return this.getText((boolean)1);
   }

   public String[] getNativeFormats() {
      List var1 = this.getNativeFormatsList();
      String[] var2 = new String[0];
      return (String[])var1.toArray(var2);
   }

   public List<String> getNativeFormatsList() {
      if(this.nativeFormats == null) {
         List var1 = getNativesForFlavors(this.getDataFlavors());
         this.nativeFormats = var1;
      }

      return this.nativeFormats;
   }

   public RawBitmap getRawBitmap() {
      DataFlavor[] var1 = this.contents.getTransferDataFlavors();
      int var2 = 0;

      RawBitmap var4;
      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            var4 = null;
            break;
         }

         DataFlavor var5 = var1[var2];
         Class var6 = var5.getRepresentationClass();
         if(var6 != null && Image.class.isAssignableFrom(var6)) {
            DataFlavor var7 = DataFlavor.imageFlavor;
            if(var5.isMimeTypeEqual(var7) || var5.isFlavorSerializedObjectType()) {
               label38: {
                  RawBitmap var9;
                  try {
                     Image var8 = (Image)this.contents.getTransferData(var5);
                     var9 = this.getImageBitmap(var8);
                  } catch (Throwable var11) {
                     break label38;
                  }

                  var4 = var9;
                  break;
               }
            }
         }

         ++var2;
      }

      return var4;
   }

   public byte[] getSerializedObject(Class<?> var1) {
      byte[] var5;
      byte[] var6;
      try {
         DataFlavor var2 = new DataFlavor(var1, (String)null);
         Serializable var3 = (Serializable)this.contents.getTransferData(var2);
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         (new ObjectOutputStream(var4)).writeObject(var3);
         var5 = var4.toByteArray();
      } catch (Throwable var8) {
         var6 = null;
         return var6;
      }

      var6 = var5;
      return var6;
   }

   public String getText() {
      return this.getText((boolean)0);
   }

   public String getURL() {
      String var3;
      String var4;
      try {
         Transferable var1 = this.contents;
         DataFlavor var2 = urlFlavor;
         var3 = ((URL)var1.getTransferData(var2)).toString();
      } catch (Exception var13) {
         try {
            Transferable var6 = this.contents;
            DataFlavor var7 = uriFlavor;
            var3 = ((URL)var6.getTransferData(var7)).toString();
         } catch (Exception var12) {
            try {
               String var9 = this.getText();
               var3 = (new URL(var9)).toString();
            } catch (Exception var11) {
               var4 = null;
               return var4;
            }

            var4 = var3;
            return var4;
         }

         var4 = var3;
         return var4;
      }

      var4 = var3;
      return var4;
   }

   public boolean isNativeFormatAvailable(String var1) {
      return this.getNativeFormatsList().contains(var1);
   }
}
