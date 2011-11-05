package com.google.android.common.http;

import com.google.android.common.http.FilePartSource;
import com.google.android.common.http.PartBase;
import com.google.android.common.http.PartSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public class FilePart extends PartBase {

   public static final String DEFAULT_CHARSET = "ISO-8859-1";
   public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
   public static final String DEFAULT_TRANSFER_ENCODING = "binary";
   protected static final String FILE_NAME = "; filename=";
   private static final byte[] FILE_NAME_BYTES = EncodingUtils.getAsciiBytes("; filename=");
   private PartSource source;


   public FilePart(String var1, PartSource var2) {
      this(var1, var2, (String)null, (String)null);
   }

   public FilePart(String var1, PartSource var2, String var3, String var4) {
      if(var3 == null) {
         var3 = "application/octet-stream";
      }

      if(var4 == null) {
         var4 = "ISO-8859-1";
      }

      super(var1, var3, var4, "binary");
      if(var2 == null) {
         throw new IllegalArgumentException("Source may not be null");
      } else {
         this.source = var2;
      }
   }

   public FilePart(String var1, File var2) throws FileNotFoundException {
      FilePartSource var3 = new FilePartSource(var2);
      this(var1, (PartSource)var3, (String)null, (String)null);
   }

   public FilePart(String var1, File var2, String var3, String var4) throws FileNotFoundException {
      FilePartSource var5 = new FilePartSource(var2);
      this(var1, (PartSource)var5, var3, var4);
   }

   public FilePart(String var1, String var2, File var3) throws FileNotFoundException {
      FilePartSource var4 = new FilePartSource(var2, var3);
      this(var1, (PartSource)var4, (String)null, (String)null);
   }

   public FilePart(String var1, String var2, File var3, String var4, String var5) throws FileNotFoundException {
      FilePartSource var6 = new FilePartSource(var2, var3);
      this(var1, (PartSource)var6, var4, var5);
   }

   protected PartSource getSource() {
      return this.source;
   }

   protected long lengthOfData() {
      return this.source.getLength();
   }

   protected void sendData(OutputStream var1) throws IOException {
      if(this.lengthOfData() != 0L) {
         byte[] var2 = new byte[4096];
         InputStream var3 = this.source.createInputStream();

         try {
            while(true) {
               int var4 = var3.read(var2);
               if(var4 < 0) {
                  return;
               }

               var1.write(var2, 0, var4);
            }
         } finally {
            var3.close();
         }
      }
   }

   protected void sendDispositionHeader(OutputStream var1) throws IOException {
      super.sendDispositionHeader(var1);
      String var2 = this.source.getFileName();
      if(var2 != null) {
         byte[] var3 = FILE_NAME_BYTES;
         var1.write(var3);
         byte[] var4 = QUOTE_BYTES;
         var1.write(var4);
         byte[] var5 = EncodingUtils.getAsciiBytes(var2);
         var1.write(var5);
         byte[] var6 = QUOTE_BYTES;
         var1.write(var6);
      }
   }
}
