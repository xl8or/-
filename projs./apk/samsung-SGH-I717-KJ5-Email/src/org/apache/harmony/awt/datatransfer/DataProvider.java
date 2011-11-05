package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import org.apache.harmony.awt.datatransfer.RawBitmap;

public interface DataProvider {

   String FORMAT_FILE_LIST = "application/x-java-file-list";
   String FORMAT_HTML = "text/html";
   String FORMAT_IMAGE = "image/x-java-image";
   String FORMAT_TEXT = "text/plain";
   String FORMAT_URL = "application/x-java-url";
   String TYPE_FILELIST = "application/x-java-file-list";
   String TYPE_HTML = "text/html";
   String TYPE_IMAGE = "image/x-java-image";
   String TYPE_PLAINTEXT = "text/plain";
   String TYPE_SERIALIZED = "application/x-java-serialized-object";
   String TYPE_TEXTENCODING = "application/x-java-text-encoding";
   String TYPE_URILIST = "text/uri-list";
   String TYPE_URL = "application/x-java-url";
   DataFlavor uriFlavor = new DataFlavor("text/uri-list", "URI");
   DataFlavor urlFlavor = new DataFlavor("application/x-java-url;class=java.net.URL", "URL");


   String[] getFileList();

   String getHTML();

   String[] getNativeFormats();

   RawBitmap getRawBitmap();

   byte[] getSerializedObject(Class<?> var1);

   String getText();

   String getURL();

   boolean isNativeFormatAvailable(String var1);
}
