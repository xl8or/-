package com.digc.seven;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.combined.CombinedEmailManager;
import com.android.email.combined.MessageFacade;
import com.android.email.provider.EmailContent;
import com.digc.seven.SevenSyncProvider;
import com.seven.Z7.provider.Z7Content;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class SevenMessageManager {

   private static final int ATTACHMENT_CONTENTID = 10;
   private static final int ATTACHMENT_EMAIL_ID = 7;
   private static final int ATTACHMENT_EST_SIZE = 3;
   private static final int ATTACHMENT_FILE_NAME = 1;
   private static final int ATTACHMENT_ID = 0;
   private static final int ATTACHMENT_MIME_TYPE = 5;
   private static final int ATTACHMENT_OFFSET = 9;
   private static final int ATTACHMENT_POS = 8;
   public static final String[] ATTACHMENT_PROJECTION = new String[11];
   private static final int ATTACHMENT_SIZE = 2;
   private static final int ATTACHMENT_STATUS = 6;
   private static final int ATTACHMENT_URI = 4;
   private static MessageFacade FACADE = null;
   private static final int MESSAGE_FWD_ACTION = 3;
   private static final int MESSAGE_NEW_ACTION = 0;
   private static final int MESSAGE_REP_ACTION = 1;
   private static final String[] PROJECTION = SevenSyncProvider.RECEIVED_PROJECTION;
   private static final String SELECTION = "_id=?";
   private static final String TAG = "SevenMessageManager";


   static {
      ATTACHMENT_PROJECTION[0] = "_id";
      ATTACHMENT_PROJECTION[1] = "file_name";
      ATTACHMENT_PROJECTION[2] = "size";
      ATTACHMENT_PROJECTION[3] = "est_size";
      ATTACHMENT_PROJECTION[4] = "uri";
      ATTACHMENT_PROJECTION[5] = "mime_type";
      ATTACHMENT_PROJECTION[6] = "status";
      ATTACHMENT_PROJECTION[7] = "email_id";
      ATTACHMENT_PROJECTION[8] = "pos";
      ATTACHMENT_PROJECTION[9] = "offset";
      ATTACHMENT_PROJECTION[10] = "content_id";
   }

   public SevenMessageManager() {}

   public static void basicMoveToSent(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static int convertStdFolderIdToLocalFolderIdforSeven(Context param0, int param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   private static String[] convertorMailAddressForBasic(String var0) {
      String[] var1 = new String[2];
      if(var0 != null && !var0.equals("")) {
         int var2 = var0.indexOf("<");
         int var3 = var0.indexOf(">", var2);
         if(var2 > -1 && var3 > -1) {
            String var4 = var0.substring(0, var2);
            var1[0] = var4;
            String var5 = var1[0].replace("\"", "").trim();
            var1[0] = var5;
            int var6 = var2 + 1;
            String var7 = var0.substring(var6, var3).trim();
            var1[1] = var7;
         } else {
            var1[0] = var0;
            var1[1] = var0;
         }
      } else {
         var1[0] = "";
         var1[1] = "";
      }

      return var1;
   }

   private static String convertorMailAddressForSeven(String var0) {
      return convertorMailAddressForSeven(var0, (boolean)1);
   }

   private static String convertorMailAddressForSeven(String var0, boolean var1) {
      String var2 = var0;
      if(var0 != null && var0.indexOf("") > 0 && var1) {
         var2 = var0.replaceAll("", ", ");
      } else if(var0 != null && var0.indexOf("") > 0 && !var1) {
         int var3 = var0.indexOf("") + 1;
         var2 = var0.substring(0, var3);
      }

      return var2;
   }

   private static String convertorMailAddressesForBasic(String var0) {
      String var1 = var0;
      if(var0 != null) {
         StringBuffer var2 = new StringBuffer();
         int var3 = 0;
         StringTokenizer var4 = new StringTokenizer(var0, ",");
         int var5 = var4.countTokens();
         if(var5 > 0) {
            while(var4.hasMoreTokens()) {
               String var6 = convertorMailAddressForBasic(var4.nextToken().trim())[1];
               var2.append(var6);
               ++var3;
               if(var3 < var5) {
                  StringBuffer var8 = var2.append("");
               }
            }

            var1 = var2.toString();
         } else {
            var1 = convertorMailAddressForBasic(var0)[1];
         }
      }

      return var1;
   }

   private static String convertorMailFromAddressForBasic(String var0, boolean var1) {
      String[] var2 = convertorMailAddressForBasic(var0);
      String var3 = var2[0];
      String var4 = var2[1];
      String var5 = var0;
      if(!var3.equals(var4)) {
         var5 = var4 + "" + var3;
      }

      String var6;
      if(var1) {
         var6 = var5;
      } else {
         var6 = var3;
      }

      return var6;
   }

   public static void makeMessage(Intent param0, Context param1) {
      // $FF: Couldn't be decompiled
   }

   public static EmailContent.Attachment[] putAttachement(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static int saveUsingLocalFolderIdforSeven(Context var0, int var1, EmailContent.Message var2, EmailContent.Attachment[] var3, int var4) {
      ContentValues var5 = new ContentValues();
      Long var6 = Long.valueOf(var2.mSevenAccountKey);
      var5.put("account_id", var6);
      Uri var9 = SevenSyncProvider.getComposeUri(var0, var5);
      Cursor var12 = SevenSyncProvider.getComposeCursor(var0, var9);
      if(var3.length > 0) {
         String var13 = var9.getLastPathSegment();
         HashMap var14 = null;
         String var15 = "";
         byte var16 = 0;
         String var17 = "";
         byte var18 = -1;
         if(var2.mSevenMessageKey != 0L) {
            byte var20 = 3;
            if(var4 == var20) {
               int var21 = (int)var2.mSevenMessageKey;
               var14 = SevenSyncProvider.getAttacheUri(var0, var21);
            }
         }

         EmailContent.Attachment[] var24 = var3;
         int var25 = var3.length;
         int var26 = var18;
         int var27 = var16;
         int var28 = 0;
         int var29 = 0;

         for(String var30 = var15; var28 < var25; var30 = var15) {
            EmailContent.Attachment var31 = var24[var28];
            if(var31.isSaved()) {
               ContentValues var32 = new ContentValues();
               String var34 = "email_id";
               var32.put(var34, var13);
               String var36 = var31.mFileName;
               String var38 = "file_name";
               var32.put(var38, var36);
               if(var14 != null) {
                  var30 = var31.mLocation;
                  if(var30 != null) {
                     var27 = Integer.parseInt(var30);
                     Integer var40 = Integer.valueOf(var27 - 2);
                     Hashtable var41 = (Hashtable)var14.get(var40);
                     String var42 = "pos";
                     var26 = ((Integer)var41.get(var42)).intValue();
                  } else {
                     int var88 = var14.size();
                     byte var89 = 1;
                     if(var88 == var89) {
                        Integer var90 = Integer.valueOf(0);
                        Hashtable var91 = (Hashtable)var14.get(var90);
                        String var92 = "pos";
                        var26 = ((Integer)var91.get(var92)).intValue();
                     }
                  }
               }

               label184: {
                  byte var44 = 3;
                  if(var4 == var44 && var30 != null) {
                     int var45 = var27 - 2;
                     if(var45 == var26) {
                        int var47 = var3.length;
                        byte var48 = 1;
                        if(var47 > var48) {
                           Integer var49 = Integer.valueOf(var26);
                           Hashtable var52 = (Hashtable)var14.get(var49);
                           String var53 = "uri";
                           String var54 = (String)var52.get(var53);
                           var31.mContentUri = var54;
                           if(var31.mContentUri != null) {
                              String var55 = var31.mContentUri;
                              String var56 = "";
                              if(var55.equals(var56)) {
                                 Object var57 = null;
                                 var31.mContentUri = (String)var57;
                              }
                           }

                           if(var31.mContentId != null) {
                              Object var58 = null;
                              var31.mContentUri = (String)var58;
                           }

                           String var59 = var31.mContentUri;
                           String var61 = "uri";
                           var32.put(var61, var59);
                           break label184;
                        }
                     }
                  }

                  byte var94 = 3;
                  if(var4 == var94 && var30 == null && var17.equals("2")) {
                     int var95 = var3.length;
                     byte var96 = 1;
                     if(var95 == var96) {
                        Integer var97 = Integer.valueOf(var26);
                        Hashtable var100 = (Hashtable)var14.get(var97);
                        String var101 = "file_name";
                        String var102 = (String)var100.get(var101);
                        if(var31.mFileName != null) {
                           String var103 = var31.mFileName;
                           if(var103.equals(var102)) {
                              Integer var105 = Integer.valueOf(var26);
                              Hashtable var108 = (Hashtable)var14.get(var105);
                              String var109 = "uri";
                              String var110 = (String)var108.get(var109);
                              var31.mContentUri = var110;
                              if(var31.mContentUri != null) {
                                 String var111 = var31.mContentUri;
                                 String var112 = "";
                                 if(var111.equals(var112)) {
                                    Object var113 = null;
                                    var31.mContentUri = (String)var113;
                                 }
                              }

                              if(var31.mContentId != null) {
                                 Object var114 = null;
                                 var31.mContentUri = (String)var114;
                              }

                              String var115 = var31.mContentUri;
                              String var117 = "uri";
                              var32.put(var117, var115);
                              break label184;
                           }
                        }

                        if(var31.mContentId != null) {
                           Object var119 = null;
                           var31.mContentUri = (String)var119;
                        }

                        String var120 = var31.mContentUri;
                        String var122 = "uri";
                        var32.put(var122, var120);
                        break label184;
                     }
                  }

                  byte var125 = 3;
                  if(var4 == var125 && var30 == null && var3.length > 0) {
                     String var126 = var31.mContentUri;
                     String var128 = "uri";
                     var32.put(var128, var126);
                  } else {
                     String var130 = var31.mContentUri;
                     String var132 = "uri";
                     var32.put(var132, var130);
                  }
               }

               if(var31.mContentId != null) {
                  Object var63 = null;
                  var31.mMimeType = (String)var63;
                  String var64 = var31.mMimeType;
                  String var66 = "mime_type";
                  var32.put(var66, var64);
                  String var68 = var31.mContentId;
                  String var70 = "content_id";
                  var32.put(var70, var68);
               } else {
                  String var134 = var31.mMimeType;
                  String var136 = "mime_type";
                  var32.put(var136, var134);
               }

               label188: {
                  String var74;
                  long var75;
                  if(var31.mMimeType != null) {
                     String var72 = var31.mMimeType;
                     String var73 = "x-vcard";
                     if(var72.indexOf(var73) > 0 && var31.mSize <= 0L) {
                        var74 = "size";
                        if(var31.mSize <= 0L) {
                           var75 = 1L;
                        } else {
                           var75 = var31.mSize;
                        }

                        Long var77 = Long.valueOf(var75);
                        var32.put(var74, var77);
                        break label188;
                     }
                  }

                  String var138 = "size";
                  long var139;
                  if(var31.mSize < 0L) {
                     var139 = 0L;
                  } else {
                     var139 = var31.mSize;
                  }

                  Long var141 = Long.valueOf(var139);
                  var32.put(var138, var141);
                  var74 = "est_size";
                  if(var31.mSize < 0L) {
                     var75 = 0L;
                  } else {
                     var75 = var31.mSize;
                  }

                  Long var145 = Long.valueOf(var75);
                  var32.put(var74, var145);
               }

               ContentResolver var81 = var0.getContentResolver();
               Uri var82 = Z7Content.Attachment.CONTENT_URI;
               Uri var86 = var81.insert(var82, var32);
               var15 = var30;
            } else {
               var15 = var30;
            }

            int var87 = var29 + 1;
            ++var28;
            var29 = var87;
         }

         ContentValues var149 = new ContentValues();
         String var150 = "has_attachments";
         int var151;
         if(var3.length > 0) {
            var151 = var3.length;
         } else {
            var151 = 0;
         }

         Integer var152 = Integer.valueOf(var151);
         var149.put(var150, var152);
         ContentResolver var153 = var0.getContentResolver();
         Object var156 = null;
         Object var157 = null;
         var153.update(var9, var149, (String)var156, (String[])var157);
      }

      int var252;
      if(var1 >= 0 && var12 != null) {
         int var159 = (int)var2.mSevenAccountKey;
         if(var159 == 0) {
            throw new RuntimeException("accountId must be set");
         }

         ContentValues var160 = new ContentValues();
         Long var161 = Long.valueOf(System.currentTimeMillis());
         var160.put("modified", var161);
         Long var162 = Long.valueOf(System.currentTimeMillis());
         var160.put("delivery_time", var162);
         Integer var163 = Integer.valueOf(var159);
         var160.put("account_id", var163);
         Integer var164 = Integer.valueOf(var1);
         String var166 = "folder_id";
         var160.put(var166, var164);
         String var168 = convertorMailAddressForSeven(var2.mFrom, (boolean)0);
         String var170 = "_from";
         var160.put(var170, var168);
         if(var2.mTextReply != null) {
            if(var2.mIntroText == null) {
               String var172 = "";
               var2.mIntroText = var172;
            }

            StringBuilder var173 = new StringBuilder();
            String var174 = var2.mText;
            StringBuilder var175 = var173.append(var174).append("\n\n");
            String var176 = var2.mIntroText;
            StringBuilder var177 = var175.append(var176).append("\n\n");
            String var178 = var2.mTextReply;
            String var179 = var177.append(var178).toString();
            String var181 = "body";
            var160.put(var181, var179);
         } else {
            String var253 = var2.mText;
            String var255 = "body";
            var160.put(var255, var253);
         }

         String var184 = "message_content_type";
         String var185 = "text/html";
         var160.put(var184, var185);
         if(var2.mHtmlReply != null) {
            if(var2.mIntroText == null) {
               String var186 = "";
               var2.mIntroText = var186;
            }

            StringBuilder var187 = new StringBuilder();
            String var188 = var2.mHtml;
            StringBuilder var189 = var187.append(var188).append("<br><br>");
            String var190 = var2.mIntroText;
            StringBuilder var191 = var189.append(var190).append("<br><br>");
            String var192 = var2.mHtmlReply;
            String var193 = var191.append(var192).toString();
            String var195 = "html_body";
            var160.put(var195, var193);
         } else {
            String var257 = var2.mHtml;
            String var259 = "html_body";
            var160.put(var259, var257);
         }

         String var197 = convertorMailAddressForSeven(var2.mTo);
         String var199 = "_to";
         var160.put(var199, var197);
         String var201 = convertorMailAddressForSeven(var2.mCc);
         String var203 = "cc";
         var160.put(var203, var201);
         String var205 = convertorMailAddressForSeven(var2.mBcc);
         String var207 = "bcc";
         var160.put(var207, var205);
         String var209 = var2.mSubject;
         String var211 = "subject";
         var160.put(var211, var209);
         Integer var213 = Integer.valueOf(5);
         String var215 = "importance";
         var160.put(var215, var213);
         Integer var217 = Integer.valueOf(var4);
         String var219 = "org_action";
         var160.put(var219, var217);
         Integer var221 = Integer.valueOf((int)var2.mSevenMessageKey);
         String var223 = "org_id";
         var160.put(var223, var221);
         if(var3.length <= 0) {
            Integer var225 = Integer.valueOf(0);
            String var227 = "has_attachments";
            var160.put(var227, var225);
         }

         Integer var229 = Integer.valueOf(0);
         String var231 = "is_unread";
         var160.put(var231, var229);
         Integer var233 = Integer.valueOf(0);
         String var235 = "followup_status";
         var160.put(var235, var233);
         Integer var237 = Integer.valueOf(0);
         String var239 = "is_receipt_pending";
         var160.put(var239, var237);
         Integer var241 = Integer.valueOf(0);
         String var243 = "is_omit_receipt";
         var160.put(var243, var241);
         ContentResolver var245 = var0.getContentResolver();
         Object var248 = null;
         Object var249 = null;
         if(var245.update(var9, var160, (String)var248, (String[])var249) != 0 && var12.moveToFirst()) {
            byte var251 = 0;
            var252 = var12.getInt(var251);
            return var252;
         }
      }

      var252 = '\uffff';
      return var252;
   }

   public static int saveforSeven(Context var0, EmailContent.Message var1, EmailContent.Attachment[] var2, int var3) {
      if(var3 == 2) {
         var3 = 3;
      }

      int var4 = -1;
      if(var1 != null) {
         int var5 = (int)var1.mSevenAccountKey;
         if(var5 == 0) {
            throw new RuntimeException("accountId must be set");
         }

         int var6 = convertStdFolderIdToLocalFolderIdforSeven(var0, var5, 2);
         var4 = saveUsingLocalFolderIdforSeven(var0, var6, var1, var2, var3);
         if(var4 != -1) {
            long var7 = (long)var5;
            long var9 = var1.mId;
            long var11 = (long)var4;
            SevenSyncProvider.setBasicMessageCBupdate(var0, var7, var9, var11);
         }
      }

      return var4;
   }

   public static void syncFolder(Intent var0, Context var1, String var2) {
      FACADE = CombinedEmailManager.getInstance(var1).getMessageFacade();
      int var3 = var0.getIntExtra("account_id", -1);
      String var4 = " remoteAccountId=" + var3;
      int var5 = Log.d("SevenMessageManager", var4);
      if(var3 != -1) {
         int var6 = var0.getIntExtra("folder_id", -1);
         String var7 = var0.getStringExtra("folder_name");
         int var8 = var0.getIntExtra("parent_folder_id", -1);
         if(var8 != -1) {
            String var9 = SevenSyncProvider.getParentsFolderName(var1, var8);
            if(var9 != null) {
               var7 = var9 + "/" + var7;
            }
         }

         if(var2 != null) {
            if(var2.equals("com.seven.Z7.FOLDER_ADDED")) {
               FACADE.addFolder(var3, var6, var7);
            } else if(var2.equals("com.seven.Z7.FOLDER_REMOVED")) {
               FACADE.removeFolder(var3, var6);
            }
         }
      }
   }

   public static void syncMessageTimeLimit(Intent var0, Context var1) {
      FACADE = CombinedEmailManager.getInstance(var1).getMessageFacade();
      int var2 = var0.getIntExtra("account_id", -1);
      long var3 = var0.getLongExtra("delete_timestamp", 65535L);
      FACADE.removeMessageForDate(var2, var3);
   }

   public static void syncUpdateMessage(Intent var0, Context var1, String var2) {
      FACADE = CombinedEmailManager.getInstance(var1).getMessageFacade();
      int var3 = var0.getIntExtra("message_id", -1);
      boolean var4 = var0.getBooleanExtra("is_read", (boolean)0);
      int var5 = var0.getIntExtra("folder_id", -1);
      int var6 = var0.getIntExtra("src_folder_id", -1);
      int var7 = var0.getIntExtra("folder_id", -1);
      if(var2 != null) {
         if(var2.equals("com.seven.Z7.EMAIL_UPDATED")) {
            FACADE.setMessageRead(var3, var4);
            if(var7 != -1) {
               FACADE.moveMessage(var3, var6, var7);
            }
         } else if(var2.equals("com.seven.Z7.EMAIL_REMOVED")) {
            FACADE.removeMessage(var3, var5);
         }
      }
   }

   class AttachBean {

      int _id;
      int email_id;
      long est_size;
      String file_name;
      String mime_type;
      int offset;
      int pos;
      long size;
      int status;
      String uris;


      AttachBean() {}

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("_id = ");
         int var2 = this._id;
         String var3 = var1.append(var2).toString();
         int var4 = Log.d("SevenMessageManager", var3);
         StringBuilder var5 = (new StringBuilder()).append("file_name = ");
         String var6 = this.file_name;
         String var7 = var5.append(var6).toString();
         int var8 = Log.d("SevenMessageManager", var7);
         StringBuilder var9 = (new StringBuilder()).append("size = ");
         long var10 = this.size;
         String var12 = var9.append(var10).toString();
         int var13 = Log.d("SevenMessageManager", var12);
         StringBuilder var14 = (new StringBuilder()).append("est_size = ");
         long var15 = this.est_size;
         String var17 = var14.append(var15).toString();
         int var18 = Log.d("SevenMessageManager", var17);
         StringBuilder var19 = (new StringBuilder()).append("uris = ");
         String var20 = this.uris;
         String var21 = var19.append(var20).toString();
         int var22 = Log.d("SevenMessageManager", var21);
         StringBuilder var23 = (new StringBuilder()).append("mime_type = ");
         String var24 = this.mime_type;
         String var25 = var23.append(var24).toString();
         int var26 = Log.d("SevenMessageManager", var25);
         StringBuilder var27 = (new StringBuilder()).append("status = ");
         int var28 = this.status;
         String var29 = var27.append(var28).toString();
         int var30 = Log.d("SevenMessageManager", var29);
         StringBuilder var31 = (new StringBuilder()).append("email_id = ");
         int var32 = this.email_id;
         String var33 = var31.append(var32).toString();
         int var34 = Log.d("SevenMessageManager", var33);
         StringBuilder var35 = (new StringBuilder()).append("pos = ");
         int var36 = this.pos;
         String var37 = var35.append(var36).toString();
         int var38 = Log.d("SevenMessageManager", var37);
         StringBuilder var39 = (new StringBuilder()).append("offset = ");
         int var40 = this.offset;
         String var41 = var39.append(var40).toString();
         int var42 = Log.d("SevenMessageManager", var41);
         return "";
      }
   }
}
