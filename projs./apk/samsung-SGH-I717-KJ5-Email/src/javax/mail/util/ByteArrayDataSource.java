package javax.mail.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.activation.DataSource;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;

public class ByteArrayDataSource implements DataSource {

   private byte[] data;
   private String name = "";
   private String type;


   public ByteArrayDataSource(InputStream var1, String var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      byte[] var4 = new byte[4096];

      for(int var5 = var1.read(var4); var5 != -1; var5 = var1.read(var4)) {
         var3.write(var4, 0, var5);
      }

      byte[] var6 = var3.toByteArray();
      this.data = var6;
      this.type = var2;
   }

   public ByteArrayDataSource(String var1, String var2) throws IOException {
      try {
         String var3 = (new ContentType(var2)).getParameter("charset");
         String var4;
         if(var3 == null) {
            var4 = MimeUtility.getDefaultJavaCharset();
         } else {
            var4 = MimeUtility.javaCharset(var3);
         }

         if(var4 == null) {
            throw new UnsupportedEncodingException(var3);
         } else {
            byte[] var8 = var1.getBytes(var4);
            this.data = var8;
            this.type = var2;
         }
      } catch (ParseException var9) {
         IOException var6 = new IOException("can\'t parse MIME type");
         Throwable var7 = var6.initCause(var9);
         throw var6;
      }
   }

   public ByteArrayDataSource(byte[] var1, String var2) {
      this.data = var1;
      this.type = var2;
   }

   public String getContentType() {
      return this.type;
   }

   public InputStream getInputStream() throws IOException {
      byte[] var1 = this.data;
      return new ByteArrayInputStream(var1);
   }

   public String getName() {
      return this.name;
   }

   public OutputStream getOutputStream() throws IOException {
      return new ByteArrayDataSource.ErrorOutputStream();
   }

   public void setName(String var1) {
      this.name = var1;
   }

   static class ErrorOutputStream extends OutputStream {

      ErrorOutputStream() {}

      public void write(int var1) throws IOException {
         throw new IOException("writing to this stream is not allowed");
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         throw new IOException("writing to this stream is not allowed");
      }
   }
}
