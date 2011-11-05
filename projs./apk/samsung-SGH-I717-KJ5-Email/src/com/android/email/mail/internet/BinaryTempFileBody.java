package com.android.email.mail.internet;

import android.content.Context;
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

public class BinaryTempFileBody implements Body {

   private File mFile;


   public BinaryTempFileBody() throws IOException {
      if(Email.getTempDirectory() == null) {
         throw new RuntimeException("setTempDirectory has not been called on BinaryTempFileBody!");
      }
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

   public void writeTo(Context var1, long var2, OutputStream var4) throws IOException, MessagingException {}

   public void writeTo(OutputStream param1) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
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
