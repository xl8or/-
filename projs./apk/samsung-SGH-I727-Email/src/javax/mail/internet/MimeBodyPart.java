package javax.mail.internet;

import gnu.inet.util.GetSystemPropertyAction;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.FileTypeMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ContentType;
import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import javax.mail.internet.SharedInputStream;

public class MimeBodyPart extends BodyPart implements MimePart {

   static final String CONTENT_DESCRIPTION_NAME = "Content-Description";
   static final String CONTENT_DISPOSITION_NAME = "Content-Disposition";
   static final String CONTENT_ID_NAME = "Content-ID";
   static final String CONTENT_LANGUAGE_NAME = "Content-Language";
   static final String CONTENT_MD5_NAME = "Content-MD5";
   static final String CONTENT_TRANSFER_ENCODING_NAME = "Content-Transfer-Encoding";
   static final String CONTENT_TYPE_NAME = "Content-Type";
   static final String TEXT_PLAIN = "text/plain";
   protected byte[] content;
   protected InputStream contentStream;
   protected DataHandler dh;
   protected InternetHeaders headers;


   public MimeBodyPart() {
      InternetHeaders var1 = new InternetHeaders();
      this.headers = var1;
   }

   public MimeBodyPart(InputStream var1) throws MessagingException {
      if(var1 instanceof SharedInputStream) {
         InternetHeaders var2 = new InternetHeaders(var1);
         this.headers = var2;
         SharedInputStream var3 = (SharedInputStream)var1;
         long var4 = var3.getPosition();
         InputStream var6 = var3.newStream(var4, 65535L);
         this.contentStream = var6;
      } else {
         Object var7;
         if(!(var1 instanceof ByteArrayInputStream) && !(var1 instanceof BufferedInputStream)) {
            var7 = new BufferedInputStream(var1);
         } else {
            var7 = var1;
         }

         InternetHeaders var8 = new InternetHeaders((InputStream)var7);
         this.headers = var8;
         short var9 = 1024;

         try {
            if(var7 instanceof ByteArrayInputStream) {
               int var10 = ((InputStream)var7).available();
               byte[] var11 = new byte[var10];
               this.content = var11;
               byte[] var12 = this.content;
               ((InputStream)var7).read(var12, 0, var10);
            } else {
               ByteArrayOutputStream var15 = new ByteArrayOutputStream(var9);
               byte[] var19 = new byte[var9];

               for(int var16 = ((InputStream)var7).read(var19); var16 != -1; var16 = ((InputStream)var7).read(var19)) {
                  var15.write(var19, 0, var16);
               }

               byte[] var17 = var15.toByteArray();
               this.content = var17;
            }
         } catch (IOException var18) {
            throw new MessagingException("I/O error", var18);
         }
      }
   }

   public MimeBodyPart(InternetHeaders var1, byte[] var2) throws MessagingException {
      this.headers = var1;
      this.content = var2;
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      this.headers.addHeader(var1, var2);
   }

   public void addHeaderLine(String var1) throws MessagingException {
      this.headers.addHeaderLine(var1);
   }

   public void attachFile(File var1) throws IOException, MessagingException {
      if(FileTypeMap.getDefaultFileTypeMap().getContentType(var1) == null) {
         String var2 = "Unable to determine MIME type of " + var1;
         throw new MessagingException(var2);
      } else {
         this.setContent(var1);
         String var3 = var1.getName();
         this.setFileName(var3);
      }
   }

   public void attachFile(String var1) throws IOException, MessagingException {
      File var2 = new File(var1);
      this.attachFile(var2);
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      return this.headers.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      return this.headers.getAllHeaders();
   }

   public Object getContent() throws IOException, MessagingException {
      return this.getDataHandler().getContent();
   }

   public String getContentID() throws MessagingException {
      return this.getHeader("Content-ID", (String)null);
   }

   public String[] getContentLanguage() throws MessagingException {
      String var1 = this.getHeader("Content-Language", (String)null);
      String[] var10;
      if(var1 != null) {
         HeaderTokenizer var2 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");
         ArrayList var9 = new ArrayList();
         boolean var3 = false;

         while(!var3) {
            HeaderTokenizer.Token var4 = var2.next();
            switch(var4.getType()) {
            case -4:
               var3 = true;
            case -3:
            case -2:
            default:
               break;
            case -1:
               String var5 = var4.getValue();
               var9.add(var5);
            }
         }

         if(var9.size() > 0) {
            String[] var7 = new String[var9.size()];
            var9.toArray(var7);
            var10 = var7;
            return var10;
         }
      }

      var10 = null;
      return var10;
   }

