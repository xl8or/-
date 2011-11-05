package com.broadcom.map.email;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.NetworkInfo.DetailedState;
import android.util.Log;
import com.android.email.provider.EmailContent;

public class MAPEmailUtils {

   public static final boolean DEBUG = false;
   private static final String TAG = "MAPEmailUtils";


   public MAPEmailUtils() {}

   public static String FromCaretToSlash(String var0) {
      return var0.replace('^', '/');
   }

   public static String FromSlashToCaret(String var0) {
      return var0.replace('/', '^');
   }

   public static boolean MailBoxIsSinked(Context var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         long var2 = EmailContent.Account.getDefaultAccountId(var0);
         if(EmailContent.Mailbox.findMailboxOfType(var0, var2, 0) == 65535L) {
            int var4 = Log.e("MAPEmailUtils", "MailBox is not ready yet. do not register");
            var1 = false;
         } else {
            var1 = true;
         }
      }

      return var1;
   }

   public static long findMailboxIdFromPath(ContentResolver var0, long var1, String var3) {
      Object var4;
      if(var1 != 65535L && var3 != null && !var3.equals("")) {
         String[] var6 = new String[]{"_id", "serverId"};
         Object var7 = null;
         String[] var8;
         if(var3.charAt(0) == 47) {
            var8 = var3.substring(1).split("/");
         } else {
            var8 = var3.split("/");
         }

         String var9 = "accountKey=? AND displayName=? AND parentServerId is NULL";
         int var10 = 0;
         Object var11 = 65535L;
         Object var13 = var7;

         while(true) {
            Cursor var46 = var8.length;
            if(var10 < var46) {
               if(var13 == null) {
                  Uri var14 = EmailContent.Mailbox.CONTENT_URI;
                  String[] var15 = new String[2];
                  String var16 = String.valueOf(var1);
                  var15[0] = var16;
                  String var17 = var8[var10];
                  var15[1] = var17;
                  var46 = var0.query(var14, var6, var9, var15, (String)null);
               } else {
                  var9 = "accountKey=? AND displayName=? AND parentServerId=\'" + var13 + "\'";
                  Uri var25 = EmailContent.Mailbox.CONTENT_URI;
                  String[] var26 = new String[2];
                  String var27 = String.valueOf(var1);
                  var26[0] = var27;
                  String var28 = var8[var10];
                  var26[1] = var28;
                  var0.query(var25, var6, var9, var26, (String)null);
               }

               if(var46 != false) {
                  boolean var43 = false;

                  label192: {
                     Object var19;
                     try {
                        var43 = true;
                        if(var46.getCount() != 1) {
                           int var30 = Log.e("MAPEmailUtils", "findParentIdFromPath. count is not 1");
                           var43 = false;
                           break label192;
                        }

                        boolean var18 = var46.moveToFirst();
                        var19 = var46.getLong(0);
                        var43 = false;
                     } finally {
                        if(var43) {
                           var46.close();
                        }
                     }

                     var13 = var19;
                     byte var21 = 1;

                     try {
                        String var22 = var46.getString(var21);
                     } finally {
                        ;
                     }

                     var46.close();
                     ++var10;
                     var11 = var19;
                     continue;
                  }

                  var4 = 65535L;
                  var46.close();
                  break;
               }

               int var34 = Log.e("MAPEmailUtils", "findParentIdFromPath. cursor is null");
               var4 = 65535L;
               break;
            }

            String var35 = "findParentIdFromPath. folderId: " + var11;
            int var36 = Log.d("MAPEmailUtils", var35);
            var4 = var11;
            break;
         }
      } else {
         var4 = 65535L;
      }

      return (long)var4;
   }

   public static String findServerIDFromPath(ContentResolver var0, long var1, String var3) {
      if(var1 != 65535L && var3 != null && !var3.equals("")) {
         String[] var4 = new String[]{"_id", "serverId"};
         Object var5 = null;
         String[] var6;
         if(var3.charAt(0) == 47) {
            var6 = var3.substring(1).split("/");
         } else {
            var6 = var3.split("/");
         }

         String var7 = "accountKey=? AND displayName=? AND parentServerId is NULL";
         int var8 = 0;
         long var9 = 65535L;
         Object var11 = var5;

         while(true) {
            Cursor var44 = var6.length;
            if(var8 < var44) {
               if(var11 == null) {
                  Uri var12 = EmailContent.Mailbox.CONTENT_URI;
                  String[] var13 = new String[2];
                  String var14 = String.valueOf(var1);
                  var13[0] = var14;
                  String var15 = var6[var8];
                  var13[1] = var15;
                  var44 = var0.query(var12, var4, var7, var13, (String)null);
               } else {
                  var7 = "accountKey=? AND displayName=? AND parentServerId=\'" + var11 + "\'";
                  Uri var23 = EmailContent.Mailbox.CONTENT_URI;
                  String[] var24 = new String[2];
                  String var25 = String.valueOf(var1);
                  var24[0] = var25;
                  String var26 = var6[var8];
                  var24[1] = var26;
                  var0.query(var23, var4, var7, var24, (String)null);
               }

               if(var44 != false) {
                  boolean var41 = false;

                  label192: {
                     Object var17;
                     try {
                        var41 = true;
                        if(var44.getCount() != 1) {
                           int var28 = Log.e("MAPEmailUtils", "findParentIdFromPath. count is not null");
                           var41 = false;
                           break label192;
                        }

                        boolean var16 = var44.moveToFirst();
                        var17 = var44.getLong(0);
                        var41 = false;
                     } finally {
                        if(var41) {
                           var44.close();
                        }
                     }

                     var11 = var17;
                     byte var19 = 1;

                     try {
                        String var20 = var44.getString(var19);
                     } finally {
                        ;
                     }

                     var44.close();
                     ++var8;
                     continue;
                  }

                  var0 = null;
                  var44.close();
                  break;
               }

               int var32 = Log.e("MAPEmailUtils", "findParentIdFromPath. cursor is null");
               var0 = null;
               break;
            }

            String var33 = "findParentIdFromPath. parentServerID: " + var11;
            int var34 = Log.d("MAPEmailUtils", var33);
            var0 = (ContentResolver)var11;
            break;
         }
      } else {
         var0 = null;
      }

      return var0;
   }

   public static String getFolderPathFromId(ContentResolver var0, long var1, long var3) {
      String var40;
      if(var1 != 65535L && var3 != 65535L) {
         StringBuffer var5 = new StringBuffer();
         String[] var6 = new String[]{"displayName", "parentServerId"};
         Uri var7 = EmailContent.Mailbox.CONTENT_URI;
         String[] var8 = new String[2];
         String var9 = String.valueOf(var3);
         var8[0] = var9;
         String var10 = String.valueOf(var1);
         var8[1] = var10;
         Cursor var41 = var0.query(var7, var6, "accountKey=? AND _id=?", var8, (String)null);
         if(var41 != null) {
            boolean var37 = false;

            String var14;
            label194: {
               try {
                  var37 = true;
                  if(var41.getCount() == 1) {
                     boolean var11 = var41.moveToFirst();
                     String var12 = var41.getString(0);
                     var5.append(var12);
                     var14 = var41.getString(1);
                     var37 = false;
                     break label194;
                  }

                  int var24 = Log.e("MAPEmailUtils", "getFolderPathFromId. count is not 1");
                  var37 = false;
               } finally {
                  if(var37) {
                     var41.close();
                  }
               }

               var40 = null;
               var41.close();
               return var40;
            }

            String var15 = var14;
            var41.close();

            while(true) {
               if(var15 == null) {
                  String var30 = "getFolderPathFromId. folderPath: " + var5;
                  int var31 = Log.d("MAPEmailUtils", var30);
                  var40 = var5.toString();
                  break;
               }

               String var16 = "accountKey=? AND serverId=\'" + var15 + "\'";
               Uri var17 = EmailContent.Mailbox.CONTENT_URI;
               String[] var18 = new String[1];
               String var19 = String.valueOf(var3);
               var18[0] = var19;
               var41 = var0.query(var17, var6, var16, var18, (String)null);
               if(var41 != null) {
                  boolean var34 = false;

                  label161: {
                     try {
                        var34 = true;
                        if(var41.getCount() != 1) {
                           int var27 = Log.e("MAPEmailUtils", "getFolderPathFromId. count is not 1");
                           var34 = false;
                           break label161;
                        }

                        boolean var20 = var41.moveToFirst();
                        StringBuffer var21 = var5.insert(0, "/");
                        String var22 = var41.getString(0);
                        var21.insert(0, var22);
                        var14 = var41.getString(1);
                        var34 = false;
                     } finally {
                        if(var34) {
                           var41.close();
                        }
                     }

                     var15 = var14;
                     var41.close();
                     continue;
                  }

                  var40 = null;
                  var41.close();
                  break;
               }

               int var29 = Log.e("MAPEmailUtils", "getFolderPathFromId. return null");
               var40 = null;
               break;
            }
         } else {
            int var26 = Log.e("MAPEmailUtils", "getFolderPathFromId. return null");
            var40 = null;
         }
      } else {
         var40 = null;
      }

      return var40;
   }

   public static boolean isBluetoothOn() {
      BluetoothAdapter var0 = BluetoothAdapter.getDefaultAdapter();
      boolean var2;
      if(var0 != null && var0.getState() == 12) {
         var2 = true;
      } else {
         int var1 = Log.e("MAPEmailUtils", "isBluetoothOn is not on.");
         var2 = false;
      }

      return var2;
   }

   public static boolean isEASAccount(Context var0, long var1) {
      boolean var3;
      if(var0 != null && var1 != 65535L) {
         EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(var0, var1);
         long var5 = var4.mHostAuthKeyRecv;
         EmailContent.HostAuth var7 = EmailContent.HostAuth.restoreHostAuthWithId(var0, var5);
         var4.mHostAuthRecv = var7;
         String var8 = var4.mHostAuthRecv.mProtocol;
         if("eas".equals(var8) == 1) {
            var3 = true;
         } else {
            var3 = false;
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   public static boolean isEASMsgLoaded(Context var0, long var1, long var3) {
      boolean var5;
      if(var0 != null && var1 != 65535L && var3 != 65535L) {
         EmailContent.Message var6 = EmailContent.Message.restoreMessageWithId(var0, var3);
         EmailContent.Account var7 = EmailContent.Account.restoreAccountWithId(var0, var1);
         if(var6.mFlagTruncated != 1 && (var7.mProtocolVersion == null || !var7.mProtocolVersion.contains("2.5") || var6.mIsMimeLoaded != 0)) {
            var5 = true;
         } else {
            var5 = false;
         }
      } else {
         var5 = false;
      }

      return var5;
   }

   public static boolean isNetworkAvalible(Context var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         NetworkInfo var2 = ((ConnectivityManager)var0.getSystemService("connectivity")).getActiveNetworkInfo();
         if(var2 != null) {
            DetailedState var3 = var2.getDetailedState();
            DetailedState var4 = DetailedState.CONNECTED;
            if(var3 == var4) {
               var1 = true;
               return var1;
            }
         }

         var1 = false;
      }

      return var1;
   }
}
