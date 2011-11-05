package com.android.email.mail.internet;

import android.util.Base64OutputStream;
import com.android.email.Email;
import com.android.email.mail.Body;
import com.android.email.mail.MessagingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

public class BinaryTempFileBody implements Body {

   private File mFile;


   public BinaryTempFileBody() {}

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
      File var1 = Email.getTempDirectory();
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
      Base64OutputStream var3 = new Base64OutputStream(var1, 20);
      IOUtils.copy(var2, (OutputStream)var3);
      var3.close();
      var2.close();
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
