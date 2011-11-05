package org.apache.harmony.awt.datatransfer;

import java.awt.Image;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.RawBitmap;
import org.apache.harmony.awt.internal.nls.Messages;

public final class DataProxy implements Transferable {

   public static final Class[] charsetTextClasses;
   public static final Class[] unicodeTextClasses;
   private final DataProvider data;
   private final SystemFlavorMap flavorMap;


   static {
      Class[] var0 = new Class[]{String.class, Reader.class, CharBuffer.class, char[].class};
      unicodeTextClasses = var0;
      Class[] var1 = new Class[]{byte[].class, ByteBuffer.class, InputStream.class};
      charsetTextClasses = var1;
   }

   public DataProxy(DataProvider var1) {
      this.data = var1;
      SystemFlavorMap var2 = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();
      this.flavorMap = var2;
   }

   private BufferedImage createBufferedImage(RawBitmap var1) {
      BufferedImage var2;
      if(var1 != null && var1.buffer != null && var1.width > 0 && var1.height > 0) {
         Object var3 = null;
         WritableRaster var4 = null;
         if(var1.bits == 32 && var1.buffer instanceof int[]) {
            if(!this.isRGB(var1) && !this.isBGR(var1)) {
               var2 = null;
               return var2;
            }

            int[] var5 = new int[3];
            int var6 = var1.rMask;
            var5[0] = var6;
            int var7 = var1.gMask;
            var5[1] = var7;
            int var8 = var1.bMask;
            var5[2] = var8;
            int[] var9 = (int[])var1.buffer;
            DirectColorModel var10 = new DirectColorModel;
            int var11 = var1.rMask;
            int var12 = var1.gMask;
            int var13 = var1.bMask;
            byte var15 = 24;
            var10.<init>(var15, var11, var12, var13);
            DataBufferInt var19 = new DataBufferInt;
            int var20 = var9.length;
            var19.<init>(var9, var20);
            int var24 = var1.width;
            int var25 = var1.height;
            int var26 = var1.stride;
            var4 = Raster.createPackedRaster(var19, var24, var25, var26, var5, (Point)null);
            var3 = var10;
         } else if(var1.bits == 24 && var1.buffer instanceof byte[]) {
            int[] var27 = new int[]{8, 8, 8};
            int[] var28;
            if(this.isRGB(var1)) {
               var28 = new int[]{0, 1, 2};
            } else {
               if(!this.isBGR(var1)) {
                  var2 = null;
                  return var2;
               }

               var28 = new int[]{2, 1, 0};
            }

            byte[] var29 = (byte[])var1.buffer;
            ColorSpace var30 = ColorSpace.getInstance(1000);
            var3 = new ComponentColorModel(var30, var27, (boolean)0, (boolean)0, 1, 0);
            DataBufferByte var31 = new DataBufferByte;
            int var32 = var29.length;
            var31.<init>(var29, var32);
            int var36 = var1.width;
            int var37 = var1.height;
            int var38 = var1.stride;
            var4 = Raster.createInterleavedRaster(var31, var36, var37, var38, 3, var28, (Point)null);
         } else if((var1.bits == 16 || var1.bits == 15) && var1.buffer instanceof short[]) {
            int[] var39 = new int[3];
            int var40 = var1.rMask;
            var39[0] = var40;
            int var41 = var1.gMask;
            var39[1] = var41;
            int var42 = var1.bMask;
            var39[2] = var42;
            short[] var43 = (short[])var1.buffer;
            int var44 = var1.bits;
            int var45 = var1.rMask;
            int var46 = var1.gMask;
            int var47 = var1.bMask;
            var3 = new DirectColorModel(var44, var45, var46, var47);
            DataBufferUShort var48 = new DataBufferUShort;
            int var49 = var43.length;
            var48.<init>(var43, var49);
            int var53 = var1.width;
            int var54 = var1.height;
            int var55 = var1.stride;
            var4 = Raster.createPackedRaster(var48, var53, var54, var55, var39, (Point)null);
         }

         if(var3 != null && var4 != null) {
            var2 = new BufferedImage;
            byte var60 = 0;
            Object var61 = null;
            var2.<init>((ColorModel)var3, var4, (boolean)var60, (Hashtable)var61);
         } else {
            var2 = null;
         }
      } else {
         var2 = null;
      }

      return var2;
   }

   private String getCharset(DataFlavor var1) {
      return var1.getParameter("charset");
   }

   private Object getFileList(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if(!this.data.isNativeFormatAvailable("application/x-java-file-list")) {
         throw new UnsupportedFlavorException(var1);
      } else {
         String[] var2 = this.data.getFileList();
         if(var2 == null) {
            String var3 = Messages.getString("awt.4F");
            throw new IOException(var3);
         } else {
            return Arrays.asList(var2);
         }
      }
   }

   private Object getHTML(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if(!this.data.isNativeFormatAvailable("text/html")) {
         throw new UnsupportedFlavorException(var1);
      } else {
         String var2 = this.data.getHTML();
         if(var2 == null) {
            String var3 = Messages.getString("awt.4F");
            throw new IOException(var3);
         } else {
            return this.getTextRepresentation(var2, var1);
         }
      }
   }

   private Image getImage(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if(!this.data.isNativeFormatAvailable("image/x-java-image")) {
         throw new UnsupportedFlavorException(var1);
      } else {
         RawBitmap var2 = this.data.getRawBitmap();
         if(var2 == null) {
            String var3 = Messages.getString("awt.4F");
            throw new IOException(var3);
         } else {
            return this.createBufferedImage(var2);
         }
      }
   }

