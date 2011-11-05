package gnu.mail.handler;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.activation.UnsupportedDataTypeException;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public abstract class Message implements DataContentHandler {

   protected DataFlavor flavor;


   protected Message(String var1, String var2) {
      ActivationDataFlavor var3 = new ActivationDataFlavor(javax.mail.Message.class, var1, var2);
      this.flavor = var3;
   }

   public Object getContent(DataSource param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException {
      Object var3;
      if(this.flavor.equals(var1)) {
         var3 = this.getContent(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] var1 = new DataFlavor[1];
      DataFlavor var2 = this.flavor;
      var1[0] = var2;
      return var1;
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if(var1 instanceof javax.mail.Message) {
         try {
            ((javax.mail.Message)var1).writeTo(var3);
         } catch (MessagingException var5) {
            String var4 = var5.getMessage();
            throw new IOException(var4);
         }
      } else {
         throw new UnsupportedDataTypeException();
      }
   }
}
