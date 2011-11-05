package com.android.email.mail.store;

import android.content.Context;
import android.text.TextUtils;
import com.android.email.Email;
import com.android.email.Utility;
import com.android.email.mail.FetchProfile;
import com.android.email.mail.Flag;
import com.android.email.mail.Folder;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Part;
import com.android.email.mail.Store;
import com.android.email.mail.Transport;
import com.android.email.mail.internet.MimeBodyPart;
import com.android.email.mail.internet.MimeMessage;
import com.android.email.mail.internet.MimeMultipart;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.mail.store.imap.ImapElement;
import com.android.email.mail.store.imap.ImapList;
import com.android.email.mail.store.imap.ImapResponse;
import com.android.email.mail.store.imap.ImapString;
import com.android.email.mail.transport.CountingOutputStream;
import com.android.email.mail.transport.DiscourseLogger;
import com.android.email.mail.transport.EOLConvertingOutputStream;
import com.android.email.mail.transport.MailTransport;
import com.beetstra.jutf7.CharsetProvider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class ImapStore extends Store {

   private static final boolean DEBUG_FORCE_SEND_ID;
   private static final Charset MODIFIED_UTF_7_CHARSET;
   private static final Flag[] PERMANENT_FLAGS;
   private static Context mAppendContext;
   private static String sImapId;
   private final ConcurrentLinkedQueue<ImapStore.ImapConnection> mConnectionPool;
   private final Context mContext;
   private HashMap<String, ImapStore.ImapFolder> mFolderCache;
   private String mIdPhrase = null;
   private String mLoginPhrase;
   private final AtomicInteger mNextCommandTag;
   private String mPassword;
   private String mPathPrefix;
   private Transport mRootTransport;
   private String mUsername;


   static {
      Flag[] var0 = new Flag[4];
      Flag var1 = Flag.DELETED;
      var0[0] = var1;
      Flag var2 = Flag.SEEN;
      var0[1] = var2;
      Flag var3 = Flag.FLAGGED;
      var0[2] = var3;
      Flag var4 = Flag.ANSWERED;
      var0[3] = var4;
      PERMANENT_FLAGS = var0;
      sImapId = null;
      MODIFIED_UTF_7_CHARSET = (new CharsetProvider()).charsetForName("X-RFC-3501");
   }

   private ImapStore(Context var1, String var2) throws MessagingException {
      ConcurrentLinkedQueue var3 = new ConcurrentLinkedQueue();
      this.mConnectionPool = var3;
      HashMap var4 = new HashMap();
      this.mFolderCache = var4;
      AtomicInteger var5 = new AtomicInteger(0);
      this.mNextCommandTag = var5;
      this.mContext = var1;
      mAppendContext = var1;

      URI var6;
      try {
         var6 = new URI(var2);
      } catch (URISyntaxException var22) {
         throw new MessagingException("Invalid ImapStore URI", var22);
      }

      String var7 = var6.getScheme();
      if(var7 != null && var7.startsWith("imap")) {
         byte var9 = 0;
         short var10 = 143;
         if(var7.contains("+ssl")) {
            var9 = 1;
            var10 = 993;
         } else if(var7.contains("+tls")) {
            var9 = 2;
         }

         boolean var11 = var7.contains("+trustallcerts");
         MailTransport var12 = new MailTransport("IMAP");
         this.mRootTransport = var12;
         this.mRootTransport.setUri(var6, var10);
         this.mRootTransport.setSecurity(var9, var11);
         String[] var13 = this.mRootTransport.getUserInfoParts();
         if(var13 != null) {
            String var14 = var13[0];
            this.mUsername = var14;
            if(var13.length > 1) {
               String var15 = var13[1];
               this.mPassword = var15;
               StringBuilder var16 = (new StringBuilder()).append("LOGIN ");
               String var17 = this.mUsername;
               StringBuilder var18 = var16.append(var17).append(" ");
               String var19 = Utility.imapQuoted(this.mPassword);
               String var20 = var18.append(var19).toString();
               this.mLoginPhrase = var20;
            }
         }

         if(var6.getPath() != null) {
            if(var6.getPath().length() > 0) {
               String var21 = var6.getPath().substring(1);
               this.mPathPrefix = var21;
            }
         }
      } else {
         throw new MessagingException("Unsupported protocol");
      }
   }

   // $FF: synthetic method
   static Transport access$300(ImapStore var0) {
      return var0.mRootTransport;
   }

   // $FF: synthetic method
   static Context access$400(ImapStore var0) {
      return var0.mContext;
   }

   // $FF: synthetic method
   static String access$500(ImapStore var0) {
      return var0.mUsername;
   }

   // $FF: synthetic method
   static String access$600(ImapStore var0) {
      return var0.mIdPhrase;
   }

   // $FF: synthetic method
   static String access$602(ImapStore var0, String var1) {
      var0.mIdPhrase = var1;
      return var1;
   }

   // $FF: synthetic method
   static String access$700(ImapStore var0) {
      return var0.mLoginPhrase;
   }

   static String decodeFolderName(String var0) {
      Charset var1 = MODIFIED_UTF_7_CHARSET;
      ByteBuffer var2 = ByteBuffer.wrap(Utility.toAscii(var0));
      return var1.decode(var2).toString();
   }

   static String encodeFolderName(String var0) {
      ByteBuffer var1 = MODIFIED_UTF_7_CHARSET.encode(var0);
      byte[] var2 = new byte[var1.limit()];
      var1.get(var2);
      return Utility.fromAscii(var2);
   }

   static String getImapId(Context param0, String param1, String param2, ImapResponse param3) {
      // $FF: Couldn't be decompiled
   }

   static String joinMessageUids(Message[] var0) {
      StringBuilder var1 = new StringBuilder();
      boolean var2 = false;
      Message[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Message var6 = var3[var5];
         if(var2) {
            StringBuilder var7 = var1.append(',');
         }

         String var8 = var6.getUid();
         var1.append(var8);
         var2 = true;
      }

      return var1.toString();
   }

   static String makeCommonImapId(String var0, String var1, String var2, String var3, String var4, String var5, String var6) {
      Pattern var7 = Pattern.compile("[^a-zA-Z0-9-_\\+=;:\\.,/ ]");
      String var8 = var7.matcher(var0).replaceAll("");
      String var9 = var7.matcher(var1).replaceAll("");
      String var10 = var7.matcher(var2).replaceAll("");
      String var11 = var7.matcher(var3).replaceAll("");
      String var12 = var7.matcher(var4).replaceAll("");
      String var13 = var7.matcher(var5).replaceAll("");
      String var14 = var7.matcher(var6).replaceAll("");
      StringBuffer var15 = new StringBuffer("\"name\" \"");
      var15.append(var8);
      StringBuffer var17 = var15.append("\"");
      StringBuffer var18 = var15.append(" \"os\" \"android\"");
      StringBuffer var19 = var15.append(" \"os-version\" \"");
      if(var9.length() > 0) {
         var15.append(var9);
      } else {
         StringBuffer var33 = var15.append("1.0");
      }

      if(var12.length() > 0) {
         StringBuffer var21 = var15.append("; ");
         var15.append(var12);
      }

      StringBuffer var23 = var15.append("\"");
      if(var13.length() > 0) {
         StringBuffer var24 = var15.append(" \"vendor\" \"");
         var15.append(var13);
         StringBuffer var26 = var15.append("\"");
      }

      if("REL".equals(var10) && var11.length() > 0) {
         StringBuffer var27 = var15.append(" \"x-android-device-model\" \"");
         var15.append(var11);
         StringBuffer var29 = var15.append("\"");
      }

      if(var14.length() > 0) {
         StringBuffer var30 = var15.append(" \"x-android-mobile-net-operator\" \"");
         var15.append(var14);
         StringBuffer var32 = var15.append("\"");
      }

      return var15.toString();
   }

   public static Store newInstance(String var0, Context var1, Store.PersistentDataCallbacks var2) throws MessagingException {
      return new ImapStore(var1, var0);
   }

   public void checkSettings() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   ImapStore.ImapConnection getConnection() {
      while(true) {
         ImapStore.ImapConnection var1 = (ImapStore.ImapConnection)this.mConnectionPool.poll();
         if(var1 != null) {
            label63: {
               try {
                  label60: {
                     String var2;
                     try {
                        var2 = "NOOP";
                     } catch (IOException var13) {
                        break label60;
                     }

                     try {
                        var1.executeSimpleCommand(var2);
                        break label63;
                     } catch (IOException var12) {
                        ;
                     }
                  }
               } catch (MessagingException var14) {
                  ;
               } finally {
                  var1.destroyResponses();
               }

               var1.close();
               continue;
            }
         }

         if(var1 == null) {
            var1 = new ImapStore.ImapConnection();
         }

         return var1;
      }
   }

   Collection<ImapStore.ImapConnection> getConnectionPoolForTest() {
      return this.mConnectionPool;
   }

   public Folder getFolder(String var1) throws MessagingException {
      HashMap var2 = this.mFolderCache;
      synchronized(var2) {
         ImapStore.ImapFolder var3 = (ImapStore.ImapFolder)this.mFolderCache.get(var1);
         if(var3 == null) {
            var3 = new ImapStore.ImapFolder(this, var1);
            this.mFolderCache.put(var1, var3);
         }

         return var3;
      }
   }

   public Folder[] getPersonalNamespaces() throws MessagingException {
      ImapStore.ImapConnection var1 = this.getConnection();
      boolean var92 = false;

      Folder[] var87;
      try {
         var92 = true;
         ArrayList var2 = new ArrayList();
         String var3 = "LIST \"\" \"%s*\"";
         Object[] var4 = new Object[1];
         byte var5 = 0;
         String var6;
         if(this.mPathPrefix == null) {
            var6 = "";
         } else {
            var6 = this.mPathPrefix;
         }

         var4[var5] = var6;
         String var7 = String.format(var3, var4);
         List var10 = var1.executeSimpleCommand(var7);
         this.mFolderCache.clear();
         Iterator var11 = var10.iterator();

         while(var11.hasNext()) {
            ImapResponse var12 = (ImapResponse)var11.next();
            byte var14 = 0;
            String var15 = "LIST";
            if(var12.isDataResponse(var14, var15)) {
               byte var17 = 2;
               ImapString var18 = var12.getStringOrEmpty(var17);
               if(Email.DEBUG) {
                  StringBuilder var19 = (new StringBuilder()).append("delimiter = [");
                  String var20 = var18.getString();
                  String var21 = var19.append(var20).append("]").toString();
                  Email.logd("Email", var21);
               }

               byte var23 = 3;
               ImapString var24 = var12.getStringOrEmpty(var23);
               if(!var24.isEmpty()) {
                  String var25 = decodeFolderName(var24.getString());
                  String var26 = "INBOX";
                  if(!var26.equalsIgnoreCase(var25)) {
                     if(Email.DEBUG) {
                        byte var29 = 1;
                        if(var12.getListOrEmpty(var29).contains("\\HASCHILDREN")) {
                           Email.logd("Email", "bHasChildren :  TRUE");
                        } else {
                           Email.logd("Email", "bHasChildren :  FALSE");
                        }
                     }

                     String var31 = var18.getString();
                     String[] var34 = var25.split(var31);
                     String var35 = null;
                     if(var34 != null && var34.length > 0) {
                        int var37 = var34.length - 1;
                        String var38 = var34[var37];
                        int var39 = var34.length;
                        byte var40 = 1;
                        if(var39 > var40) {
                           int var43 = var25.lastIndexOf(var38);
                           if(var43 > 0) {
                              int var44 = var43 - 1;
                              byte var46 = 0;
                              var35 = var25.substring(var46, var44);
                           }
                        }

                        if(Email.DEBUG) {
                           StringBuilder var48 = (new StringBuilder()).append("orgFolderName: ");
                           StringBuilder var50 = var48.append(var38).append(" newFolderName : ");
                           StringBuilder var52 = var50.append(var25).append(" parentFolderName: ");
                           String var54 = var52.append(var35).toString();
                           Email.logd("Email", var54);
                        }
                     }

                     if(var25 != null) {
                        Folder var57 = this.getFolder(var25);
                        byte var59 = 1;
                        if(var12.getListOrEmpty(var59).contains("\\NOSELECT")) {
                           byte var61 = 0;
                           var57.setSelect((boolean)var61);
                        } else {
                           byte var77 = 1;
                           var57.setSelect((boolean)var77);
                        }

                        char var62 = var18.getString().charAt(0);
                        var57.setDelimiter(var62);
                        var57.setFolderPath(var25);
                        var57.setParentFolderName(var35);
                        var2.add(var57);
                     }
                  }
               }
            }
         }

         String var79 = "INBOX";
         Folder var80 = this.getFolder(var79);
         byte var82 = 1;
         var80.setSelect((boolean)var82);
         var2.add(var80);
         Folder[] var84 = new Folder[0];
         var87 = (Folder[])var2.toArray(var84);
         var92 = false;
      } catch (IOException var93) {
         var1.close();
         MessagingException var69 = new MessagingException;
         String var71 = "Unable to get folder list.";
         var69.<init>(var71, var93);
         throw var69;
      } finally {
         if(var92) {
            var1.destroyResponses();
            this.poolConnection(var1);
         }
      }

      var1.destroyResponses();
      this.poolConnection(var1);
      return var87;
   }

   void poolConnection(ImapStore.ImapConnection var1) {
      if(var1 != null) {
         this.mConnectionPool.add(var1);
      }
   }

   public void removeFolder(String var1) {
      HashMap var2 = this.mFolderCache;
      synchronized(var2) {
         if((ImapStore.ImapFolder)this.mFolderCache.get(var1) != null) {
            this.mFolderCache.remove(var1);
         }

      }
   }

   public void renameFolder(String var1, String var2) {
      HashMap var3 = this.mFolderCache;
      synchronized(var3) {
         ImapStore.ImapFolder var4 = (ImapStore.ImapFolder)this.mFolderCache.get(var1);
         if(var4 != null) {
            this.mFolderCache.put(var2, var4);
            this.mFolderCache.remove(var1);
         }

      }
   }

   void setTransport(Transport var1) {
      this.mRootTransport = var1;
   }

   class ImapConnection {

      private static final int DISCOURSE_LOGGER_SIZE = 64;
      private static final String IMAP_DEDACTED_LOG = "[IMAP command redacted]";
      private final DiscourseLogger mDiscourse;
      private com.android.email.mail.store.imap.ImapResponseParser mParser;
      private Transport mTransport;


      ImapConnection() {
         DiscourseLogger var2 = new DiscourseLogger(64);
         this.mDiscourse = var2;
      }

      private void createParser() {
         this.destroyResponses();
         InputStream var1 = this.mTransport.getInputStream();
         DiscourseLogger var2 = this.mDiscourse;
         com.android.email.mail.store.imap.ImapResponseParser var3 = new com.android.email.mail.store.imap.ImapResponseParser(var1, var2);
         this.mParser = var3;
      }

      private ImapResponse queryCapabilities() throws IOException, MessagingException {
         ImapResponse var1 = null;
         Iterator var2 = this.executeSimpleCommand("CAPABILITY").iterator();

         while(var2.hasNext()) {
            ImapResponse var3 = (ImapResponse)var2.next();
            if(var3.is(0, "CAPABILITY")) {
               var1 = var3;
               break;
            }
         }

         if(var1 == null) {
            throw new MessagingException("Invalid CAPABILITY response received");
         } else {
            return var1;
         }
      }

      public void close() {
         if(this.mTransport != null) {
            this.mTransport.close();
            this.mTransport = null;
         }
      }

      public void destroyResponses() {
         if(this.mParser != null) {
            this.mParser.destroyResponses();
         }
      }

      public List<ImapResponse> executeSimpleCommand(String var1) throws IOException, MessagingException {
         return this.executeSimpleCommand(var1, (boolean)0);
      }

      public List<ImapResponse> executeSimpleCommand(String var1, boolean var2) throws IOException, MessagingException {
         this.sendCommand(var1, var2);
         ArrayList var4 = new ArrayList();

         ImapResponse var5;
         do {
            var5 = this.mParser.readResponse();
            var4.add(var5);
         } while(!var5.isTagged());

         if(!var5.isOk()) {
            String var7 = var5.toString();
            String var8 = var5.getAlertTextOrEmpty().getString();
            this.destroyResponses();
            throw new ImapStore.ImapException(var7, var8);
         } else {
            return var4;
         }
      }

      boolean isTransportOpenForTest() {
         boolean var1;
         if(this.mTransport != null) {
            var1 = this.mTransport.isOpen();
         } else {
            var1 = false;
         }

         return var1;
      }

      public void logLastDiscourse() {
         this.mDiscourse.logLastDiscourse();
      }

      public void open() throws IOException, MessagingException {
         // $FF: Couldn't be decompiled
      }

      public ImapResponse readResponse() throws IOException, MessagingException {
         return this.mParser.readResponse();
      }

      public String sendCommand(String var1, boolean var2) throws MessagingException, IOException {
         this.open();
         String var3 = Integer.toString(ImapStore.this.mNextCommandTag.incrementAndGet());
         String var4 = var3 + " " + var1;
         Transport var5 = this.mTransport;
         String var6;
         if(var2) {
            var6 = "[IMAP command redacted]";
         } else {
            var6 = null;
         }

         var5.writeLine(var4, var6);
         DiscourseLogger var8 = this.mDiscourse;
         String var7;
         if(var2) {
            var7 = "[IMAP command redacted]";
         } else {
            var7 = var4;
         }

         var8.addSentCommand(var7);
         return var3;
      }
   }

   static class ImapFolder extends Folder {

      private int delimiter = 0;
      private ImapStore.ImapConnection mConnection;
      private boolean mExists;
      private int mMessageCount = -1;
      private Folder.OpenMode mMode;
      private String mName;
      private String mParentName;
      private String mPath;
      private boolean mSelect = 1;
      private final ImapStore mStore;


      public ImapFolder(ImapStore var1, String var2) {
         this.mStore = var1;
         this.mName = var2;
      }

      private void checkOpen() throws MessagingException {
         if(!this.isOpen()) {
            StringBuilder var1 = (new StringBuilder()).append("Folder ");
            String var2 = this.mName;
            String var3 = var1.append(var2).append(" is not open.").toString();
            throw new MessagingException(var3);
         }
      }

      private void destroyResponses() {
         if(this.mConnection != null) {
            this.mConnection.destroyResponses();
         }
      }

      private void handleUntaggedResponse(ImapResponse var1) {
         if(var1.isDataResponse(1, "EXISTS")) {
            int var2 = var1.getStringOrEmpty(0).getNumberOrZero();
            this.mMessageCount = var2;
         }
      }

      private void handleUntaggedResponses(List<ImapResponse> var1) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            ImapResponse var3 = (ImapResponse)var2.next();
            this.handleUntaggedResponse(var3);
         }

      }

      private MessagingException ioExceptionHandler(ImapStore.ImapConnection var1, IOException var2) throws MessagingException {
         if(Email.DEBUG) {
            Email.loge("Email", "IO Exception detected: ", var2);
         }

         var1.destroyResponses();
         var1.close();
         ImapStore.ImapConnection var3 = this.mConnection;
         if(var1 == var3) {
            this.mConnection = null;
            this.close((boolean)0);
         }

         return new MessagingException("IO Error", var2);
      }

      private static void parseBodyStructure(ImapList var0, Part var1, String var2) throws MessagingException {
         if(var0.getElementOrNone(0).isList()) {
            MimeMultipart var55 = new MimeMultipart();
            int var56 = var0.size();

            for(int var57 = 0; var57 < var56; ++var57) {
               ImapElement var6 = var0.getElementOrNone(var57);
               if(!var6.isList()) {
                  if(var6.isString()) {
                     String var14 = var0.getStringOrEmpty(var57).getString().toLowerCase();
                     var55.setSubType(var14);
                  }
                  break;
               }

               MimeBodyPart var7 = new MimeBodyPart();
               if(var2.equals("TEXT")) {
                  ImapList var8 = var0.getListOrEmpty(var57);
                  String var9 = Integer.toString(var57 + 1);
                  parseBodyStructure(var8, var7, var9);
               } else {
                  ImapList var10 = var0.getListOrEmpty(var57);
                  StringBuilder var11 = (new StringBuilder()).append(var2).append(".");
                  int var12 = var57 + 1;
                  String var13 = var11.append(var12).toString();
                  parseBodyStructure(var10, var7, var13);
               }

               var55.addBodyPart(var7);
            }

            var1.setBody(var55);
         } else {
            ImapString var15 = var0.getStringOrEmpty(0);
            ImapString var16 = var0.getStringOrEmpty(1);
            StringBuilder var17 = new StringBuilder();
            String var18 = var15.getString();
            StringBuilder var19 = var17.append(var18).append("/");
            String var20 = var16.getString();
            String var21 = var19.append(var20).toString().toLowerCase();
            ImapList var22 = var0.getListOrEmpty(2);
            ImapString var4 = var0.getStringOrEmpty(3);
            ImapString var23 = var0.getStringOrEmpty(5);
            int var24 = var0.getStringOrEmpty(6).getNumberOrZero();
            if(MimeUtility.mimeTypeMatches(var21, "message/rfc822")) {
               throw new MessagingException("BODYSTRUCTURE message/rfc822 not yet supported.");
            } else {
               StringBuilder var25 = new StringBuilder(var21);
               int var26 = 1;

               for(int var3 = var22.size(); var26 < var3; var26 += 2) {
                  Object[] var27 = new Object[2];
                  int var28 = var26 - 1;
                  String var29 = var22.getStringOrEmpty(var28).getString();
                  var27[0] = var29;
                  String var30 = var22.getStringOrEmpty(var26).getString();
                  var27[1] = var30;
                  String var31 = String.format(";\n %s=\"%s\"", var27);
                  var25.append(var31);
               }

               String var33 = var25.toString();
               var1.setHeader("Content-Type", var33);
               ImapList var34;
               if(var15.is("TEXT") && var0.getElementOrNone(9).isList()) {
                  var34 = var0.getListOrEmpty(9);
               } else {
                  var34 = var0.getListOrEmpty(8);
               }

               StringBuilder var5 = new StringBuilder();
               if(var34.size() > 0) {
                  String var35 = var34.getStringOrEmpty(0).getString().toLowerCase();
                  if(!TextUtils.isEmpty(var35)) {
                     var5.append(var35);
                  }

                  var34 = var34.getListOrEmpty(1);
                  if(!var34.isEmpty()) {
                     int var37 = 1;

                     for(int var38 = var34.size(); var37 < var38; var37 += 2) {
                        Object[] var39 = new Object[2];
                        int var40 = var37 - 1;
                        String var41 = var34.getStringOrEmpty(var40).getString().toLowerCase();
                        var39[0] = var41;
                        String var42 = var34.getStringOrEmpty(var37).getString();
                        var39[1] = var42;
                        String var43 = String.format(";\n %s=\"%s\"", var39);
                        var5.append(var43);
                     }
                  }
               }

               if(var24 > 0 && MimeUtility.getHeaderParameter(var5.toString(), "size") == null) {
                  Object[] var45 = new Object[1];
                  Integer var46 = Integer.valueOf(var24);
                  var45[0] = var46;
                  String var47 = String.format(";\n size=%d", var45);
                  var5.append(var47);
               }

               if(var5.length() > 0) {
                  String var49 = var5.toString();
                  var1.setHeader("Content-Disposition", var49);
               }

               if(!var23.isEmpty()) {
                  String var50 = var23.getString();
                  var1.setHeader("Content-Transfer-Encoding", var50);
               }

               if(!var4.isEmpty()) {
                  String var51 = var4.getString();
                  var1.setHeader("Content-ID", var51);
               }

               if(var24 > 0) {
                  if(var1 instanceof ImapStore.ImapMessage) {
                     ((ImapStore.ImapMessage)var1).setSize(var24);
                  } else {
                     if(!(var1 instanceof MimeBodyPart)) {
                        StringBuilder var52 = (new StringBuilder()).append("Unknown part type ");
                        String var53 = var1.toString();
                        String var54 = var52.append(var53).toString();
                        throw new MessagingException(var54);
                     }

                     ((MimeBodyPart)var1).setSize(var24);
                  }
               }

               var1.setHeader("X-Android-Attachment-StoreData", var2);
            }
         }
      }

      public void appendMessages(Message[] var1) throws MessagingException {
         this.checkOpen();
         Message[] var2 = var1;

         try {
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Message var5 = var2[var4];
               CountingOutputStream var6 = new CountingOutputStream();
               EOLConvertingOutputStream var7 = new EOLConvertingOutputStream(var6);
               Context var10 = ImapStore.mAppendContext;
               long var11 = var5.getMessageId_original();
               var5.writeTo(var10, var11, var7);
               var7.flush();
               String var18 = "";
               Flag[] var19 = var5.getFlags();
               if(var19.length > 0) {
                  StringBuilder var20 = new StringBuilder();
                  int var21 = 0;

                  for(int var22 = var19.length; var21 < var22; ++var21) {
                     Flag var23 = var19[var21];
                     Flag var24 = Flag.SEEN;
                     if(var23 == var24) {
                        String var28 = " \\SEEN";
                        var20.append(var28);
                     } else {
                        Flag var30 = Flag.FLAGGED;
                        if(var23 == var30) {
                           String var34 = " \\FLAGGED";
                           var20.append(var34);
                        }
                     }
                  }

                  if(var20.length() > 0) {
                     byte var43 = 1;
                     var18 = var20.substring(var43);
                  }
               }

               ImapStore.ImapConnection var44 = this.mConnection;
               Object[] var45 = new Object[3];
               String var46 = ImapStore.encodeFolderName(this.mName);
               var45[0] = var46;
               var45[1] = var18;
               Long var47 = Long.valueOf(var6.getCount());
               var45[2] = var47;
               String var48 = String.format("APPEND \"%s\" (%s) {%d}", var45);
               var44.sendCommand(var48, (boolean)0);

               ImapResponse var50;
               do {
                  var50 = this.mConnection.readResponse();
                  if(var50.isContinuationRequest()) {
                     EOLConvertingOutputStream var51 = new EOLConvertingOutputStream;
                     OutputStream var52 = this.mConnection.mTransport.getOutputStream();
                     var51.<init>(var52);
                     Context var55 = ImapStore.mAppendContext;
                     long var56 = var5.getMessageId_original();
                     var5.writeTo(var55, var56, var51);
                     byte var64 = 13;
                     var51.write(var64);
                     byte var66 = 10;
                     var51.write(var66);
                     var51.flush();
                  } else if(!var50.isTagged()) {
                     this.handleUntaggedResponse(var50);
                  }
               } while(!var50.isTagged());

               byte var68 = 1;
               ImapList var69 = var50.getListOrEmpty(var68);
               int var70 = var69.size();
               byte var71 = 3;
               if(var70 >= var71) {
                  byte var73 = 0;
                  String var74 = "APPENDUID";
                  if(var69.is(var73, var74)) {
                     byte var76 = 2;
                     String var77 = var69.getStringOrEmpty(var76).getString();
                     if(!TextUtils.isEmpty(var77)) {
                        var5.setUid(var77);
                        continue;
                     }
                  }
               }

               String var82 = var5.getMessageId();
               if(var82 != null && var82.length() != 0) {
                  Object[] var83 = new Object[]{var82};
                  String var84 = String.format("(HEADER MESSAGE-ID %s)", var83);
                  String[] var87 = this.searchForUids(var84);
                  if(var87.length > 0) {
                     String var88 = var87[0];
                     var5.setUid(var88);
                  }
               }
            }
         } catch (IOException var93) {
            ImapStore.ImapConnection var37 = this.mConnection;
            throw this.ioExceptionHandler(var37, var93);
         } finally {
            this.destroyResponses();
         }

      }

      public boolean canCreate(Folder.FolderType var1) {
         return true;
      }

      public void close(boolean var1) {
         this.mMessageCount = -1;
         synchronized(this) {
            this.destroyResponses();
            ImapStore var2 = this.mStore;
            ImapStore.ImapConnection var3 = this.mConnection;
            var2.poolConnection(var3);
            this.mConnection = null;
         }
      }

      public void copyMessages(Message[] var1, Folder var2, Folder.MessageUpdateCallbacks var3) throws MessagingException {
         this.checkOpen();

         try {
            ImapStore.ImapConnection var4 = this.mConnection;
            Object[] var5 = new Object[2];
            String var6 = ImapStore.joinMessageUids(var1);
            var5[0] = var6;
            String var7 = ImapStore.encodeFolderName(var2.getName());
            var5[1] = var7;
            String var8 = String.format("UID COPY %s \"%s\"", var5);
            var4.executeSimpleCommand(var8);
         } catch (IOException var15) {
            ImapStore.ImapConnection var11 = this.mConnection;
            throw this.ioExceptionHandler(var11, var15);
         } finally {
            this.destroyResponses();
         }

      }

      public boolean create(Folder.FolderType var1) throws MessagingException {
         ImapStore.ImapConnection var2;
         synchronized(this) {
            if(this.mConnection == null) {
               var2 = this.mStore.getConnection();
            } else {
               var2 = this.mConnection;
            }
         }

         boolean var7;
         label101: {
            try {
               Object[] var3 = new Object[1];
               String var4 = ImapStore.encodeFolderName(this.mName);
               var3[0] = var4;
               String var5 = String.format("CREATE \"%s\"", var3);
               var2.executeSimpleCommand(var5);
               break label101;
            } catch (MessagingException var17) {
               ;
            } catch (IOException var18) {
               throw this.ioExceptionHandler(var2, var18);
            } finally {
               var2.destroyResponses();
               if(this.mConnection == null) {
                  this.mStore.poolConnection(var2);
               }

            }

            var7 = false;
            return var7;
         }

         var7 = true;
         return var7;
      }

      public Message createMessage(String var1) throws MessagingException {
         return new ImapStore.ImapMessage(var1, this);
      }

      public boolean delete(boolean var1) throws MessagingException {
         ImapStore.ImapConnection var2;
         synchronized(this) {
            if(this.mConnection == null) {
               var2 = this.mStore.getConnection();
            } else {
               var2 = this.mConnection;
            }
         }

         boolean var7;
         label101: {
            try {
               Object[] var3 = new Object[1];
               String var4 = ImapStore.encodeFolderName(this.mName);
               var3[0] = var4;
               String var5 = String.format("DELETE \"%s\"", var3);
               var2.executeSimpleCommand(var5);
               break label101;
            } catch (MessagingException var17) {
               ;
            } catch (IOException var18) {
               throw this.ioExceptionHandler(var2, var18);
            } finally {
               var2.destroyResponses();
               if(this.mConnection == null) {
                  this.mStore.poolConnection(var2);
               }

            }

            var7 = false;
            return var7;
         }

         var7 = true;
         return var7;
      }

      public boolean equals(Object var1) {
         boolean var4;
         if(var1 instanceof ImapStore.ImapFolder) {
            String var2 = ((ImapStore.ImapFolder)var1).mName;
            String var3 = this.mName;
            var4 = var2.equals(var3);
         } else {
            var4 = super.equals(var1);
         }

         return var4;
      }

      public boolean exists() throws MessagingException {
         boolean var1;
         if(this.mExists) {
            var1 = true;
         } else {
            ImapStore.ImapConnection var2;
            synchronized(this) {
               if(this.mConnection == null) {
                  var2 = this.mStore.getConnection();
               } else {
                  var2 = this.mConnection;
               }
            }

            label105: {
               try {
                  Object[] var3 = new Object[1];
                  String var4 = ImapStore.encodeFolderName(this.mName);
                  var3[0] = var4;
                  String var5 = String.format("STATUS \"%s\" (UIDVALIDITY)", var3);
                  var2.executeSimpleCommand(var5);
                  this.mExists = (boolean)1;
                  break label105;
               } catch (MessagingException var16) {
                  ;
               } catch (IOException var17) {
                  throw this.ioExceptionHandler(var2, var17);
               } finally {
                  var2.destroyResponses();
                  if(this.mConnection == null) {
                     this.mStore.poolConnection(var2);
                  }

               }

               var1 = false;
               return var1;
            }

            var1 = true;
         }

         return var1;
      }

      public Message[] expunge() throws MessagingException {
         this.checkOpen();

         try {
            List var1 = this.mConnection.executeSimpleCommand("EXPUNGE");
            this.handleUntaggedResponses(var1);
         } catch (IOException var7) {
            ImapStore.ImapConnection var3 = this.mConnection;
            throw this.ioExceptionHandler(var3, var7);
         } finally {
            this.destroyResponses();
         }

         return null;
      }

      public void fetch(Message[] var1, FetchProfile var2, Folder.MessageRetrievalListener var3) throws MessagingException {
         try {
            this.fetchInternal(var1, var2, var3);
         } catch (RuntimeException var8) {
            StringBuilder var5 = (new StringBuilder()).append("Exception detected: ");
            String var6 = var8.getMessage();
            String var7 = var5.append(var6).toString();
            Email.logd("Email", var7);
            if(this.mConnection != null) {
               this.mConnection.logLastDiscourse();
            }

            throw var8;
         }
      }

      public void fetchInternal(Message[] param1, FetchProfile param2, Folder.MessageRetrievalListener param3) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public int getDelimiter() {
         return this.delimiter;
      }

      public String getFolderPath() {
         String var1;
         if(this.mPath == null) {
            var1 = this.mName;
         } else {
            var1 = this.mPath;
         }

         return var1;
      }

      public Message getMessage(String var1) throws MessagingException {
         this.checkOpen();
         String var2 = "UID " + var1;
         String[] var3 = this.searchForUids(var2);
         int var4 = 0;

         ImapStore.ImapMessage var6;
         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               var6 = null;
               break;
            }

            if(var3[var4].equals(var1)) {
               var6 = new ImapStore.ImapMessage(var1, this);
               break;
            }

            ++var4;
         }

         return var6;
      }

      public int getMessageCount() {
         return this.mMessageCount;
      }

      public Message[] getMessages(int var1, int var2, Folder.MessageRetrievalListener var3) throws MessagingException {
         if(var1 >= 1 && var2 >= 1 && var2 >= var1) {
            Object[] var8 = new Object[2];
            Integer var9 = Integer.valueOf(var1);
            var8[0] = var9;
            Integer var10 = Integer.valueOf(var2);
            var8[1] = var10;
            String var11 = String.format("%d:%d NOT DELETED", var8);
            String[] var12 = this.searchForUids(var11);
            return this.getMessagesInternal(var12, var3);
         } else {
            Object[] var4 = new Object[2];
            Integer var5 = Integer.valueOf(var1);
            var4[0] = var5;
            Integer var6 = Integer.valueOf(var2);
            var4[1] = var6;
            String var7 = String.format("Invalid range: %d %d", var4);
            throw new MessagingException(var7);
         }
      }

      public Message[] getMessages(int var1, String var2, Folder.MessageRetrievalListener var3) throws MessagingException {
         if(var1 < 1) {
            Object[] var4 = new Object[2];
            Integer var5 = Integer.valueOf(var1);
            var4[0] = var5;
            var4[1] = var2;
            String var6 = String.format("Invalid message set %d %s", var4);
            throw new MessagingException(var6);
         } else {
            Object[] var7 = new Object[2];
            Integer var8 = Integer.valueOf(var1);
            var7[0] = var8;
            var7[1] = var2;
            String var9 = String.format("%d:*%s", var7);
            String[] var10 = this.searchForUids(var9);
            return this.getMessagesInternal(var10, var3);
         }
      }

      public Message[] getMessages(Folder.MessageRetrievalListener var1) throws MessagingException {
         return this.getMessages((String[])null, var1);
      }

      public Message[] getMessages(String[] var1, Folder.MessageRetrievalListener var2) throws MessagingException {
         if(var1 == null) {
            var1 = this.searchForUids("1:* NOT DELETED");
         }

         return this.getMessagesInternal(var1, var2);
      }

      public Message[] getMessagesInternal(String[] var1, Folder.MessageRetrievalListener var2) throws MessagingException {
         int var3 = var1.length;
         ArrayList var4 = new ArrayList(var3);
         int var5 = 0;

         while(true) {
            int var6 = var1.length;
            if(var5 >= var6) {
               Message[] var13 = Message.EMPTY_ARRAY;
               return (Message[])var4.toArray(var13);
            }

            if(var2 != null) {
               String var7 = var1[var5];
               int var8 = var1.length;
               var2.messageStarted(var7, var5, var8);
            }

            String var9 = var1[var5];
            ImapStore.ImapMessage var10 = new ImapStore.ImapMessage(var9, this);
            var4.add(var10);
            if(var2 != null) {
               int var12 = var1.length;
               var2.messageFinished(var10, var5, var12);
            }

            ++var5;
         }
      }

      public Folder.OpenMode getMode() throws MessagingException {
         return this.mMode;
      }

      public String getName() {
         return this.mName;
      }

      public String getParentFolderName() {
         return this.mParentName;
      }

      public Flag[] getPermanentFlags() throws MessagingException {
         return ImapStore.PERMANENT_FLAGS;
      }

      public boolean getSelect() {
         return this.mSelect;
      }

      public int getUnreadMessageCount() throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public boolean isOpen() {
         boolean var1;
         if(this.mExists && this.mConnection != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void open(Folder.OpenMode param1, Folder.PersistentDataCallbacks param2) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void reConnect(boolean var1) {}

      public boolean rename(String var1) throws MessagingException {
         ImapStore.ImapConnection var2;
         synchronized(this) {
            if(this.mConnection == null) {
               var2 = this.mStore.getConnection();
            } else {
               var2 = this.mConnection;
            }
         }

         boolean var13;
         label101: {
            try {
               Object[] var3 = new Object[1];
               StringBuilder var4 = (new StringBuilder()).append("\"");
               String var5 = ImapStore.encodeFolderName(this.mName);
               StringBuilder var6 = var4.append(var5).append("\" \"");
               String var7 = ImapStore.encodeFolderName(var1);
               String var8 = var6.append(var7).append("\"").toString();
               var3[0] = var8;
               String var9 = String.format("RENAME %s", var3);
               var2.executeSimpleCommand(var9);
               String var11 = new String(var1);
               this.mName = var11;
               String var12 = new String(var1);
               this.mPath = var12;
               break label101;
            } catch (MessagingException var23) {
               ;
            } catch (IOException var24) {
               throw this.ioExceptionHandler(var2, var24);
            } finally {
               var2.destroyResponses();
               if(this.mConnection == null) {
                  this.mStore.poolConnection(var2);
               }

            }

            var13 = false;
            return var13;
         }

         var13 = true;
         return var13;
      }

      String[] searchForUids(String param1) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void setDelimiter(int var1) {
         this.delimiter = var1;
      }

      public void setFlags(Message[] var1, Flag[] var2, boolean var3) throws MessagingException {
         this.checkOpen();
         String var4 = "";
         if(var2.length > 0) {
            StringBuilder var5 = new StringBuilder();
            int var6 = 0;

            for(int var7 = var2.length; var6 < var7; ++var6) {
               Flag var8 = var2[var6];
               Flag var9 = Flag.SEEN;
               if(var8 == var9) {
                  StringBuilder var10 = var5.append(" \\SEEN");
               } else {
                  Flag var11 = Flag.DELETED;
                  if(var8 == var11) {
                     StringBuilder var12 = var5.append(" \\DELETED");
                  } else {
                     Flag var13 = Flag.FLAGGED;
                     if(var8 == var13) {
                        StringBuilder var14 = var5.append(" \\FLAGGED");
                     } else {
                        Flag var15 = Flag.ANSWERED;
                        if(var8 == var15) {
                           StringBuilder var16 = var5.append(" \\ANSWERED");
                        }
                     }
                  }
               }
            }

            var4 = var5.substring(1);
         }

         try {
            ImapStore.ImapConnection var17 = this.mConnection;
            String var18 = "UID STORE %s %sFLAGS.SILENT (%s)";
            Object[] var19 = new Object[3];
            String var20 = ImapStore.joinMessageUids(var1);
            var19[0] = var20;
            byte var21 = 1;
            String var22;
            if(var3) {
               var22 = "+";
            } else {
               var22 = "-";
            }

            var19[var21] = var22;
            var19[2] = var4;
            String var23 = String.format(var18, var19);
            var17.executeSimpleCommand(var23);
         } catch (IOException var30) {
            ImapStore.ImapConnection var26 = this.mConnection;
            throw this.ioExceptionHandler(var26, var30);
         } finally {
            this.destroyResponses();
         }

      }

      public void setFolderPath(String var1) {
         this.mPath = var1;
      }

      public void setParentFolderName(String var1) {
         this.mParentName = var1;
      }

      public void setSelect(boolean var1) {
         this.mSelect = var1;
      }
   }

   static class ImapException extends MessagingException {

      String mAlertText;


      public ImapException(String var1, String var2) {
         super(var1);
         this.mAlertText = var2;
      }

      public ImapException(String var1, String var2, Throwable var3) {
         super(var1, var3);
         this.mAlertText = var2;
      }

      public String getAlertText() {
         return this.mAlertText;
      }

      public void setAlertText(String var1) {
         this.mAlertText = var1;
      }
   }

   static class ImapMessage extends MimeMessage {

      ImapMessage(String var1, Folder var2) throws MessagingException {
         this.mUid = var1;
         this.mFolder = var2;
      }

      public void parse(InputStream var1) throws IOException, MessagingException {
         super.parse(var1);
      }

      public void setFlag(Flag var1, boolean var2) throws MessagingException {
         super.setFlag(var1, var2);
         Folder var3 = this.mFolder;
         Message[] var4 = new Message[]{this};
         Flag[] var5 = new Flag[]{var1};
         var3.setFlags(var4, var5, var2);
      }

      public void setFlagInternal(Flag var1, boolean var2) throws MessagingException {
         super.setFlag(var1, var2);
      }

      public void setSize(int var1) {
         this.mSize = var1;
      }
   }
}
