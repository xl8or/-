package javax.mail.internet;

import gnu.inet.util.GetSystemPropertyAction;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ContentType;
import javax.mail.internet.HeaderTokenizer;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MailDateFormat;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.NewsAddress;
import javax.mail.internet.SharedInputStream;

public class MimeMessage extends Message implements MimePart {

   static final String BCC_NAME = "Bcc";
   static final String CC_NAME = "Cc";
   static final String DATE_NAME = "Date";
   static final String FROM_NAME = "From";
   static final String MESSAGE_ID_NAME = "Message-ID";
   static final String NEWSGROUPS_NAME = "Newsgroups";
   static final String REPLY_TO_NAME = "Reply-To";
   static final String SENDER_NAME = "Sender";
   static final String SUBJECT_NAME = "Subject";
   static final String TO_NAME = "To";
   private static MailDateFormat dateFormat = new MailDateFormat();
   static int fc = 1;
   protected byte[] content;
   protected InputStream contentStream;
   protected DataHandler dh;
   protected Flags flags;
   protected InternetHeaders headers;
   protected boolean modified;
   protected boolean saved;


   protected MimeMessage(Folder var1, int var2) {
      super(var1, var2);
      Flags var3 = new Flags();
      this.flags = var3;
      this.saved = (boolean)1;
   }

   protected MimeMessage(Folder var1, InputStream var2, int var3) throws MessagingException {
      this(var1, var3);
      this.parse(var2);
   }

   protected MimeMessage(Folder var1, InternetHeaders var2, byte[] var3, int var4) throws MessagingException {
      this(var1, var4);
      this.headers = var2;
      this.content = var3;
   }

   public MimeMessage(Session var1) {
      super(var1);
      InternetHeaders var2 = new InternetHeaders();
      this.headers = var2;
      Flags var3 = new Flags();
      this.flags = var3;
      this.modified = (boolean)1;
   }

   public MimeMessage(Session var1, InputStream var2) throws MessagingException {
      super(var1);
      Flags var3 = new Flags();
      this.flags = var3;
      this.parse(var2);
      this.saved = (boolean)1;
   }

   public MimeMessage(MimeMessage var1) throws MessagingException {
      Session var2 = var1.session;
      super(var2);

      try {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         var1.writeTo(var3);
         var3.close();
         byte[] var4 = var3.toByteArray();
         ByteArrayInputStream var5 = new ByteArrayInputStream(var4);
         this.parse(var5);
         var5.close();
         this.saved = (boolean)1;
      } catch (IOException var7) {
         throw new MessagingException("I/O error", var7);
      }
   }

   private void addInternetAddresses(String var1, Address[] var2) throws MessagingException {
      String var3 = InternetAddress.toString(var2);
      if(var3 != null) {
         this.addHeader(var1, var3);
      }
   }

   private String getHeader(Message.RecipientType var1) throws MessagingException {
      Message.RecipientType var2 = Message.RecipientType.TO;
      String var3;
      if(var1 == var2) {
         var3 = "To";
      } else {
         Message.RecipientType var4 = Message.RecipientType.CC;
         if(var1 == var4) {
            var3 = "Cc";
         } else {
            Message.RecipientType var5 = Message.RecipientType.BCC;
            if(var1 == var5) {
               var3 = "Bcc";
            } else {
               MimeMessage.RecipientType var6 = MimeMessage.RecipientType.NEWSGROUPS;
               if(var1 != var6) {
                  throw new MessagingException("Invalid recipient type");
               }

               var3 = "Newsgroups";
            }
         }
      }

      return var3;
   }

   private Address[] getInternetAddresses(String var1) throws MessagingException {
      String var2 = this.getHeader(var1, ",");
      String var3 = this.session.getProperty("mail.mime.address.strict");
      byte var5;
      if(var3 != null && !Boolean.valueOf(var3).booleanValue()) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      InternetAddress[] var4;
      if(var2 != null) {
         var4 = InternetAddress.parseHeader(var2, (boolean)var5);
      } else {
         var4 = null;
      }

      return var4;
   }

