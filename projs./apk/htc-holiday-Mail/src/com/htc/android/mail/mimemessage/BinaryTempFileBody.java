package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.Base64OutputStream;
import com.htc.android.mail.mimemessage.Body;
import com.htc.android.mail.mimemessage.MessagingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

public class BinaryTempFileBody implements Body {

   private static File mTempDirectory;
   private File mFile;


   public BinaryTempFileBody() throws IOException {
      if(mTempDirectory == null) {
         mTempDirectory = new File("/data/data/com.htc.android.mail/cache/");
         if(!mTempDirectory.isDirectory()) {
            boolean var1 = mTempDirectory.mkdir();
         }
      }
   }

   public static void setTempDirectory(File var0) {
      mTempDirectory = var0;
   }

   public InputStream getInputStream() throws MessagingException {
      try {
         File var1 = this.mFile;
         FileInputStream var2 = new FileInputStream(var1);
         BinaryTempFileBody.BinaryTempFileBodyInputStream var3 = new BinaryTempFileBody.BinaryTempFileBodyInputStream(var2);
         return var3;
      } catch (IOException var5) {
         throw new MessagingException("Unable to open body", var5);
      }
   }

   public OutputStream getOutputStream() throws IOException {
      File var1 = mTempDirectory;
      File var2 = File.createTempFile("body", (String)null, var1);
      this.mFile = var2;
      this.mFile.deleteOnExit();
      File var3 = this.mFile;
      return new FileOutputStream(var3);
   }

   public void setFile(String var1) {
      File var2 = new File(var1);
      this.mFile = var2;
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      InputStream var2 = this.getInputStream();
      Base64OutputStream var3 = new Base64OutputStream(var1);
      IOUtils.copy(var2, (OutputStream)var3);
      var3.close();
      boolean var5 = this.mFile.delete();
   }

   class BinaryTempFileBodyInputStream extends FilterInputStream {

      public BinaryTempFileBodyInputStream(InputStream var2) {
         super(var2);
      }

      public void close() throws IOException {
         super.close();
         boolean var1 = BinaryTempFileBody.this.mFile.delete();
      }
   }
}
