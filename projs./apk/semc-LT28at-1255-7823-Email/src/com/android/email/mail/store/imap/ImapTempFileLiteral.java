package com.android.email.mail.store.imap;

import android.util.Log;
import com.android.email.Email;
import com.android.email.FixedLengthInputStream;
import com.android.email.Utility;
import com.android.email.mail.store.imap.ImapString;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

public class ImapTempFileLiteral extends ImapString {

   final File mFile;
   private final int mSize;


   ImapTempFileLiteral(FixedLengthInputStream var1) throws IOException {
      int var2 = var1.getLength();
      this.mSize = var2;
      File var3 = Email.getTempDirectory();
      File var4 = File.createTempFile("imap", ".tmp", var3);
      this.mFile = var4;
      File var5 = this.mFile;
      FileOutputStream var6 = new FileOutputStream(var5);
      IOUtils.copy((InputStream)var1, (OutputStream)var6);
      var6.close();
   }

   public void destroy() {
      try {
         if(!this.isDestroyed() && this.mFile.exists()) {
            boolean var1 = this.mFile.delete();
         }
      } catch (RuntimeException var7) {
         StringBuilder var3 = (new StringBuilder()).append("Failed to remove temp file: ");
         String var4 = var7.getMessage();
         String var5 = var3.append(var4).toString();
         int var6 = Log.w("Email", var5);
      }

      super.destroy();
   }

   protected void finalize() throws Throwable {
      try {
         this.destroy();
      } finally {
         super.finalize();
      }

   }

   public InputStream getAsStream() {
      this.checkNotDestroyed();

      Object var2;
      try {
         File var1 = this.mFile;
         var2 = new FileInputStream(var1);
      } catch (FileNotFoundException var6) {
         int var4 = Log.w("Email", "ImapTempFileLiteral: Temp file not found");
         byte[] var5 = new byte[0];
         var2 = new ByteArrayInputStream(var5);
      }

      return (InputStream)var2;
   }

   public String getString() {
      this.checkNotDestroyed();

      String var1;
      String var2;
      try {
         var1 = Utility.fromAscii(IOUtils.toByteArray(this.getAsStream()));
      } catch (IOException var5) {
         int var4 = Log.w("Email", "ImapTempFileLiteral: Error while reading temp file");
         var2 = "";
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public boolean tempFileExistsForTest() {
      return this.mFile.exists();
   }

   public String toString() {
      Object[] var1 = new Object[1];
      Integer var2 = Integer.valueOf(this.mSize);
      var1[0] = var2;
      return String.format("{%d byte literal(file)}", var1);
   }
}
