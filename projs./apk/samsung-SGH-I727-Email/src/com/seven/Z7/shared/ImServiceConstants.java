package com.seven.Z7.shared;

import com.seven.Z7.shared.Z7IDLCallbackType;

public class ImServiceConstants {

   public static final String EXTRA_INTENT_ACCOUNT_ID = "accountId";
   public static final String EXTRA_INTENT_BUDDY_SEARCH_QUERY = "buddy_search";
   public static final String EXTRA_INTENT_CHAT_ID = "chatId";
   public static final String EXTRA_INTENT_FROM_ADDRESS = "from";
   public static final String EXTRA_INTENT_FROM_NOTIFICATION_BAR = "from_notification_bar";
   public static final String EXTRA_INTENT_SHOW_MULTIPLE = "show_multiple";
   public static final String EXTRA_INTENT_SUBSCRIPTION = "subscription";


   public ImServiceConstants() {}

   public static enum ImCallbackType implements Z7IDLCallbackType {

      // $FF: synthetic field
      private static final ImServiceConstants.ImCallbackType[] $VALUES;
      IM_CALLBACK_CHAT_CREATED("IM_CALLBACK_CHAT_CREATED", 4),
      IM_CALLBACK_CHAT_CREATION_ERROR("IM_CALLBACK_CHAT_CREATION_ERROR", 5),
      IM_CALLBACK_CONNECTION_STATE_CHANGE("IM_CALLBACK_CONNECTION_STATE_CHANGE", 6),
      IM_CALLBACK_CONTACT_ADDED("IM_CALLBACK_CONTACT_ADDED", 10),
      IM_CALLBACK_CONTACT_CHANGED("IM_CALLBACK_CONTACT_CHANGED", 11),
      IM_CALLBACK_CONTACT_ERROR("IM_CALLBACK_CONTACT_ERROR", 13),
      IM_CALLBACK_CONTACT_JOINED("IM_CALLBACK_CONTACT_JOINED", 1),
      IM_CALLBACK_CONTACT_LEFT("IM_CALLBACK_CONTACT_LEFT", 2),
      IM_CALLBACK_CONTACT_PRESENCE_UPDATED("IM_CALLBACK_CONTACT_PRESENCE_UPDATED", 12),
      IM_CALLBACK_CONTACT_REMOVED("IM_CALLBACK_CONTACT_REMOVED", 14),
      IM_CALLBACK_GROUP_INVITATION("IM_CALLBACK_GROUP_INVITATION", 15),
      IM_CALLBACK_INCOMING_MESSAGE("IM_CALLBACK_INCOMING_MESSAGE", 0),
      IM_CALLBACK_PRESENCE_UPDATE("IM_CALLBACK_PRESENCE_UPDATE", 7),
      IM_CALLBACK_PRESENCE_UPDATE_ERROR("IM_CALLBACK_PRESENCE_UPDATE_ERROR", 8),
      IM_CALLBACK_ROSTER_UPDATED("IM_CALLBACK_ROSTER_UPDATED", 9),
      IM_CALLBACK_SEND_MESSAGE_ERROR("IM_CALLBACK_SEND_MESSAGE_ERROR", 3);
      static int base;


