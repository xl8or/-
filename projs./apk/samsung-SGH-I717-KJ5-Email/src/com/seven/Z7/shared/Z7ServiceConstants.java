package com.seven.Z7.shared;

import com.seven.Z7.shared.Z7IDLCallbackType;

public class Z7ServiceConstants {

   public static final String EXTRA_INTENT_ACCOUNT_ID = "accountId";


   public Z7ServiceConstants() {}

   public static enum SystemCallbackType implements Z7IDLCallbackType {

      // $FF: synthetic field
      private static final Z7ServiceConstants.SystemCallbackType[] $VALUES;
      Z7_CALLBACK_ACCOUNT_ADDED("Z7_CALLBACK_ACCOUNT_ADDED", 3),
      Z7_CALLBACK_ACCOUNT_REMOVED("Z7_CALLBACK_ACCOUNT_REMOVED", 4),
      Z7_CALLBACK_ACCOUNT_STATUS_DATAILS_CHANGED("Z7_CALLBACK_ACCOUNT_STATUS_DATAILS_CHANGED", 33),
      Z7_CALLBACK_ADD_ACCOUNT_EXTERNALLY("Z7_CALLBACK_ADD_ACCOUNT_EXTERNALLY", 34),
      Z7_CALLBACK_ATTACHMENT_DOWNLOAD_CANCELLED("Z7_CALLBACK_ATTACHMENT_DOWNLOAD_CANCELLED", 28),
      Z7_CALLBACK_ATTACHMENT_DOWNLOAD_FAILED("Z7_CALLBACK_ATTACHMENT_DOWNLOAD_FAILED", 30),
      Z7_CALLBACK_ATTACHMENT_DOWNLOAD_FINISHED("Z7_CALLBACK_ATTACHMENT_DOWNLOAD_FINISHED", 29),
      Z7_CALLBACK_ATTACHMENT_DOWNLOAD_PROGRESS("Z7_CALLBACK_ATTACHMENT_DOWNLOAD_PROGRESS", 31),
      Z7_CALLBACK_CONNECT_STATE_CHANGED("Z7_CALLBACK_CONNECT_STATE_CHANGED", 2),
      Z7_CALLBACK_CONTENT_UPDATE_COMPLETED("Z7_CALLBACK_CONTENT_UPDATE_COMPLETED", 32),
      Z7_CALLBACK_ENGINE_INITIALIZED("Z7_CALLBACK_ENGINE_INITIALIZED", 36),
      Z7_CALLBACK_ERROR("Z7_CALLBACK_ERROR", 0),
      Z7_CALLBACK_IGNORE_CERT_REQUIRED("Z7_CALLBACK_IGNORE_CERT_REQUIRED", 10),
      Z7_CALLBACK_LOGIN_RESULT("Z7_CALLBACK_LOGIN_RESULT", 9),
      Z7_CALLBACK_LOG_LEVEL_CHANGED("Z7_CALLBACK_LOG_LEVEL_CHANGED", 1),
      Z7_CALLBACK_PAUSE("Z7_CALLBACK_PAUSE", 5),
      Z7_CALLBACK_PROVISIONING_CERTIFICATE_REQUIRED("Z7_CALLBACK_PROVISIONING_CERTIFICATE_REQUIRED", 18),
      Z7_CALLBACK_PROVISIONING_CONNECTORS("Z7_CALLBACK_PROVISIONING_CONNECTORS", 13),
      Z7_CALLBACK_PROVISIONING_FAILED("Z7_CALLBACK_PROVISIONING_FAILED", 16),
      Z7_CALLBACK_PROVISIONING_SET_ISP("Z7_CALLBACK_PROVISIONING_SET_ISP", 17),
      Z7_CALLBACK_PROVISIONING_SUCCESS("Z7_CALLBACK_PROVISIONING_SUCCESS", 15),
      Z7_CALLBACK_PROVISIONING_VALIDATE_URL("Z7_CALLBACK_PROVISIONING_VALIDATE_URL", 14),
      Z7_CALLBACK_PROVISIONING_VALIDATION_COMPLETED("Z7_CALLBACK_PROVISIONING_VALIDATION_COMPLETED", 19),
      Z7_CALLBACK_PROVISIONING_VALIDATION_REQUIRED("Z7_CALLBACK_PROVISIONING_VALIDATION_REQUIRED", 20),
      Z7_CALLBACK_RELOGIN_REQUIRED("Z7_CALLBACK_RELOGIN_REQUIRED", 7),
      Z7_CALLBACK_RELOGIN_REQUIRED_CONTACT_SEARCH("Z7_CALLBACK_RELOGIN_REQUIRED_CONTACT_SEARCH", 8),
      Z7_CALLBACK_RESUME("Z7_CALLBACK_RESUME", 6),
      Z7_CALLBACK_SERVICE_STATE_CHANGED("Z7_CALLBACK_SERVICE_STATE_CHANGED", 27),
      Z7_CALLBACK_SYNC_ADAPTER_SYNC_DONE("Z7_CALLBACK_SYNC_ADAPTER_SYNC_DONE", 26),
      Z7_CALLBACK_TASK_COMPLETED("Z7_CALLBACK_TASK_COMPLETED", 21),
      Z7_CALLBACK_UPDATE_AVAILABLE("Z7_CALLBACK_UPDATE_AVAILABLE", 25),
      Z7_CALLBACK_UPDATE_SYNC_ADAPTER_SETTINGS("Z7_CALLBACK_UPDATE_SYNC_ADAPTER_SETTINGS", 35),
      Z7_CALLBACK_UPGRADE_AVAILABLE("Z7_CALLBACK_UPGRADE_AVAILABLE", 24),
      Z7_CALLBACK_UPGRADE_DOWNLOADED("Z7_CALLBACK_UPGRADE_DOWNLOADED", 23),
      Z7_CALLBACK_VALIDATION_OK("Z7_CALLBACK_VALIDATION_OK", 22),
      Z7_NOTIFY_ACCOUNT_STATUS_CHANGED("Z7_NOTIFY_ACCOUNT_STATUS_CHANGED", 12),
      Z7_NOTIFY_ENDPOINT_STATUS_CHANGED("Z7_NOTIFY_ENDPOINT_STATUS_CHANGED", 11);
      static int base;


