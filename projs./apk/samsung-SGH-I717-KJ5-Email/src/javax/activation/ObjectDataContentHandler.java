package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.activation.UnsupportedDataTypeException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

class ObjectDataContentHandler implements DataContentHandler {

   private DataContentHandler dch;
   private DataFlavor[] flavors;
   private String mimeType;
   private Object object;


   public ObjectDataContentHandler(DataContentHandler var1, Object var2, String var3) {
      this.dch = var1;
      this.object = var2;
      this.mimeType = var3;
   }

   public Object getContent(DataSource var1) {
      return this.object;
   }

   public DataContentHandler getDCH() {
      return this.dch;
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException {
      Object var3;
      if(this.dch != null) {
         var3 = this.dch.getTransferData(var1, var2);
      } else {
         if(this.flavors == null) {
            DataFlavor[] var4 = this.getTransferDataFlavors();
         }

         DataFlavor var5 = this.flavors[0];
         if(!var1.equals(var5)) {
            throw new UnsupportedFlavorException(var1);
         }

         var3 = this.object;
      }

      return var3;
   }

   public DataFlavor[] getTransferDataFlavors() {
      if(this.flavors == null) {
         if(this.dch != null) {
            DataFlavor[] var1 = this.dch.getTransferDataFlavors();
            this.flavors = var1;
         } else {
            DataFlavor[] var2 = new DataFlavor[1];
            this.flavors = var2;
            DataFlavor[] var3 = this.flavors;
            Class var4 = this.object.getClass();
            String var5 = this.mimeType;
            String var6 = this.mimeType;
            ActivationDataFlavor var7 = new ActivationDataFlavor(var4, var5, var6);
            var3[0] = var7;
         }
      }

      return this.flavors;
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if(this.dch != null) {
         this.dch.writeTo(var1, var2, var3);
      } else {
         String var4 = "no object DCH for MIME type " + var2;
         throw new UnsupportedDataTypeException(var4);
      }
   }
}