      static {
         ImServiceConstants.ImCallbackType[] var0 = new ImServiceConstants.ImCallbackType[16];
         ImServiceConstants.ImCallbackType var1 = IM_CALLBACK_INCOMING_MESSAGE;
         var0[0] = var1;
         ImServiceConstants.ImCallbackType var2 = IM_CALLBACK_CONTACT_JOINED;
         var0[1] = var2;
         ImServiceConstants.ImCallbackType var3 = IM_CALLBACK_CONTACT_LEFT;
         var0[2] = var3;
         ImServiceConstants.ImCallbackType var4 = IM_CALLBACK_SEND_MESSAGE_ERROR;
         var0[3] = var4;
         ImServiceConstants.ImCallbackType var5 = IM_CALLBACK_CHAT_CREATED;
         var0[4] = var5;
         ImServiceConstants.ImCallbackType var6 = IM_CALLBACK_CHAT_CREATION_ERROR;
         var0[5] = var6;
         ImServiceConstants.ImCallbackType var7 = IM_CALLBACK_CONNECTION_STATE_CHANGE;
         var0[6] = var7;
         ImServiceConstants.ImCallbackType var8 = IM_CALLBACK_PRESENCE_UPDATE;
         var0[7] = var8;
         ImServiceConstants.ImCallbackType var9 = IM_CALLBACK_PRESENCE_UPDATE_ERROR;
         var0[8] = var9;
         ImServiceConstants.ImCallbackType var10 = IM_CALLBACK_ROSTER_UPDATED;
         var0[9] = var10;
         ImServiceConstants.ImCallbackType var11 = IM_CALLBACK_CONTACT_ADDED;
         var0[10] = var11;
         ImServiceConstants.ImCallbackType var12 = IM_CALLBACK_CONTACT_CHANGED;
         var0[11] = var12;
         ImServiceConstants.ImCallbackType var13 = IM_CALLBACK_CONTACT_PRESENCE_UPDATED;
         var0[12] = var13;
         ImServiceConstants.ImCallbackType var14 = IM_CALLBACK_CONTACT_ERROR;
         var0[13] = var14;
         ImServiceConstants.ImCallbackType var15 = IM_CALLBACK_CONTACT_REMOVED;
         var0[14] = var15;
         ImServiceConstants.ImCallbackType var16 = IM_CALLBACK_GROUP_INVITATION;
         var0[15] = var16;
         $VALUES = var0;
         base = 300;
      }

      private ImCallbackType(String var1, int var2) {}