      static {
         Z7ServiceConstants.SystemCallbackType[] var0 = new Z7ServiceConstants.SystemCallbackType[37];
         Z7ServiceConstants.SystemCallbackType var1 = Z7_CALLBACK_ERROR;
         var0[0] = var1;
         Z7ServiceConstants.SystemCallbackType var2 = Z7_CALLBACK_LOG_LEVEL_CHANGED;
         var0[1] = var2;
         Z7ServiceConstants.SystemCallbackType var3 = Z7_CALLBACK_CONNECT_STATE_CHANGED;
         var0[2] = var3;
         Z7ServiceConstants.SystemCallbackType var4 = Z7_CALLBACK_ACCOUNT_ADDED;
         var0[3] = var4;
         Z7ServiceConstants.SystemCallbackType var5 = Z7_CALLBACK_ACCOUNT_REMOVED;
         var0[4] = var5;
         Z7ServiceConstants.SystemCallbackType var6 = Z7_CALLBACK_PAUSE;
         var0[5] = var6;
         Z7ServiceConstants.SystemCallbackType var7 = Z7_CALLBACK_RESUME;
         var0[6] = var7;
         Z7ServiceConstants.SystemCallbackType var8 = Z7_CALLBACK_RELOGIN_REQUIRED;
         var0[7] = var8;
         Z7ServiceConstants.SystemCallbackType var9 = Z7_CALLBACK_RELOGIN_REQUIRED_CONTACT_SEARCH;
         var0[8] = var9;
         Z7ServiceConstants.SystemCallbackType var10 = Z7_CALLBACK_LOGIN_RESULT;
         var0[9] = var10;
         Z7ServiceConstants.SystemCallbackType var11 = Z7_CALLBACK_IGNORE_CERT_REQUIRED;
         var0[10] = var11;
         Z7ServiceConstants.SystemCallbackType var12 = Z7_NOTIFY_ENDPOINT_STATUS_CHANGED;
         var0[11] = var12;
         Z7ServiceConstants.SystemCallbackType var13 = Z7_NOTIFY_ACCOUNT_STATUS_CHANGED;
         var0[12] = var13;
         Z7ServiceConstants.SystemCallbackType var14 = Z7_CALLBACK_PROVISIONING_CONNECTORS;
         var0[13] = var14;
         Z7ServiceConstants.SystemCallbackType var15 = Z7_CALLBACK_PROVISIONING_VALIDATE_URL;
         var0[14] = var15;
         Z7ServiceConstants.SystemCallbackType var16 = Z7_CALLBACK_PROVISIONING_SUCCESS;
         var0[15] = var16;
         Z7ServiceConstants.SystemCallbackType var17 = Z7_CALLBACK_PROVISIONING_FAILED;
         var0[16] = var17;
         Z7ServiceConstants.SystemCallbackType var18 = Z7_CALLBACK_PROVISIONING_SET_ISP;
         var0[17] = var18;
         Z7ServiceConstants.SystemCallbackType var19 = Z7_CALLBACK_PROVISIONING_CERTIFICATE_REQUIRED;
         var0[18] = var19;
         Z7ServiceConstants.SystemCallbackType var20 = Z7_CALLBACK_PROVISIONING_VALIDATION_COMPLETED;
         var0[19] = var20;
         Z7ServiceConstants.SystemCallbackType var21 = Z7_CALLBACK_PROVISIONING_VALIDATION_REQUIRED;
         var0[20] = var21;
         Z7ServiceConstants.SystemCallbackType var22 = Z7_CALLBACK_TASK_COMPLETED;
         var0[21] = var22;
         Z7ServiceConstants.SystemCallbackType var23 = Z7_CALLBACK_VALIDATION_OK;
         var0[22] = var23;
         Z7ServiceConstants.SystemCallbackType var24 = Z7_CALLBACK_UPGRADE_DOWNLOADED;
         var0[23] = var24;
         Z7ServiceConstants.SystemCallbackType var25 = Z7_CALLBACK_UPGRADE_AVAILABLE;
         var0[24] = var25;
         Z7ServiceConstants.SystemCallbackType var26 = Z7_CALLBACK_UPDATE_AVAILABLE;
         var0[25] = var26;
         Z7ServiceConstants.SystemCallbackType var27 = Z7_CALLBACK_SYNC_ADAPTER_SYNC_DONE;
         var0[26] = var27;
         Z7ServiceConstants.SystemCallbackType var28 = Z7_CALLBACK_SERVICE_STATE_CHANGED;
         var0[27] = var28;
         Z7ServiceConstants.SystemCallbackType var29 = Z7_CALLBACK_ATTACHMENT_DOWNLOAD_CANCELLED;
         var0[28] = var29;
         Z7ServiceConstants.SystemCallbackType var30 = Z7_CALLBACK_ATTACHMENT_DOWNLOAD_FINISHED;
         var0[29] = var30;
         Z7ServiceConstants.SystemCallbackType var31 = Z7_CALLBACK_ATTACHMENT_DOWNLOAD_FAILED;
         var0[30] = var31;
         Z7ServiceConstants.SystemCallbackType var32 = Z7_CALLBACK_ATTACHMENT_DOWNLOAD_PROGRESS;
         var0[31] = var32;
         Z7ServiceConstants.SystemCallbackType var33 = Z7_CALLBACK_CONTENT_UPDATE_COMPLETED;
         var0[32] = var33;
         Z7ServiceConstants.SystemCallbackType var34 = Z7_CALLBACK_ACCOUNT_STATUS_DATAILS_CHANGED;
         var0[33] = var34;
         Z7ServiceConstants.SystemCallbackType var35 = Z7_CALLBACK_ADD_ACCOUNT_EXTERNALLY;
         var0[34] = var35;
         Z7ServiceConstants.SystemCallbackType var36 = Z7_CALLBACK_UPDATE_SYNC_ADAPTER_SETTINGS;
         var0[35] = var36;
         Z7ServiceConstants.SystemCallbackType var37 = Z7_CALLBACK_ENGINE_INITIALIZED;
         var0[36] = var37;
         $VALUES = var0;
         base = 100;
      }

      private SystemCallbackType(String var1, int var2) {}

      public static Z7ServiceConstants.SystemCallbackType fromId(int var0) {
         Z7ServiceConstants.SystemCallbackType[] var1 = values();
         int var2 = base;
         int var3 = var0 - var2;
         Z7ServiceConstants.SystemCallbackType var5;
         if(var3 >= 0) {
            int var4 = var1.length;
            if(var3 < var4) {
               var5 = var1[var3];
               return var5;
            }
         }

         var5 = null;
         return var5;
      }

      public int getEventId() {
         int var1 = this.ordinal();
         int var2 = base;
         return var1 + var2;
      }
   }
}