   private void setInternetAddresses(String var1, Address[] var2) throws MessagingException {
      String var3 = InternetAddress.toString(var2);
      if(var3 == null) {
         this.removeHeader(var3);
      } else {
         this.setHeader(var1, var3);
      }
   }

   public void addFrom(Address[] var1) throws MessagingException {
      this.addInternetAddresses("From", var1);
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      this.headers.addHeader(var1, var2);
   }

   public void addHeaderLine(String var1) throws MessagingException {
      this.headers.addHeaderLine(var1);
   }

   public void addRecipients(Message.RecipientType var1, String var2) throws MessagingException {
      MimeMessage.RecipientType var3 = MimeMessage.RecipientType.NEWSGROUPS;
      if(var1 == var3) {
         if(var2 != null) {
            if(var2.length() != 0) {
               this.addHeader("Newsgroups", var2);
            }
         }
      } else {
         String var4 = this.getHeader(var1);
         InternetAddress[] var5 = InternetAddress.parse(var2);
         this.addInternetAddresses(var4, var5);
      }
   }

   public void addRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException {
      MimeMessage.RecipientType var3 = MimeMessage.RecipientType.NEWSGROUPS;
      if(var1 == var3) {
         String var4 = NewsAddress.toString(var2);
         if(var4 != null) {
            this.addHeader("Newsgroups", var4);
         }
      } else {
         String var5 = this.getHeader(var1);
         this.addInternetAddresses(var5, var2);
      }
   }

   protected InternetHeaders createInternetHeaders(InputStream var1) throws MessagingException {
      return new InternetHeaders(var1);
   }

   protected MimeMessage createMimeMessage(Session var1) throws MessagingException {
      return new MimeMessage(var1);
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      return this.headers.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      return this.headers.getAllHeaders();
   }