   public String getContentMD5() throws MessagingException {
      return this.getHeader("Content-MD5", (String)null);
   }

   protected InputStream getContentStream() throws MessagingException {
      Object var1;
      if(this.contentStream != null) {
         var1 = ((SharedInputStream)this.contentStream).newStream(0L, 65535L);
      } else {
         if(this.content == null) {
            throw new MessagingException("No content");
         }

         byte[] var2 = this.content;
         var1 = new ByteArrayInputStream(var2);
      }

      return (InputStream)var1;
   }

   public String getContentType() throws MessagingException {
      String var1 = this.getHeader("Content-Type", (String)null);
      if(var1 == null) {
         var1 = "text/plain";
      }

      return var1;
   }

   public DataHandler getDataHandler() throws MessagingException {
      if(this.dh == null) {
         MimePartDataSource var1 = new MimePartDataSource(this);
         DataHandler var2 = new DataHandler(var1);
         this.dh = var2;
      }

      return this.dh;
   }

   public String getDescription() throws MessagingException {
      String var1 = this.getHeader("Content-Description", (String)null);
      if(var1 != null) {
         String var2;
         try {
            var2 = MimeUtility.decodeText(var1);
         } catch (UnsupportedEncodingException var4) {
            return var1;
         }

         var1 = var2;
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getDisposition() throws MessagingException {
      String var1 = this.getHeader("Content-Disposition", (String)null);
      if(var1 != null) {
         var1 = (new ContentDisposition(var1)).getDisposition();
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getEncoding() throws MessagingException {
      HeaderTokenizer var1 = null;
      String var2 = this.getHeader("Content-Transfer-Encoding", var1);
      String var3;
      if(var2 != null) {
         var3 = var2.trim();
         if(!var3.equalsIgnoreCase("7bit") && !var3.equalsIgnoreCase("8bit") && !var3.equalsIgnoreCase("quoted-printable") && !var3.equalsIgnoreCase("base64")) {
            var1 = new HeaderTokenizer(var3, "()<>@,;:\\\"\t []/?=");
            boolean var4 = false;

            while(!var4) {
               HeaderTokenizer.Token var5 = var1.next();
               switch(var5.getType()) {
               case -4:
                  var4 = true;
               case -3:
               case -2:
               default:
                  break;
               case -1:
                  var3 = var5.getValue();
                  return var3;
               }
            }
         }
      } else {
         var3 = null;
      }

      return var3;
   }

   public String getFileName() throws MessagingException {
      String var1 = this.getHeader("Content-Disposition", (String)null);
      String var2;
      if(var1 != null) {
         var2 = (new ContentDisposition(var1)).getParameter("filename");
      } else {
         var2 = null;
      }

      if(var2 == null) {
         String var3 = this.getHeader("Content-Type", (String)null);
         if(var3 != null) {
            label27: {
               String var4;
               try {
                  var4 = (new ContentType(var3)).getParameter("name");
               } catch (ParseException var11) {
                  break label27;
               }

               var2 = var4;
            }
         }
      }

      Object var5 = AccessController.doPrivileged(new GetSystemPropertyAction("mail.mime.decodefilename"));
      if("true".equals(var5)) {
         String var6;
         try {
            var6 = MimeUtility.decodeText(var2);
         } catch (UnsupportedEncodingException var10) {
            String var8 = var10.getMessage();
            throw new MessagingException(var8, var10);
         }

         var2 = var6;
      }

      return var2;
   }

   public String getHeader(String var1, String var2) throws MessagingException {
      return this.headers.getHeader(var1, var2);
   }

   public String[] getHeader(String var1) throws MessagingException {
      return this.headers.getHeader(var1);
   }

   public InputStream getInputStream() throws IOException, MessagingException {
      return this.getDataHandler().getInputStream();
   }

   public int getLineCount() throws MessagingException {
      return -1;
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      return this.headers.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      return this.headers.getMatchingHeaders(var1);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      return this.headers.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      return this.headers.getNonMatchingHeaders(var1);
   }

   public InputStream getRawInputStream() throws MessagingException {
      return this.getContentStream();
   }

   public int getSize() throws MessagingException {
      int var1;
      if(this.content != null) {
         var1 = this.content.length;
      } else {
         if(this.contentStream != null) {
            label18: {
               int var2;
               try {
                  var2 = this.contentStream.available();
               } catch (IOException var4) {
                  break label18;
               }

               var1 = var2;
               if(var2 > 0) {
                  return var1;
               }
            }
         }

         var1 = -1;
      }

      return var1;
   }

   public boolean isMimeType(String var1) throws MessagingException {
      String var2 = this.getContentType();

      boolean var3;
      boolean var4;
      try {
         var3 = (new ContentType(var2)).match(var1);
      } catch (ParseException var6) {
         var4 = this.getContentType().equalsIgnoreCase(var1);
         return var4;
      }

      var4 = var3;
      return var4;
   }

   public void removeHeader(String var1) throws MessagingException {
      this.headers.removeHeader(var1);
   }

   public void saveFile(File var1) throws IOException, MessagingException {
      FileOutputStream var2 = new FileOutputStream(var1);

      OutputStream var4;
      label68: {
         Object var7;
         Throwable var8;
         label72: {
            try {
               String var3 = this.getEncoding();
               var4 = MimeUtility.encode(var2, var3);
            } catch (Throwable var15) {
               var7 = var2;
               var8 = var15;
               break label72;
            }

            OutputStream var5 = var4;

            label63:
            try {
               this.getDataHandler().writeTo(var5);
               var5.flush();
               break label68;
            } catch (Throwable var14) {
               var7 = var4;
               var8 = var14;
               break label63;
            }
         }

         ((OutputStream)var7).close();
         throw var8;
      }

      var4.close();
   }

   public void saveFile(String var1) throws IOException, MessagingException {
      File var2 = new File(var1);
      this.saveFile(var2);
   }

   public void setContent(File var1) throws MessagingException {
      FileDataSource var2 = new FileDataSource(var1);
      DataHandler var3 = new DataHandler(var2);
      this.setDataHandler(var3);
   }

   public void setContent(Object var1, String var2) throws MessagingException {
      if(var1 instanceof Multipart) {
         Multipart var3 = (Multipart)var1;
         this.setContent(var3);
      } else {
         DataHandler var4 = new DataHandler(var1, var2);
         this.setDataHandler(var4);
      }
   }

   public void setContent(Multipart var1) throws MessagingException {
      String var2 = var1.getContentType();
      DataHandler var3 = new DataHandler(var1, var2);
      this.setDataHandler(var3);
      var1.setParent(this);
   }

   public void setContentID(String var1) throws MessagingException {
      if(var1 == null) {
         this.removeHeader("Content-ID");
      } else {
         this.setHeader("Content-ID", var1);
      }
   }

   public void setContentLanguage(String[] var1) throws MessagingException {
      if(var1 != null && var1.length > 0) {
         StringBuffer var2 = new StringBuffer();
         String var3 = var1[0];
         var2.append(var3);
         int var5 = 1;

         while(true) {
            int var6 = var1.length;
            if(var5 >= var6) {
               String var10 = var2.toString();
               this.setHeader("Content-Language", var10);
               return;
            }

            StringBuffer var7 = var2.append(',');
            String var8 = var1[var5];
            var2.append(var8);
            ++var5;
         }
      } else {
         this.setHeader("Content-Language", (String)null);
      }
   }

   public void setContentMD5(String var1) throws MessagingException {
      this.setHeader("Content-MD5", var1);
   }

   public void setDataHandler(DataHandler var1) throws MessagingException {
      this.dh = var1;
      this.removeHeader("Content-Type");
      this.removeHeader("Content-Transfer-Encoding");
   }

   public void setDescription(String var1) throws MessagingException {
      this.setDescription(var1, (String)null);
   }

   public void setDescription(String var1, String var2) throws MessagingException {
      if(var1 != null) {
         try {
            String var3 = MimeUtility.encodeText(var1, var2, (String)null);
            this.setHeader("Content-Description", var3);
         } catch (UnsupportedEncodingException var5) {
            throw new MessagingException("Encode error", var5);
         }
      } else {
         this.removeHeader("Content-Description");
      }
   }

   public void setDisposition(String var1) throws MessagingException {
      if(var1 == null) {
         this.removeHeader("Content-Disposition");
      } else {
         String var2 = this.getHeader("Content-Disposition", (String)null);
         String var4;
         if(var2 != null) {
            ContentDisposition var3 = new ContentDisposition(var2);
            var3.setDisposition(var1);
            var4 = var3.toString();
         } else {
            var4 = var1;
         }

         this.setHeader("Content-Disposition", var4);
      }
   }

   public void setFileName(String var1) throws MessagingException {
      Object var2 = AccessController.doPrivileged(new GetSystemPropertyAction("mail.mime.encodefilename"));
      String var4;
      if("true".equals(var2)) {
         String var3;
         try {
            var3 = MimeUtility.encodeText(var1);
         } catch (UnsupportedEncodingException var16) {
            String var13 = var16.getMessage();
            throw new MessagingException(var13, var16);
         }

         var4 = var3;
      } else {
         var4 = var1;
      }

      String var5 = this.getHeader("Content-Disposition", (String)null);
      if(var5 == null) {
         var5 = "attachment";
      }

      ContentDisposition var6 = new ContentDisposition(var5);
      var6.setParameter("filename", var4);
      String var7 = var6.toString();
      this.setHeader("Content-Disposition", var7);
      String var8 = this.getHeader("Content-Type", (String)null);
      if(var8 == null) {
         DataHandler var9 = this.getDataHandler();
         if(var9 != null) {
            var8 = var9.getContentType();
         } else {
            var8 = "text/plain";
         }
      }

      try {
         ContentType var10 = new ContentType(var8);
         var10.setParameter("name", var4);
         String var11 = var10.toString();
         this.setHeader("Content-Type", var11);
      } catch (ParseException var15) {
         ;
      }
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      this.headers.setHeader(var1, var2);
   }

   public void setText(String var1) throws MessagingException {
      this.setText(var1, (String)null, "plain");
   }

   public void setText(String var1, String var2) throws MessagingException {
      this.setText(var1, var2, "plain");
   }

   public void setText(String var1, String var2, String var3) throws MessagingException {
      String var4;
      if(var2 == null) {
         var4 = MimeUtility.mimeCharset(MimeUtility.getDefaultJavaCharset());
      } else {
         var4 = var2;
      }

      String var5;
      if(var3 != null && !"".equals(var3)) {
         var5 = var3;
      } else {
         var5 = "plain";
      }

      StringBuffer var6 = new StringBuffer();
      StringBuffer var7 = var6.append("text/").append(var5).append("; charset=");
      String var8 = MimeUtility.quote(var4, "()<>@,;:\\\"\t []/?=");
      var6.append(var8);
      String var10 = var6.toString();
      this.setContent(var1, var10);
   }

   protected void updateHeaders() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      byte[] var2 = new byte[]{(byte)13, (byte)10};
      Enumeration var3 = this.getAllHeaderLines();

      while(var3.hasMoreElements()) {
         String var4 = (String)var3.nextElement();
         StringTokenizer var5 = new StringTokenizer(var4, "\r\n");

         for(int var6 = 0; var5.hasMoreTokens(); ++var6) {
            String var7 = var5.nextToken();
            if(var6 > 0 && var7.charAt(0) != 9) {
               var1.write(9);
            }

            short var8;
            if(var6 > 0) {
               var8 = 997;
            } else {
               var8 = 998;
            }

            String var10 = var7;

            for(short var11 = var8; var10.length() > var11; var11 = 997) {
               byte[] var12 = var10.substring(0, var11).getBytes("US-ASCII");
               var1.write(var12);
               var1.write(var2);
               var1.write(9);
               var10 = var10.substring(var11);
            }

            byte[] var13 = var10.getBytes("US-ASCII");
            var1.write(var13);
            var1.write(var2);
         }
      }

      var1.write(var2);
      var1.flush();
      String var14 = this.getEncoding();
      OutputStream var15 = MimeUtility.encode(var1, var14);
      this.getDataHandler().writeTo(var15);
      var15.flush();
   }
}
