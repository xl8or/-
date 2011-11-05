package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.activation.UnsupportedDataTypeException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

class DataSourceDataContentHandler implements DataContentHandler {

   private DataContentHandler dch;
   private DataSource ds;
   private DataFlavor[] flavors;


   public DataSourceDataContentHandler(DataContentHandler var1, DataSource var2) {
      this.ds = var2;
      this.dch = var1;
   }

   public Object getContent(DataSource var1) throws IOException {
      Object var2;
      if(this.dch != null) {
         var2 = this.dch.getContent(var1);
      } else {
         var2 = var1.getInputStream();
      }

      return var2;
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException {
      Object var3;
      if(this.dch != null) {
         var3 = this.dch.getTransferData(var1, var2);
         return var3;
      } else {
         DataFlavor[] var4 = this.getTransferDataFlavors();
         if(var4.length > 0) {
            DataFlavor var5 = var4[0];
            if(var1.equals(var5)) {
               var3 = var2.getInputStream();
               return var3;
            }
         }

         throw new UnsupportedFlavorException(var1);
      }
   }

   public DataFlavor[] getTransferDataFlavors() {
      if(this.flavors == null) {
         if(this.dch != null) {
            DataFlavor[] var1 = this.dch.getTransferDataFlavors();
            this.flavors = var1;
         } else {
            String var2 = this.ds.getContentType();
            DataFlavor[] var3 = new DataFlavor[1];
            this.flavors = var3;
            DataFlavor[] var4 = this.flavors;
            ActivationDataFlavor var5 = new ActivationDataFlavor(var2, var2);
            var4[0] = var5;
         }
      }

      return this.flavors;
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if(this.dch == null) {
         StringBuilder var4 = (new StringBuilder()).append("no DCH for content type ");
         String var5 = this.ds.getContentType();
         String var6 = var4.append(var5).toString();
         throw new UnsupportedDataTypeException(var6);
      } else {
         this.dch.writeTo(var1, var2, var3);
      }
   }
}
