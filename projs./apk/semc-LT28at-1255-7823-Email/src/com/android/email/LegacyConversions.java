package com.android.email;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.Account;
import com.android.email.mail.Address;
import com.android.email.mail.Body;
import com.android.email.mail.Flag;
import com.android.email.mail.Folder;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Part;
import com.android.email.mail.internet.MimeBodyPart;
import com.android.email.mail.internet.MimeMessage;
import com.android.email.mail.internet.MimeMultipart;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.mail.internet.TextBody;
import com.android.email.mail.store.LocalStore;
import com.android.email.provider.AttachmentProvider;
import com.android.email.provider.EmailContent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.io.IOUtils;

public class LegacyConversions {

   private static final String[] ATTACHMENT_META_COLUMNS_PROJECTION;
   private static final int ATTACHMENT_META_COLUMNS_SIZE = 1;
   static final String BODY_QUOTED_PART_FORWARD = "quoted-forward";
   static final String BODY_QUOTED_PART_INTRO = "quoted-intro";
   static final String BODY_QUOTED_PART_REPLY = "quoted-reply";
   private static final boolean DEBUG_ATTACHMENTS;
   private static final HashMap<String, Integer> sServerMailboxNames = new HashMap();


   static {
      String[] var0 = new String[]{"_display_name", "_size"};
      ATTACHMENT_META_COLUMNS_PROJECTION = var0;
   }

   public LegacyConversions() {}

   private static void addOneAttachment(Context var0, EmailContent.Message var1, Part var2, boolean var3) throws MessagingException, IOException {
      EmailContent.Attachment var4 = new EmailContent.Attachment();
      String var5 = MimeUtility.getHeaderParameter(MimeUtility.unfoldAndDecode(var2.getContentType()), "name");
      String var6;
      if(var5 == null) {
         var6 = MimeUtility.getHeaderParameter(MimeUtility.unfoldAndDecode(var2.getDisposition()), "filename");
      } else {
         var6 = var5;
      }

      Uri var7;
      String var10;
      label298: {
         var7 = null;
         Object var8 = null;
         if(var3) {
            Body var9 = var2.getBody();
            if(var9 instanceof LocalStore.LocalAttachmentBody) {
               var7 = ((LocalStore.LocalAttachmentBody)var9).getContentUri();
               if(var7 != null) {
                  var10 = var7.toString();
                  break label298;
               }
            }
         }

         var10 = (String)var8;
      }

      long var16;
      label292: {
         long var11 = 0L;
         if(var3) {
            if(var7 != null) {
               ContentResolver var13 = var0.getContentResolver();
               String[] var14 = ATTACHMENT_META_COLUMNS_PROJECTION;
               Cursor var63 = var13.query(var7, var14, (String)null, (String[])null, (String)null);
               if(var63 != null) {
                  boolean var60 = false;

                  long var64;
                  label283: {
                     label282: {
                        int var15;
                        try {
                           var60 = true;
                           if(!var63.moveToFirst()) {
                              var60 = false;
                              break label282;
                           }

                           var15 = var63.getInt(1);
                           var60 = false;
                        } finally {
                           if(var60) {
                              var63.close();
                           }
                        }

                        var64 = (long)var15;
                        break label283;
                     }

                     var64 = var11;
                  }

                  var63.close();
                  var16 = var64;
                  break label292;
               }
            }
         } else {
            var5 = var2.getDisposition();
            if(var5 != null) {
               var5 = MimeUtility.getHeaderParameter(var5, "size");
               if(var5 != null) {
                  long var51 = Long.parseLong(var5);
                  break label292;
               }
            }
         }

         var16 = var11;
      }

      long var18;
      if(var16 == 0L) {
         var18 = (long)var2.getSize();
      } else {
         var18 = var16;
      }

      String[] var20 = var2.getHeader("X-Android-Attachment-StoreData");
      String var21;
      if(var20 != null) {
         var21 = var20[0];
      } else {
         var21 = null;
      }

      var4.mFileName = var6;
      String var22 = var2.getMimeType();
      var4.mMimeType = var22;
      var4.mSize = var18;
      String var23 = var2.getContentId();
      var4.mContentId = var23;
      var4.mContentUri = var10;
      long var24 = var1.mId;
      var4.mMessageKey = var24;
      var4.mLocation = var21;
      var4.mEncoding = "B";
      Uri var26 = EmailContent.Attachment.MESSAGE_ID_URI;
      long var27 = var1.mId;
      Uri var29 = ContentUris.withAppendedId(var26, var27);
      ContentResolver var30 = var0.getContentResolver();
      String[] var31 = EmailContent.Attachment.CONTENT_PROJECTION;
      Cursor var32 = var30.query(var29, var31, (String)null, (String[])null, (String)null);
      boolean var33 = false;

      while(true) {
         boolean var57 = false;

         try {
            var57 = true;
            if(var32.moveToNext()) {
               EmailContent.Attachment var34 = (new EmailContent.Attachment()).restore(var32);
               String var35 = var34.mFileName;
               String var36 = var4.mFileName;
               if(stringNotEqual(var35, var36)) {
                  continue;
               }

               String var37 = var34.mMimeType;
               String var38 = var4.mMimeType;
               if(stringNotEqual(var37, var38)) {
                  continue;
               }

               String var39 = var34.mContentId;
               String var40 = var4.mContentId;
               if(stringNotEqual(var39, var40)) {
                  continue;
               }

               String var41 = var34.mLocation;
               String var42 = var4.mLocation;
               if(stringNotEqual(var41, var42)) {
                  continue;
               }

               var33 = true;
               long var43 = var34.mId;
               var4.mId = var43;
               var57 = false;
               break;
            }

            var57 = false;
            break;
         } finally {
            if(var57) {
               var32.close();
            }
         }
      }

      var32.close();
      if(!var33) {
         var4.save(var0);
      }

      if(!var3) {
         long var46 = var1.mAccountKey;
         saveAttachmentBody(var0, var2, var4, var46);
      }

      if(var1.mAttachments == null) {
         ArrayList var48 = new ArrayList();
         var1.mAttachments = var48;
      }

      var1.mAttachments.add(var4);
      var1.mFlagAttachment = (boolean)1;
   }