   public Address[] getAllRecipients() throws MessagingException {
      Address[] var1 = super.getAllRecipients();
      MimeMessage.RecipientType var2 = MimeMessage.RecipientType.NEWSGROUPS;
      Address[] var3 = this.getRecipients(var2);
      if(var3 != null) {
         if(var1 == null) {
            var1 = var3;
         } else {
            int var4 = var1.length;
            int var5 = var3.length;
            Address[] var6 = new Address[var4 + var5];
            int var7 = var1.length;
            System.arraycopy(var1, 0, var6, 0, var7);
            int var8 = var1.length;
            int var9 = var3.length;
            System.arraycopy(var3, 0, var6, var8, var9);
            var1 = var6;
         }
      }

      return var1;
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
      synchronized(this){}

      DataHandler var3;
      try {
         if(this.dh == null) {
            MimePartDataSource var1 = new MimePartDataSource(this);
            DataHandler var2 = new DataHandler(var1);
            this.dh = var2;
         }

         var3 = this.dh;
      } finally {
         ;
      }

      return var3;
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
            var2 = (new ContentType(var3)).getParameter("name");
         }
      }

      Object var4 = AccessController.doPrivileged(new GetSystemPropertyAction("mail.mime.decodefilename"));
      if("true".equals(var4)) {
         String var5;
         try {
            var5 = MimeUtility.decodeText(var2);
         } catch (UnsupportedEncodingException var8) {
            String var7 = var8.getMessage();
            throw new MessagingException(var7, var8);
         }

         var2 = var5;
      }

      return var2;
   }

   public Flags getFlags() throws MessagingException {
      return (Flags)this.flags.clone();
   }

   public Address[] getFrom() throws MessagingException {
      Address[] var1 = this.getInternetAddresses("From");
      if(var1 == null) {
         var1 = this.getInternetAddresses("Sender");
      }

      return var1;
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

   public String getMessageID() throws MessagingException {
      return this.getHeader("Message-ID", (String)null);
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

   public Date getReceivedDate() throws MessagingException {
      return null;
   }

   public Address[] getRecipients(Message.RecipientType var1) throws MessagingException {
      MimeMessage.RecipientType var2 = MimeMessage.RecipientType.NEWSGROUPS;
      Object var5;
      if(var1 == var2) {
         String var3 = this.getHeader("Newsgroups", ",");
         if(var3 != null) {
            var5 = NewsAddress.parse(var3);
         } else {
            var5 = null;
         }
      } else {
         String var4 = this.getHeader(var1);
         var5 = this.getInternetAddresses(var4);
      }

      return (Address[])var5;
   }

   public Address[] getReplyTo() throws MessagingException {
      Address[] var1 = this.getInternetAddresses("Reply-To");
      if(var1 == null) {
         var1 = this.getFrom();
      }

      return var1;
   }

   public Address getSender() throws MessagingException {
      Address[] var1 = this.getInternetAddresses("Sender");
      Address var2;
      if(var1 != null && var1.length > 0) {
         var2 = var1[0];
      } else {
         var2 = null;
      }

      return var2;
   }

   public Date getSentDate() throws MessagingException {
      String var1 = this.getHeader("Date", (String)null);
      Date var5;
      if(var1 != null) {
         label23: {
            Date var2;
            try {
               var2 = dateFormat.parse(var1);
            } catch (java.text.ParseException var4) {
               break label23;
            }

            var5 = var2;
            return var5;
         }
      }

      var5 = null;
      return var5;
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

   public String getSubject() throws MessagingException {
      String var1 = this.getHeader("Subject", (String)null);
      if(var1 == null) {
         var1 = null;
      } else {
         String var2;
         try {
            var2 = MimeUtility.decodeText(var1);
         } catch (UnsupportedEncodingException var4) {
            return var1;
         }

         var1 = var2;
      }

      return var1;
   }

   public boolean isMimeType(String var1) throws MessagingException {
      String var2 = this.getContentType();
      return (new ContentType(var2)).match(var1);
   }

   public boolean isSet(Flags.Flag var1) throws MessagingException {
      return this.flags.contains(var1);
   }

   protected void parse(InputStream var1) throws MessagingException {
      if(var1 instanceof SharedInputStream) {
         InternetHeaders var2 = this.createInternetHeaders(var1);
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

         InternetHeaders var8 = this.createInternetHeaders((InputStream)var7);
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

      this.modified = (boolean)0;
   }

   public void removeHeader(String var1) throws MessagingException {
      this.headers.removeHeader(var1);
   }

   public Message reply(boolean var1) throws MessagingException {
      Session var2 = this.session;
      MimeMessage var3 = this.createMimeMessage(var2);
      String var4 = this.getHeader("Subject", (String)null);
      if(var4 != null) {
         if(!var4.startsWith("Re: ")) {
            var4 = "Re: " + var4;
         }

         var3.setHeader("Subject", var4);
      }

      Address[] var5 = this.getReplyTo();
      Message.RecipientType var6 = Message.RecipientType.TO;
      var3.setRecipients(var6, var5);
      if(var1) {
         HashSet var7 = new HashSet();
         List var8 = Arrays.asList(var5);
         var7.addAll(var8);
         InternetAddress var10 = InternetAddress.getLocalAddress(this.session);
         if(var10 != null) {
            var7.add(var10);
         }

         String var12 = this.session.getProperty("mail.alternates");
         if(var12 != null) {
            List var13 = Arrays.asList(InternetAddress.parse(var12, (boolean)0));
            var7.addAll(var13);
         }

         Message.RecipientType var15 = Message.RecipientType.TO;
         List var16 = Arrays.asList(this.getRecipients(var15));
         var7.addAll(var16);
         Address[] var18 = new Address[var7.size()];
         var7.toArray(var18);
         String var20 = this.session.getProperty("mail.replyallcc");
         boolean var21 = (new Boolean(var20)).booleanValue();
         if(var18.length > 0) {
            if(var21) {
               Message.RecipientType var22 = Message.RecipientType.CC;
               var3.addRecipients(var22, var18);
            } else {
               Message.RecipientType var33 = Message.RecipientType.TO;
               var3.addRecipients(var33, var18);
            }
         }

         var7.clear();
         Message.RecipientType var23 = Message.RecipientType.CC;
         List var24 = Arrays.asList(this.getRecipients(var23));
         var7.addAll(var24);
         Address[] var26 = new Address[var7.size()];
         var7.toArray(var26);
         if(var26 != null && var26.length > 0) {
            Message.RecipientType var28 = Message.RecipientType.CC;
            var3.addRecipients(var28, var26);
         }

         MimeMessage.RecipientType var29 = MimeMessage.RecipientType.NEWSGROUPS;
         var5 = this.getRecipients(var29);
         if(var5 != null && var5.length > 0) {
            MimeMessage.RecipientType var30 = MimeMessage.RecipientType.NEWSGROUPS;
            var3.setRecipients(var30, var5);
         }
      }

      String var31 = this.getHeader("Message-ID", (String)null);
      if(var31 != null) {
         var3.setHeader("In-Reply-To", var31);
      }

      try {
         Flags.Flag var32 = Flags.Flag.ANSWERED;
         this.setFlag(var32, (boolean)1);
      } catch (MessagingException var35) {
         ;
      }

      return var3;
   }

   public void saveChanges() throws MessagingException {
      this.modified = (boolean)1;
      this.saved = (boolean)1;
      this.updateHeaders();
   }

   public void setContent(Object var1, String var2) throws MessagingException {
      DataHandler var3 = new DataHandler(var1, var2);
      this.setDataHandler(var3);
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
      this.removeHeader("Message-ID");
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
         } catch (UnsupportedEncodingException var14) {
            String var13 = var14.getMessage();
            throw new MessagingException(var13, var14);
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

      ContentType var10 = new ContentType(var8);
      var10.setParameter("name", var4);
      String var11 = var10.toString();
      this.setHeader("Content-Type", var11);
   }

   public void setFlags(Flags var1, boolean var2) throws MessagingException {
      if(var2) {
         this.flags.add(var1);
      } else {
         this.flags.remove(var1);
      }
   }

   public void setFrom() throws MessagingException {
      InternetAddress var1 = InternetAddress.getLocalAddress(this.session);
      if(var1 != null) {
         this.setFrom(var1);
      } else {
         throw new MessagingException("No local address");
      }
   }

   public void setFrom(Address var1) throws MessagingException {
      if(var1 == null) {
         this.removeHeader("From");
      } else {
         String var2 = var1.toString();
         this.setHeader("From", var2);
      }
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      this.headers.setHeader(var1, var2);
   }

   public void setRecipients(Message.RecipientType var1, String var2) throws MessagingException {
      MimeMessage.RecipientType var3 = MimeMessage.RecipientType.NEWSGROUPS;
      if(var1 == var3) {
         if(var2 != null && var2.length() != 0) {
            this.setHeader("Newsgroups", var2);
         } else {
            this.removeHeader("Newsgroups");
         }
      } else {
         String var4 = this.getHeader(var1);
         InternetAddress[] var5 = InternetAddress.parse(var2);
         this.setInternetAddresses(var4, var5);
      }
   }

   public void setRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException {
      MimeMessage.RecipientType var3 = MimeMessage.RecipientType.NEWSGROUPS;
      if(var1 == var3) {
         if(var2 != null && var2.length != 0) {
            String var4 = NewsAddress.toString(var2);
            this.setHeader("Newsgroups", var4);
         } else {
            this.removeHeader("Newsgroups");
         }
      } else {
         String var5 = this.getHeader(var1);
         this.setInternetAddresses(var5, var2);
      }
   }

   public void setReplyTo(Address[] var1) throws MessagingException {
      this.setInternetAddresses("Reply-To", var1);
   }

   public void setSender(Address var1) throws MessagingException {
      Address[] var2 = new Address[]{var1};
      this.addInternetAddresses("Sender", var2);
   }

   public void setSentDate(Date var1) throws MessagingException {
      if(var1 == null) {
         this.removeHeader("Date");
      } else {
         String var2 = dateFormat.format(var1);
         this.setHeader("Date", var2);
      }
   }

   public void setSubject(String var1) throws MessagingException {
      this.setSubject(var1, (String)null);
   }

   public void setSubject(String var1, String var2) throws MessagingException {
      if(var1 == null) {
         this.removeHeader("Subject");
      }

      try {
         String var3 = MimeUtility.encodeText(var1, var2, (String)null);
         this.setHeader("Subject", var3);
      } catch (UnsupportedEncodingException var5) {
         throw new MessagingException("Encoding error", var5);
      }
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

   protected void updateMessageId() throws MessagingException {
      if(this.getHeader("Message-ID", (String)null) == null) {
         StringBuffer var1 = new StringBuffer();
         StringBuffer var2 = var1.append('<');
         String var3 = MimeUtility.getUniqueMessageIDValue(this.session);
         var1.append(var3);
         StringBuffer var5 = var1.append('>');
         String var6 = var1.toString();
         this.setHeader("Message-ID", var6);
      }
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      this.writeTo(var1, (String[])null);
   }

   public void writeTo(OutputStream var1, String[] var2) throws IOException, MessagingException {
      if(!this.saved) {
         this.saveChanges();
      }

      String var3 = "US-ASCII";
      byte[] var4 = new byte[]{(byte)13, (byte)10};
      Enumeration var5 = this.getNonMatchingHeaderLines(var2);

      while(var5.hasMoreElements()) {
         String var6 = (String)var5.nextElement();
         StringTokenizer var7 = new StringTokenizer(var6, "\r\n");

         for(int var8 = 0; var7.hasMoreTokens(); ++var8) {
            String var9 = var7.nextToken();
            if(var8 > 0 && var9.charAt(0) != 9) {
               var1.write(9);
            }

            short var10;
            if(var8 > 0) {
               var10 = 997;
            } else {
               var10 = 998;
            }

            String var12 = var9;

            for(short var13 = var10; var12.length() > var13; var13 = 997) {
               byte[] var14 = var12.substring(0, var13).getBytes(var3);
               var1.write(var14);
               var1.write(var4);
               var1.write(9);
               var12 = var12.substring(var13);
            }

            byte[] var15 = var12.getBytes(var3);
            var1.write(var15);
            var1.write(var4);
         }
      }

      var1.write(var4);
      var1.flush();
      if(!this.modified && (this.content != null || this.contentStream != null)) {
         if(this.contentStream != null) {
            InputStream var18 = ((SharedInputStream)this.contentStream).newStream(0L, 65535L);
            byte[] var20 = new byte[8192];

            while(true) {
               int var21 = var18.read(var20);
               if(var21 <= -1) {
                  var18.close();
                  break;
               }

               var1.write(var20, 0, var21);
            }
         } else {
            byte[] var19 = this.content;
            var1.write(var19);
         }

         var1.flush();
      } else {
         String var16 = this.getEncoding();
         OutputStream var17 = MimeUtility.encode(var1, var16);
         this.getDataHandler().writeTo(var17);
         var17.flush();
      }
   }

   public static class RecipientType extends Message.RecipientType {

      public static final MimeMessage.RecipientType NEWSGROUPS = new MimeMessage.RecipientType("Newsgroups");


      protected RecipientType(String var1) {
         super(var1);
      }

      protected Object readResolve() throws ObjectStreamException {
         Object var1;
         if(this.type.equals("Newsgroups")) {
            var1 = NEWSGROUPS;
         } else {
            var1 = super.readResolve();
         }

         return var1;
      }
   }
}
