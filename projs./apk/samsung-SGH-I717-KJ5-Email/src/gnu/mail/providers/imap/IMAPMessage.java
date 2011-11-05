package gnu.mail.providers.imap;

import gnu.inet.imap.MessageStatus;
import gnu.inet.imap.Pair;
import gnu.mail.providers.ReadOnlyMessage;
import gnu.mail.providers.imap.IMAPBodyPart;
import gnu.mail.providers.imap.IMAPFlags;
import gnu.mail.providers.imap.IMAPFolder;
import gnu.mail.providers.imap.IMAPMultipart;
import gnu.mail.providers.imap.IMAPMultipartDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.activation.DataHandler;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.ParameterList;

public final class IMAPMessage extends ReadOnlyMessage {

   static final int BS_CONTENT_SUBTYPE = 1;
   static final int BS_CONTENT_TYPE = 0;
   static final int BS_DESCRIPTION = 4;
   static final int BS_ENCODING = 5;
   static final int BS_EXT_DISPOSITION = 8;
   static final int BS_EXT_LANGUAGE = 9;
   static final int BS_ID = 3;
   static final int BS_LINES = 7;
   static final int BS_OCTETS = 6;
   static final int BS_PARAMETERS = 2;
   static final String FETCH_CONTENT = "BODY.PEEK[]";
   static final String FETCH_HEADERS = "BODY.PEEK[HEADER]";
   static final String MINUS_FLAGS = "-FLAGS";
   static final String PLUS_FLAGS = "+FLAGS";
   protected static final DateFormat internalDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss zzzzz");
   protected boolean headersComplete = 0;
   protected String internalDate = null;
   private IMAPMultipart multipart = null;
   protected long uid = 65535L;


   IMAPMessage(IMAPFolder var1, int var2) throws MessagingException {
      super(var1, var2);
      this.flags = null;
   }

   IMAPMessage(IMAPFolder var1, InputStream var2, int var3) throws MessagingException {
      super(var1, var2, var3);
      this.flags = null;
   }

