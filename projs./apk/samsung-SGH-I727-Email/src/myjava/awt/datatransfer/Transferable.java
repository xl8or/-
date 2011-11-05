package myjava.awt.datatransfer;

import java.io.IOException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public interface Transferable {

   Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException, IOException;

   DataFlavor[] getTransferDataFlavors();

   boolean isDataFlavorSupported(DataFlavor var1);
}
