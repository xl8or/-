package com.android.email.mail.store;

import android.content.Context;
import android.util.Log;
import com.android.email.Email;
import com.android.email.mail.FetchProfile;
import com.android.email.mail.Flag;
import com.android.email.mail.Folder;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.mail.Transport;
import com.android.email.mail.internet.MimeMessage;
import com.android.email.mail.transport.LoggingInputStream;
import com.android.email.mail.transport.MailTransport;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Pop3Store extends Store {

   private static boolean DEBUG_FORCE_SINGLE_LINE_UIDL = 0;
   private static boolean DEBUG_LOG_RAW_STREAM = 0;
   private static final Flag[] PERMANENT_FLAGS;
   private HashMap<String, Folder> mFolders;
   private String mPassword;
   private Transport mTransport;
   private String mUsername;


   static {
      Flag[] var0 = new Flag[1];
      Flag var1 = Flag.DELETED;
      var0[0] = var1;
      PERMANENT_FLAGS = var0;
   }

   private Pop3Store(String var1) throws MessagingException {
      HashMap var2 = new HashMap();
      this.mFolders = var2;

      URI var3;
      try {
         var3 = new URI(var1);
      } catch (URISyntaxException var13) {
         throw new MessagingException("Invalid Pop3Store URI", var13);
      }

      String var4 = var3.getScheme();
      if(var4 != null && var4.startsWith("pop3")) {
         byte var6 = 0;
         short var7 = 110;
         if(var4.contains("+ssl")) {
            var6 = 1;
            var7 = 995;
         } else if(var4.contains("+tls")) {
            var6 = 2;
         }

         boolean var8 = var4.contains("+trustallcerts");
         MailTransport var9 = new MailTransport("POP3");
         this.mTransport = var9;
         this.mTransport.setUri(var3, var7);
         this.mTransport.setSecurity(var6, var8);
         String[] var10 = this.mTransport.getUserInfoParts();
         if(var10 != null) {
            String var11 = var10[0];
            this.mUsername = var11;
            if(var10.length > 1) {
               String var12 = var10[1];
               this.mPassword = var12;
            }
         }
      } else {
         throw new MessagingException("Unsupported protocol");
      }
   }

   // $FF: synthetic method
   static String access$100(Pop3Store var0) {
      return var0.mUsername;
   }

   // $FF: synthetic method
   static String access$200(Pop3Store var0) {
      return var0.mPassword;
   }

   public static Store newInstance(String var0, Context var1, Store.PersistentDataCallbacks var2) throws MessagingException {
      return new Pop3Store(var0);
   }

   public void checkSettings() throws MessagingException {
      Pop3Store.Pop3Folder var1 = new Pop3Store.Pop3Folder("INBOX");
      if(this.mTransport.isOpen()) {
         var1.close((boolean)0);
      }

      try {
         Folder.OpenMode var2 = Folder.OpenMode.READ_WRITE;
         var1.open(var2, (Folder.PersistentDataCallbacks)null);
         var1.checkSettings();
      } finally {
         var1.close((boolean)0);
      }

   }

   public Folder getFolder(String var1) throws MessagingException {
      Object var2 = (Folder)this.mFolders.get(var1);
      if(var2 == null) {
         var2 = new Pop3Store.Pop3Folder(var1);
         HashMap var3 = this.mFolders;
         String var4 = ((Folder)var2).getName();
         var3.put(var4, var2);
      }

      return (Folder)var2;
   }

   public Folder[] getPersonalNamespaces() throws MessagingException {
      Folder[] var1 = new Folder[1];
      Folder var2 = this.getFolder("INBOX");
      var1[0] = var2;
      return var1;
   }

   public int hashCode() {
      return super.hashCode();
   }

   public void removeFolder(String var1) {}

   public void renameFolder(String var1, String var2) {}

   void setTransport(Transport var1) {
      this.mTransport = var1;
   }

   class Pop3Message extends MimeMessage {

      public Pop3Message(String var2, Pop3Store.Pop3Folder var3) throws MessagingException {
         this.mUid = var2;
         this.mFolder = var3;
         this.mSize = -1;
      }

      protected void parse(InputStream var1) throws IOException, MessagingException {
         super.parse(var1);
      }

      public void setFlag(Flag var1, boolean var2) throws MessagingException {
         super.setFlag(var1, var2);
         Folder var3 = this.mFolder;
         Message[] var4 = new Message[]{this};
         Flag[] var5 = new Flag[]{var1};
         var3.setFlags(var4, var5, var2);
      }

      public void setSize(int var1) {
         this.mSize = var1;
      }
   }

   class Pop3Folder extends Folder {

      private Pop3Store.Pop3Capabilities mCapabilities;
      private int mMessageCount;
      private HashMap<Integer, Pop3Store.Pop3Message> mMsgNumToMsgMap;
      private String mName;
      private HashMap<String, Pop3Store.Pop3Message> mUidToMsgMap;
      private HashMap<String, Integer> mUidToMsgNumMap;


      public Pop3Folder(String var2) {
         HashMap var3 = new HashMap();
         this.mUidToMsgMap = var3;
         HashMap var4 = new HashMap();
         this.mMsgNumToMsgMap = var4;
         HashMap var5 = new HashMap();
         this.mUidToMsgNumMap = var5;
         this.mName = var2;
         if(this.mName.equalsIgnoreCase("INBOX")) {
            this.mName = "INBOX";
         }
      }

      private String executeSensitiveCommand(String var1, String var2) throws IOException, MessagingException {
         Folder.OpenMode var3 = Folder.OpenMode.READ_WRITE;
         this.open(var3, (Folder.PersistentDataCallbacks)null);
         if(var1 != null) {
            Pop3Store.this.mTransport.writeLine(var1, var2);
         }

         String var4 = Pop3Store.this.mTransport.readLine();
         String var5;
         if(var4 == null) {
            var5 = null;
         } else {
            if(var4.length() > 1 && var4.charAt(0) == 45) {
               throw new MessagingException(var4);
            }

            var5 = var4;
         }

         return var5;
      }

      private String executeSimpleCommand(String var1) throws IOException, MessagingException {
         return this.executeSensitiveCommand(var1, (String)null);
      }

      private void fetchBody(Pop3Store.Pop3Message var1, int var2) throws IOException, MessagingException {
         int var5;
         try {
            HashMap var3 = this.mUidToMsgNumMap;
            String var4 = var1.getUid();
            var5 = ((Integer)var3.get(var4)).intValue();
         } catch (Exception var25) {
            String var14 = var25.toString();
            throw new MessagingException(var14);
         }

         int var6 = var5;
         String var10;
         if(var2 == -1) {
            Object[] var7 = new Object[1];
            Integer var8 = Integer.valueOf(var5);
            var7[0] = var8;
            String var9 = String.format("RETR %d", var7);
            var10 = this.executeSimpleCommand(var9);
         } else {
            label37: {
               String var19;
               try {
                  Object[] var15 = new Object[2];
                  Integer var16 = Integer.valueOf(var6);
                  var15[0] = var16;
                  Integer var17 = Integer.valueOf(var2);
                  var15[1] = var17;
                  String var18 = String.format("TOP %d %d", var15);
                  var19 = this.executeSimpleCommand(var18);
               } catch (MessagingException var27) {
                  Object[] var21 = new Object[1];
                  Integer var22 = Integer.valueOf(var5);
                  var21[0] = var22;
                  String var23 = String.format("RETR %d", var21);
                  var10 = this.executeSimpleCommand(var23);
                  break label37;
               }

               var10 = var19;
            }
         }

         if(var10 != null) {
            try {
               Object var11 = Pop3Store.this.mTransport.getInputStream();
               if(Pop3Store.DEBUG_LOG_RAW_STREAM && Email.DEBUG) {
                  var11 = new LoggingInputStream((InputStream)var11);
               }

               var1.setIntegrity((boolean)0);
               Pop3Store var12 = Pop3Store.this;
               Pop3Store.Pop3ResponseInputStream var13 = var12.new Pop3ResponseInputStream((InputStream)var11);
               var1.parse(var13);
            } catch (MessagingException var26) {
               if(var2 == -1) {
                  throw var26;
               }
            }
         }
      }

      private void fetchEnvelope(Message[] var1, Folder.MessageRetrievalListener var2) throws IOException, MessagingException {
         int var3 = 0;
         Message[] var4 = var1;
         int var5 = var1.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            int var7 = var4[var6].getSize();
            char var8 = '\uffff';
            if(var7 == var8) {
               ++var3;
            }
         }

         if(var3 != 0) {
            byte var10 = 50;
            int var13;
            int var14;
            if(var3 < var10) {
               int var11 = this.mMessageCount;
               short var12 = 5000;
               if(var11 > var12) {
                  var13 = 0;

                  for(var14 = var1.length; var13 < var14; ++var13) {
                     Message var15 = var1[var13];
                     if(!(var15 instanceof Pop3Store.Pop3Message)) {
                        throw new MessagingException("Pop3Store.fetch called with non-Pop3 Message");
                     }

                     Pop3Store.Pop3Message var16 = (Pop3Store.Pop3Message)var15;
                     if(var2 != null) {
                        String var17 = var16.getUid();
                        var2.messageStarted(var17, var13, var14);
                     }

                     Object[] var22 = new Object[1];
                     HashMap var23 = this.mUidToMsgNumMap;
                     String var24 = var16.getUid();
                     Object var25 = var23.get(var24);
                     var22[0] = var25;
                     String var26 = String.format("LIST %d", var22);
                     String var29 = this.executeSimpleCommand(var26);

                     try {
                        String var31 = " ";
                        String[] var32 = var29.split(var31);
                        int var33 = Integer.parseInt(var32[1]);
                        int var34 = Integer.parseInt(var32[2]);
                        var16.setSize(var34);
                        byte var36 = 1;
                        var16.setIntegrity((boolean)var36);
                     } catch (NumberFormatException var77) {
                        throw new IOException();
                     }

                     if(var2 != null) {
                        var2.messageFinished(var16, var13, var14);
                     }
                  }

                  return;
               }
            }

            HashSet var42 = new HashSet();
            var4 = var1;
            var5 = var1.length;

            int var47;
            for(byte var79 = 0; var79 < var5; var47 = var79 + 1) {
               String var43 = var4[var79].getUid();
               boolean var46 = var42.add(var43);
            }

            var13 = 0;
            var14 = var1.length;
            String var49 = "LIST";
            this.executeSimpleCommand(var49);

            while(true) {
               String var51 = Pop3Store.this.mTransport.readLine();
               if(var51 == null) {
                  return;
               }

               String var52 = ".";
               if(var52.equals(var51)) {
                  return;
               }

               int var58;
               Pop3Store.Pop3Message var61;
               try {
                  String var55 = " ";
                  String[] var56 = var51.split(var55);
                  int var57 = Integer.parseInt(var56[0]);
                  var58 = Integer.parseInt(var56[1]);
                  HashMap var59 = this.mMsgNumToMsgMap;
                  Integer var60 = Integer.valueOf(var57);
                  var61 = (Pop3Store.Pop3Message)var59.get(var60);
               } catch (NumberFormatException var78) {
                  throw new IOException();
               }

               if(var61 != null) {
                  String var62 = var61.getUid();
                  if(var42.contains(var62)) {
                     if(var2 != null) {
                        String var65 = var61.getUid();
                        var2.messageStarted(var65, var13, var14);
                     }

                     var61.setSize(var58);
                     byte var71 = 1;
                     var61.setIntegrity((boolean)var71);
                     if(var2 != null) {
                        var2.messageFinished(var61, var13, var14);
                     }

                     ++var13;
                  }
               }
            }
         }
      }

      private Pop3Store.Pop3Capabilities getCapabilities() throws IOException, MessagingException {
         // $FF: Couldn't be decompiled
      }

      private void indexMessage(int var1, Pop3Store.Pop3Message var2) {
         HashMap var3 = this.mMsgNumToMsgMap;
         Integer var4 = Integer.valueOf(var1);
         var3.put(var4, var2);
         HashMap var6 = this.mUidToMsgMap;
         String var7 = var2.getUid();
         var6.put(var7, var2);
         HashMap var9 = this.mUidToMsgNumMap;
         String var10 = var2.getUid();
         Integer var11 = Integer.valueOf(var1);
         var9.put(var10, var11);
      }

      private void indexMsgNums(int var1, int var2) throws MessagingException, IOException {
         int var3 = 0;

         int var4;
         for(var4 = var1; var4 <= var2; ++var4) {
            HashMap var5 = this.mMsgNumToMsgMap;
            Integer var6 = Integer.valueOf(var4);
            if(var5.get(var6) == null) {
               ++var3;
            }
         }

         if(var3 != 0) {
            Pop3Store.Pop3Folder.UidlParser var7 = new Pop3Store.Pop3Folder.UidlParser();
            if(!Pop3Store.DEBUG_FORCE_SINGLE_LINE_UIDL && (var3 >= 50 || this.mMessageCount <= 5000)) {
               String var19 = this.executeSimpleCommand("UIDL");

               while(true) {
                  String var20 = Pop3Store.this.mTransport.readLine();
                  if(var20 == null) {
                     return;
                  }

                  if(!var7.parseMultiLine(var20)) {
                     throw new IOException();
                  }

                  if(var7.mEndOfMessage) {
                     return;
                  }

                  var4 = var7.mMessageNumber;
                  if(var4 >= var1 && var4 <= var2) {
                     HashMap var21 = this.mMsgNumToMsgMap;
                     Integer var22 = Integer.valueOf(var4);
                     if((Pop3Store.Pop3Message)var21.get(var22) == null) {
                        Pop3Store var23 = Pop3Store.this;
                        String var24 = var7.mUniqueId;
                        Pop3Store.Pop3Message var25 = var23.new Pop3Message(var24, this);
                        var25.setIntegrity((boolean)1);
                        this.indexMessage(var4, var25);
                     }
                  }
               }
            } else {
               StringBuffer var8 = new StringBuffer();

               for(var4 = var1; var4 <= var2; ++var4) {
                  HashMap var9 = this.mMsgNumToMsgMap;
                  Integer var10 = Integer.valueOf(var4);
                  if((Pop3Store.Pop3Message)var9.get(var10) == null) {
                     StringBuffer var11 = var8.append("UIDL ").append(var4);
                     String var12 = var8.toString();
                     String var13 = this.executeSimpleCommand(var12);
                     int var14 = var8.length();
                     var8.delete(0, var14);
                     if(!var7.parseSingleLine(var13)) {
                        throw new IOException();
                     }

                     Pop3Store var16 = Pop3Store.this;
                     String var17 = var7.mUniqueId;
                     Pop3Store.Pop3Message var18 = var16.new Pop3Message(var17, this);
                     this.indexMessage(var4, var18);
                  }
               }

            }
         }
      }

      private void indexUids(ArrayList<String> var1) throws MessagingException, IOException {
         HashSet var2 = new HashSet();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if(this.mUidToMsgMap.get(var4) == null) {
               var2.add(var4);
            }
         }

         if(var2.size() != 0) {
            Pop3Store.Pop3Folder.UidlParser var6 = new Pop3Store.Pop3Folder.UidlParser();
            String var7 = this.executeSimpleCommand("UIDL");

            while(true) {
               String var8 = Pop3Store.this.mTransport.readLine();
               if(var8 == null) {
                  return;
               }

               var6.parseMultiLine(var8);
               if(var6.mEndOfMessage) {
                  return;
               }

               String var10 = var6.mUniqueId;
               if(var2.contains(var10)) {
                  HashMap var11 = this.mUidToMsgMap;
                  String var12 = var6.mUniqueId;
                  Pop3Store.Pop3Message var13 = (Pop3Store.Pop3Message)var11.get(var12);
                  if(var13 == null) {
                     Pop3Store var14 = Pop3Store.this;
                     String var15 = var6.mUniqueId;
                     var13 = var14.new Pop3Message(var15, this);
                  }

                  var13.setIntegrity((boolean)1);
                  int var16 = var6.mMessageNumber;
                  this.indexMessage(var16, var13);
               }
            }
         }
      }

      public void appendMessages(Message[] var1) throws MessagingException {}

      public boolean canCreate(Folder.FolderType var1) {
         return false;
      }

      public void checkSettings() throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void close(boolean var1) {
         try {
            String var2 = this.executeSimpleCommand("QUIT");
         } catch (Exception var4) {
            ;
         }

         Pop3Store.this.mTransport.close();
      }

      public void copyMessages(Message[] var1, Folder var2, Folder.MessageUpdateCallbacks var3) throws MessagingException {
         throw new UnsupportedOperationException("copyMessages is not supported in POP3");
      }

      public boolean create(Folder.FolderType var1) throws MessagingException {
         return false;
      }

      public Message createMessage(String var1) throws MessagingException {
         Pop3Store var2 = Pop3Store.this;
         return var2.new Pop3Message(var1, this);
      }

      public boolean delete(boolean var1) throws MessagingException {
         return false;
      }

      public boolean equals(Object var1) {
         boolean var4;
         if(var1 instanceof Pop3Store.Pop3Folder) {
            String var2 = ((Pop3Store.Pop3Folder)var1).mName;
            String var3 = this.mName;
            var4 = var2.equals(var3);
         } else {
            var4 = super.equals(var1);
         }

         return var4;
      }

      public boolean exists() throws MessagingException {
         return this.mName.equalsIgnoreCase("INBOX");
      }

      public Message[] expunge() throws MessagingException {
         return null;
      }

      public void fetch(Message[] param1, FetchProfile param2, Folder.MessageRetrievalListener param3) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public int getDelimiter() {
         return 0;
      }

      public String getFolderPath() {
         return this.mName;
      }

      public Message getMessage(String var1) throws MessagingException {
         if(this.mUidToMsgNumMap.size() == 0) {
            byte var2 = 1;

            try {
               int var3 = this.mMessageCount;
               this.indexMsgNums(var2, var3);
            } catch (IOException var7) {
               Pop3Store.this.mTransport.close();
               if(Email.DEBUG) {
                  String var5 = "Unable to index during getMessage " + var7;
                  int var6 = Log.d("Email", var5);
               }

               throw new MessagingException("getMessages", var7);
            }
         }

         return (Pop3Store.Pop3Message)this.mUidToMsgMap.get(var1);
      }

      public int getMessageCount() {
         return this.mMessageCount;
      }

      public Message[] getMessages(int var1, int var2, Folder.MessageRetrievalListener var3) throws MessagingException {
         if(var1 >= 1 && var2 >= 1 && var2 >= var1) {
            try {
               this.indexMsgNums(var1, var2);
            } catch (IOException var25) {
               Pop3Store.this.mTransport.close();
               if(Email.DEBUG) {
                  String var22 = var25.toString();
                  int var23 = Log.d("Email", var22);
               }

               throw new MessagingException("getMessages", var25);
            }

            ArrayList var8 = new ArrayList();
            int var9 = var1;

            int var15;
            for(int var10 = 0; var9 <= var2; var10 = var15) {
               HashMap var11 = this.mMsgNumToMsgMap;
               Integer var12 = Integer.valueOf(var9);
               Pop3Store.Pop3Message var13 = (Pop3Store.Pop3Message)var11.get(var12);
               if(var3 != null) {
                  String var14 = var13.getUid();
                  var15 = var10 + 1;
                  int var16 = var2 - var1 + 1;
                  var3.messageStarted(var14, var10, var16);
               } else {
                  var15 = var10;
               }

               var8.add(var13);
               if(var3 != null) {
                  int var18 = var15 + 1;
                  int var19 = var2 - var1 + 1;
                  var3.messageFinished(var13, var15, var19);
               }

               ++var9;
            }

            Message[] var24 = new Message[var8.size()];
            return (Message[])var8.toArray(var24);
         } else {
            Object[] var4 = new Object[2];
            Integer var5 = Integer.valueOf(var1);
            var4[0] = var5;
            Integer var6 = Integer.valueOf(var2);
            var4[1] = var6;
            String var7 = String.format("Invalid message set %d %d", var4);
            throw new MessagingException(var7);
         }
      }

      public Message[] getMessages(int var1, String var2, Folder.MessageRetrievalListener var3) {
         return null;
      }

      public Message[] getMessages(Folder.MessageRetrievalListener var1) throws MessagingException {
         throw new UnsupportedOperationException("Pop3Folder.getMessage(MessageRetrievalListener)");
      }

      public Message[] getMessages(String[] var1, Folder.MessageRetrievalListener var2) throws MessagingException {
         throw new UnsupportedOperationException("Pop3Folder.getMessage(MessageRetrievalListener)");
      }

      public Folder.OpenMode getMode() throws MessagingException {
         return Folder.OpenMode.READ_WRITE;
      }

      public String getName() {
         return this.mName;
      }

      public String getParentFolderName() {
         return null;
      }

      public Flag[] getPermanentFlags() throws MessagingException {
         return Pop3Store.PERMANENT_FLAGS;
      }

      public boolean getSelect() {
         return true;
      }

      public int getUnreadMessageCount() throws MessagingException {
         return -1;
      }

      public boolean isOpen() {
         return Pop3Store.this.mTransport.isOpen();
      }

      public void open(Folder.OpenMode param1, Folder.PersistentDataCallbacks param2) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void reConnect(boolean var1) {}

      public boolean rename(String var1) throws MessagingException {
         return false;
      }

      public void setDelimiter(int var1) {}

      public void setFlags(Message[] param1, Flag[] param2, boolean param3) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void setFolderPath(String var1) {}

      public void setParentFolderName(String var1) {}

      public void setSelect(boolean var1) {}

      class UidlParser {

         public boolean mEndOfMessage;
         public boolean mErr = 1;
         public int mMessageNumber;
         public String mUniqueId;


         public UidlParser() {}

         public boolean parseMultiLine(String var1) {
            this.mErr = (boolean)0;
            boolean var2;
            if(var1 != null && var1.length() != 0) {
               if(var1.charAt(0) == 46) {
                  this.mEndOfMessage = (boolean)1;
                  var2 = true;
               } else {
                  String[] var3 = var1.split(" +");
                  if(var3.length >= 2) {
                     byte var4 = 0;

                     try {
                        int var5 = Integer.parseInt(var3[var4]);
                        this.mMessageNumber = var5;
                     } catch (NumberFormatException var12) {
                        var2 = false;
                        return var2;
                     }

                     String var6 = var3[1];
                     this.mUniqueId = var6;
                     this.mEndOfMessage = (boolean)0;
                     var2 = true;
                  } else if(var3.length == 1) {
                     byte var8 = 0;

                     try {
                        int var9 = Integer.parseInt(var3[var8]);
                        this.mMessageNumber = var9;
                     } catch (NumberFormatException var11) {
                        var2 = false;
                        return var2;
                     }

                     this.mUniqueId = "";
                     this.mEndOfMessage = (boolean)0;
                     var2 = true;
                  } else {
                     var2 = false;
                  }
               }
            } else {
               var2 = false;
            }

            return var2;
         }

         public boolean parseSingleLine(String var1) {
            this.mErr = (boolean)0;
            boolean var2;
            if(var1 != null && var1.length() != 0) {
               char var3 = var1.charAt(0);
               if(var3 == 43) {
                  String[] var4 = var1.split(" +");
                  if(var4.length >= 3) {
                     byte var5 = 1;

                     try {
                        int var6 = Integer.parseInt(var4[var5]);
                        this.mMessageNumber = var6;
                     } catch (NumberFormatException var9) {
                        var2 = false;
                        return var2;
                     }

                     String var7 = var4[2];
                     this.mUniqueId = var7;
                     this.mEndOfMessage = (boolean)1;
                     var2 = true;
                     return var2;
                  }
               } else if(var3 == 45) {
                  this.mErr = (boolean)1;
                  var2 = true;
                  return var2;
               }

               var2 = false;
            } else {
               var2 = false;
            }

            return var2;
         }
      }
   }

   class Pop3Capabilities {

      public boolean pipelining;
      public boolean stls;
      public boolean top;
      public boolean uidl;
      public boolean user;


      Pop3Capabilities() {}

      public String toString() {
         Object[] var1 = new Object[5];
         Boolean var2 = Boolean.valueOf(this.stls);
         var1[0] = var2;
         Boolean var3 = Boolean.valueOf(this.top);
         var1[1] = var3;
         Boolean var4 = Boolean.valueOf(this.user);
         var1[2] = var4;
         Boolean var5 = Boolean.valueOf(this.uidl);
         var1[3] = var5;
         Boolean var6 = Boolean.valueOf(this.pipelining);
         var1[4] = var6;
         return String.format("STLS %b, TOP %b, USER %b, UIDL %b, PIPELINING %b", var1);
      }
   }

   class Pop3ResponseInputStream extends InputStream {

      boolean mFinished;
      InputStream mIn;
      boolean mStartOfLine = 1;


      public Pop3ResponseInputStream(InputStream var2) {
         this.mIn = var2;
      }

      public int read() throws IOException {
         int var1;
         if(this.mFinished) {
            var1 = -1;
         } else {
            int var2 = this.mIn.read();
            if(this.mStartOfLine && var2 == 46) {
               var2 = this.mIn.read();
               if(var2 == 13) {
                  this.mFinished = (boolean)1;
                  int var3 = this.mIn.read();
                  var1 = -1;
                  return var1;
               }
            }

            byte var4;
            if(var2 == 10) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            this.mStartOfLine = (boolean)var4;
            var1 = var2;
         }

         return var1;
      }
   }
}
