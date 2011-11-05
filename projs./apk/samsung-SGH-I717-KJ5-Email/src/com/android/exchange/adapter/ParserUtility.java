package com.android.exchange.adapter;

import android.util.Log;
import android.webkit.MimeTypeMap;
import com.android.email.irm.IRMLicenseParserUtility;
import com.android.email.mail.Address;
import com.android.email.provider.EmailContent;
import com.android.exchange.adapter.AbstractSyncParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ParserUtility {

   private ParserUtility() {}

   public static void addMessageData(AbstractSyncParser var0, EmailContent.Message var1, int var2) throws IOException {
      ArrayList var3 = new ArrayList();

      EmailContent.FollowupFlag var5;
      for(EmailContent.FollowupFlag var4 = null; var0.nextTag(var2) != 3; var4 = var5) {
         switch(var0.tag) {
         case 134:
         case 1102:
            attachmentsParser(var0, var3, var1);
            var5 = var4;
            continue;
         case 142:
            int var31 = Log.e("ParserUtility", "EMAIL_BODY_TRUNCATED1");
            if(var0.getValueInt() == 1) {
               var1.mFlagTruncated = 1;
               int var32 = Log.e("ParserUtility", "EMAIL_BODY_TRUNCATED2");
               var5 = var4;
               continue;
            }
            break;
         case 143:
            String var12 = var0.getValue();
            GregorianCalendar var13 = new GregorianCalendar();
            int var14 = Integer.parseInt(var12.substring(0, 4));
            int var15 = Integer.parseInt(var12.substring(5, 7)) - 1;
            int var16 = Integer.parseInt(var12.substring(8, 10));
            int var17 = Integer.parseInt(var12.substring(11, 13));
            int var18 = Integer.parseInt(var12.substring(14, 16));
            int var19 = Integer.parseInt(var12.substring(17, 19));
            var13.set(var14, var15, var16, var17, var18, var19);
            TimeZone var20 = TimeZone.getTimeZone("GMT");
            var13.setTimeZone(var20);
            long var21 = var13.getTimeInMillis();
            var1.mTimeStamp = var21;
            var5 = var4;
            continue;
         case 146:
            int var36 = Integer.parseInt(var0.getValue());
            var1.mImportance = var36;
            var5 = var4;
            continue;
         case 147:
            int var37 = decodeMsgClass(var0.getValue());
            if(var37 == 2) {
               var1.mEncrypted = (boolean)1;
               var5 = var4;
               continue;
            }

            if(var37 == 1) {
               var1.mSigned = (boolean)1;
               var5 = var4;
               continue;
            }
            break;
         case 148:
            String var23 = var0.getValue();
            var1.mSubject = var23;
            var5 = var4;
            continue;
         case 149:
            byte var24;
            if(var0.getValueInt() == 1) {
               var24 = 1;
            } else {
               var24 = 0;
            }

            var1.mFlagRead = (boolean)var24;
            var5 = var4;
            continue;
         case 150:
            String var6 = Address.pack(Address.parse(var0.getValue()));
            var1.mTo = var6;
            var5 = var4;
            continue;
         case 151:
            String var10 = Address.pack(Address.parse(var0.getValue()));
            var1.mCc = var10;
            var5 = var4;
            continue;
         case 152:
            Address[] var7 = Address.parse(var0.getValue());
            if(var7 != null && var7.length > 0) {
               String var8 = var7[0].toFriendly();
               var1.mDisplayName = var8;
            }

            String var9 = Address.pack(var7);
            var1.mFrom = var9;
            var5 = var4;
            continue;
         case 153:
            String var11 = Address.pack(Address.parse(var0.getValue()));
            var1.mReplyTo = var11;
            var5 = var4;
            continue;
         case 186:
            var5 = new EmailContent.FollowupFlag();
            flagParser(var0, var5);
            long var28 = var1.mMailboxKey;
            var5.MsgId = var28;
            String var30 = var1.mServerId;
            var5.MsgSyncServerId = var30;
            var1.mFollowupFlag = var5;
            continue;
         case 1098:
            bodyParser(var0, var1);
            var5 = var4;
            continue;
         case 1413:
            String var25 = var0.getValue();
            var1.mUmCallerId = var25;
            var5 = var4;
            continue;
         case 1414:
            String var26 = var0.getValue();
            var1.mUmUserNotes = var26;
            var5 = var4;
            continue;
         case 1417:
            byte[] var33 = var0.getValueOpaque();
            String var34 = var0.crypt(var33);
            var1.mConversationId = var34;
            var5 = var4;
            continue;
         case 1418:
            byte[] var35 = var0.getValueOpaque();
            var1.mConversationIndex = var35;
            var5 = var4;
            continue;
         case 1544:
            IRMLicenseParserUtility.parseLicense(var1, var0);
            var5 = var4;
            continue;
         default:
            var0.skipTag();
         }

         var5 = var4;
      }

      if(var3.size() > 0) {
         var1.mAttachments = var3;
      }
   }

   private static void attachmentParser(AbstractSyncParser var0, ArrayList<EmailContent.Attachment> var1, EmailContent.Message var2) throws IOException {
      String var3 = null;
      String var4 = null;
      String var5 = null;

      while(var0.nextTag(133) != 3) {
         switch(var0.tag) {
         case 135:
         case 1105:
            var5 = var0.getValue();
            break;
         case 136:
         case 1100:
            var4 = var0.getValue();
            break;
         case 144:
         case 1104:
            var3 = var0.getValue();
            break;
         case 1415:
            int var7 = var0.getValueInt();
            break;
         case 1416:
            int var6 = var0.getValueInt();
            break;
         default:
            var0.skipTag();
         }
      }

      if(var3 != null) {
         if(var4 != null) {
            if(var5 != null) {
               EmailContent.Attachment var8 = new EmailContent.Attachment();
               var8.mEncoding = "base64";
               long var9 = Long.parseLong(var4);
               var8.mSize = var9;
               var8.mFileName = var3;
               var8.mLocation = var5;
               String var11 = getMimeTypeFromFileName(var3);
               var8.mMimeType = var11;
               var1.add(var8);
               var2.mFlagAttachment = (boolean)1;
            }
         }
      }
   }

   private static void attachmentsParser(AbstractSyncParser var0, ArrayList<EmailContent.Attachment> var1, EmailContent.Message var2) throws IOException {
      while(var0.nextTag(134) != 3) {
         switch(var0.tag) {
         case 133:
         case 1103:
            attachmentParser(var0, var1, var2);
            break;
         default:
            var0.skipTag();
         }
      }

   }

   private static void bodyParser(AbstractSyncParser var0, EmailContent.Message var1) throws IOException {
      String var2 = "1";
      String var3 = "";

      while(var0.nextTag(140) != 3) {
         switch(var0.tag) {
         case 1094:
            var2 = var0.getValue();
            break;
         case 1099:
            var3 = var0.getValue();
            break;
         case 1101:
            int var4 = Log.e("ParserUtility", "BASE_TRUNCATED1");
            if(var0.getValueInt() == 1) {
               var1.mFlagTruncated = 1;
               int var5 = Log.e("ParserUtility", "BASE_TRUNCATED2");
            }
            break;
         default:
            var0.skipTag();
         }
      }

      if(var2.equals("2")) {
         var1.mHtml = var3;
      } else {
         var1.mText = var3;
      }
   }

   public static int decodeMsgClass(String var0) {
      byte var1;
      if(var0 == null) {
         var1 = 0;
      } else {
         if(var0.startsWith("IPM.Note.SMIME")) {
            if(var0.equalsIgnoreCase("IPM.Note.SMIME")) {
               var1 = 2;
               return var1;
            }

            if(var0.equalsIgnoreCase("IPM.Note.SMIME.MultipartSigned")) {
               var1 = 1;
               return var1;
            }
         }

         var1 = 0;
      }

      return var1;
   }

   private static Boolean flagParser(AbstractSyncParser var0, EmailContent.FollowupFlag var1) throws IOException {
      Boolean var2 = Boolean.valueOf((boolean)0);

      while(var0.nextTag(186) != 3) {
         switch(var0.tag) {
         case 187:
            int var3 = var0.getValueInt();
            if(var3 == 2) {
               var2 = Boolean.valueOf((boolean)1);
               EmailContent.FollowupFlag.FollowupFlagStatus var4 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_ACTIVE;
               var1.Status = var4;
            } else if(var3 == 1) {
               var2 = Boolean.valueOf((boolean)1);
               EmailContent.FollowupFlag.FollowupFlagStatus var5 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_COMPLETE;
               var1.Status = var5;
            } else {
               var2 = Boolean.valueOf((boolean)0);
               EmailContent.FollowupFlag.FollowupFlagStatus var6 = EmailContent.FollowupFlag.FollowupFlagStatus.FOLLOWUP_STATUS_CLEARED;
               var1.Status = var6;
            }
            break;
         case 189:
            String var7 = var0.getValue();
            var1.FlagType = var7;
            break;
         case 190:
            String var8 = var0.getValue();
            var0.text = var8;
            long var9 = getTimeInMillis(var0.text);
            var1.CompleteTime = var9;
            break;
         case 587:
            String var11 = var0.getValue();
            var0.text = var11;
            long var12 = getTimeInMillis(var0.text);
            var1.DateCompleted = var12;
            break;
         case 588:
            String var17 = var0.getValue();
            var0.text = var17;
            long var18 = getTimeInMillis(var0.text);
            var1.DueDate = var18;
            break;
         case 589:
            String var20 = var0.getValue();
            var0.text = var20;
            long var21 = getTimeInMillis(var0.text);
            var1.UTCDueDate = var21;
            break;
         case 603:
            byte var26;
            if(var0.getValueInt() == 1) {
               var26 = 1;
            } else {
               var26 = 0;
            }

            Boolean var27 = Boolean.valueOf((boolean)var26);
            var1.ReminderSet = var27;
            break;
         case 604:
            String var28 = var0.getValue();
            var0.text = var28;
            long var29 = getTimeInMillis(var0.text);
            var1.ReminderTime = var29;
            break;
         case 606:
            String var14 = var0.getValue();
            var0.text = var14;
            long var15 = getTimeInMillis(var0.text);
            var1.StartDate = var15;
            break;
         case 607:
            String var23 = var0.getValue();
            var0.text = var23;
            long var24 = getTimeInMillis(var0.text);
            var1.UTCStartDate = var24;
            break;
         case 610:
            String var31 = var0.getValue();
            var0.text = var31;
            long var32 = getTimeInMillis(var0.text);
            var1.OrdinalDate = var32;
            break;
         case 611:
            String var34 = var0.getValue();
            var1.SubOrdinalDate = var34;
            break;
         default:
            var0.skipTag();
         }
      }

      return var2;
   }

   public static String getMimeTypeFromFileName(String var0) {
      int var1 = var0.lastIndexOf(46);
      String var2 = null;
      if(var1 > 0) {
         int var3 = var0.length() - 1;
         if(var1 < var3) {
            int var4 = var1 + 1;
            var2 = var0.substring(var4).toLowerCase();
         }
      }

      String var5;
      if(var2 == null) {
         var5 = "application/octet-stream";
      } else {
         var5 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var2);
         if(var5 == null) {
            var5 = "application/" + var2;
         }
      }

      return var5;
   }

   public static long getTimeInMillis(String var0) {
      return getTimeInMillis(var0, "GMT");
   }

   public static long getTimeInMillis(String var0, String var1) {
      GregorianCalendar var2 = new GregorianCalendar();
      int var3 = Integer.parseInt(var0.substring(0, 4));
      int var4 = Integer.parseInt(var0.substring(5, 7)) - 1;
      int var5 = Integer.parseInt(var0.substring(8, 10));
      int var6 = Integer.parseInt(var0.substring(11, 13));
      int var7 = Integer.parseInt(var0.substring(14, 16));
      int var8 = Integer.parseInt(var0.substring(17, 19));
      var2.set(var3, var4, var5, var6, var7, var8);
      TimeZone var9 = TimeZone.getTimeZone(var1);
      var2.setTimeZone(var9);
      return var2.getTimeInMillis();
   }
}