   private Object getPlainText(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if(!this.data.isNativeFormatAvailable("text/plain")) {
         throw new UnsupportedFlavorException(var1);
      } else {
         String var2 = this.data.getText();
         if(var2 == null) {
            String var3 = Messages.getString("awt.4F");
            throw new IOException(var3);
         } else {
            return this.getTextRepresentation(var2, var1);
         }
      }
   }

   private Object getSerializedObject(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      String var2 = SystemFlavorMap.encodeDataFlavor(var1);
      if(var2 != null && this.data.isNativeFormatAvailable(var2)) {
         DataProvider var3 = this.data;
         Class var4 = var1.getRepresentationClass();
         byte[] var5 = var3.getSerializedObject(var4);
         if(var5 == null) {
            String var6 = Messages.getString("awt.4F");
            throw new IOException(var6);
         } else {
            ByteArrayInputStream var7 = new ByteArrayInputStream(var5);

            try {
               Object var8 = (new ObjectInputStream(var7)).readObject();
               return var8;
            } catch (ClassNotFoundException var10) {
               String var9 = var10.getMessage();
               throw new IOException(var9);
            }
         }
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   private Object getTextRepresentation(String var1, DataFlavor var2) throws UnsupportedFlavorException, IOException {
      Object var3;
      if(var2.getRepresentationClass() == String.class) {
         var3 = var1;
      } else if(var2.isRepresentationClassReader()) {
         var3 = new StringReader(var1);
      } else if(var2.isRepresentationClassCharBuffer()) {
         var3 = CharBuffer.wrap(var1);
      } else if(var2.getRepresentationClass() == char[].class) {
         char[] var4 = new char[var1.length()];
         int var5 = var1.length();
         var1.getChars(0, var5, var4, 0);
         var3 = var4;
      } else {
         String var6 = this.getCharset(var2);
         if(var2.getRepresentationClass() == byte[].class) {
            var3 = var1.getBytes(var6);
         } else if(var2.isRepresentationClassByteBuffer()) {
            var3 = ByteBuffer.wrap(var1.getBytes(var6));
         } else {
            if(!var2.isRepresentationClassInputStream()) {
               throw new UnsupportedFlavorException(var2);
            }

            byte[] var7 = var1.getBytes(var6);
            var3 = new ByteArrayInputStream(var7);
         }
      }

      return var3;
   }

   private Object getURL(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if(!this.data.isNativeFormatAvailable("application/x-java-url")) {
         throw new UnsupportedFlavorException(var1);
      } else {
         String var2 = this.data.getURL();
         if(var2 == null) {
            String var3 = Messages.getString("awt.4F");
            throw new IOException(var3);
         } else {
            URL var4 = new URL(var2);
            Object var5;
            if(var1.getRepresentationClass().isAssignableFrom(URL.class)) {
               var5 = var4;
            } else {
               if(!var1.isFlavorTextType()) {
                  throw new UnsupportedFlavorException(var1);
               }

               String var6 = var4.toString();
               var5 = this.getTextRepresentation(var6, var1);
            }

            return var5;
         }
      }
   }

   private boolean isBGR(RawBitmap var1) {
      boolean var2;
      if(var1.rMask == 255 && var1.gMask == '\uff00' && var1.bMask == 16711680) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isRGB(RawBitmap var1) {
      boolean var2;
      if(var1.rMask == 16711680 && var1.gMask == '\uff00' && var1.bMask == 255) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public DataProvider getDataProvider() {
      return this.data;
   }

   public Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException, IOException {
      String var2 = String.valueOf(var1.getPrimaryType());
      StringBuilder var3 = (new StringBuilder(var2)).append("/");
      String var4 = var1.getSubType();
      String var5 = var3.append(var4).toString();
      Object var6;
      if(var1.isFlavorTextType()) {
         if(var5.equalsIgnoreCase("text/html")) {
            var6 = this.getHTML(var1);
         } else if(var5.equalsIgnoreCase("text/uri-list")) {
            var6 = this.getURL(var1);
         } else {
            var6 = this.getPlainText(var1);
         }
      } else if(var1.isFlavorJavaFileListType()) {
         var6 = this.getFileList(var1);
      } else if(var1.isFlavorSerializedObjectType()) {
         var6 = this.getSerializedObject(var1);
      } else {
         DataFlavor var7 = DataProvider.urlFlavor;
         if(var1.equals(var7)) {
            var6 = this.getURL(var1);
         } else {
            if(var5.equalsIgnoreCase("image/x-java-image")) {
               Class var8 = var1.getRepresentationClass();
               if(Image.class.isAssignableFrom(var8)) {
                  var6 = this.getImage(var1);
                  return var6;
               }
            }

            throw new UnsupportedFlavorException(var1);
         }
      }

      return var6;
   }

   public DataFlavor[] getTransferDataFlavors() {
      ArrayList var1 = new ArrayList();
      String[] var2 = this.data.getNativeFormats();
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            DataFlavor[] var5 = new DataFlavor[var1.size()];
            return (DataFlavor[])var1.toArray(var5);
         }

         SystemFlavorMap var6 = this.flavorMap;
         String var7 = var2[var3];
         Iterator var8 = var6.getFlavorsForNative(var7).iterator();

         while(var8.hasNext()) {
            DataFlavor var9 = (DataFlavor)var8.next();
            if(!var1.contains(var9)) {
               var1.add(var9);
            }
         }

         ++var3;
      }
   }

   public boolean isDataFlavorSupported(DataFlavor var1) {
      DataFlavor[] var2 = this.getTransferDataFlavors();
      int var3 = 0;

      boolean var5;
      while(true) {
         int var4 = var2.length;
         if(var3 >= var4) {
            var5 = false;
            break;
         }

         if(var2[var3].equals(var1)) {
            var5 = true;
            break;
         }

         ++var3;
      }

      return var5;
   }
}
