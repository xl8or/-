package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.DataSource;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public interface DataContentHandler {

   Object getContent(DataSource var1) throws IOException;

   Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException;

   DataFlavor[] getTransferDataFlavors();

   void writeTo(Object var1, String var2, OutputStream var3) throws IOException;
}