   private static void addTextBodyPart(MimeMultipart var0, String var1, String var2, String var3) throws MessagingException {
      if(var3 != null) {
         TextBody var4 = new TextBody(var3);
         MimeBodyPart var5 = new MimeBodyPart(var4, var1);
         if(var2 != null) {
            var5.addHeader("X-Android-Body-Quoted-Part", var2);
         }

         var0.addBodyPart(var5);
      }
   }

   private static StringBuffer appendTextPart(StringBuffer var0, String var1) {
      if(var1 != null) {
         if(var0 == null) {
            var0 = new StringBuffer(var1);
         } else {
            if(var0.length() > 0) {
               StringBuffer var2 = var0.append('\n');
            }

            var0.append(var1);
         }
      }

      return var0;
   }

   public static int inferMailboxTypeFromName(Context param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static EmailContent.Account makeAccount(Context var0, Account var1) {
      EmailContent.Account var2 = new EmailContent.Account();
      String var3 = var1.getDescription();
      var2.setDisplayName(var3);
      String var4 = var1.getEmail();
      var2.setEmailAddress(var4);
      var2.mSyncKey = null;
      int var5 = var1.getSyncWindow();
      var2.setSyncLookback(var5);
      int var6 = var1.getAutomaticCheckIntervalMinutes();
      var2.setSyncInterval(var6);
      int var7 = 0;
      if(var1.isNotifyNewMail()) {
         var7 = 0 | 1;
      }

      if(var1.isVibrate()) {
         var7 |= 2;
      }

      if(var1.isVibrateWhenSilent()) {
         var7 |= 64;
      }

      if(var1.isLEDOn()) {
         var7 |= 128;
      }

      var2.setFlags(var7);
      int var8 = var1.getDeletePolicy();
      var2.setDeletePolicy(var8);
      String var9 = var1.getUuid();
      var2.mCompatibilityUuid = var9;
      String var10 = var1.getName();
      var2.setSenderName(var10);
      String var11 = var1.getRingtone();
      var2.setRingtone(var11);
      String var12 = var1.mProtocolVersion;
      var2.mProtocolVersion = var12;
      var2.mNewMessageCount = 0;
      int var13 = var1.mSecurityFlags;
      var2.mSecurityFlags = var13;
      var2.mSecuritySyncKey = null;
      String var14 = var1.mSignature;
      var2.mSignature = var14;
      String var15 = var1.getStoreUri();
      var2.setStoreUri(var0, var15);
      String var16 = var1.getSenderUri();
      var2.setSenderUri(var0, var16);
      return var2;
   }

   static Account makeLegacyAccount(Context var0, EmailContent.Account var1) {
      Account var2 = new Account(var0);
      String var3 = var1.getDisplayName();
      var2.setDescription(var3);
      String var4 = var1.getEmailAddress();
      var2.setEmail(var4);
      int var5 = var1.getSyncLookback();
      var2.setSyncWindow(var5);
      int var6 = var1.getSyncInterval();
      var2.setAutomaticCheckIntervalMinutes(var6);
      byte var7;
      if((var1.getFlags() & 1) != 0) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var2.setNotifyNewMail((boolean)var7);
      byte var8;
      if((var1.getFlags() & 2) != 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var2.setVibrate((boolean)var8);
      byte var9;
      if((var1.getFlags() & 64) != 0) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      var2.setVibrateWhenSilent((boolean)var9);
      byte var10;
      if((var1.getFlags() & 128) != 0) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var2.setLED((boolean)var10);
      int var11 = var1.getDeletePolicy();
      var2.setDeletePolicy(var11);
      String var12 = var1.getUuid();
      var2.mUuid = var12;
      String var13 = var1.mSenderName;
      var2.setName(var13);
      String var14 = var1.mRingtoneUri;
      var2.setRingtone(var14);
      String var15 = var1.mProtocolVersion;
      var2.mProtocolVersion = var15;
      int var16 = var1.mSecurityFlags;
      var2.mSecurityFlags = var16;
      String var17 = var1.mSignature;
      var2.mSignature = var17;
      String var18 = var1.getStoreUri(var0);
      var2.setStoreUri(var18);
      String var19 = var1.getSenderUri(var0);
      var2.setSenderUri(var19);
      return var2;
   }

   public static EmailContent.Mailbox makeMailbox(Context var0, EmailContent.Account var1, Folder var2) throws MessagingException {
      EmailContent.Mailbox var3 = new EmailContent.Mailbox();
      String var4 = var2.getName();
      var3.mDisplayName = var4;
      long var5 = var1.mId;
      var3.mAccountKey = var5;
      String var7 = var2.getName();
      int var8 = inferMailboxTypeFromName(var0, var7);
      var3.mType = var8;
      var3.mSyncTime = 0L;
      int var9 = var2.getUnreadMessageCount();
      var3.mUnreadCount = var9;
      var3.mFlagVisible = (boolean)1;
      var3.mFlags = 0;
      var3.mVisibleLimit = 25;
      return var3;
   }

   public static Message makeMessage(Context var0, EmailContent.Message var1) throws MessagingException {
      MimeMessage var2 = new MimeMessage();
      String var3;
      if(var1.mSubject == null) {
         var3 = "";
      } else {
         var3 = var1.mSubject;
      }

      var2.setSubject(var3);
      Address[] var4 = Address.unpack(var1.mFrom);
      if(var4.length > 0) {
         Address var5 = var4[0];
         var2.setFrom(var5);
      }

      long var6 = var1.mTimeStamp;
      Date var8 = new Date(var6);
      var2.setSentDate(var8);
      String var9 = var1.mServerId;
      var2.setUid(var9);
      Flag var10 = Flag.DELETED;
      byte var11;
      if(var1.mFlagLoaded == 3) {
         var11 = 1;
      } else {
         var11 = 0;
      }

      var2.setFlag(var10, (boolean)var11);
      Flag var12 = Flag.SEEN;
      boolean var13 = var1.mFlagRead;
      var2.setFlag(var12, var13);
      Flag var14 = Flag.FLAGGED;
      boolean var15 = var1.mFlagFavorite;
      var2.setFlag(var14, var15);
      Message.RecipientType var16 = Message.RecipientType.TO;
      Address[] var17 = Address.unpack(var1.mTo);
      var2.setRecipients(var16, var17);
      Message.RecipientType var18 = Message.RecipientType.CC;
      Address[] var19 = Address.unpack(var1.mCc);
      var2.setRecipients(var18, var19);
      Message.RecipientType var20 = Message.RecipientType.BCC;
      Address[] var21 = Address.unpack(var1.mBcc);
      var2.setRecipients(var20, var21);
      Address[] var22 = Address.unpack(var1.mReplyTo);
      var2.setReplyTo(var22);
      long var23 = var1.mServerTimeStamp;
      Date var25 = new Date(var23);
      var2.setInternalDate(var25);
      String var26 = var1.mMessageId;
      var2.setMessageId(var26);
      var2.setHeader("Content-Type", "multipart/mixed");
      MimeMultipart var27 = new MimeMultipart();
      var27.setSubType("mixed");
      var2.setBody(var27);

      try {
         long var28 = var1.mId;
         String var30 = EmailContent.Body.restoreBodyHtmlWithMessageId(var0, var28);
         addTextBodyPart(var27, "text/html", (String)null, var30);
      } catch (RuntimeException var75) {
         StringBuilder var47 = (new StringBuilder()).append("Exception while reading html body ");
         String var48 = var75.toString();
         String var49 = var47.append(var48).toString();
         int var50 = Log.d("Email", var49);
      }

      try {
         long var31 = var1.mId;
         String var33 = EmailContent.Body.restoreBodyTextWithMessageId(var0, var31);
         addTextBodyPart(var27, "text/plain", (String)null, var33);
      } catch (RuntimeException var74) {
         StringBuilder var52 = (new StringBuilder()).append("Exception while reading text body ");
         String var53 = var74.toString();
         String var54 = var52.append(var53).toString();
         int var55 = Log.d("Email", var54);
      }

      boolean var34;
      if((var1.mFlags & 1) != 0) {
         var34 = true;
      } else {
         var34 = false;
      }

      boolean var35;
      if((var1.mFlags & 2) != 0) {
         var35 = true;
      } else {
         var35 = false;
      }

      if(var34 || var35) {
         try {
            long var36 = var1.mId;
            String var38 = EmailContent.Body.restoreIntroTextWithMessageId(var0, var36);
            addTextBodyPart(var27, "text/plain", "quoted-intro", var38);
         } catch (RuntimeException var73) {
            StringBuilder var57 = (new StringBuilder()).append("Exception while reading text reply ");
            String var58 = var73.toString();
            String var59 = var57.append(var58).toString();
            int var60 = Log.d("Email", var59);
         }

         String var39;
         if(var34) {
            var39 = "quoted-reply";
         } else {
            var39 = "quoted-forward";
         }

         try {
            long var40 = var1.mId;
            String var42 = EmailContent.Body.restoreReplyHtmlWithMessageId(var0, var40);
            addTextBodyPart(var27, "text/html", var39, var42);
         } catch (RuntimeException var72) {
            StringBuilder var62 = (new StringBuilder()).append("Exception while reading html reply ");
            String var63 = var72.toString();
            String var64 = var62.append(var63).toString();
            int var65 = Log.d("Email", var64);
         }

         try {
            long var43 = var1.mId;
            String var45 = EmailContent.Body.restoreReplyTextWithMessageId(var0, var43);
            addTextBodyPart(var27, "text/plain", var39, var45);
         } catch (RuntimeException var71) {
            StringBuilder var67 = (new StringBuilder()).append("Exception while reading text reply ");
            String var68 = var71.toString();
            String var69 = var67.append(var68).toString();
            int var70 = Log.d("Email", var69);
         }
      }

      return var2;
   }

   public static void saveAttachmentBody(Context var0, Part var1, EmailContent.Attachment var2, long var3) throws MessagingException, IOException {
      if(var1.getBody() != null) {
         long var5 = var2.mId;
         InputStream var7 = var1.getBody().getInputStream();
         File var8 = AttachmentProvider.getAttachmentDirectory(var0, var3);
         if(!var8.exists()) {
            boolean var9 = var8.mkdirs();
         }

         File var10 = AttachmentProvider.getAttachmentFilename(var0, var3, var5);
         boolean var11 = var10.createNewFile();
         FileOutputStream var12 = new FileOutputStream(var10);
         long var13 = (long)IOUtils.copy(var7, (OutputStream)var12);
         var7.close();
         var12.close();
         String var15 = AttachmentProvider.getAttachmentUri(var3, var5).toString();
         var2.mSize = var13;
         var2.mContentUri = var15;
         ContentValues var16 = new ContentValues();
         Long var17 = Long.valueOf(var13);
         var16.put("size", var17);
         var16.put("contentUri", var15);
         Uri var18 = ContentUris.withAppendedId(EmailContent.Attachment.CONTENT_URI, var5);
         var0.getContentResolver().update(var18, var16, (String)null, (String[])null);
      }
   }

   static boolean stringNotEqual(String var0, String var1) {
      boolean var2;
      if(var0 == null && var1 == null) {
         var2 = false;
      } else {
         if(var0 == null) {
            var0 = "";
         }

         if(var1 == null) {
            var1 = "";
         }

         if(!var0.equals(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public static void updateAttachments(Context var0, EmailContent.Message var1, ArrayList<Part> var2, boolean var3) throws MessagingException, IOException {
      var1.mAttachments = null;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         Part var5 = (Part)var4.next();
         addOneAttachment(var0, var1, var5, var3);
      }

   }

   public static boolean updateBodyFields(EmailContent.Body var0, EmailContent.Message var1, ArrayList<Part> var2) throws MessagingException {
      long var3 = var1.mId;
      var0.mMessageKey = var3;
      Iterator var5 = var2.iterator();
      StringBuffer var6 = null;
      StringBuffer var7 = null;
      StringBuffer var8 = null;
      StringBuffer var9 = null;
      StringBuffer var10 = null;

      while(var5.hasNext()) {
         Part var11 = (Part)var5.next();
         String var12 = MimeUtility.getTextFromPart(var11);
         String[] var13 = var11.getHeader("X-Android-Body-Quoted-Part");
         Object var14 = null;
         String var15;
         if(var13 != null && var13.length > 0) {
            var15 = var13[0];
         } else {
            var15 = (String)var14;
         }

         String var16 = var11.getMimeType();
         boolean var17 = "text/html".equalsIgnoreCase(var16);
         StringBuffer var19;
         StringBuffer var31;
         if(var15 != null) {
            boolean var28 = "quoted-reply".equalsIgnoreCase(var15);
            boolean var18 = "quoted-forward".equalsIgnoreCase(var15);
            boolean var30 = "quoted-intro".equalsIgnoreCase(var15);
            if(var28 || var18) {
               if(var17) {
                  var19 = appendTextPart(var8, var12);
                  var31 = var7;
               } else {
                  var31 = appendTextPart(var7, var12);
                  var19 = var8;
               }

               int var20 = var1.mFlags & -4;
               var1.mFlags = var20;
               int var21 = var1.mFlags;
               byte var29;
               if(var28) {
                  var29 = 1;
               } else {
                  var29 = 2;
               }

               int var22 = var21 | var29;
               var1.mFlags = var22;
               var7 = var31;
               var8 = var19;
               continue;
            }

            if(var30) {
               var6 = appendTextPart(var6, var12);
               continue;
            }
         }

         if(var17) {
            var19 = appendTextPart(var10, var12);
            var31 = var9;
         } else {
            var31 = appendTextPart(var9, var12);
            var19 = var10;
         }

         var9 = var31;
         var10 = var19;
      }

      if(var9 != null && var9.length() != 0) {
         String var23 = var9.toString();
         var0.mTextContent = var23;
      }

      if(var10 != null && var10.length() != 0) {
         String var24 = var10.toString();
         var0.mHtmlContent = var24;
      }

      if(var8 != null && var8.length() != 0) {
         String var25 = var8.toString();
         var0.mHtmlReply = var25;
      }

      if(var7 != null && var7.length() != 0) {
         String var26 = var7.toString();
         var0.mTextReply = var26;
      }

      if(var6 != null && var6.length() != 0) {
         String var27 = var6.toString();
         var0.mIntroText = var27;
      }

      return true;
   }

   public static boolean updateMessageFields(EmailContent.Message var0, Message var1, long var2, long var4) throws MessagingException {
      Address[] var6 = var1.getFrom();
      Message.RecipientType var7 = Message.RecipientType.TO;
      Address[] var8 = var1.getRecipients(var7);
      Message.RecipientType var9 = Message.RecipientType.CC;
      Address[] var10 = var1.getRecipients(var9);
      Message.RecipientType var11 = Message.RecipientType.BCC;
      Address[] var12 = var1.getRecipients(var11);
      Address[] var13 = var1.getReplyTo();
      String var14 = var1.getSubject();
      Date var15 = var1.getSentDate();
      Date var16 = var1.getInternalDate();
      if(var1.getFolder() != null) {
         String var17 = var1.getFolder().getName();
         if(!"Sent".equals(var17) && !"Outbox".equals(var17) && !"Draft".equals(var17)) {
            if(var6 != null && var6.length > 0) {
               String var36 = var6[0].toFriendly();
               var0.mDisplayName = var36;
            }
         } else {
            Message.RecipientType var18 = Message.RecipientType.TO;
            String var19 = Address.toFriendly(var1.getRecipients(var18));
            var0.mDisplayName = var19;
         }
      }

      if(var15 != null) {
         long var20 = var15.getTime();
         var0.mTimeStamp = var20;
      }

      if(var14 != null) {
         var0.mSubject = var14;
      }

      Flag var22 = Flag.SEEN;
      boolean var23 = var1.isSet(var22);
      var0.mFlagRead = var23;
      if(var0.mFlagLoaded != 1) {
         label42: {
            if(var0.mDisplayName != null) {
               String var24 = var0.mDisplayName;
               if(!"".equals(var24)) {
                  var0.mFlagLoaded = 2;
                  break label42;
               }
            }

            var0.mFlagLoaded = 0;
         }
      }

      Flag var25 = Flag.FLAGGED;
      boolean var26 = var1.isSet(var25);
      var0.mFlagFavorite = var26;
      String var27 = var1.getUid();
      var0.mServerId = var27;
      if(var16 != null) {
         long var28 = var16.getTime();
         var0.mServerTimeStamp = var28;
      }

      String var30 = ((MimeMessage)var1).getMessageId();
      if(var30 != null) {
         var0.mMessageId = var30;
      }

      var0.mMailboxKey = var4;
      var0.mAccountKey = var2;
      if(var6 != null && var6.length > 0) {
         String var31 = Address.pack(var6);
         var0.mFrom = var31;
      }

      String var32 = Address.pack(var8);
      var0.mTo = var32;
      String var33 = Address.pack(var10);
      var0.mCc = var33;
      String var34 = Address.pack(var12);
      var0.mBcc = var34;
      String var35 = Address.pack(var13);
      var0.mReplyTo = var35;
      return true;
   }
}
