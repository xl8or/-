package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.dnd.peer.DropTargetContextPeer;
import java.nio.charset.Charset;
import org.apache.harmony.awt.ContextStorage;
import org.apache.harmony.awt.datatransfer.DataProvider;
import org.apache.harmony.awt.datatransfer.DataTransferThread;
import org.apache.harmony.awt.datatransfer.NativeClipboard;
import org.apache.harmony.awt.datatransfer.TextFlavor;
import org.apache.harmony.awt.internal.nls.Messages;
import org.apache.harmony.misc.SystemUtils;

public abstract class DTK {

   protected final DataTransferThread dataTransferThread;
   private NativeClipboard nativeClipboard = null;
   private NativeClipboard nativeSelection = null;
   protected SystemFlavorMap systemFlavorMap;


   protected DTK() {
      DataTransferThread var1 = new DataTransferThread(this);
      this.dataTransferThread = var1;
      this.dataTransferThread.start();
   }

   private static DTK createDTK() {
      String var1;
      switch(SystemUtils.getOS()) {
      case 1:
         var1 = "org.apache.harmony.awt.datatransfer.windows.WinDTK";
         break;
      case 2:
         var1 = "org.apache.harmony.awt.datatransfer.linux.LinuxDTK";
         break;
      default:
         String var0 = Messages.getString("awt.4E");
         throw new RuntimeException(var0);
      }

      try {
         DTK var2 = (DTK)Class.forName(var1).newInstance();
         return var2;
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public static DTK getDTK() {
      Object var0 = ContextStorage.getContextLock();
      synchronized(var0) {
         DTK var4;
         if(ContextStorage.shutdownPending()) {
            var4 = null;
         } else {
            DTK var1 = ContextStorage.getDTK();
            if(var1 == null) {
               var1 = createDTK();
               ContextStorage.setDTK(var1);
            }

            var4 = var1;
         }

         return var4;
      }
   }

   protected void appendSystemFlavorMap(SystemFlavorMap var1, DataFlavor var2, String var3) {
      var1.addFlavorForUnencodedNative(var3, var2);
      var1.addUnencodedNativeForFlavor(var2, var3);
   }

   protected void appendSystemFlavorMap(SystemFlavorMap var1, String[] var2, String var3, String var4) {
      TextFlavor.addUnicodeClasses(var1, var4, var3);
      int var5 = 0;

      while(true) {
         int var6 = var2.length;
         if(var5 >= var6) {
            return;
         }

         if(var2[var5] != false && Charset.isSupported(var2[var5])) {
            String var7 = var2[var5];
            TextFlavor.addCharsetClasses(var1, var4, var3, var7);
         }

         ++var5;
      }
   }

   public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent var1);

   public abstract DropTargetContextPeer createDropTargetContextPeer(DropTargetContext var1);

   protected String[] getCharsets() {
      String[] var1 = new String[]{"UTF-16", "UTF-8", "unicode", "ISO-8859-1", "US-ASCII"};
      return var1;
   }

   public String getDefaultCharset() {
      return "unicode";
   }

   public NativeClipboard getNativeClipboard() {
      if(this.nativeClipboard == null) {
         NativeClipboard var1 = this.newNativeClipboard();
         this.nativeClipboard = var1;
      }

      return this.nativeClipboard;
   }

   public NativeClipboard getNativeSelection() {
      if(this.nativeSelection == null) {
         NativeClipboard var1 = this.newNativeSelection();
         this.nativeSelection = var1;
      }

      return this.nativeSelection;
   }

   public SystemFlavorMap getSystemFlavorMap() {
      synchronized(this){}

      SystemFlavorMap var1;
      try {
         var1 = this.systemFlavorMap;
      } finally {
         ;
      }

      return var1;
   }

   public abstract void initDragAndDrop();

   public void initSystemFlavorMap(SystemFlavorMap var1) {
      String[] var2 = this.getCharsets();
      DataFlavor var3 = DataFlavor.stringFlavor;
      this.appendSystemFlavorMap(var1, var3, "text/plain");
      this.appendSystemFlavorMap(var1, var2, "plain", "text/plain");
      this.appendSystemFlavorMap(var1, var2, "html", "text/html");
      DataFlavor var4 = DataProvider.urlFlavor;
      this.appendSystemFlavorMap(var1, var4, "application/x-java-url");
      this.appendSystemFlavorMap(var1, var2, "uri-list", "application/x-java-url");
      DataFlavor var5 = DataFlavor.javaFileListFlavor;
      this.appendSystemFlavorMap(var1, var5, "application/x-java-file-list");
      DataFlavor var6 = DataFlavor.imageFlavor;
      this.appendSystemFlavorMap(var1, var6, "image/x-java-image");
   }

   protected abstract NativeClipboard newNativeClipboard();

   protected abstract NativeClipboard newNativeSelection();

   public abstract void runEventLoop();

   public void setSystemFlavorMap(SystemFlavorMap var1) {
      synchronized(this){}

      try {
         this.systemFlavorMap = var1;
      } finally {
         ;
      }

   }
}
