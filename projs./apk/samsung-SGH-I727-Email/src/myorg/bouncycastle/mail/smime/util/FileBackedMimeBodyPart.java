package myorg.bouncycastle.mail.smime.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import myorg.bouncycastle.mail.smime.util.SharedFileInputStream;

public class FileBackedMimeBodyPart extends MimeBodyPart {

   private static final int BUF_SIZE = 32760;
   private final File _file;


   public FileBackedMimeBodyPart(File var1) throws MessagingException, IOException {
      SharedFileInputStream var2 = new SharedFileInputStream(var1);
      super(var2);
      this._file = var1;
   }

   public FileBackedMimeBodyPart(InputStream var1, File var2) throws MessagingException, IOException {
      File var3 = saveStreamToFile(var1, var2);
      this(var3);
   }

   public FileBackedMimeBodyPart(InternetHeaders var1, InputStream var2, File var3) throws MessagingException, IOException {
      File var4 = saveStreamToFile(var1, var2, var3);
      this(var4);
   }

   private static void saveContentToStream(OutputStream var0, InputStream var1) throws IOException {
      byte[] var2 = new byte[32760];

      while(true) {
         int var3 = var2.length;
         int var4 = var1.read(var2, 0, var3);
         if(var4 <= 0) {
            var0.close();
            var1.close();
            return;
         }

         var0.write(var2, 0, var4);
      }
   }

   private static File saveStreamToFile(InputStream var0, File var1) throws IOException {
      saveContentToStream(new FileOutputStream(var1), var0);
      return var1;
   }

   private static File saveStreamToFile(InternetHeaders var0, InputStream var1, File var2) throws IOException {
      FileOutputStream var3 = new FileOutputStream(var2);
      Enumeration var4 = var0.getAllHeaderLines();

      while(var4.hasMoreElements()) {
         String var5 = (String)var4.nextElement();
         writeHeader(var3, var5);
      }

      writeSeperator(var3);
      saveContentToStream(var3, var1);
      return var2;
   }

   private static void writeHeader(OutputStream var0, String var1) throws IOException {
      int var2 = 0;

      while(true) {
         int var3 = var1.length();
         if(var2 == var3) {
            writeSeperator(var0);
            return;
         }

         char var4 = var1.charAt(var2);
         var0.write(var4);
         ++var2;
      }
   }

   private static void writeSeperator(OutputStream var0) throws IOException {
      var0.write(13);
      var0.write(10);
   }

   public void dispose() throws IOException {
      ((SharedFileInputStream)this.contentStream).getRoot().dispose();
      if(this._file.exists()) {
         if(!this._file.delete()) {
            StringBuilder var1 = (new StringBuilder()).append("deletion of underlying file <");
            String var2 = this._file.getCanonicalPath();
            String var3 = var1.append(var2).append("> failed.").toString();
            throw new IOException(var3);
         }
      }
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      if(!this._file.exists()) {
         StringBuilder var2 = (new StringBuilder()).append("file ");
         String var3 = this._file.getCanonicalPath();
         String var4 = var2.append(var3).append(" no longer exists.").toString();
         throw new IOException(var4);
      } else {
         super.writeTo(var1);
      }
   }
}
