package javax.mail.internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class EASMIMEMessage extends MimeMessage {

   private int bufferSize;
   boolean isWriten = 0;
   InputStream mMessageInuputStream;


   public EASMIMEMessage(Folder var1, int var2) {
      super(var1, var2);
   }

   public EASMIMEMessage(Folder var1, InputStream var2, int var3) throws MessagingException {
      super(var1, var2, var3);
   }

   public EASMIMEMessage(Folder var1, InternetHeaders var2, byte[] var3, int var4) throws MessagingException {
      super(var1, var2, var3, var4);
   }

   public EASMIMEMessage(Session var1) {
      super(var1);
   }

   public EASMIMEMessage(Session var1, InputStream var2, int var3) throws MessagingException {
      super(var1);
      Flags var4 = new Flags();
      this.flags = var4;
      this.mMessageInuputStream = var2;
      InputStream var5 = this.mMessageInuputStream;
      InternetHeaders var6 = this.createInternetHeaders(var5);
      this.headers = var6;
      this.saved = (boolean)1;
      this.modified = (boolean)0;
      this.bufferSize = var3;
   }

   public EASMIMEMessage(MimeMessage var1) throws MessagingException {
      super(var1);
   }

   protected InputStream getContentStream() throws MessagingException {
      return this.mMessageInuputStream;
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      return super.getNonMatchingHeaders(var1);
   }

   public void writeTo(OutputStream var1, String[] var2) throws IOException, MessagingException, IllegalStateException {
      if(this.isWriten) {
         throw new IllegalStateException("MYMIMEMessage can be writen only one time");
      } else {
         long var3 = System.currentTimeMillis();
         if(var1 == null) {
            throw new IllegalArgumentException("Output stream may not be null");
         } else {
            if(!this.saved) {
               this.saveChanges();
            }

            String var5 = "US-ASCII";
            byte[] var6 = new byte[]{(byte)13, (byte)10};
            Enumeration var7 = this.getNonMatchingHeaderLines(var2);

            while(var7.hasMoreElements()) {
               String var8 = (String)var7.nextElement();
               StringTokenizer var9 = new StringTokenizer(var8, "\r\n");

               for(int var10 = 0; var9.hasMoreTokens(); ++var10) {
                  String var11 = var9.nextToken();
                  if(var10 > 0 && var11.charAt(0) != 9) {
                     var1.write(9);
                  }

                  short var12;
                  if(var10 > 0) {
                     var12 = 997;
                  } else {
                     var12 = 998;
                  }

                  String var14 = var11;

                  for(short var15 = var12; var14.length() > var15; var15 = 997) {
                     byte[] var16 = var14.substring(0, var15).getBytes(var5);
                     var1.write(var16);
                     var1.write(var6);
                     var1.write(9);
                     var14 = var14.substring(var15);
                  }

                  byte[] var17 = var14.getBytes(var5);
                  var1.write(var17);
                  var1.write(var6);
               }
            }

            var1.write(var6);
            var1.flush();
            if(!this.modified && (this.content != null || this.contentStream != null)) {
               InputStream var20 = this.mMessageInuputStream;
               byte[] var21 = new byte[this.bufferSize];
               int var23 = var20.read(var21);

               while(var23 != -1) {
                  var1.write(var21, 0, var23);
                  var20.read(var21);
               }

               var20.close();
               var1.flush();
               this.isWriten = (boolean)1;
            } else {
               String var18 = this.getEncoding();
               OutputStream var19 = MimeUtility.encode(var1, var18);
               this.getDataHandler().writeTo(var19);
               var19.flush();
            }
         }
      }
   }
}
