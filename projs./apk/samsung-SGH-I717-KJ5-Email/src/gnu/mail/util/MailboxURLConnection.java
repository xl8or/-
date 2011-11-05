package gnu.mail.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;

public class MailboxURLConnection extends URLConnection {

   protected Folder folder;
   private List headerKeys;
   protected Map headers;
   protected Message message;
   protected Store store;


   public MailboxURLConnection(URL var1) {
      super(var1);
   }

   protected static URLName asURLName(URL var0) {
      String var1 = var0.getProtocol();
      String var2 = var0.getHost();
      int var3 = var0.getPort();
      String var4 = var0.getUserInfo();
      String var5 = var0.getPath();
      String var9;
      String var10;
      if(var4 != null) {
         int var6 = var4.indexOf(58);
         String var7;
         if(var6 != -1) {
            var7 = var4.substring(0, var6);
         } else {
            var7 = var4;
         }

         if(var6 != -1) {
            int var8 = var6 + 1;
            var4 = var4.substring(var8);
         } else {
            var4 = null;
         }

         var9 = var7;
         var10 = var4;
      } else {
         var10 = null;
         var9 = null;
      }

      return new URLName(var1, var2, var3, var5, var9, var10);
   }

   public void connect() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Object getContent() throws IOException {
      Object var1;
      if(this.message != null) {
         var1 = this.message;
      } else {
         var1 = this.folder;
      }

      return var1;
   }

   public String getHeaderField(int var1) {
      String var2 = this.getHeaderFieldKey(var1);
      return this.getHeaderField(var2);
   }

   public String getHeaderField(String var1) {
      return (String)this.headers.get(var1);
   }

   public String getHeaderFieldKey(int var1) {
      return (String)this.headerKeys.get(var1);
   }

   public Map getHeaderFields() {
      return Collections.unmodifiableMap(this.headers);
   }

   public InputStream getInputStream() throws IOException {
      PipedOutputStream var1 = new PipedOutputStream();
      Object var3;
      if(this.message == null) {
         Folder var2 = this.folder;
         var3 = new MailboxURLConnection.FolderWriter(var2, var1);
      } else {
         Message var4 = this.message;
         var3 = new MailboxURLConnection.MessageWriter(var4, var1);
      }

      (new Thread((Runnable)var3, "MailboxURLConnection.getInputStream")).start();
      return new PipedInputStream(var1);
   }

   static class FolderWriter implements Runnable {

      Folder folder;
      OutputStream out;


      FolderWriter(Folder var1, OutputStream var2) {
         this.folder = var1;
         this.out = var2;
      }

      public void run() {}
   }

   static class MessageWriter implements Runnable {

      Message message;
      OutputStream out;


      MessageWriter(Message var1, OutputStream var2) {
         this.message = var1;
         this.out = var2;
      }

      public void run() {
         try {
            if(this.message instanceof MimeMessage) {
               MimeMessage var1 = (MimeMessage)this.message;
               OutputStream var2 = this.out;
               var1.writeTo(var2);
            }
         } catch (Exception var4) {
            ;
         }
      }
   }
}
