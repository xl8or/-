package com.htc.android.mail.database;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.pim.EASMoveItems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExchangeUtil {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "ExchangeUtil";


   public ExchangeUtil() {}

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

   public static String getDeleteboxSvrId(long param0, ContentResolver param2) {
      // $FF: Couldn't be decompiled
   }

   public static long getMailboxIdByMailboxSvrId(long param0, String param2, ContentResolver param3) {
      // $FF: Couldn't be decompiled
   }

   public static long getMailboxIdByMessageId(long param0, ContentResolver param2) {
      // $FF: Couldn't be decompiled
   }

   public static String getMailboxSvrIdByMailSvrId(Account param0, String param1, ContentResolver param2) {
      // $FF: Couldn't be decompiled
   }

   public static String getMailboxSvrIdByMessageId(long param0, boolean param2, ContentResolver param3) {
      // $FF: Couldn't be decompiled
   }

   public static String getMailboxSvrIdFromTrackingTBL(long param0, String param2, ContentResolver param3) {
      // $FF: Couldn't be decompiled
   }

   public static String getMailboxSvrIdbyMailboxId(long param0, ContentResolver param2) {
      // $FF: Couldn't be decompiled
   }

   public static EASSyncCommon.MailboxInfo getMailboxbyMailboxId(long param0, String param2, ContentResolver param3) {
      // $FF: Couldn't be decompiled
   }

   public static EASSyncCommon.MailboxInfo getMailboxbyMailboxSvrId(long param0, String param2, ContentResolver param3) {
      // $FF: Couldn't be decompiled
   }

   public static long getMessageIdByUid(long param0, String param2, ContentResolver param3) {
      // $FF: Couldn't be decompiled
   }

   public static List<String> getMsgIdList(EASMoveItems var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.moveItemList.iterator();

      while(var2.hasNext()) {
         String var3 = Long.toString(((EASMoveItems.EASMoveItem)var2.next()).messageId);
         var1.add(var3);
      }

      return var1;
   }

   public static List<String> getMsgIdList(ArrayList<String> var0) {
      ArrayList var1 = new ArrayList();
      int var2 = 0;

      for(int var3 = var0.size(); var2 < var3; ++var2) {
         Object var4 = var0.get(var2);
         var1.add(var4);
      }

      return var1;
   }

   public static ArrayList<MailMessage> getMsgServerId(ArrayList<MailMessage> param0, long param1, ContentResolver param3) {
      // $FF: Couldn't be decompiled
   }

   public static List<String> getMsgServerIdList(EASMoveItems var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.moveItemList.iterator();

      while(var2.hasNext()) {
         String var3 = ((EASMoveItems.EASMoveItem)var2.next()).srcMsgServerId;
         var1.add(var3);
      }

      return var1;
   }

   public static long getOutboxId(long param0, ContentResolver param2) {
      // $FF: Couldn't be decompiled
   }

   public static String getUidByMessageId(long var0, boolean var2, ContentResolver var3) {
      String var4;
      if(var0 > 0L && var3 != false) {
         String var5 = null;
         Cursor var6 = null;
         byte var7 = 1;
         boolean var19 = false;

         label133: {
            label142: {
               label143: {
                  label130: {
                     String var14;
                     try {
                        var19 = true;
                        String[] var8 = new String[var7];
                        var8[0] = "_uid";
                        StringBuilder var9 = new StringBuilder("_id=");
                        String var10 = Long.toString(var0);
                        DatabaseUtils.appendEscapedSQLString(var9, var10);
                        Uri var11;
                        if(var2) {
                           var11 = MailProvider.sSearchSvrMessagesURI;
                        } else {
                           var11 = MailProvider.sMessagesURI;
                        }

                        String var12 = var9.toString();
                        var6 = var3.query(var11, var8, var12, (String[])null, (String)null);
                        if(var6 == null) {
                           var19 = false;
                           break label143;
                        }

                        if(!var6.moveToFirst()) {
                           var19 = false;
                           break label143;
                        }

                        int var13 = var6.getColumnIndexOrThrow("_uid");
                        var14 = var6.getString(var13);
                        var19 = false;
                     } catch (Exception var20) {
                        var20.printStackTrace();
                        var19 = false;
                        break label130;
                     } finally {
                        if(var19) {
                           if(var6 != null && !var6.isClosed()) {
                              var6.close();
                           }

                        }
                     }

                     var5 = var14;
                     break label143;
                  }

                  var5 = null;
                  if(var6 == null || var6.isClosed()) {
                     break label133;
                  }
                  break label142;
               }

               if(var6 == null || var6.isClosed()) {
                  break label133;
               }
            }

            var6.close();
         }

         var4 = var5;
      } else {
         ll.e("ExchangeUtil", "getUidByMessageId error: parameter is null.");
         var4 = null;
      }

      return var4;
   }
}
