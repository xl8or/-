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
import com.android.email.mail.Snippet;
import com.android.email.mail.Store;
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
   private static final boolean DEBUG_ATTACHMENTS = false;
   private static final int MESSAGE_BODY_MAX_SIZE = 1048576;
   private static final HashMap<String, Integer> sServerMailboxNames = new HashMap();


   static {
      String[] var0 = new String[]{"_display_name", "_size"};
      ATTACHMENT_META_COLUMNS_PROJECTION = var0;
   }

   public LegacyConversions() {}

   private static void addOneAttachment(Context var0, EmailContent.Message var1, Part var2, boolean var3, EmailContent.Account var4) throws MessagingException, IOException {
      EmailContent.Attachment var5 = new EmailContent.Attachment();
      String var6 = MimeUtility.getHeaderParameter(MimeUtility.unfoldAndDecode(var2.getContentType()), "name");
      String var7;
      if(var6 == null) {
         var7 = MimeUtility.getHeaderParameter(MimeUtility.unfoldAndDecode(var2.getDisposition()), "filename");
      } else {
         var7 = var6;
      }

      Uri var8;
      String var11;
      label332: {
         var8 = null;
         Object var9 = null;
         if(var3) {
            Body var10 = var2.getBody();
            if(var10 instanceof LocalStore.LocalAttachmentBody) {
               var8 = ((LocalStore.LocalAttachmentBody)var10).getContentUri();
               if(var8 != null) {
                  var11 = var8.toString();
                  break label332;
               }
            }
         }

         var11 = (String)var9;
      }

      long var18;
      label326: {
         long var12 = 0L;
         if(var3) {
            if(var8 != null) {
               ContentResolver var14 = var0.getContentResolver();
               String[] var15 = ATTACHMENT_META_COLUMNS_PROJECTION;
               Cursor var16 = var14.query(var8, var15, (String)null, (String[])null, (String)null);
               long var65;
               if(var16 != null) {
                  boolean var61 = false;

                  long var66;
                  label316: {
                     int var17;
                     label315: {
                        try {
                           var61 = true;
                           if(var16.moveToFirst()) {
                              var17 = var16.getInt(1);
                              var61 = false;
                              break label315;
                           }

                           var61 = false;
                        } finally {
                           if(var61) {
                              var16.close();
                           }
                        }

                        var66 = var12;
                        break label316;
                     }

                     var66 = (long)var17;
                  }

                  var16.close();
                  var65 = var66;
               } else {
                  var65 = var12;
               }

               var18 = var65;
               break label326;
            }
         } else {
            String var52 = var2.getDisposition();
            if(var52 != null) {
               var52 = MimeUtility.getHeaderParameter(var52, "size");
               if(var52 != null) {
                  var18 = Long.parseLong(var52);
                  break label326;
               }
            }
         }

         var18 = var12;
      }

      String[] var19 = var2.getHeader("X-Android-Attachment-StoreData");
      String var20;
      if(var19 != null) {
         var20 = var19[0];
      } else {
         var20 = null;
      }

      byte var27;
      label305: {
         var5.mFileName = var7;
         String var21 = var2.getMimeType();
         var5.mMimeType = var21;
         var5.mSize = var18;
         String var22 = var2.getContentId();
         var5.mContentId = var22;
         var5.mContentUri = var11;
         long var23 = var1.mId;
         var5.mMessageKey = var23;
         var5.mLocation = var20;
         var5.mEncoding = "B";
         byte var25 = 0;
         if(var4 != null) {
            Store.StoreInfo var64 = Store.StoreInfo.getStoreInfo(var4.getStoreUri(var0), var0);
            if(var64 != null) {
               String var26 = var64.mScheme;
               var27 = "pop3".equalsIgnoreCase(var26);
               break label305;
            }
         }

         var27 = var25;
      }

      if(var27 == 1) {
         Uri var28 = EmailContent.Attachment.MESSAGE_ID_URI;
         long var29 = var1.mId;
         Uri var31 = ContentUris.withAppendedId(var28, var29);
         ContentResolver var32 = var0.getContentResolver();
         String[] var33 = EmailContent.Attachment.CONTENT_PROJECTION;
         Cursor var34 = var32.query(var31, var33, (String)null, (String[])null, (String)null);
         boolean var67 = false;

         while(true) {
            boolean var58 = false;

            try {
               var58 = true;
               if(var34.moveToNext()) {
                  EmailContent.Attachment var35 = (new EmailContent.Attachment()).restore(var34);
                  String var36 = var35.mFileName;
                  String var37 = var5.mFileName;
                  if(stringNotEqual(var36, var37)) {
                     continue;
                  }

                  String var38 = var35.mMimeType;
                  String var39 = var5.mMimeType;
                  if(stringNotEqual(var38, var39)) {
                     continue;
                  }

                  String var40 = var35.mContentId;
                  String var41 = var5.mContentId;
                  if(stringNotEqual(var40, var41)) {
                     continue;
                  }

                  String var42 = var35.mLocation;
                  String var43 = var5.mLocation;
                  if(stringNotEqual(var42, var43)) {
                     continue;
                  }

                  var67 = true;
                  long var44 = var35.mId;
                  var5.mId = var44;
                  var58 = false;
                  break;
               }

               var58 = false;
               break;
            } finally {
               if(var58) {
                  var34.close();
               }
            }
         }

         var34.close();
         if(!var67) {
            var5.save(var0);
         }
      } else {
         var5.save(var0);
      }

      if(!var3) {
         long var47 = var1.mAccountKey;
         saveAttachmentBody(var0, var2, var5, var47);
      }

      if(var1.mAttachments == null) {
         ArrayList var49 = new ArrayList();
         var1.mAttachments = var49;
      }

      var1.mAttachments.add(var5);
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
      long var17 = var1.mAccountKey;
      var2.setAccountKey(var17);
      long var19 = var1.mSevenAccountKey;
      var2.setSevenAccountKey(var19);
      int var21 = var1.mTypeMsg;
      var2.setTypeMsg(var21);
      long var22 = var1.mTimeLimit;
      var2.setTimeLimit(var22);
      long var24 = var1.mSizeLimit;
      var2.setSizeLimit(var24);
      return var2;
   }

   private static String makeDisplayName(Context var0, String var1, String var2, String var3) {
      Address var4 = null;
      int var5 = 0;
      String[] var6 = new String[]{var1, var2, var3};
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Address[] var9 = Address.unpack(var6[var8]);
         int var10 = var9.length;
         var5 += var10;
         if(var4 == null && var9.length > 0) {
            var4 = var9[0];
         }
      }

      String var11;
      if(var5 == 0) {
         var11 = "";
      } else {
         String var12 = null;
         if(var4 != null) {
            var12 = var4.toFriendly();
         }

         if(var5 == 1) {
            var11 = var12;
         } else {
            Object[] var13 = new Object[]{var12, null};
            Integer var14 = Integer.valueOf(var5 - 1);
            var13[1] = var14;
            var11 = var0.getString(2131167151, var13);
         }
      }

      return var11;
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
      int var10 = var1.getDeletePolicy();
      var2.setDeletePolicy(var10);
      String var11 = var1.getUuid();
      var2.mUuid = var11;
      String var12 = var1.mSenderName;
      var2.setName(var12);
      String var13 = var1.mRingtoneUri;
      var2.setRingtone(var13);
      String var14 = var1.mProtocolVersion;
      var2.mProtocolVersion = var14;
      int var15 = var1.mSecurityFlags;
      var2.mSecurityFlags = var15;
      String var16 = var1.mSignature;
      var2.mSignature = var16;
      String var17 = var1.getStoreUri(var0);
      var2.setStoreUri(var17);
      String var18 = var1.getSenderUri(var0);
      var2.setSenderUri(var18);
      long var19 = var1.mAccountKey;
      var2.setAccountKey(var19);
      long var21 = var1.mSevenAccountKey;
      var2.setSevenAccountKey(var21);
      int var23 = var1.mTypeMsg;
      var2.setTypeMsg(var23);
      long var24 = var1.mTimeLimit;
      var2.setTimeLimit(var24);
      long var26 = var1.mSizeLimit;
      var2.setSizeLimit(var26);
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
      long var27 = var1.mId;
      var2.setMessageId_original(var27);
      var2.setHeader("Content-Type", "multipart/mixed");
      MimeMultipart var29 = new MimeMultipart();
      var29.setSubType("mixed");
      var2.setBody(var29);

      try {
         long var30 = var1.mId;
         String var32 = EmailContent.Body.restoreBodyHtmlWithMessageId(var0, var30);
         addTextBodyPart(var29, "text/html", (String)null, var32);
      } catch (RuntimeException var77) {
         StringBuilder var49 = (new StringBuilder()).append("Exception while reading html body ");
         String var50 = var77.toString();
         String var51 = var49.append(var50).toString();
         int var52 = Log.d("Email", var51);
      }

      try {
         long var33 = var1.mId;
         String var35 = EmailContent.Body.restoreBodyTextWithMessageId(var0, var33);
         addTextBodyPart(var29, "text/plain", (String)null, var35);
      } catch (RuntimeException var76) {
         StringBuilder var54 = (new StringBuilder()).append("Exception while reading text body ");
         String var55 = var76.toString();
         String var56 = var54.append(var55).toString();
         int var57 = Log.d("Email", var56);
      }

      boolean var36;
      if((var1.mFlags & 1) != 0) {
         var36 = true;
      } else {
         var36 = false;
      }

      boolean var37;
      if((var1.mFlags & 2) != 0) {
         var37 = true;
      } else {
         var37 = false;
      }

      if(var36 || var37) {
         try {
            long var38 = var1.mId;
            String var40 = EmailContent.Body.restoreIntroTextWithMessageId(var0, var38);
            addTextBodyPart(var29, "text/plain", "quoted-intro", var40);
         } catch (RuntimeException var75) {
            StringBuilder var59 = (new StringBuilder()).append("Exception while reading text reply ");
            String var60 = var75.toString();
            String var61 = var59.append(var60).toString();
            int var62 = Log.d("Email", var61);
         }

         String var41;
         if(var36) {
            var41 = "quoted-reply";
         } else {
            var41 = "quoted-forward";
         }

         try {
            long var42 = var1.mId;
            String var44 = EmailContent.Body.restoreReplyHtmlWithMessageId(var0, var42);
            addTextBodyPart(var29, "text/html", var41, var44);
         } catch (RuntimeException var74) {
            StringBuilder var64 = (new StringBuilder()).append("Exception while reading html reply ");
            String var65 = var74.toString();
            String var66 = var64.append(var65).toString();
            int var67 = Log.d("Email", var66);
         }

         try {
            long var45 = var1.mId;
            String var47 = EmailContent.Body.restoreReplyTextWithMessageId(var0, var45);
            addTextBodyPart(var29, "text/plain", var41, var47);
         } catch (RuntimeException var73) {
            StringBuilder var69 = (new StringBuilder()).append("Exception while reading text reply ");
            String var70 = var73.toString();
            String var71 = var69.append(var70).toString();
            int var72 = Log.d("Email", var71);
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
         long var13 = 0L;
         if(var7 != null) {
            var13 = (long)IOUtils.copy(var7, (OutputStream)var12);
            var7.close();
         }

         if(var12 != null) {
            var12.close();
         }

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

   public static void updateAttachments(Context var0, EmailContent.Message var1, ArrayList<Part> var2, boolean var3, EmailContent.Account var4) throws MessagingException, IOException {
      var1.mAttachments = null;
      Iterator var5 = var2.iterator();

      while(var5.hasNext()) {
         Part var6 = (Part)var5.next();
         addOneAttachment(var0, var1, var6, var3, var4);
      }

   }

   public static boolean updateBodyFields(Context var0, EmailContent.Body var1, EmailContent.Message var2, ArrayList<Part> var3) throws MessagingException {
      long var4 = var2.mId;
      var1.mMessageKey = var4;
      Iterator var6 = var3.iterator();
      StringBuffer var7 = null;
      StringBuffer var8 = null;
      StringBuffer var9 = null;
      StringBuffer var10 = null;
      StringBuffer var11 = null;

      while(var6.hasNext()) {
         Part var12 = (Part)var6.next();
         Object var13 = null;
         String[] var14 = var12.getHeader("X-Android-Body-Quoted-Part");
         Object var15 = null;
         String var16;
         if(var14 != null && var14.length > 0) {
            var16 = var14[0];
         } else {
            var16 = (String)var15;
         }

         String var17 = var12.getMimeType();
         boolean var18 = "text/html".equalsIgnoreCase(var17);

         String var25;
         label111: {
            String var20;
            label110: {
               label124: {
                  String var19;
                  try {
                     var19 = MimeUtility.getTextFromPart(var12);
                  } catch (Exception var46) {
                     var20 = (String)var13;
                     break label124;
                  }

                  var20 = var19;

                  label104: {
                     try {
                        if(var20.length() <= 1048576) {
                           break label110;
                        }

                        if(!var18) {
                           StringBuilder var32 = new StringBuilder();
                           String var33 = var20.substring(0, 1048576);
                           StringBuilder var34 = var32.append(var33).append("\n\r\n\r...\n\r\n\r");
                           String var35 = var0.getString(2131167129);
                           var19 = var34.append(var35).append("\n\r").toString();
                           break label104;
                        }

                        StringBuilder var21 = new StringBuilder();
                        String var22 = var20.substring(0, 1048576);
                        StringBuilder var23 = var21.append(var22).append("<BR><BR>...<BR><BR>");
                        String var24 = var0.getString(2131167129);
                        var19 = var23.append(var24).append("<BR>").toString();
                     } catch (Exception var45) {
                        break label124;
                     }

                     var20 = var19;
                     break label110;
                  }

                  var20 = var19;
                  break label110;
               }

               var25 = var20;
               break label111;
            }

            var25 = var20;
         }

         StringBuffer var28;
         StringBuffer var49;
         if(var16 != null) {
            boolean var26 = "quoted-reply".equalsIgnoreCase(var16);
            boolean var27 = "quoted-forward".equalsIgnoreCase(var16);
            boolean var47 = "quoted-intro".equalsIgnoreCase(var16);
            if(var26 || var27) {
               if(var18) {
                  var28 = appendTextPart(var9, var25);
                  var49 = var8;
               } else {
                  var49 = appendTextPart(var8, var25);
                  var28 = var9;
               }

               int var29 = var2.mFlags & -4;
               var2.mFlags = var29;
               int var30 = var2.mFlags;
               byte var48;
               if(var26) {
                  var48 = 1;
               } else {
                  var48 = 2;
               }

               int var31 = var30 | var48;
               var2.mFlags = var31;
               var8 = var49;
               var9 = var28;
               continue;
            }

            if(var47) {
               var7 = appendTextPart(var7, var25);
               continue;
            }
         }

         if(var18) {
            var28 = appendTextPart(var11, var25);
            var49 = var10;
         } else {
            var49 = appendTextPart(var10, var25);
            var28 = var11;
         }

         var10 = var49;
         var11 = var28;
      }

      if(var10 != null && var10.length() != 0) {
         String var37 = var10.toString();
         var1.mTextContent = var37;
         String var38 = Snippet.fromPlainText(var1.mTextContent);
         var2.mSnippet = var38;
      }

      if(var11 != null && var11.length() != 0) {
         String var39 = var11.toString();
         var1.mHtmlContent = var39;
         if(var2.mSnippet == null) {
            String var40 = Snippet.fromHtmlText(var1.mHtmlContent);
            var2.mSnippet = var40;
         }
      }

      if(var9 != null && var9.length() != 0) {
         String var41 = var9.toString();
         var1.mHtmlReply = var41;
      }

      if(var8 != null && var8.length() != 0) {
         String var42 = var8.toString();
         var1.mTextReply = var42;
      }

      if(var7 != null && var7.length() != 0) {
         String var43 = var7.toString();
         var1.mIntroText = var43;
      }

      return true;
   }

   public static boolean updateMessageFields(Context var0, EmailContent.Message var1, Message var2, long var3, long var5) throws MessagingException {
      Address[] var7 = var2.getFrom();
      Message.RecipientType var8 = Message.RecipientType.TO;
      Address[] var11 = var2.getRecipients(var8);
      Message.RecipientType var12 = Message.RecipientType.CC;
      Address[] var15 = var2.getRecipients(var12);
      Message.RecipientType var16 = Message.RecipientType.BCC;
      Address[] var19 = var2.getRecipients(var16);
      Address[] var20 = var2.getReplyTo();
      String var21 = var2.getSubject();
      Date var22 = var2.getInternalDate();
      long var23 = 0L;
      Date var25 = var2.getSentDate();
      if(var25 != null) {
         var23 = var25.getTime();
      } else {
         Date var73 = var2.getReceivedDate();
         if(var22 != null) {
            var23 = var22.getTime();
         } else if(var73 != null) {
            var23 = var73.getTime();
         }
      }

      if(var23 != 0L) {
         var1.mTimeStamp = var23;
      } else {
         int var74 = Log.d("Email", "*****[toma_naver] localMessage.mTimeStamp = null ");
      }

      if(var7 != null && var7.length > 0) {
         String var28 = var7[0].toFriendly();
         var1.mDisplayName = var28;
      } else {
         int var75 = Log.d("Email", "*****[toma_naver] localMessage.mDisplayName null ");
      }

      if(var21 != null) {
         var1.mSubject = var21;
      } else {
         int var76 = Log.d("Email", "*****[toma_naver] localMessage.mSubject == null");
      }

      Flag var30 = Flag.SEEN;
      boolean var33 = var2.isSet(var30);
      var1.mFlagRead = var33;
      if(var2.getIntegrity()) {
         byte var34 = 1;
         var1.mFlagRead = (boolean)var34;
      }

      Flag var35 = Flag.ANSWERED;
      boolean var38 = var2.isSet(var35);
      var1.mFlagReply = var38;
      if(var1.mFlagLoaded != 1) {
         label65: {
            if(var1.mDisplayName != null) {
               String var39 = var1.mDisplayName;
               if(!"".equals(var39)) {
                  byte var77 = 2;
                  var1.mFlagLoaded = var77;
                  break label65;
               }
            }

            byte var40 = 0;
            var1.mFlagLoaded = var40;
         }
      }

      Flag var41 = Flag.FLAGGED;
      boolean var44 = var2.isSet(var41);
      var1.mFlagFavorite = var44;
      String var45 = var2.getUid();
      var1.mServerId = var45;
      if(var22 != null) {
         long var46 = var22.getTime();
         var1.mServerTimeStamp = var46;
      }

      String var48 = ((MimeMessage)var2).getMessageId();
      if(var48 != null) {
         var1.mMessageId = var48;
      }

      var1.mMailboxKey = var5;
      var1.mAccountKey = var3;
      if(var7 != null && var7.length > 0) {
         String var54 = Address.pack(var7);
         var1.mFrom = var54;
      }

      String var55 = Address.pack(var11);
      var1.mTo = var55;
      String var56 = Address.pack(var15);
      var1.mCc = var56;
      String var57 = Address.pack(var19);
      var1.mBcc = var57;
      String var58 = Address.pack(var20);
      var1.mReplyTo = var58;
      byte var62 = 5;
      long var63 = EmailContent.Mailbox.findMailboxOfType(var0, var3, var62);
      if(var5 == var63) {
         String var65 = var1.mTo;
         Object var68 = null;
         Object var69 = null;
         String var70 = makeDisplayName(var0, var65, (String)var68, (String)var69);
         var1.mDisplayName = var70;
      } else if(var7 != null && var7.length > 0) {
         String var78 = var7[0].toFriendly();
         var1.mDisplayName = var78;
      } else {
         String var79 = "Email";
         String var80 = "***** localMessage.mDisplayName null ";
         Log.d(var79, var80);
      }

      long var71 = EmailContent.Message.getThreadIdFromSubject(var1.mSubject);
      var1.mThreadId = var71;
      return true;
   }
}