      public static ImServiceConstants.ImCallbackType fromId(int var0) {
         ImServiceConstants.ImCallbackType[] var1 = values();
         int var2 = base;
         int var3 = var0 - var2;
         ImServiceConstants.ImCallbackType var5;
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

   public static enum Z7IMClientSetting {

      // $FF: synthetic field
      private static final ImServiceConstants.Z7IMClientSetting[] $VALUES;
      Z7_IM_CLIENT_SETTING_AUDIO_NOTIFICATION_BOOLEAN("Z7_IM_CLIENT_SETTING_AUDIO_NOTIFICATION_BOOLEAN", 11, "checkbox_audio_notification"),
      Z7_IM_CLIENT_SETTING_AUTO_LOGIN_BOOLEAN("Z7_IM_CLIENT_SETTING_AUTO_LOGIN_BOOLEAN", 6, "checkbox_auto_sign_in"),
      Z7_IM_CLIENT_SETTING_GATEWAY_STATUS_INTEGER("Z7_IM_CLIENT_SETTING_GATEWAY_STATUS_INTEGER", 5),
      Z7_IM_CLIENT_SETTING_INACTIVE_PRESENCE_INTEGER("Z7_IM_CLIENT_SETTING_INACTIVE_PRESENCE_INTEGER", 2),
      Z7_IM_CLIENT_SETTING_INACTIVE_STATUS_STRING("Z7_IM_CLIENT_SETTING_INACTIVE_STATUS_STRING", 3),
      Z7_IM_CLIENT_SETTING_NEW_MESSAGE_NOTIFICATION_BOOLEAN("Z7_IM_CLIENT_SETTING_NEW_MESSAGE_NOTIFICATION_BOOLEAN", 9, "checkbox_new_message_notification"),
      Z7_IM_CLIENT_SETTING_OPERATION_MODE_INTEGER("Z7_IM_CLIENT_SETTING_OPERATION_MODE_INTEGER", 4),
      Z7_IM_CLIENT_SETTING_PASSWORD_STRING("Z7_IM_CLIENT_SETTING_PASSWORD_STRING", 8),
      Z7_IM_CLIENT_SETTING_PRESENCE_INTEGER("Z7_IM_CLIENT_SETTING_PRESENCE_INTEGER", 0),
      Z7_IM_CLIENT_SETTING_SAVE_PASSWORD_BOOLEAN("Z7_IM_CLIENT_SETTING_SAVE_PASSWORD_BOOLEAN", 7, "checkbox_save_password"),
      Z7_IM_CLIENT_SETTING_STATUS_STRING("Z7_IM_CLIENT_SETTING_STATUS_STRING", 1),
      Z7_IM_CLIENT_SETTING_VIBRATION_NOTIFICATION_BOOLEAN("Z7_IM_CLIENT_SETTING_VIBRATION_NOTIFICATION_BOOLEAN", 10, "checkbox_vibrate");
      String mKey;


      static {
         ImServiceConstants.Z7IMClientSetting[] var0 = new ImServiceConstants.Z7IMClientSetting[12];
         ImServiceConstants.Z7IMClientSetting var1 = Z7_IM_CLIENT_SETTING_PRESENCE_INTEGER;
         var0[0] = var1;
         ImServiceConstants.Z7IMClientSetting var2 = Z7_IM_CLIENT_SETTING_STATUS_STRING;
         var0[1] = var2;
         ImServiceConstants.Z7IMClientSetting var3 = Z7_IM_CLIENT_SETTING_INACTIVE_PRESENCE_INTEGER;
         var0[2] = var3;
         ImServiceConstants.Z7IMClientSetting var4 = Z7_IM_CLIENT_SETTING_INACTIVE_STATUS_STRING;
         var0[3] = var4;
         ImServiceConstants.Z7IMClientSetting var5 = Z7_IM_CLIENT_SETTING_OPERATION_MODE_INTEGER;
         var0[4] = var5;
         ImServiceConstants.Z7IMClientSetting var6 = Z7_IM_CLIENT_SETTING_GATEWAY_STATUS_INTEGER;
         var0[5] = var6;
         ImServiceConstants.Z7IMClientSetting var7 = Z7_IM_CLIENT_SETTING_AUTO_LOGIN_BOOLEAN;
         var0[6] = var7;
         ImServiceConstants.Z7IMClientSetting var8 = Z7_IM_CLIENT_SETTING_SAVE_PASSWORD_BOOLEAN;
         var0[7] = var8;
         ImServiceConstants.Z7IMClientSetting var9 = Z7_IM_CLIENT_SETTING_PASSWORD_STRING;
         var0[8] = var9;
         ImServiceConstants.Z7IMClientSetting var10 = Z7_IM_CLIENT_SETTING_NEW_MESSAGE_NOTIFICATION_BOOLEAN;
         var0[9] = var10;
         ImServiceConstants.Z7IMClientSetting var11 = Z7_IM_CLIENT_SETTING_VIBRATION_NOTIFICATION_BOOLEAN;
         var0[10] = var11;
         ImServiceConstants.Z7IMClientSetting var12 = Z7_IM_CLIENT_SETTING_AUDIO_NOTIFICATION_BOOLEAN;
         var0[11] = var12;
         $VALUES = var0;
      }

      private Z7IMClientSetting(String var1, int var2) {}

      private Z7IMClientSetting(String var1, int var2, String var3) {
         this.mKey = var3;
      }

      public String toString() {
         String var1;
         if(this.mKey != null) {
            var1 = this.mKey;
         } else {
            var1 = super.toString();
         }

         return var1;
      }
   }

   public static enum ImGateway {

      // $FF: synthetic field
      private static final ImServiceConstants.ImGateway[] $VALUES;
      GATEWAY_ERROR("GATEWAY_ERROR", 5),
      GATEWAY_OFFLINE("GATEWAY_OFFLINE", 2),
      GATEWAY_ONLINE("GATEWAY_ONLINE", 1),
      GATEWAY_OVER_TAKEN("GATEWAY_OVER_TAKEN", 3),
      GATEWAY_UNKNOWN("GATEWAY_UNKNOWN", 0),
      GATEWAY_UNREACHABLE("GATEWAY_UNREACHABLE", 4);


      static {
         ImServiceConstants.ImGateway[] var0 = new ImServiceConstants.ImGateway[6];
         ImServiceConstants.ImGateway var1 = GATEWAY_UNKNOWN;
         var0[0] = var1;
         ImServiceConstants.ImGateway var2 = GATEWAY_ONLINE;
         var0[1] = var2;
         ImServiceConstants.ImGateway var3 = GATEWAY_OFFLINE;
         var0[2] = var3;
         ImServiceConstants.ImGateway var4 = GATEWAY_OVER_TAKEN;
         var0[3] = var4;
         ImServiceConstants.ImGateway var5 = GATEWAY_UNREACHABLE;
         var0[4] = var5;
         ImServiceConstants.ImGateway var6 = GATEWAY_ERROR;
         var0[5] = var6;
         $VALUES = var0;
      }

      private ImGateway(String var1, int var2) {}
   }

   public static enum ImMessageType {

      // $FF: synthetic field
      private static final ImServiceConstants.ImMessageType[] $VALUES;
      INSTANT_MESSAGE_CHAT("INSTANT_MESSAGE_CHAT", 1),
      INSTANT_MESSAGE_ERROR("INSTANT_MESSAGE_ERROR", 4),
      INSTANT_MESSAGE_GROUPCHAT("INSTANT_MESSAGE_GROUPCHAT", 2),
      INSTANT_MESSAGE_HEADLINE("INSTANT_MESSAGE_HEADLINE", 3),
      INSTANT_MESSAGE_NORMAL("INSTANT_MESSAGE_NORMAL", 0);


      static {
         ImServiceConstants.ImMessageType[] var0 = new ImServiceConstants.ImMessageType[5];
         ImServiceConstants.ImMessageType var1 = INSTANT_MESSAGE_NORMAL;
         var0[0] = var1;
         ImServiceConstants.ImMessageType var2 = INSTANT_MESSAGE_CHAT;
         var0[1] = var2;
         ImServiceConstants.ImMessageType var3 = INSTANT_MESSAGE_GROUPCHAT;
         var0[2] = var3;
         ImServiceConstants.ImMessageType var4 = INSTANT_MESSAGE_HEADLINE;
         var0[3] = var4;
         ImServiceConstants.ImMessageType var5 = INSTANT_MESSAGE_ERROR;
         var0[4] = var5;
         $VALUES = var0;
      }

      private ImMessageType(String var1, int var2) {}
   }

   public static enum ImRosterModifyAction {

      // $FF: synthetic field
      private static final ImServiceConstants.ImRosterModifyAction[] $VALUES;
      IM_ROSTER_APPROVE_SUBSCRIPTION("IM_ROSTER_APPROVE_SUBSCRIPTION", 0),
      IM_ROSTER_BLOCK("IM_ROSTER_BLOCK", 3),
      IM_ROSTER_DECLINE_SUBSCRIPTION("IM_ROSTER_DECLINE_SUBSCRIPTION", 1),
      IM_ROSTER_END_CONVERSATION("IM_ROSTER_END_CONVERSATION", 6),
      IM_ROSTER_REMOVE_CONTACT("IM_ROSTER_REMOVE_CONTACT", 5),
      IM_ROSTER_REQUEST_SUBSCRIPTION("IM_ROSTER_REQUEST_SUBSCRIPTION", 2),
      IM_ROSTER_UNBLOCK("IM_ROSTER_UNBLOCK", 4);


      static {
         ImServiceConstants.ImRosterModifyAction[] var0 = new ImServiceConstants.ImRosterModifyAction[7];
         ImServiceConstants.ImRosterModifyAction var1 = IM_ROSTER_APPROVE_SUBSCRIPTION;
         var0[0] = var1;
         ImServiceConstants.ImRosterModifyAction var2 = IM_ROSTER_DECLINE_SUBSCRIPTION;
         var0[1] = var2;
         ImServiceConstants.ImRosterModifyAction var3 = IM_ROSTER_REQUEST_SUBSCRIPTION;
         var0[2] = var3;
         ImServiceConstants.ImRosterModifyAction var4 = IM_ROSTER_BLOCK;
         var0[3] = var4;
         ImServiceConstants.ImRosterModifyAction var5 = IM_ROSTER_UNBLOCK;
         var0[4] = var5;
         ImServiceConstants.ImRosterModifyAction var6 = IM_ROSTER_REMOVE_CONTACT;
         var0[5] = var6;
         ImServiceConstants.ImRosterModifyAction var7 = IM_ROSTER_END_CONVERSATION;
         var0[6] = var7;
         $VALUES = var0;
      }

      private ImRosterModifyAction(String var1, int var2) {}
   }

   public static enum ImPresence {

      // $FF: synthetic field
      private static final ImServiceConstants.ImPresence[] $VALUES;
      PRESENCE_AVAILABLE("PRESENCE_AVAILABLE", 1),
      PRESENCE_AWAY("PRESENCE_AWAY", 2),
      PRESENCE_BE_RIGHT_BACK("PRESENCE_BE_RIGHT_BACK", 4),
      PRESENCE_BUSY("PRESENCE_BUSY", 3),
      PRESENCE_EXTENDED_AWAY("PRESENCE_EXTENDED_AWAY", 5),
      PRESENCE_INVISIBLE("PRESENCE_INVISIBLE", 6),
      PRESENCE_OFFLINE("PRESENCE_OFFLINE", 7),
      PRESENCE_UNKNOWN("PRESENCE_UNKNOWN", 0);


      static {
         ImServiceConstants.ImPresence[] var0 = new ImServiceConstants.ImPresence[8];
         ImServiceConstants.ImPresence var1 = PRESENCE_UNKNOWN;
         var0[0] = var1;
         ImServiceConstants.ImPresence var2 = PRESENCE_AVAILABLE;
         var0[1] = var2;
         ImServiceConstants.ImPresence var3 = PRESENCE_AWAY;
         var0[2] = var3;
         ImServiceConstants.ImPresence var4 = PRESENCE_BUSY;
         var0[3] = var4;
         ImServiceConstants.ImPresence var5 = PRESENCE_BE_RIGHT_BACK;
         var0[4] = var5;
         ImServiceConstants.ImPresence var6 = PRESENCE_EXTENDED_AWAY;
         var0[5] = var6;
         ImServiceConstants.ImPresence var7 = PRESENCE_INVISIBLE;
         var0[6] = var7;
         ImServiceConstants.ImPresence var8 = PRESENCE_OFFLINE;
         var0[7] = var8;
         $VALUES = var0;
      }

      private ImPresence(String var1, int var2) {}
   }

   public static enum ImMode {

      // $FF: synthetic field
      private static final ImServiceConstants.ImMode[] $VALUES;
      MODE_INACTIVE("MODE_INACTIVE", 1),
      MODE_OFFLINE("MODE_OFFLINE", 0),
      MODE_ONLINE("MODE_ONLINE", 2);


      static {
         ImServiceConstants.ImMode[] var0 = new ImServiceConstants.ImMode[3];
         ImServiceConstants.ImMode var1 = MODE_OFFLINE;
         var0[0] = var1;
         ImServiceConstants.ImMode var2 = MODE_INACTIVE;
         var0[1] = var2;
         ImServiceConstants.ImMode var3 = MODE_ONLINE;
         var0[2] = var3;
         $VALUES = var0;
      }

      private ImMode(String var1, int var2) {}
   }

   public static enum ImSubscription {

      // $FF: synthetic field
      private static final ImServiceConstants.ImSubscription[] $VALUES;
      SUBSCRIPTION_ALLOWED("SUBSCRIPTION_ALLOWED", 1),
      SUBSCRIPTION_BLOCKED("SUBSCRIPTION_BLOCKED", 2),
      SUBSCRIPTION_NONE("SUBSCRIPTION_NONE", 0),
      SUBSCRIPTION_PENDNG("SUBSCRIPTION_PENDNG", 3);


      static {
         ImServiceConstants.ImSubscription[] var0 = new ImServiceConstants.ImSubscription[4];
         ImServiceConstants.ImSubscription var1 = SUBSCRIPTION_NONE;
         var0[0] = var1;
         ImServiceConstants.ImSubscription var2 = SUBSCRIPTION_ALLOWED;
         var0[1] = var2;
         ImServiceConstants.ImSubscription var3 = SUBSCRIPTION_BLOCKED;
         var0[2] = var3;
         ImServiceConstants.ImSubscription var4 = SUBSCRIPTION_PENDNG;
         var0[3] = var4;
         $VALUES = var0;
      }

      private ImSubscription(String var1, int var2) {}
   }
}
