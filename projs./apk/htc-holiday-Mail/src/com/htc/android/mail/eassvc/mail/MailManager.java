package com.htc.android.mail.eassvc.mail;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.htc.android.mail.BaseStone;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Rfc2822;
import com.htc.android.mail.database.ExchangeUtil;
import com.htc.android.mail.eassvc.common.EASEMail;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.core.EASRequestController;
import com.htc.android.mail.eassvc.core.MailSyncSource;
import com.htc.android.mail.eassvc.mail.MailItem;
import com.htc.android.mail.eassvc.mail.MailTrackManager;
import com.htc.android.mail.eassvc.pim.EASMail;
import com.htc.android.mail.eassvc.pim.EASMoveItems;
import com.htc.android.mail.eassvc.pim.EASOptions;
import com.htc.android.mail.eassvc.util.EASEventBroadcaster;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.android.mail.mimemessage.MessagingException;
import com.htc.android.mail.mimemessage.MimeMessage;
import com.htc.android.mail.mimemessage.MimeUtility;
import com.htc.android.mail.mimemessage.Part;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class MailManager {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "MailManager";
   private ContentResolver cResolver = null;
   private Context mContext = null;
   private MailSyncSource mMailSyncSrc;
   private EASOptions mSyncOptions;
   Calendar m_cal;
   private long mnAccountId = 0L;


   public MailManager(Context var1, MailSyncSource var2) {
      Calendar var3 = Calendar.getInstance(TimeZone.getTimeZone("GMT+0000"));
      this.m_cal = var3;
      this.mContext = var1;
      ContentResolver var4 = var1.getContentResolver();
      this.cResolver = var4;
      this.mMailSyncSrc = var2;
      long var5 = var2.getAccountId();
      this.mnAccountId = var5;
   }

   private ArrayList<EASEMail.AttachInfo> addOrUpdateMail(long param1, String param3, EASEMail param4, Uri param5, EASSyncCommon.EASMailSyncResult param6) {
      // $FF: Couldn't be decompiled
   }

   private static boolean checkContain(ArrayList<Long> var0, long var1) {
      byte var3 = 0;
      if(var0 != null) {
         Boolean var4 = Boolean.valueOf((boolean)0);
         Iterator var5 = var0.iterator();

         while(var5.hasNext()) {
            if(((Long)var5.next()).longValue() == var1) {
               var4 = Boolean.valueOf((boolean)1);
               break;
            }
         }

         var3 = var4.booleanValue();
      }

      return (boolean)var3;
   }

   public static final String combine(List<String> var0, String var1, boolean var2) {
      StringBuilder var3 = new StringBuilder();
      String var4;
      if(var0.size() <= 0) {
         var4 = "";
      } else {
         if(var0.size() > 0 && (String)var0.get(0) != null) {
            if(var2) {
               String var5 = DatabaseUtils.sqlEscapeString((String)var0.get(0));
               var3.append(var5);
            } else {
               String var12 = (String)var0.get(0);
               var3.append(var12);
            }
         }

         int var7 = 1;

         while(true) {
            int var8 = var0.size();
            if(var7 >= var8) {
               var4 = var3.toString();
               break;
            }

            if((String)var0.get(var7) != null) {
               var3.append(var1);
               if(var2) {
                  String var10 = DatabaseUtils.sqlEscapeString((String)var0.get(var7));
                  var3.append(var10);
               } else {
                  String var14 = (String)var0.get(var7);
                  var3.append(var14);
               }
            }

            ++var7;
         }
      }

      return var4;
   }

   public static void delMoveSuccessButNotAddMail(long param0, String param2, ArrayList<String> param3, Context param4) {
      // $FF: Couldn't be decompiled
   }

   public static boolean easTrackMoveMailFailProced(long param0, EASMoveItems param2, Context param3) {
      // $FF: Couldn't be decompiled
   }

   private long formatDateReceived(String var1) {
      long var4;
      if(var1 == null) {
         long var2 = this.mnAccountId;
         EASLog.e("MailManager", var2, "formatDateReceived failed: parameter is null");
         var4 = 65535L;
      } else {
         long var6 = 65535L;

         label22: {
            long var19;
            try {
               String[] var8 = var1.split("T");
               String[] var9 = var8[0].split("-");
               String[] var10 = var8[1].split(":");
               String[] var11 = var10[2].split("\\.");
               Calendar var12 = this.m_cal;
               int var13 = Integer.valueOf(var9[0]).intValue();
               int var14 = Integer.valueOf(var9[1]).intValue() - 1;
               int var15 = Integer.valueOf(var9[2]).intValue();
               int var16 = Integer.valueOf(var10[0]).intValue();
               int var17 = Integer.valueOf(var10[1]).intValue();
               int var18 = Integer.valueOf(var11[0]).intValue();
               var12.set(var13, var14, var15, var16, var17, var18);
               var19 = this.m_cal.getTimeInMillis() / 1000L;
            } catch (Exception var27) {
               if(DEBUG) {
                  long var22 = this.mnAccountId;
                  StringBuilder var24 = (new StringBuilder()).append("formatDateReceived() Exception: ");
                  String var25 = var27.getMessage();
                  String var26 = var24.append(var25).toString();
                  EASLog.e("MailManager", var22, var26);
               }

               var27.printStackTrace();
               break label22;
            }

            var6 = var19 * 1000L;
         }

         var4 = var6;
      }

      return var4;
   }

   private int getFolderType(String param1) {
      // $FF: Couldn't be decompiled
   }

   public static EASMoveItems getMoveItemInfo(long param0, Context param2) {
      // $FF: Couldn't be decompiled
   }

   private int isFolderEnable(EASSyncCommon.EASCollection param1) {
      // $FF: Couldn't be decompiled
   }

   private String parseMailBodySize(EASEMail param1, EASOptions param2) {
      // $FF: Couldn't be decompiled
   }

   private void prcessReadSize(EASEMail var1, MailItem var2) {
      if(DEBUG) {
         long var3 = this.mnAccountId;
         EASLog.d("MailManager", var3, "processReadSize");
      }

      String var5;
      String var6;
      String var7;
      if(var1.isMimeData) {
         var5 = var1.MIMEData;
         var6 = var1.MIMESize;
         var7 = var1.MIMETruncated;
      } else {
         var5 = var1.Data;
         var6 = var1.EstimatedDataSize;
         var7 = var1.Truncated;
      }

      if(TextUtils.isEmpty(var5)) {
         var2.mReadsize = "0";
         var2.mText = "";
         if(var1.isMimeData) {
            var1.MIMEData = "";
         } else {
            var1.Data = "";
         }
      } else if(!TextUtils.isEmpty(var7) && !"0".equals(var7)) {
         if(this.mSyncOptions.mailTruncationSize != 9 && this.mSyncOptions.mailTruncationSize != 10) {
            EASOptions var8 = this.mSyncOptions;
            String var9 = this.parseMailBodySize(var1, var8);
            var2.mReadsize = var9;

            try {
               long var10 = Long.valueOf(var2.mReadsize).longValue();
               long var12 = Long.valueOf(var6).longValue();
               if(var10 > var12) {
                  if(DEBUG) {
                     long var14 = this.mnAccountId;
                     EASLog.v("MailManager", var14, "Modify estimate data size");
                  }

                  String var16 = Long.toString(3000L + var10);
                  var2.mReadTotalsize = var16;
               }
            } catch (Exception var17) {
               var17.printStackTrace();
            }
         } else {
            var2.mReadsize = var6;
            var2.mReadTotalsize = var6;
         }
      } else {
         if(TextUtils.isEmpty(var6) || "0".equals(var6)) {
            var6 = Integer.toString(var5.length());
         }

         var2.mReadsize = var6;
         var2.mReadTotalsize = var6;
      }
   }

   private String processDownloadTotalSize(EASEMail param1, MailItem param2) {
      // $FF: Couldn't be decompiled
   }

   private String processMIMEType(EASEMail var1) {
      String var2;
      if(TextUtils.isEmpty(var1.Type)) {
         var2 = "text/plain";
      } else {
         int var3;
         try {
            var3 = Integer.valueOf(var1.Type).intValue();
         } catch (NumberFormatException var7) {
            long var5 = this.mnAccountId;
            EASLog.e("MailManager", var5, "processMIMEType failed");
            var2 = "text/plain";
            return var2;
         }

         if(var3 == 1) {
            var2 = "text/plain";
         } else {
            var2 = "text/html";
         }
      }

      return var2;
   }

   private String processMessageSize(EASEMail var1, MailItem var2) {
      String var5;
      if(var1 != null && var2 != null) {
         long var6 = 65535L;
         long var10;
         if(var2.mReadTotalsize == null) {
            if(DEBUG) {
               long var8 = this.mnAccountId;
               EASLog.d("MailManager", var8, "! ReadTotalsize is null, use current mail body size");
            }

            label78: {
               try {
                  var10 = Long.valueOf(var2.mReadsize).longValue();
               } catch (NumberFormatException var37) {
                  if(DEBUG) {
                     long var22 = this.mnAccountId;
                     EASLog.e("MailManager", var22, "Convert pimMail.mReadsize from string to value failed.");
                  }
                  break label78;
               }

               var6 = var10;
            }

            if(var1.isMimeData) {
               if(var1.MIMETruncated != null) {
                  String var12 = var1.MIMETruncated;
                  if("1".equals(var12)) {
                     if(DEBUG) {
                        long var13 = this.mnAccountId;
                        EASLog.d("MailManager", var13, "mail body is truncated, set read totalsize is read size + 10");
                     }

                     var6 += 10L;
                  }
               }
            } else if(var1.Truncated != null && var1.Truncated.equals("1")) {
               if(DEBUG) {
                  long var24 = this.mnAccountId;
                  EASLog.d("MailManager", var24, "mail body is truncated, set read totalsize is read size + 10");
               }

               var6 += 10L;
            }
         } else {
            label87: {
               try {
                  var10 = Long.valueOf(var2.mReadTotalsize).longValue();
               } catch (NumberFormatException var38) {
                  if(DEBUG) {
                     long var27 = this.mnAccountId;
                     EASLog.e("MailManager", var27, "Convert pimMail.mReadTotalsize from string to value failed");
                  }
                  break label87;
               }

               var6 = var10;
            }
         }

         long var15 = 0L;
         int var17 = var1.mailAttachment.size();

         for(int var18 = 0; var18 < var17; ++var18) {
            if(((EASEMail.AttachInfo)var1.mailAttachment.get(var18)).AttachmentEstimatedDataSize == null) {
               if(DEBUG) {
                  long var19 = this.mnAccountId;
                  EASLog.d("MailManager", var19, "mailAttachment size is null, continue");
               }
            } else {
               long var31;
               label65: {
                  long var29;
                  try {
                     var29 = Long.valueOf(((EASEMail.AttachInfo)var1.mailAttachment.get(var18)).AttachmentEstimatedDataSize).longValue();
                  } catch (NumberFormatException var36) {
                     if(DEBUG) {
                        long var34 = this.mnAccountId;
                        EASLog.e("MailManager", var34, "easEMail.mailAttachment.get(i).AttachmentEstimatedDataSize formant exception");
                     }

                     var31 = 0L;
                     break label65;
                  }

                  var31 = var29;
               }

               var15 += var31;
            }
         }

         var5 = Long.toString(var6 + var15);
      } else {
         long var3 = this.mnAccountId;
         EASLog.e("MailManager", var3, "processMessageSize failed, param is null");
         var5 = "0";
      }

      return var5;
   }

   private void processMimeAttachment(MimeMessage var1, ArrayList<Part> var2, MailItem var3, EASEMail var4) throws MessagingException, Exception {
      if(var3 == null && var4 == null) {
         long var5 = this.mnAccountId;
         EASLog.d("MailManager", var5, "processMimeAttachment null");
      } else {
         if(DEBUG) {
            long var7 = this.mnAccountId;
            EASLog.d("MailManager", var7, "processMimeAttachment");
         }

         Iterator var9 = var2.iterator();

         while(var9.hasNext()) {
            Part var10 = (Part)var9.next();
            String var11 = MimeUtility.getHeaderParameter(MimeUtility.unfoldAndDecode(var10.getContentType()), "name");
            if(var11 == null) {
               var11 = MimeUtility.getHeaderParameter(MimeUtility.unfoldAndDecode(var10.getContentType()), "filename");
            }

            String var12;
            if(var10.getContentId() == null) {
               var12 = null;
            } else {
               var12 = var10.getContentId().trim().replace("<", "").replace(">", "");
            }

            if(var11 != null) {
               Iterator var13;
               if(var3 != null) {
                  var13 = var3.mailAttach.iterator();

                  while(true) {
                     if(var13.hasNext()) {
                        MailItem.pimAttachInfo var16 = (MailItem.pimAttachInfo)var13.next();
                        if(var16.mFilename == null || !var16.mFilename.equalsIgnoreCase(var11) || TextUtils.isEmpty(var12)) {
                           continue;
                        }

                        var16.mContentId = var12;
                        var16.mIsInLine = "1";
                     }

                     if(TextUtils.isEmpty(var12) && TextUtils.isEmpty(var3.mIncattachment)) {
                        String var15 = "1";
                        var3.mIncattachment = var15;
                     }
                     break;
                  }
               } else if(var4 != null) {
                  var13 = var4.mailAttachment.iterator();

                  while(var13.hasNext()) {
                     EASEMail.AttachInfo var14 = (EASEMail.AttachInfo)var13.next();
                     if(var14.AttachmentDisplayName != null && var14.AttachmentDisplayName.equals(var11) && !TextUtils.isEmpty(var12)) {
                        var14.AttachmentContentId = var12;
                        var14.AttachmentIsInLine = "1";
                        break;
                     }
                  }
               }
            }
         }

      }
   }

   private void processMimeBody(MimeMessage var1, ArrayList<Part> var2, MailItem var3, EASEMail var4) throws Exception {
      if(DEBUG) {
         long var5 = this.mnAccountId;
         EASLog.d("MailManager", var5, "processMimeBody");
      }

      StringBuilder var7 = new StringBuilder();
      StringBuilder var8 = new StringBuilder();
      Iterator var9 = var2.iterator();

      while(var9.hasNext()) {
         Part var10 = (Part)var9.next();
         if(var10.getMimeType().equalsIgnoreCase("text/html")) {
            if((long)var7.length() > 819200L) {
               if(DEBUG) {
                  long var11 = this.mnAccountId;
                  StringBuilder var13 = (new StringBuilder()).append("Due to Htmlbdoy is large. stop get the content.");
                  int var14 = var7.length();
                  String var15 = var13.append(var14).toString();
                  EASLog.d("MailManager", var11, var15);
               }
               break;
            }

            long var21 = (long)var7.length();
            long var23 = 819200L - var21;
            String var25 = MimeUtility.getTextFromPart(var10, var23);
            var7.append(var25);
            if(Mail.MAIL_DETAIL_DEBUG) {
               long var27 = this.mnAccountId;
               String var29 = "Html:" + var7;
               EASLog.d("MailManager", var27, var29);
            }
         } else if((var7 == null || var7.length() == 0) && var10.getMimeType().equalsIgnoreCase("text/plain")) {
            if((long)var8.length() > 819200L) {
               if(DEBUG) {
                  long var30 = this.mnAccountId;
                  StringBuilder var32 = (new StringBuilder()).append("Due to Textbody is large. stop get the content.");
                  int var33 = var8.length();
                  String var34 = var32.append(var33).toString();
                  EASLog.d("MailManager", var30, var34);
               }
               break;
            }

            long var35 = (long)var8.length();
            long var37 = 819200L - var35;
            String var39 = MimeUtility.getTextFromPart(var10, var37);
            var8.append(var39);
            if(Mail.MAIL_DETAIL_DEBUG) {
               long var41 = this.mnAccountId;
               String var43 = "Plain:" + var8;
               EASLog.d("MailManager", var41, var43);
            }
         } else if(DEBUG) {
            long var44 = this.mnAccountId;
            StringBuilder var46 = (new StringBuilder()).append("Unknown body:");
            String var47 = var10.getMimeType();
            StringBuilder var48 = var46.append(var47).append(", ");
            String var49 = var10.getContentType();
            String var50 = var48.append(var49).toString();
            EASLog.d("MailManager", var44, var50);
         }
      }

      String var16 = null;
      String var17;
      String var18;
      if(var7 != null && var7.length() > 0) {
         var17 = "text/html";
         var18 = "2";
         var16 = var7.toString();
      } else if(var8 != null && var8.length() > 0) {
         var18 = "1";
         var17 = "text/plain";
         var16 = var8.toString();
      } else {
         if(DEBUG) {
            long var51 = this.mnAccountId;
            EASLog.d("MailManager", var51, "body is empty");
         }

         var18 = "1";
         var17 = "text/plain";
      }

      if(var3 != null) {
         var3.mMimetype = var17;
         var3.mText = var16;
      } else if(var4 != null) {
         var4.Type = var18;
         var4.Data = var16;
      }
   }

   private void processMimeData(MimeMessage var1, MailItem var2, EASEMail var3) throws Exception {
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      MimeUtility.collectParts(var1, var4, var5);
      this.processMimeBody(var1, var4, var2, var3);
      this.processMimeAttachment(var1, var5, var2, var3);
   }

   private String[] processSubject(String var1) {
      String[] var2 = new String[2];
      if(var1 != null && var1.length() > 0) {
         int var3 = var1.length();
         byte var4 = 2;
         if(var3 < var4) {
            var2[0] = var1;
            var2[1] = "";
         } else {
            String var5 = "re";
            String var6 = "re: ";
            String var7 = "re:";
            String var8 = "fw";
            String var9 = "fwd: ";
            String var10 = "fwd:";
            String var11 = "fw: ";
            String var12 = "fw:";

            try {
               if(!var1.equalsIgnoreCase(var5) && !var1.equalsIgnoreCase(var6) && !var1.equalsIgnoreCase(var7) && !var1.equalsIgnoreCase(var8) && !var1.equalsIgnoreCase(var9) && !var1.equalsIgnoreCase(var10) && !var1.equalsIgnoreCase(var11) && !var1.equalsIgnoreCase(var12)) {
                  String var35 = var1.toLowerCase();
                  byte var37 = 0;
                  byte var38 = 2;
                  String var39 = var35.substring(var37, var38);
                  if(var39.equals(var5)) {
                     byte var43 = 0;
                     byte var44 = 4;
                     String var45 = var35.substring(var43, var44);
                     if(var45.contains(var6)) {
                        byte var47 = 4;
                        String var48 = var1.substring(var47);
                        var2[0] = var48;
                        byte var50 = 0;
                        byte var51 = 4;
                        String var52 = var1.substring(var50, var51);
                        var2[1] = var52;
                     } else if(var45.contains(var7)) {
                        byte var54 = 3;
                        String var55 = var1.substring(var54);
                        var2[0] = var55;
                        byte var57 = 0;
                        byte var58 = 4;
                        String var59 = var1.substring(var57, var58);
                        var2[1] = var59;
                     } else {
                        var2[0] = var1;
                        var2[1] = "";
                     }
                  } else if(var39.equals(var8)) {
                     byte var63 = 0;
                     byte var64 = 5;
                     String var65 = var35.substring(var63, var64);
                     if(var65.contains(var9)) {
                        byte var67 = 5;
                        String var68 = var1.substring(var67);
                        var2[0] = var68;
                        byte var70 = 0;
                        byte var71 = 5;
                        String var72 = var1.substring(var70, var71);
                        var2[1] = var72;
                     } else if(var65.contains(var10)) {
                        byte var74 = 4;
                        String var75 = var1.substring(var74);
                        var2[0] = var75;
                        byte var77 = 0;
                        byte var78 = 4;
                        String var79 = var1.substring(var77, var78);
                        var2[1] = var79;
                     } else if(var65.contains(var11)) {
                        byte var81 = 4;
                        String var82 = var1.substring(var81);
                        var2[0] = var82;
                        byte var84 = 0;
                        byte var85 = 4;
                        String var86 = var1.substring(var84, var85);
                        var2[1] = var86;
                     } else if(var65.contains(var12)) {
                        byte var88 = 3;
                        String var89 = var1.substring(var88);
                        var2[0] = var89;
                        byte var91 = 0;
                        byte var92 = 3;
                        String var93 = var1.substring(var91, var92);
                        var2[1] = var93;
                     } else {
                        var2[0] = var1;
                        var2[1] = "";
                     }
                  } else {
                     var2[0] = var1;
                     var2[1] = "";
                  }
               } else {
                  var2[0] = var1;
                  var2[1] = "";
               }
            } catch (IndexOutOfBoundsException var94) {
               long var30 = this.mnAccountId;
               StringBuilder var32 = (new StringBuilder()).append("processSubject(");
               String var34 = var32.append(var1).append("): IndexOutOfBoundsException").toString();
               EASLog.d("MailManager", var30, var34);
               var2[0] = var1;
               var2[1] = "";
            }
         }
      } else {
         var2[0] = "";
         var2[1] = "";
      }

      return var2;
   }

   public static void retrieveCollectionFromDB_static(ArrayList<EASSyncCommon.MailboxInfo> param0, Context param1) {
      // $FF: Couldn't be decompiled
   }

   public static EASSyncCommon.MailboxInfo retrieveMailbox(String var0, ArrayList<EASSyncCommon.MailboxInfo> var1) {
      EASSyncCommon.MailboxInfo var2;
      if(!TextUtils.isEmpty(var0) && var1 != null) {
         EASSyncCommon.MailboxInfo var3 = null;
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            EASSyncCommon.MailboxInfo var5 = (EASSyncCommon.MailboxInfo)var4.next();
            if(var5.id.equalsIgnoreCase(var0)) {
               var3 = var5;
               break;
            }
         }

         var2 = var3;
      } else {
         var2 = null;
      }

      return var2;
   }

   private void setCollectionSyncUp_Down(EASSyncCommon.EASCollection param1) {
      // $FF: Couldn't be decompiled
   }

   private void subStringFrom(String param1, MailItem param2) {
      // $FF: Couldn't be decompiled
   }

   public static void updateMailAction(long var0, String var2, String var3, int var4, Context var5) {
      if(DEBUG) {
         String var6 = "> updateMailAction: " + var2 + ", " + var3 + ", " + var4;
         EASLog.d("MailManager", var0, var6);
      }

      if(!TextUtils.isEmpty(var2) && !TextUtils.isEmpty(var3) && var4 != null) {
         ContentResolver var7 = var5.getContentResolver();
         long var8 = ExchangeUtil.getMailboxIdByMailboxSvrId(var0, var2, var7);
         ContentValues var10 = new ContentValues();
         String var11 = Integer.toString(var4);
         var10.put("_mailAct", var11);
         Object var27 = null;
         String[] var12 = new String[]{"_id"};
         Object[] var13 = new Object[3];
         Long var14 = Long.valueOf(var0);
         var13[0] = var14;
         var13[1] = var3;
         Long var15 = Long.valueOf(var8);
         var13[2] = var15;
         String var16 = String.format("_account=%d AND _uid=\'%s\' AND _mailboxId=%d", var13);
         ContentResolver var17 = var5.getContentResolver();
         Uri var18 = EASSyncCommon.EASMESSAGES_URI;
         Cursor var19 = var17.query(var18, var12, var16, (String[])null, (String)null);
         String var21;
         if(var19 != null && var19.moveToFirst()) {
            int var20 = var19.getColumnIndexOrThrow("_id");
            var21 = var19.getString(var20);
         } else {
            var21 = (String)var27;
         }

         if(var19 != null && !var19.isClosed()) {
            var19.close();
         }

         if(TextUtils.isEmpty(var21)) {
            EASLog.e("MailManager", var0, "updateMailAction error: Can not get id");
         } else {
            Builder var22 = EASSyncCommon.EASMESSAGES_URI.buildUpon();
            var22.appendEncodedPath(var21);
            ContentResolver var24 = var5.getContentResolver();
            Uri var25 = var22.build();
            var24.update(var25, var10, (String)null, (String[])null);
            if(DEBUG) {
               EASLog.d("MailManager", var0, "< updateMailAction");
            }
         }
      } else {
         EASLog.e("MailManager", var0, "updateMailAction error: parameter is null");
      }
   }

   private EASSyncCommon.UpdateMailBody updateMailBody_body(Uri var1, Cursor var2, EASEMail var3, long var4, long var6) throws Exception {
      if(DEBUG) {
         long var8 = this.mnAccountId;
         StringBuilder var10 = (new StringBuilder()).append("> updateMailBody_body: ");
         StringBuilder var13 = var10.append(var4).append(", ");
         String var16 = var13.append(var6).toString();
         EASLog.v("MailManager", var8, var16);
      }

      EASSyncCommon.UpdateMailBody var17 = new EASSyncCommon.UpdateMailBody();
      if(var3.isMimeData) {
         MailItem var18 = new MailItem();
         this.populatePIMItem(var6, var3, var18);
         String var24 = var18.mMimetype;
         var17.mimeType = var24;
         String var25 = var18.mNativeBodyType;
         var17.nativeType = var25;
         String var26 = var18.mText;
         var17.text = var26;
         ArrayList var27 = var18.mailAttach;
         var17.attachmentList = var27;
      } else {
         String var61 = this.processMIMEType(var3);
         var17.mimeType = var61;
         String var62 = var3.NaviteBodyType;
         var17.nativeType = var62;
         String var63 = var3.Data;
         var17.text = var63;
      }

      if(!var2.moveToFirst()) {
         if(DEBUG) {
            long var28 = this.mnAccountId;
            EASLog.v("MailManager", var28, "insert mail body. #1");
         }

         ContentValues var30 = new ContentValues();
         Long var31 = Long.valueOf(var4);
         String var33 = "_message";
         var30.put(var33, var31);
         String var35 = var17.mimeType;
         String var37 = "_mimetype";
         var30.put(var37, var35);
         String var39 = var17.nativeType;
         String var41 = "_nativeType";
         var30.put(var41, var39);
         String var43 = var17.text;
         String var45 = "_text";
         var30.put(var45, var43);
         Long var47 = Long.valueOf(this.mnAccountId);
         String var49 = "_account";
         var30.put(var49, var47);
         if(var3.Flag != null) {
            String var51 = var3.Flag;
            String var53 = "_flags";
            var30.put(var53, var51);
         }

         ContentResolver var55 = this.cResolver;
         var55.insert(var1, var30);
      } else {
         boolean var64 = false;
         ArrayList var65 = new ArrayList();

         do {
            String var67 = "_id";
            int var68 = var2.getColumnIndexOrThrow(var67);
            long var71 = var2.getLong(var68);
            String var74 = "_filename";
            int var75 = var2.getColumnIndexOrThrow(var74);
            String var78 = var2.getString(var75);
            if(TextUtils.isEmpty(var78)) {
               ContentValues var79 = new ContentValues();
               String var80 = var17.mimeType;
               String var82 = "_mimetype";
               var79.put(var82, var80);
               String var84 = var17.text;
               String var86 = "_text";
               var79.put(var86, var84);
               Builder var88 = var1.buildUpon();
               String var89 = Long.toString(var71);
               Builder var92 = var88.appendEncodedPath(var89);
               android.content.ContentProviderOperation.Builder var93 = ContentProviderOperation.newUpdate(var88.build());
               var93.withValues(var79);
               ContentProviderOperation var95 = var93.build();
               boolean var98 = var65.add(var95);
               var64 = true;
            } else if(var3.isMimeData && var17.attachmentList != null && !var17.attachmentList.isEmpty()) {
               Iterator var145 = var17.attachmentList.iterator();

               while(var145.hasNext()) {
                  MailItem.pimAttachInfo var146 = (MailItem.pimAttachInfo)var145.next();
                  if(!TextUtils.isEmpty(var146.mContentId)) {
                     String var147 = var146.mIsInLine;
                     if("1".equals(var147)) {
                        String var148 = var146.mFilename;
                        if(var78.equals(var148)) {
                           ContentValues var151 = new ContentValues();
                           String var152 = var146.mContentId;
                           String var154 = "_contentId";
                           var151.put(var154, var152);
                           String var156 = var146.mContentId;
                           String var158 = "_cid";
                           var151.put(var158, var156);
                           String var160 = var146.mIsInLine;
                           String var162 = "_inline";
                           var151.put(var162, var160);
                           Integer var164 = Integer.valueOf(Rfc2822.CONTENTTYPE_RELATED);
                           String var166 = "_contenttype";
                           var151.put(var166, var164);
                           Builder var168 = var1.buildUpon();
                           String var169 = Long.toString(var71);
                           Builder var172 = var168.appendEncodedPath(var169);
                           android.content.ContentProviderOperation.Builder var173 = ContentProviderOperation.newUpdate(var168.build());
                           var173.withValues(var151);
                           ContentProviderOperation var175 = var173.build();
                           boolean var178 = var65.add(var175);
                           break;
                        }
                     }
                  }
               }
            }
         } while(var2.moveToNext());

         if(!var64) {
            if(DEBUG) {
               long var99 = this.mnAccountId;
               EASLog.v("MailManager", var99, "insert mail body. #2");
            }

            ContentValues var101 = new ContentValues();
            Long var102 = Long.valueOf(var4);
            String var104 = "_message";
            var101.put(var104, var102);
            String var106 = var17.mimeType;
            String var108 = "_mimetype";
            var101.put(var108, var106);
            String var110 = var17.nativeType;
            String var112 = "_nativeType";
            var101.put(var112, var110);
            String var114 = var17.text;
            String var116 = "_text";
            var101.put(var116, var114);
            Long var118 = Long.valueOf(this.mnAccountId);
            String var120 = "_account";
            var101.put(var120, var118);
            if(var3.Flag != null) {
               String var122 = var3.Flag;
               String var124 = "_flags";
               var101.put(var124, var122);
            }

            android.content.ContentProviderOperation.Builder var126 = ContentProviderOperation.newInsert(var1);
            var126.withValues(var101);
            ContentProviderOperation var128 = var126.build();
            boolean var131 = var65.add(var128);
         }

         if(var65 != null && var65.size() > 0) {
            ContentResolver var132 = this.cResolver;
            String var133 = "mail";
            var132.applyBatch(var133, var65);
         }

         if(DEBUG) {
            long var136 = this.mnAccountId;
            StringBuilder var138 = (new StringBuilder()).append("< updateMailBody_body: ");
            StringBuilder var141 = var138.append(var4).append(", ");
            String var144 = var141.append(var6).toString();
            EASLog.v("MailManager", var136, var144);
         }
      }

      return var17;
   }

   public void addSendMeetingMailRecord(long var1, long var3) {
      if(var1 > 0L) {
         if(var3 > 0L) {
            if(DEBUG) {
               String var5 = "addSendMeetingMailRecord: " + var3;
               EASLog.d("MailManager", var1, var5);
            }

            Context var6 = this.mContext;
            MailTrackManager.addSendMeetingMailRecord(var1, var3, var6);
         }
      }
   }

   public void cleanExisted(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void cleanTrackStatus(ArrayList<String> var1) {
      if(var1 != null) {
         if(var1.size() > 0) {
            String var2 = null;
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
               String var4 = (String)var3.next();
               if(var2 == null) {
                  var2 = "\'" + var4 + "\'";
               } else {
                  var2 = var2 + ",\'" + var4 + "\'";
               }
            }

            ContentValues var5 = new ContentValues();
            var5.put("_synckey", "0");
            ContentResolver var6 = this.mContext.getContentResolver();
            Uri var7 = EASSyncCommon.EASMESSAGES_URI;
            String var8 = "_uid IN (" + var2 + ")";
            var6.update(var7, var5, var8, (String[])null);
         }
      }
   }

   public void combineMoveCommand(EASMoveItems param1) {
      // $FF: Couldn't be decompiled
   }

   public void deleteCancelledAttachment(EASRequestController.FetchAttachmentItem var1) {
      if(var1 != null) {
         if(!TextUtils.isEmpty(var1.savePath)) {
            try {
               long var2 = var1.accountId;
               EASLog.d("MailManager", var2, "delete cancelled attachment");
               String var4 = var1.savePath;
               if(!(new File(var4)).delete()) {
                  if(DEBUG) {
                     long var5 = var1.accountId;
                     EASLog.d("MailManager", var5, "deleteCancelledAttachment fail");
                  }
               }
            } catch (Exception var7) {
               var7.printStackTrace();
            }
         }
      }
   }

   public boolean deleteMail(long var1, boolean var3, boolean var4) {
      if(DEBUG) {
         long var5 = this.mnAccountId;
         String var7 = "> deleteMail: " + var1 + ", " + var3 + ", " + var4;
         EASLog.d("MailManager", var5, var7);
      }

      boolean var10;
      if(this.mnAccountId > 0L && var1 > 0L) {
         String var11;
         if(var4) {
            var11 = "1";
         } else {
            var11 = "0";
         }

         Builder var12 = EASSyncCommon.EASMESSAGES_URI.buildUpon();
         String var13 = Long.toString(var1);
         var12.appendEncodedPath(var13);
         var12.appendQueryParameter("delAttachment", var11);
         ContentResolver var16 = this.mContext.getContentResolver();
         Uri var17 = var12.build();
         int var18 = var16.delete(var17, (String)null, (String[])null);
         StringBuilder var19 = (new StringBuilder()).append("_accountId=");
         long var20 = this.mnAccountId;
         StringBuilder var22 = var19.append(var20).append(" AND _message=");
         String var23 = Long.toString(var1);
         String var24 = var22.append(var23).toString();
         ContentResolver var25 = this.mContext.getContentResolver();
         Uri var26 = EASSyncCommon.EASTRACKING_URI;
         var25.delete(var26, var24, (String[])null);
         boolean var28 = false;
         if(var18 > 0) {
            var28 = true;
         }

         if(DEBUG) {
            long var29 = this.mnAccountId;
            String var31 = "< deleteMail: " + var28;
            EASLog.d("MailManager", var29, var31);
         }

         var10 = var28;
      } else {
         long var8 = this.mnAccountId;
         EASLog.e("MailManager", var8, "deleteMail error: parameter is null");
         var10 = false;
      }

      return var10;
   }

   public int deleteSurplusMail() {
      ContentResolver var1 = this.mContext.getContentResolver();
      StringBuilder var2 = (new StringBuilder()).append("_del=1 AND _account=\'");
      long var3 = this.mnAccountId;
      String var5 = var2.append(var3).append("\'").toString();
      Uri var6 = EASSyncCommon.SummariesDeleteMailURI;
      int var7 = var1.delete(var6, var5, (String[])null);
      if(DEBUG) {
         long var8 = this.mnAccountId;
         String var10 = "deleteSurplusMail: " + var7;
         EASLog.d("MailManager", var8, var10);
      }

      return var7;
   }

   public boolean easTrackDelProced(long var1, ArrayList<String> var3) {
      boolean var4;
      if(var3 != null && var3.size() > 0) {
         boolean var5 = false;
         String var6 = combine(var3, ",", (boolean)1);
         Object[] var7 = new Object[2];
         Long var8 = Long.valueOf(var1);
         var7[0] = var8;
         var7[1] = var6;
         String var9 = String.format("_accountId=%d AND _uid in (%s)", var7);
         ContentResolver var10 = this.cResolver;
         Uri var11 = EASSyncCommon.EASTRACKING_URI;
         if(var10.delete(var11, var9, (String[])null) > 0) {
            var5 = true;
         }

         if(DEBUG) {
            String var12 = "easTrackDelProced: " + var5;
            EASLog.d("MailManager", var1, var12);
         }

         var4 = var5;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean easTrackMoveMailSuccessProced(ArrayList<EASSyncCommon.EASMoveItemsResp> var1) {
      if(DEBUG) {
         long var2 = this.mnAccountId;
         EASLog.d("MailManager", var2, "> easTrackMoveMailSuccessProced");
      }

      boolean var4 = false;
      boolean var7;
      if(var1 != null && var1.size() > 0) {
         ArrayList var8 = new ArrayList();
         ArrayList var9 = new ArrayList();
         ArrayList var10 = new ArrayList();
         ArrayList var11 = new ArrayList();

         try {
            Iterator var12 = var1.iterator();

            EASSyncCommon.EASMoveItemsResp var13;
            while(var12.hasNext()) {
               var13 = (EASSyncCommon.EASMoveItemsResp)var12.next();
               if(var13.status && var13.bInvalid_src_colid != 1) {
                  String var27 = Long.toString(var13.messageId);
                  boolean var30 = var10.add(var27);
                  boolean var33 = var8.add(var13);
               } else {
                  String var14 = Long.toString(var13.messageId);
                  boolean var17 = var9.add(var14);
               }
            }

            if(var8 != null && var8.size() > 0) {
               var12 = var8.iterator();

               while(var12.hasNext()) {
                  var13 = (EASSyncCommon.EASMoveItemsResp)var12.next();
                  if(TextUtils.isEmpty(var13.DstMsgId)) {
                     if(DEBUG) {
                        EASLog.d("MailManager", "move mail update message, dstMsgid is null");
                     }
                  } else {
                     StringBuilder var34 = new StringBuilder("_uid=");
                     String var35 = var13.SrcMsgId;
                     DatabaseUtils.appendEscapedSQLString(var34, var35);
                     StringBuilder var36 = var34.append(" AND _account=");
                     String var37 = Long.toString(this.mnAccountId);
                     DatabaseUtils.appendEscapedSQLString(var34, var37);
                     ContentValues var38 = new ContentValues();
                     String var39 = var13.DstMsgId;
                     String var41 = "_uid";
                     var38.put(var41, var39);
                     android.content.ContentProviderOperation.Builder var43 = ContentProviderOperation.newUpdate(MailProvider.sSummariesMoveMailURI);
                     android.content.ContentProviderOperation.Builder var46 = var43.withValues(var38);
                     String var47 = var34.toString();
                     Object var50 = null;
                     var43.withSelection(var47, (String[])var50);
                     ContentProviderOperation var52 = var43.build();
                     boolean var55 = var11.add(var52);
                     if(DEBUG) {
                        long var56 = this.mnAccountId;
                        StringBuilder var58 = (new StringBuilder()).append("move mail update message table: where: ");
                        String var59 = var34.toString();
                        StringBuilder var60 = var58.append(var59).append(" to: ");
                        String var61 = var13.DstMsgId;
                        String var62 = var60.append(var61).toString();
                        EASLog.v("MailManager", var56, var62);
                     }
                  }
               }
            }

            if(var11 != null && var11.size() > 0) {
               ContentResolver var63 = this.cResolver;
               String var64 = "mail";
               var63.applyBatch(var64, var11);
            }

            if(var10 != null && var10.size() > 0) {
               String var68 = ",";
               byte var69 = 1;
               String var70 = combine(var10, var68, (boolean)var69);
               Object[] var71 = new Object[3];
               Long var72 = Long.valueOf(this.mnAccountId);
               var71[0] = var72;
               var71[1] = var70;
               Integer var73 = Integer.valueOf(2);
               var71[2] = var73;
               String var74 = String.format("_accountId=%d AND _message in (%s) AND _move=1 AND _param=%d", var71);
               ContentValues var75 = new ContentValues();
               Integer var76 = Integer.valueOf(3);
               String var78 = "_param";
               var75.put(var78, var76);
               ContentResolver var80 = this.cResolver;
               Uri var81 = EASSyncCommon.EASTRACKING_URI;
               Object var86 = null;
               int var87 = var80.update(var81, var75, var74, (String[])var86);
               if(DEBUG) {
                  long var88 = this.mnAccountId;
                  StringBuilder var90 = (new StringBuilder()).append("easTrackMoveMailSuccessProced update: ").append(var87).append(", where: ");
                  String var92 = var90.append(var74).toString();
                  EASLog.v("MailManager", var88, var92);
               }

               var12 = var8.iterator();

               while(var12.hasNext()) {
                  var13 = (EASSyncCommon.EASMoveItemsResp)var12.next();
                  if(!TextUtils.isEmpty(var13.DstMsgId) && var13.bInvalid_src_colid != 1) {
                     long var98 = 65535L;
                     StringBuilder var100 = (new StringBuilder()).append("_account=\'");
                     long var101 = this.mnAccountId;
                     StringBuilder var103 = var100.append(var101).append("\' AND _uid=\'");
                     String var104 = var13.DstMsgId;
                     String var105 = var103.append(var104).append("\'").toString();
                     String[] var106 = new String[]{"_mailboxId"};
                     ContentResolver var107 = this.cResolver;
                     Uri var108 = EASSyncCommon.MESSAGES_URI;
                     Cursor var109 = var107.query(var108, var106, var105, (String[])null, (String)null);
                     if(var109 != null && var109.moveToFirst()) {
                        var98 = var109.getLong(0);
                     }

                     ContentResolver var110 = this.cResolver;
                     String var114 = ExchangeUtil.getMailboxSvrIdbyMailboxId(var98, var110);
                     StringBuilder var115 = (new StringBuilder()).append("_message=\'");
                     long var116 = var13.messageId;
                     String var118 = var115.append(var116).append("\'").toString();
                     ContentValues var119 = new ContentValues();
                     String var120 = var13.DstMsgId;
                     String var122 = "_uid";
                     var119.put(var122, var120);
                     if(!TextUtils.isEmpty(var114)) {
                        String var125 = "_collectionId";
                        var119.put(var125, var114);
                     } else if(DEBUG) {
                        long var140 = this.mnAccountId;
                        EASLog.d("MailManager", var140, "Can\'t retrieve mailbox info");
                     }

                     if(var109 != null && !var109.isClosed()) {
                        var109.close();
                     }

                     ContentResolver var127 = this.cResolver;
                     Uri var128 = EASSyncCommon.EASTRACKING_URI;
                     Object var133 = null;
                     int var134 = var127.update(var128, var119, var118, (String[])var133);
                     if(DEBUG) {
                        StringBuilder var135 = (new StringBuilder()).append("update easTracking table: ").append(var134).append(", from: ");
                        String var136 = var13.SrcMsgId;
                        StringBuilder var137 = var135.append(var136).append("=>");
                        String var138 = var13.DstMsgId;
                        String var139 = var137.append(var138).append(", collId: ").append(var114).toString();
                        EASLog.v("MailManager", var139);
                     }
                  } else if(DEBUG) {
                     long var93 = this.mnAccountId;
                     StringBuilder var95 = (new StringBuilder()).append("update tracking failed: ");
                     String var96 = var13.SrcMsgId;
                     String var97 = var95.append(var96).toString();
                     EASLog.d("MailManager", var93, var97);
                  }
               }
            }
         } catch (Exception var142) {
            long var19 = this.mnAccountId;
            String var21 = "MailManager";
            EASLog.e(var21, var19, var142);
            var4 = false;
         }

         if(DEBUG) {
            long var25 = this.mnAccountId;
            EASLog.v("MailManager", var25, "<<< easTrackMoveMailSuccessProced");
         }

         var7 = var4;
      } else {
         long var5 = this.mnAccountId;
         EASLog.e("MailManager", var5, "move mail success proced failed: parameter is null");
         var7 = false;
      }

      return var7;
   }

   public boolean easTrackUpdProced(long var1, ArrayList<EASSyncCommon.EASUpdInfo> var3) {
      boolean var4;
      if(var3 != null && var3.size() > 0) {
         boolean var5 = false;
         ArrayList var6 = new ArrayList();
         Iterator var7 = var3.iterator();

         while(var7.hasNext()) {
            String var8 = ((EASSyncCommon.EASUpdInfo)var7.next()).uid;
            var6.add(var8);
         }

         String var10 = combine(var6, ",", (boolean)1);
         Object[] var11 = new Object[2];
         Long var12 = Long.valueOf(var1);
         var11[0] = var12;
         var11[1] = var10;
         String var13 = String.format("_accountId=%d AND _uid in (%s)", var11);
         ContentResolver var14 = this.cResolver;
         Uri var15 = EASSyncCommon.EASTRACKING_URI;
         if(var14.delete(var15, var13, (String[])null) > 0) {
            var5 = true;
         }

         if(DEBUG) {
            String var16 = "easTrackUpdProced: " + var5;
            EASLog.d("MailManager", var1, var16);
         }

         var4 = var5;
      } else {
         var4 = false;
      }

      return var4;
   }

   public void firstFolderUpdate(ArrayList<EASSyncCommon.EASCollection> param1) {
      // $FF: Couldn't be decompiled
   }

   public long getAccountId() {
      return this.mnAccountId;
   }

   public String getAttachmentMimeType(String param1) {
      // $FF: Couldn't be decompiled
   }

   public long getAttachmentSize(String param1) {
      // $FF: Couldn't be decompiled
   }

   long[] getClientIds(String param1) {
      // $FF: Couldn't be decompiled
   }

   public String getDefaultMailboxId() {
      // $FF: Couldn't be decompiled
   }

   public ArrayList<String> getDelList(long var1, String var3) {
      Context var4 = this.mContext;
      return MailTrackManager.getDelList(var1, var3, var4);
   }

   public ArrayList<EASSyncCommon.EASUpdInfo> getUpdList(long var1, String var3) {
      Context var4 = this.mContext;
      return MailTrackManager.getUpdList(var1, var3, var4);
   }

   public int isSDsave() {
      // $FF: Couldn't be decompiled
   }

   public void loadMailboxInfo(ArrayList<EASSyncCommon.EASCollection> param1) {
      // $FF: Couldn't be decompiled
   }

   public void populatePIMItem(long var1, EASEMail var3, MailItem var4) {
      if(this.mnAccountId <= 0L) {
         long var5 = this.mnAccountId;
         EASLog.e("MailManager", var5, "populatePIMItem failed: account id is null");
      } else {
         String var7 = Long.toString(this.mnAccountId);
         var4.mAccount = var7;
         String var8 = var3.ServerID;
         var4.mUid = var8;
         String var9 = var3.From;
         this.subStringFrom(var9, var4);
         String var13 = var3.ReplyTo;
         var4.mReplyTo = var13;
         String var14 = var3.Subject;
         String[] var17 = this.processSubject(var14);
         String var18 = var17[0];
         var4.mSubject = var18;
         String var19 = var17[1];
         var4.mSubjType = var19;
         String var20 = var3.To;
         var4.mTo = var20;
         String var21 = var3.CC;
         var4.mCc = var21;
         String var22 = var3.ThreadTopic;
         var4.mThreadtopic = var22;
         String var23 = var3.DateReceived;
         String var26 = Long.toString(this.formatDateReceived(var23));
         var4.mDate = var26;
         String var27;
         if(var3.isMimeData) {
            if(TextUtils.isEmpty(var3.MIMESize)) {
               var27 = "0";
            } else {
               var27 = var3.MIMESize;
            }
         } else if(TextUtils.isEmpty(var3.EstimatedDataSize)) {
            var27 = "0";
         } else {
            var27 = var3.EstimatedDataSize;
         }

         var4.mReadTotalsize = var27;
         this.prcessReadSize(var3, var4);
         String var35 = this.processDownloadTotalSize(var3, var4);
         var4.mDownloadTotalSize = var35;
         String var39 = this.processMessageSize(var3, var4);
         var4.mMessageSize = var39;
         String var40 = var3.Read;
         var4.mRead = var40;
         if(TextUtils.isEmpty(var3.MessageClass)) {
            String var41 = "IPM.Note";
            var3.MessageClass = var41;
         }

         String var42 = var3.MessageClass;
         var4.mMessageClass = var42;
         if(var3.MessageClass.equals("IPM.Note")) {
            byte var43 = 0;
            var4.mMessageClassInt = var43;
         } else if(var3.MessageClass.equals("IPM.Schedule.Meeting.Request")) {
            byte var76 = 6;
            var4.mMessageClassInt = var76;
         } else {
            byte var77 = 0;
            var4.mMessageClassInt = var77;
         }

         String var44 = Long.toString(var1);
         var4.mMailbox_Id = var44;
         String var45 = var3.ClientName;
         var4.mMailbox = var45;
         String var46 = var3.Importance;
         var4.mImportance = var46;
         String var47 = "";
         var4.mReference = var47;
         Object var48 = null;
         String var49 = var3.ThreadTopic;
         if(TextUtils.isEmpty(var49)) {
            if(!TextUtils.isEmpty(var4.mSubject)) {
               String var50 = var4.mSubject;
            } else {
               String var78 = var3.Subject;
            }
         }

         String var51 = var4.mFromemail;
         String var52 = var3.To;
         String var53 = var3.CC;
         String var54 = var3.CC;
         String var55 = var4.mReference;
         String var56 = BaseStone.getGroupID(var51, var52, var49, var53, var54, var55, (long[])var48);
         if(var56 == null) {
            var56 = "";
         }

         var4.mGroup = var56;
         long var58 = (long)((Object[])var48)[0];
         var4.mPsuedoGroupId = var58;
         if(var3.NaviteBodyType == null) {
            String var60 = Integer.toString(1);
            var4.mNativeBodyType = var60;
         } else {
            String var79 = var3.NaviteBodyType;
            var4.mNativeBodyType = var79;
         }

         Object var61 = null;
         var4.mIncattachment = (String)var61;
         if(var3.mailAttachment != null && !var3.mailAttachment.isEmpty()) {
            Iterator var62 = var3.mailAttachment.iterator();

            while(var62.hasNext()) {
               EASEMail.AttachInfo var63 = (EASEMail.AttachInfo)var62.next();
               MailItem.pimAttachInfo var64 = new MailItem.pimAttachInfo();
               String var65 = var63.AttachmentDisplayName;
               var64.mFilename = var65;
               String var66 = var63.AttachmentFilePath;
               var64.mFilepath = var66;
               String var67 = var63.AttachmentEstimatedDataSize;
               var64.mFileSize = var67;
               String var68 = var63.AttachmentFileRef;
               var64.mFilereference = var68;
               String var69 = var63.AttachmentIsInLine;
               var64.mIsInLine = var69;
               String var70 = var63.AttachmentContentId;
               var64.mContentId = var70;
               ArrayList var71 = var4.mailAttach;
               var71.add(var64);
               if(!TextUtils.isEmpty(var63.AttachmentIsInLine)) {
                  if(var63.AttachmentIsInLine.equalsIgnoreCase("false")) {
                     var63.AttachmentIsInLine = "0";
                     String var74 = "0";
                     var64.mIsInLine = var74;
                  } else if(var63.AttachmentIsInLine.equalsIgnoreCase("true")) {
                     var63.AttachmentIsInLine = "1";
                     String var80 = "1";
                     var64.mIsInLine = var80;
                  }
               }

               if(!TextUtils.isEmpty(var3.NaviteBodyType) && var3.NaviteBodyType.equals("3")) {
                  var63.AttachmentIsInLine = "0";
                  String var75 = "0";
                  var64.mIsInLine = var75;
               }
            }
         }

         if(var3.isMimeData) {
            try {
               MimeMessage var81 = new MimeMessage;
               byte[] var82 = var3.MIMEData.getBytes("UTF-8");
               ByteArrayInputStream var83 = new ByteArrayInputStream(var82);
               var81.<init>(var83);
               Object var89 = null;
               this.processMimeData(var81, var4, (EASEMail)var89);
            } catch (Exception var132) {
               var132.printStackTrace();
            }
         } else {
            String var124 = this.processMIMEType(var3);
            var4.mMimetype = var124;
            String var125 = var3.Data;
            var4.mText = var125;
         }

         if(!TextUtils.isEmpty(var4.mText)) {
            String var90 = var4.mMimetype;
            if("text/html".equals(var90)) {
               if(DEBUG) {
                  long var91 = this.mnAccountId;
                  EASLog.d("MailManager", var91, "preview: html preview");
               }

               String var93 = MailCommon.s_convert(MailCommon.converHTMLtoPlainText(var4.mText));
               var4.mPreview = var93;
            } else {
               String var126 = var4.mMimetype;
               if("text/plain".equals(var126)) {
                  if(DEBUG) {
                     long var127 = this.mnAccountId;
                     EASLog.d("MailManager", var127, "preview: plain text preview");
                  }

                  String var129 = MailCommon.s_convert(var4.mText);
                  var4.mPreview = var129;
               }
            }
         }

         Iterator var94 = var4.mailAttach.iterator();

         while(var94.hasNext()) {
            MailItem.pimAttachInfo var95 = (MailItem.pimAttachInfo)var94.next();
            if(!TextUtils.isEmpty(var95.mIsInLine)) {
               String var96 = var95.mIsInLine;
               if(!"0".equals(var96)) {
                  if(TextUtils.isEmpty(var95.mIsInLine)) {
                     continue;
                  }

                  String var130 = var95.mIsInLine;
                  if(!"1".equalsIgnoreCase(var130) || var4.mMessageClass == null || !var4.mMessageClass.equals("IPM.Schedule.Meeting.Request")) {
                     continue;
                  }

                  String var131 = "1";
                  var4.mIncattachment = var131;
                  break;
               }
            }

            String var97 = "1";
            var4.mIncattachment = var97;
            break;
         }

         String var98 = var3.AllDayEvent;
         var4.mAllDayEvent = var98;
         String var99 = var3.StartTime;
         var4.mStartTime = var99;
         String var100 = var3.DTStamp;
         var4.mDtStamp = var100;
         String var101 = var3.EndTime;
         var4.mEndTime = var101;
         String var102 = var3.InstanceType;
         var4.mInstanceType = var102;
         String var103 = var3.Location;
         var4.mLocation = var103;
         String var104 = var3.Organizer;
         var4.mOrganizer = var104;
         String var105 = var3.RecurrenceId;
         var4.mRecurrenceId = var105;
         String var106 = var3.Reminder;
         var4.mReminder = var106;
         String var107 = var3.ResponseRequested;
         var4.mResponseRequested = var107;
         String var108 = var3.Sensitivity;
         var4.mSensitivity = var108;
         String var109 = var3.IntDBusyStatus;
         var4.mIntDBusyStatus = var109;
         String var110 = var3.TimeZone;
         var4.mTimezone = var110;
         String var111 = var3.GlobalObjId;
         var4.mGlobalObjId = var111;
         String var112 = var3.Category;
         var4.mCategory = var112;
         String var113 = var3.Recurrence_Type;
         var4.mRecurrence_Type = var113;
         String var114 = var3.Recurrence_Occurrences;
         var4.mRecurrence_Occurrences = var114;
         String var115 = var3.Recurrence_Interval;
         var4.mRecurrence_Interval = var115;
         String var116 = var3.Recurrence_DayOfWeek;
         var4.mRecurrence_DayOfWeek = var116;
         String var117 = var3.Recurrence_DayOfMonth;
         var4.mRecurrence_DayOfMonth = var117;
         String var118 = var3.Recurrence_WeekOfMonth;
         var4.mRecurrence_WeekOfMonth = var118;
         String var119 = var3.Recurrence_MonthOfYear;
         var4.mRecurrence_MonthOfYear = var119;
         String var120 = var3.Recurrence_Until;
         var4.mRecurrence_Until = var120;
         String var121 = var3.FlagStatus;
         var4.mMessages_Flags = var121;
      }
   }

   public void processAddFolder(ArrayList<EASSyncCommon.EASCollection> var1) {
      if(DEBUG) {
         long var2 = this.mnAccountId;
         EASLog.v("MailManager", var2, "> processAddFolder()");
      }

      if(var1 != null && var1.size() > 0) {
         if(this.mnAccountId <= 0L) {
            long var6 = this.mnAccountId;
            EASLog.e("MailManager", var6, "process add folder failed: account id is null");
         } else {
            ArrayList var8 = new ArrayList();
            ArrayList var9 = new ArrayList();
            Iterator var10 = var1.iterator();

            while(var10.hasNext()) {
               EASSyncCommon.EASCollection var11 = (EASSyncCommon.EASCollection)var10.next();
               String var12 = var11.Type;
               switch(this.getFolderType(var12)) {
               case -1:
                  EASLog.e("MailManager", "process add folder failed: folder type error");
                  break;
               case 0:
               case 1:
               default:
                  var9.add(var11);
                  break;
               case 2:
               case 3:
               case 4:
               case 5:
               case 6:
                  var8.add(var11);
               }
            }

            ArrayList var17;
            if(var9 != null && var9.size() > 0) {
               var17 = new ArrayList();
               var10 = var9.iterator();

               while(var10.hasNext()) {
                  EASSyncCommon.EASCollection var18 = (EASSyncCommon.EASCollection)var10.next();
                  if(TextUtils.isEmpty(var18.HierarchyName)) {
                     String var19 = var18.DisplayName;
                     var18.HierarchyName = var19;
                  }

                  ContentValues var20 = new ContentValues();
                  String var21 = var18.DisplayName;
                  var20.put("_undecodename", var21);
                  String var22 = var18.HierarchyName;
                  var20.put("_decodename", var22);
                  String var23 = var18.DisplayName;
                  var20.put("_shortname", var23);
                  Long var24 = Long.valueOf(this.mnAccountId);
                  var20.put("_account", var24);
                  String var25 = var18.ServerID;
                  var20.put("_serverid", var25);
                  String var26 = var18.ParentID;
                  var20.put("_parentid", var26);
                  String var27 = var18.Type;
                  var20.put("_type", var27);
                  String var28 = var18.SyncKey;
                  var20.put("_synckey", var28);
                  Integer var31 = Integer.valueOf(this.isFolderEnable(var18));
                  var20.put("_enablesync", var31);
                  Boolean var32 = Boolean.valueOf(var18.defaultSync);
                  var20.put("_default_sync", var32);
                  android.content.ContentProviderOperation.Builder var33 = ContentProviderOperation.newInsert(EASSyncCommon.EASMAILBOXS_URI);
                  var33.withValues(var20);
                  ContentProviderOperation var35 = var33.build();
                  var17.add(var35);
               }

               try {
                  if(var17.size() > 0) {
                     this.cResolver.applyBatch("mail", var17);
                  }
               } catch (Exception var83) {
                  var83.printStackTrace();
               }
            }

            if(var8 != null && var8.size() > 0) {
               int var38 = 0;
               var17 = new ArrayList();

               for(var10 = var8.iterator(); var10.hasNext(); ++var38) {
                  EASSyncCommon.EASCollection var39 = (EASSyncCommon.EASCollection)var10.next();
                  long var40 = this.mnAccountId;
                  StringBuilder var42 = (new StringBuilder()).append("process add folder type: ");
                  String var43 = var39.Type;
                  String var44 = var42.append(var43).toString();
                  EASLog.d("MailManager", var40, var44);
                  if(TextUtils.isEmpty(var39.HierarchyName)) {
                     String var45 = var39.DisplayName;
                     var39.HierarchyName = var45;
                  }

                  ContentValues var46 = new ContentValues();
                  String var47 = var39.DisplayName;
                  var46.put("_undecodename", var47);
                  String var48 = var39.HierarchyName;
                  var46.put("_decodename", var48);
                  String var49 = var39.DisplayName;
                  var46.put("_shortname", var49);
                  Long var50 = Long.valueOf(this.mnAccountId);
                  var46.put("_account", var50);
                  String var51 = var39.ServerID;
                  var46.put("_serverid", var51);
                  String var52 = var39.ParentID;
                  var46.put("_parentid", var52);
                  String var53 = var39.Type;
                  var46.put("_type", var53);
                  String var54 = var39.SyncKey;
                  var46.put("_synckey", var54);
                  Integer var57 = Integer.valueOf(this.isFolderEnable(var39));
                  var46.put("_enablesync", var57);
                  Boolean var58 = Boolean.valueOf(var39.defaultSync);
                  var46.put("_default_sync", var58);
                  String var59 = null;
                  String var60 = var39.Type;
                  switch(this.getFolderType(var60)) {
                  case 2:
                     Integer var74 = Integer.valueOf(Integer.MAX_VALUE);
                     var46.put("_defaultfolder", var74);
                     var59 = "_defaultfolderId";
                     break;
                  case 3:
                     Integer var75 = Integer.valueOf(2147483644);
                     var46.put("_defaultfolder", var75);
                     var59 = "_draftfolderId";
                     break;
                  case 4:
                     Integer var76 = Integer.valueOf(2147483646);
                     var46.put("_defaultfolder", var76);
                     var59 = "_trashfolderId";
                     break;
                  case 5:
                     Integer var77 = Integer.valueOf(2147483645);
                     var46.put("_defaultfolder", var77);
                     var59 = "_sentfolderId";
                     break;
                  case 6:
                     Integer var78 = Integer.valueOf(2147483643);
                     var46.put("_defaultfolder", var78);
                     var59 = "_outfolderId";
                  }

                  android.content.ContentProviderOperation.Builder var63 = ContentProviderOperation.newInsert(EASSyncCommon.EASMAILBOXS_URI);
                  var63.withValues(var46);
                  ContentProviderOperation var65 = var63.build();
                  var17.add(var65);
                  if(!TextUtils.isEmpty(var59)) {
                     Builder var67 = EASMail.EASACCOUNTS_URI.buildUpon();
                     String var68 = Long.toString(this.mnAccountId);
                     var67.appendEncodedPath(var68);
                     android.content.ContentProviderOperation.Builder var70 = ContentProviderOperation.newUpdate(var67.build());
                     var70.withValueBackReference(var59, var38);
                     ContentProviderOperation var72 = var70.build();
                     var17.add(var72);
                     ++var38;
                  }
               }

               try {
                  if(var17.size() > 0) {
                     this.cResolver.applyBatch("mail", var17);
                  }
               } catch (Exception var82) {
                  var82.printStackTrace();
               }
            }

            if(DEBUG) {
               long var80 = this.mnAccountId;
               EASLog.v("MailManager", var80, "< processAddFolder()");
            }
         }
      } else {
         long var4 = this.mnAccountId;
         EASLog.e("MailManager", var4, "process add folder: parameter is null");
      }
   }

   public void processDelFolder(ArrayList<EASSyncCommon.EASCollection> var1) {
      long var2 = this.mnAccountId;
      EASLog.d("MailManager", var2, "> process delete folder start");
      if(var1 != null && var1.size() > 0) {
         if(this.mnAccountId <= 0L) {
            long var6 = this.mnAccountId;
            EASLog.e("MailManager", var6, "process delete folder failed: account id is null");
         } else {
            String[] var8 = new String[]{"_id"};
            int var9 = var1.size();

            for(int var10 = 0; var10 < var9; ++var10) {
               long var13 = this.mnAccountId;
               String var17 = ((EASSyncCommon.EASCollection)var1.get(var10)).ServerID;
               ContentResolver var18 = this.mContext.getContentResolver();
               long var19 = ExchangeUtil.getMailboxIdByMailboxSvrId(var13, var17, var18);
               Object[] var21 = new Object[2];
               String var24 = ((EASSyncCommon.EASCollection)var1.get(var10)).ServerID;
               var21[0] = var24;
               Long var25 = Long.valueOf(this.mnAccountId);
               var21[1] = var25;
               String var26 = String.format("_serverid=\'%s\' AND _account=%d", var21);
               ContentResolver var27 = this.cResolver;
               Uri var28 = EASSyncCommon.EASMAILBOXS_URI;
               Cursor var29 = var27.query(var28, var8, var26, (String[])null, (String)null);
               if(var29 != null && var29.moveToFirst()) {
                  String var31 = "_id";
                  int var32 = var29.getColumnIndexOrThrow(var31);
                  long var35 = var29.getLong(var32);
                  Builder var37 = EASSyncCommon.EASMAILBOXS_URI.buildUpon();
                  String var38 = Long.toString(var35);
                  var37.appendEncodedPath(var38);
                  ContentResolver var40 = this.cResolver;
                  Uri var41 = var37.build();
                  var40.delete(var41, (String)null, (String[])null);
                  if(DEBUG) {
                     long var43 = this.mnAccountId;
                     StringBuilder var45 = (new StringBuilder()).append("delList[");
                     String var47 = var45.append(var10).append("] - mailbxs delete complete").toString();
                     EASLog.v("MailManager", var43, var47);
                  }
               }

               if(var29 != null && !var29.isClosed()) {
                  var29.close();
               }

               if(var19 > 0L) {
                  Object[] var48 = new Object[2];
                  Long var49 = Long.valueOf(var19);
                  var48[0] = var49;
                  Long var50 = Long.valueOf(this.mnAccountId);
                  var48[1] = var50;
                  String var51 = String.format("_mailboxId=%d AND _account=%d", var48);
                  ContentResolver var52 = this.cResolver;
                  Uri var53 = EASSyncCommon.EASMESSAGES_URI;
                  Cursor var55 = var52.query(var53, var8, var51, (String[])null, (String)null);
                  if(var55 != null && var55.moveToFirst()) {
                     ArrayList var56 = new ArrayList();

                     do {
                        int var57 = var55.getColumnIndexOrThrow("_id");
                        String var58 = var55.getString(var57);
                        if(!TextUtils.isEmpty(var58)) {
                           boolean var61 = var56.add(var58);
                        }
                     } while(var55.moveToNext());

                     if(var56 != null && var56.size() > 0) {
                        String var63 = ",";
                        byte var64 = 1;
                        String var65 = combine(var56, var63, (boolean)var64);
                        Object[] var66 = new Object[]{var65};
                        String var67 = String.format("_id in (%s)", var66);
                        ContentResolver var68 = this.cResolver;
                        Uri var69 = EASSyncCommon.EASMESSAGES_URI;
                        Object var73 = null;
                        var68.delete(var69, var67, (String[])var73);
                     }

                     if(DEBUG) {
                        long var75 = this.mnAccountId;
                        StringBuilder var77 = (new StringBuilder()).append("delList[");
                        String var79 = var77.append(var10).append("] - messages delete complete").toString();
                        EASLog.v("MailManager", var75, var79);
                     }
                  }

                  if(var55 != null && !var55.isClosed()) {
                     var55.close();
                  }
               }
            }

            long var80 = this.mnAccountId;
            EASLog.d("MailManager", var80, "< process delete folder finish");
         }
      } else {
         long var4 = this.mnAccountId;
         EASLog.e("MailManager", var4, "process delete folder failed: parameter is null");
      }
   }

   public ArrayList<EASEMail.AttachInfo> processMailAdd(long var1, String var3, EASEMail var4, Uri var5, EASSyncCommon.EASMailSyncResult var6) {
      if(DEBUG) {
         long var7 = this.mnAccountId;
         EASLog.d("MailManager", var7, "- processMailAdd()");
      }

      return this.addOrUpdateMail(var1, var3, var4, var5, var6);
   }

   public boolean processMailChangeList(long param1, String param3, ArrayList<EASEMail> param4, ArrayList<EASEMail> param5, ArrayList<EASEMail.AttachInfo> param6, Uri param7) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void processUpdFolder(ArrayList<EASSyncCommon.EASCollection> var1) {
      if(DEBUG) {
         long var2 = this.mnAccountId;
         EASLog.v("MailManager", var2, "> processUpdFolder()");
      }

      if(var1 != null && var1.size() > 0) {
         if(this.mnAccountId <= 0L) {
            long var6 = this.mnAccountId;
            EASLog.e("MailManager", var6, "process update folder failed: account id is null");
         } else {
            ArrayList var8 = new ArrayList();
            Cursor var9 = null;
            String[] var10 = new String[]{"_id"};
            int var11 = var1.size();

            for(int var12 = 0; var12 < var11; ++var12) {
               try {
                  String var17 = ((EASSyncCommon.EASCollection)var1.get(var12)).DisplayName;
                  String var20 = ((EASSyncCommon.EASCollection)var1.get(var12)).HierarchyName;
                  if(TextUtils.isEmpty(var20)) {
                     long var21 = this.mnAccountId;
                     StringBuilder var23 = (new StringBuilder()).append("Upd folder: can\'t retrieve hierarchyName: ");
                     String var25 = var23.append(var17).toString();
                     EASLog.e("MailManager", var21, var25);
                     var20 = var17;
                  }

                  ContentValues var26 = new ContentValues();
                  String var28 = "_undecodename";
                  var26.put(var28, var17);
                  var26.put("_decodename", var20);
                  String var31 = "_shortname";
                  var26.put(var31, var17);
                  String var33 = Long.toString(this.mnAccountId);
                  var26.put("_account", var33);
                  String var36 = ((EASSyncCommon.EASCollection)var1.get(var12)).ServerID;
                  var26.put("_serverid", var36);
                  String var39 = ((EASSyncCommon.EASCollection)var1.get(var12)).ParentID;
                  var26.put("_parentid", var39);
                  String var42 = ((EASSyncCommon.EASCollection)var1.get(var12)).Type;
                  var26.put("_type", var42);
                  String var45 = ((EASSyncCommon.EASCollection)var1.get(var12)).SyncKey;
                  var26.put("_synckey", var45);
                  EASSyncCommon.EASCollection var48 = (EASSyncCommon.EASCollection)var1.get(var12);
                  Integer var51 = Integer.valueOf(this.isFolderEnable(var48));
                  var26.put("_enablesync", var51);
                  Boolean var54 = Boolean.valueOf(((EASSyncCommon.EASCollection)var1.get(var12)).defaultSync);
                  var26.put("_default_sync", var54);
                  Object[] var55 = new Object[2];
                  String var58 = ((EASSyncCommon.EASCollection)var1.get(var12)).ServerID;
                  var55[0] = var58;
                  Long var59 = Long.valueOf(this.mnAccountId);
                  var55[1] = var59;
                  String var60 = String.format("_serverid=\'%s\' AND _account=%d", var55);
                  ContentResolver var61 = this.cResolver;
                  Uri var62 = EASSyncCommon.EASMAILBOXS_URI;
                  var9 = var61.query(var62, var10, var60, (String[])null, (String)null);
                  if(var9 != null && var9.moveToFirst()) {
                     int var63 = var9.getColumnIndexOrThrow("_id");
                     long var64 = var9.getLong(var63);
                     Builder var66 = EASSyncCommon.EASMAILBOXS_URI.buildUpon();
                     String var67 = Long.toString(var64);
                     var66.appendEncodedPath(var67);
                     ContentProviderOperation var69 = ContentProviderOperation.newUpdate(var66.build()).withValues(var26).build();
                     boolean var72 = var8.add(var69);
                  }
               } catch (Exception var85) {
                  var85.printStackTrace();
               } finally {
                  if(var9 != null && !var9.isClosed()) {
                     var9.close();
                  }

               }
            }

            if(var8 != null) {
               try {
                  if(var8.size() > 0) {
                     ContentResolver var75 = this.cResolver;
                     String var76 = "mail";
                     var75.applyBatch(var76, var8);
                  }
               } catch (Exception var84) {
                  var84.printStackTrace();
               }
            }

            if(DEBUG) {
               long var79 = this.mnAccountId;
               EASLog.v("MailManager", var79, "< processUpdFolder()");
            }
         }
      } else {
         long var4 = this.mnAccountId;
         EASLog.e("MailManager", var4, "process update folder failed: parameter is null");
      }
   }

   public void release() {}

   public boolean removeAllSyncMessages(String var1) {
      if(DEBUG) {
         long var2 = this.mnAccountId;
         String var4 = "> removeAllSyncMessages: " + var1;
         EASLog.d("MailManager", var2, var4);
      }

      boolean var7;
      if(TextUtils.isEmpty(var1)) {
         long var5 = this.mnAccountId;
         EASLog.e("MailManager", var5, "remove all sync messages fail: parameter is null");
         var7 = false;
      } else {
         boolean var8 = false;
         ArrayList var9 = new ArrayList();

         label34: {
            try {
               long var10 = this.mnAccountId;
               ContentResolver var12 = this.cResolver;
               long var13 = ExchangeUtil.getMailboxIdByMailboxSvrId(var10, var1, var12);
               if(var13 <= 0L) {
                  long var15 = this.mnAccountId;
                  EASLog.e("MailManager", var15, "remove all sync messages fail: can not retrieve mail box id");
                  var7 = var8;
                  return var7;
               }

               android.content.ContentProviderOperation.Builder var17 = ContentProviderOperation.newDelete(EASSyncCommon.MESSAGES_URI);
               StringBuilder var18 = (new StringBuilder()).append("_account = \'");
               long var19 = this.mnAccountId;
               String var21 = var18.append(var19).append("\' AND _mailboxId = \'").append(var13).append("\' AND _uid is not null").toString();
               var17.withSelection(var21, (String[])null);
               ContentProviderOperation var23 = var17.build();
               var9.add(var23);
               android.content.ContentProviderOperation.Builder var25 = ContentProviderOperation.newDelete(EASSyncCommon.EASTRACKING_URI);
               String var26 = "_collectionId = \'" + var1 + "\'";
               var25.withSelection(var26, (String[])null);
               ContentProviderOperation var28 = var25.build();
               var9.add(var28);
               if(var9 == null || var9.size() <= 0) {
                  break label34;
               }

               this.cResolver.applyBatch("mail", var9);
            } catch (Exception var37) {
               long var35 = this.mnAccountId;
               EASLog.e("MailManager", var35, var37);
               var8 = false;
               break label34;
            }

            var8 = true;
         }

         if(DEBUG) {
            long var31 = this.mnAccountId;
            String var33 = "< removeAllSyncMessages: " + var8;
            EASLog.d("MailManager", var31, var33);
         }

         var7 = var8;
      }

      return var7;
   }

   public void reset() {}

   public void retrieveCollectionFromDB(ArrayList<EASSyncCommon.EASCollection> param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean saveMailboxInfo(long param1, ArrayList<EASSyncCommon.EASCollection> param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public boolean setMoveMailFlag(EASMoveItems param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public void setSyncOptions(EASOptions var1) {
      this.mSyncOptions = var1;
   }

   public void updateAccountLastUpdateTime() {
      if(DEBUG) {
         long var1 = this.mnAccountId;
         EASLog.d("MailManager", var1, "- updateAccountLastUpdateTime()");
      }

      if(this.mnAccountId <= 0L) {
         long var3 = this.mnAccountId;
         EASLog.e("MailManager", var3, "update account last update time failed: Account id is null");
      } else {
         Context var5 = this.mContext;
         long var6 = this.mnAccountId;
         EASEventBroadcaster.broadcastAccountLastUpdateTime(var5, var6);
      }
   }

   public boolean updateAttachment(String var1, String var2, boolean var3) {
      if(DEBUG) {
         long var4 = this.mnAccountId;
         String var6 = "> updateAttachment: " + var1 + ", " + var3;
         EASLog.d("MailManager", var4, var6);
      }

      boolean var9;
      if(!TextUtils.isEmpty(var1) && !TextUtils.isEmpty(var2)) {
         boolean var10 = false;
         ContentValues var11 = new ContentValues();
         var11.put("_filepath", var2);
         Uri var12;
         if(!var3) {
            var12 = EASSyncCommon.EASPARTS_URI;
         } else {
            var12 = MailProvider.sSearchSvrPartsURI;
         }

         StringBuilder var13 = new StringBuilder("_account=");
         StringBuilder var14 = new StringBuilder();
         long var15 = this.mnAccountId;
         String var17 = var14.append(var15).append(" AND _filereference=").toString();
         var13.append(var17);
         DatabaseUtils.appendEscapedSQLString(var13, var1);
         ContentResolver var19 = this.mContext.getContentResolver();
         String var20 = var13.toString();
         if(var19.update(var12, var11, var20, (String[])null) > 0) {
            var10 = true;
         }

         if(DEBUG) {
            long var21 = this.mnAccountId;
            StringBuilder var23 = (new StringBuilder()).append("< updateAttachment: ");
            String var24 = Boolean.toString(var10);
            String var25 = var23.append(var24).toString();
            EASLog.d("MailManager", var21, var25);
         }

         var9 = var10;
      } else {
         long var7 = this.mnAccountId;
         EASLog.e("MailManager", var7, "Update attachment failed: parameter is null");
         var9 = false;
      }

      return var9;
   }

   public boolean updateDownloadedSize(String var1, boolean var2) {
      if(DEBUG) {
         long var3 = this.mnAccountId;
         StringBuilder var5 = (new StringBuilder()).append("> updateDownloadedSize: ");
         StringBuilder var7 = var5.append(var1).append(", ");
         String var9 = var7.append(var2).toString();
         EASLog.d("MailManager", var3, var9);
      }

      boolean var12;
      if(TextUtils.isEmpty(var1)) {
         long var10 = this.mnAccountId;
         EASLog.e("MailManager", var10, "update downloaded size failed: parameter is null");
         var12 = false;
      } else {
         ContentResolver var13 = this.mContext.getContentResolver();
         int var14 = -1;
         long var15 = 65535L;
         long var17 = 65535L;
         long var19 = 0L;
         boolean var21 = false;
         Uri var23;
         Uri var22;
         if(!var2) {
            var22 = EASSyncCommon.EASMESSAGES_URI;
            var23 = EASSyncCommon.EASPARTS_URI;
         } else {
            var22 = MailProvider.sSearchSvrMessagesURI;
            var23 = MailProvider.sSearchSvrPartsURI;
         }

         String[] var24 = new String[]{"_message", "_filesize"};
         Object[] var25 = new Object[2];
         Long var26 = Long.valueOf(this.mnAccountId);
         var25[0] = var26;
         var25[1] = var1;
         String var27 = String.format("_account=%d AND _filereference=\'%s\'", var25);
         Cursor var28 = var13.query(var23, var24, var27, (String[])null, (String)null);
         if(var28 != null && var28.moveToFirst()) {
            String var30 = "_message";
            int var31 = var28.getColumnIndexOrThrow(var30);
            var19 = var28.getLong(var31);
            String var35 = "_filesize";
            int var36 = var28.getColumnIndexOrThrow(var35);
            var14 = var28.getInt(var36);
         }

         if(var28 != null && !var28.isClosed()) {
            var28.close();
         }

         if(var19 <= 0L) {
            long var39 = this.mnAccountId;
            EASLog.e("MailManager", var39, "Cannot get the download mail messageId from parts table");
            var12 = false;
         } else {
            String[] var41 = new String[]{"_readsize", "_downloadtotalsize", "_messagesize"};
            StringBuilder var42 = (new StringBuilder()).append("_id = \'");
            String var45 = var42.append(var19).append("\'").toString();
            Cursor var48 = var13.query(var22, var41, var45, (String[])null, (String)null);
            if(var48 != null && var48.moveToFirst()) {
               String var50 = "_downloadtotalsize";
               int var51 = var48.getColumnIndexOrThrow(var50);
               var15 = (long)var48.getInt(var51);
               String var55 = "_messagesize";
               int var56 = var48.getColumnIndexOrThrow(var55);
               var17 = (long)var48.getInt(var56);
            }

            if(var48 != null && !var48.isClosed()) {
               var48.close();
            }

            if(var15 != 65535L && var17 != 65535L) {
               long var61 = (long)var14;
               long var63 = var15 + var61;
               if(var63 > var17) {
                  var63 = var17;
               }

               ContentValues var65 = new ContentValues();
               String var66 = Long.toString(var63);
               String var68 = "_downloadtotalsize";
               var65.put(var68, var66);
               Builder var70 = var22.buildUpon();
               String var71 = Long.toString(var19);
               Builder var74 = var70.appendEncodedPath(var71);
               Uri var75 = var70.build();
               Object var79 = null;
               Object var80 = null;
               if(var13.update(var75, var65, (String)var79, (String[])var80) > 0) {
                  var21 = true;
               }

               if(DEBUG) {
                  long var81 = this.mnAccountId;
                  StringBuilder var83 = (new StringBuilder()).append("< updateDownloadedSize: ");
                  String var84 = Boolean.toString(var21);
                  String var85 = var83.append(var84).toString();
                  EASLog.d("MailManager", var81, var85);
               }

               var12 = var21;
            } else {
               long var59 = this.mnAccountId;
               EASLog.e("MailManager", var59, "Can\'t get the _downloadtotalsize or messageSize column from easmessages table");
               var12 = false;
            }
         }
      }

      return var12;
   }

   public boolean updateMailBody(String param1, EASEMail param2, boolean param3) throws Exception {
      // $FF: Couldn't be decompiled
   }
}