   void fetch(String[] param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   void fetchContent() throws MessagingException {
      String[] var1 = new String[]{"BODY.PEEK[]", "INTERNALDATE"};
      this.fetch(var1);
   }

   void fetchFlags() throws MessagingException {
      String[] var1 = new String[]{"FLAGS"};
      this.fetch(var1);
   }

   void fetchHeaders() throws MessagingException {
      String[] var1 = new String[]{"BODY.PEEK[HEADER]", "INTERNALDATE"};
      this.fetch(var1);
   }

   void fetchMultipart() throws MessagingException {
      String[] var1 = new String[]{"BODYSTRUCTURE"};
      this.fetch(var1);
   }

   void fetchUID() throws MessagingException {
      String[] var1 = new String[]{"UID"};
      this.fetch(var1);
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      if(!this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      if(!this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getAllHeaders();
   }

   public Object getContent() throws MessagingException, IOException {
      String var1 = this.getContentType();
      String var2 = (new ContentType(var1)).getPrimaryType();
      Object var3;
      if("multipart".equalsIgnoreCase(var2)) {
         if(this.multipart == null) {
            this.fetchMultipart();
         }

         var3 = this.multipart;
      } else {
         var3 = super.getContent();
      }

      return var3;
   }

   protected InputStream getContentStream() throws MessagingException {
      if(this.content == null) {
         this.fetchContent();
      }

      return super.getContentStream();
   }

   public DataHandler getDataHandler() throws MessagingException {
      String var1 = this.getContentType();
      String var2 = (new ContentType(var1)).getPrimaryType();
      DataHandler var5;
      if("multipart".equalsIgnoreCase(var2)) {
         if(this.multipart == null) {
            this.fetchMultipart();
         }

         IMAPMultipart var3 = this.multipart;
         IMAPMultipartDataSource var4 = new IMAPMultipartDataSource(var3);
         var5 = new DataHandler(var4);
      } else {
         if(this.content == null) {
            this.fetchContent();
         }

         var5 = super.getDataHandler();
      }

      return var5;
   }

   public Flags getFlags() throws MessagingException {
      if(this.flags == null) {
         this.fetchFlags();
      }

      return super.getFlags();
   }

   public String getHeader(String var1, String var2) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      if(super.getHeader(var1, var2) == null && !this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getHeader(var1, var2);
   }

   public String[] getHeader(String var1) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      if(super.getHeader(var1) == null && !this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getHeader(var1);
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      if(!this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      if(!this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getMatchingHeaders(var1);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      if(!this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      if(!this.headersComplete) {
         this.fetchHeaders();
      }

      return super.getNonMatchingHeaders(var1);
   }

   public Date getReceivedDate() throws MessagingException {
      if(this.internalDate == null && this.headers == null) {
         this.fetchHeaders();
      }

      Date var1;
      if(this.internalDate == null) {
         var1 = null;
      } else {
         Date var4;
         try {
            DateFormat var2 = internalDateFormat;
            String var3 = this.internalDate;
            var4 = var2.parse(var3);
         } catch (ParseException var7) {
            String var6 = var7.getMessage();
            throw new MessagingException(var6, var7);
         }

         var1 = var4;
      }

      return var1;
   }

   public boolean isSet(Flags.Flag var1) throws MessagingException {
      if(this.flags == null) {
         this.fetchFlags();
      }

      return super.isSet(var1);
   }

   String parseAtom(Object var1) {
      String var2;
      if(var1 instanceof String && !var1.equals("NIL")) {
         var2 = (String)var1;
      } else {
         var2 = null;
      }

      return var2;
   }

   IMAPBodyPart parseBodyPart(List param1, IMAPMultipart param2, String param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   IMAPMultipart parseMultipart(List var1, Part var2, InternetHeaders var3, String var4) throws MessagingException {
      int var5 = 0;
      int var6 = var1.size();
      if(var6 == 0) {
         throw new MessagingException("Empty [MIME-IMB] structure");
      } else {
         Object var7 = var1.get(0);
         ArrayList var8 = new ArrayList();
         ArrayList var9 = new ArrayList();

         int var10;
         Object var15;
         for(var10 = 0; var7 instanceof List; var7 = var15) {
            String var11;
            if(var4 == null) {
               var11 = Integer.toString(var10 + 1);
            } else {
               StringBuilder var16 = (new StringBuilder()).append(var4).append(".");
               int var17 = var10 + 1;
               var11 = var16.append(var17).toString();
            }

            var8.add(var7);
            var9.add(var11);
            int var14 = var10 + 1;
            var15 = var1.get(var14);
            var10 = var14;
         }

         String var18 = this.parseAtom(var7).toLowerCase();
         IMAPMultipart var19 = new IMAPMultipart(this, var2, var18);
         String var20 = var19.getContentType();

         ContentType var21;
         for(var21 = new ContentType(var20); var5 < var10; ++var5) {
            List var22 = (List)var8.get(var5);
            String var23 = (String)var9.get(var5);
            IMAPBodyPart var24 = this.parseBodyPart(var22, var19, var23);
            var19.addBodyPart(var24);
         }

         ContentType var39;
         label48: {
            if(var10 < var6) {
               int var25 = var6 - 2;
               Object var26 = var1.get(var25);
               String var27 = this.parseAtom(var26);
               int var28 = var6 - 1;
               Object var29 = var1.get(var28);
               String var30 = this.parseAtom(var29);
               if(var27 != null) {
                  var3.setHeader("Content-Disposition", var27);
               }

               if(var30 != null) {
                  var3.setHeader("Content-Language", var30);
               }

               ArrayList var31 = new ArrayList();
               int var41 = var10;

               while(true) {
                  int var32 = var6 - 2;
                  if(var41 >= var32) {
                     if(var31.size() > 0) {
                        ParameterList var37 = this.parseParameterList(var31);
                        String var38 = var21.getPrimaryType();
                        var39 = new ContentType(var38, var18, var37);
                        break label48;
                     }
                     break;
                  }

                  Object var33 = var1.get(var41);
                  if(var33 instanceof List) {
                     List var34 = (List)var33;
                     var31.addAll(var34);
                  }

                  int var36 = var41 + 1;
               }
            }

            var39 = var21;
         }

         String var40 = var39.toString();
         var3.setHeader("Content-Type", var40);
         return var19;
      }
   }

   ParameterList parseParameterList(Object var1) {
      ParameterList var10;
      if(var1 instanceof List) {
         List var11 = (List)var1;
         int var2 = var11.size();
         ParameterList var3 = new ParameterList();
         int var4 = 0;

         while(true) {
            int var5 = var2 - 1;
            if(var4 >= var5) {
               var10 = var3;
               break;
            }

            Object var6 = var11.get(var4);
            int var7 = var4 + 1;
            Object var8 = var11.get(var7);
            if(var6 instanceof String && var8 instanceof String) {
               String var12 = this.parseAtom(var8);
               if(var12 != null) {
                  String var9 = (String)var6;
                  var3.set(var9, var12);
               }
            }

            var4 += 2;
         }
      } else {
         var10 = null;
      }

      return var10;
   }

   public void setFlags(Flags param1, boolean param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   void update(MessageStatus var1) throws MessagingException {
      List var2 = var1.getCode();
      int var3 = var2.size();

      for(int var4 = 0; var4 < var3; var4 += 2) {
         Object var5 = var2.get(var4);
         List var6 = Collections.EMPTY_LIST;
         List var9;
         Object var10;
         if(var5 instanceof Pair) {
            Pair var7 = (Pair)var5;
            String var8 = var7.getKey();
            var9 = var7.getValue();
            var10 = var8;
         } else {
            if(!(var5 instanceof String)) {
               String var15 = "Unexpected status item: " + var5;
               throw new MessagingException(var15);
            }

            String var14 = (String)var5;
            var9 = var6;
         }

         if(var10 != "BODY" && var10 != "RFC822") {
            if(var10 == "RFC822.HEADER") {
               int var25 = var4 + 1;
               byte[] var26 = (byte[])((byte[])var2.get(var25));
               ByteArrayInputStream var27 = new ByteArrayInputStream(var26);
               var10 = this.createInternetHeaders(var27);
               this.headers = (InternetHeaders)var10;
               this.headersComplete = (boolean)1;
            } else if(var10 == "BODYSTRUCTURE") {
               int var28 = var4 + 1;
               List var29 = (List)var2.get(var28);
               if(this.headers == null) {
                  InternetHeaders var30 = new InternetHeaders();
                  this.headers = var30;
               }

               InternetHeaders var31 = this.headers;
               var10 = this.parseMultipart(var29, this, var31, (String)null);
               this.multipart = (IMAPMultipart)var10;
            } else if(var10 != "ENVELOPE") {
               if(var10 != "FLAGS") {
                  if(var10 == "INTERNALDATE") {
                     int var51 = var4 + 1;
                     var10 = (String)var2.get(var51);
                     this.internalDate = (String)var10;
                  } else {
                     if(var10 != "UID") {
                        String var53 = "Unknown message status key: " + var10;
                        throw new MessagingException(var53);
                     }

                     int var52 = var4 + 1;
                     var10 = Long.parseLong((String)var2.get(var52));
                     this.uid = (long)var10;
                  }
               } else {
                  int var32 = var4 + 1;
                  List var33 = (List)var2.get(var32);
                  IMAPFlags var34 = new IMAPFlags();
                  this.flags = var34;
                  Iterator var35 = var33.iterator();

                  while(var35.hasNext()) {
                     Object var36 = var35.next();
                     if(var36 == "\\Answered") {
                        Flags var37 = this.flags;
                        Flags.Flag var38 = Flags.Flag.ANSWERED;
                        var37.add(var38);
                     } else if(var36 == "\\Deleted") {
                        Flags var39 = this.flags;
                        Flags.Flag var40 = Flags.Flag.DELETED;
                        var39.add(var40);
                     } else if(var36 == "\\Draft") {
                        Flags var41 = this.flags;
                        Flags.Flag var42 = Flags.Flag.DRAFT;
                        var41.add(var42);
                     } else if(var36 == "\\Flagged") {
                        Flags var43 = this.flags;
                        Flags.Flag var44 = Flags.Flag.FLAGGED;
                        var43.add(var44);
                     } else if(var36 == "\\Recent") {
                        Flags var45 = this.flags;
                        Flags.Flag var46 = Flags.Flag.RECENT;
                        var45.add(var46);
                     } else if(var36 == "\\Seen") {
                        Flags var47 = this.flags;
                        Flags.Flag var48 = Flags.Flag.SEEN;
                        var47.add(var48);
                     } else if(var36 instanceof String) {
                        Flags var49 = this.flags;
                        String var50 = (String)var36;
                        var49.add(var50);
                     }
                  }

                  var10 = (IMAPFlags)this.flags;
                  ((IMAPFlags)var10).checkpoint();
               }
            }
         } else {
            int var11 = var4 + 1;
            var10 = (byte[])((byte[])var2.get(var11));
            int var12 = var9.size();
            if(var12 == 0) {
               ByteArrayInputStream var13 = new ByteArrayInputStream((byte[])var10);
               this.parse(var13);
            } else {
               for(int var16 = 0; var16 < var12; var16 += 2) {
                  Object var17 = var9.get(var16);
                  if(!(var17 instanceof String)) {
                     String var21 = "Unexpected status item: " + var17;
                     throw new MessagingException(var21);
                  }

                  String var18 = (String)var17;
                  if(var18 == "HEADER") {
                     ByteArrayInputStream var19 = new ByteArrayInputStream((byte[])var10);
                     InternetHeaders var20 = this.createInternetHeaders(var19);
                     this.headers = var20;
                     this.headersComplete = (boolean)1;
                  } else {
                     if(var18 != "HEADER.FIELDS") {
                        String var24 = "Unknown message status key: " + var18;
                        throw new MessagingException(var24);
                     }

                     if(!this.headersComplete) {
                        ByteArrayInputStream var22 = new ByteArrayInputStream((byte[])var10);
                        InternetHeaders var23 = this.createInternetHeaders(var22);
                        this.headers = var23;
                     }
                  }
               }
            }
         }
      }

   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      if(this.content == null) {
         this.fetchContent();
      }

      super.writeTo(var1);
   }

   public void writeTo(OutputStream var1, String[] var2) throws IOException, MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      if(this.content == null) {
         this.fetchContent();
      }

      super.writeTo(var1, var2);
   }
}
