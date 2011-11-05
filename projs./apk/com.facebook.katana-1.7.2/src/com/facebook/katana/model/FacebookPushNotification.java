package com.facebook.katana.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.facebook.katana.HomeActivity;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.UsersTabHostActivity;
import com.facebook.katana.activity.events.EventsActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ChatNotificationsManager;
import com.facebook.katana.binding.ServiceNotificationManager;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.Map;

public class FacebookPushNotification extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "message"
   )
   public final String mMessage;
   @JMAutogen.InferredType(
      jsonFieldName = "params"
   )
   public final Map<Object, Object> mParams;
   @JMAutogen.InferredType(
      jsonFieldName = "time"
   )
   public final long mTimestamp;
   @JMAutogen.InferredType(
      jsonFieldName = "type"
   )
   public final String mType;
   @JMAutogen.InferredType(
      jsonFieldName = "unread_count"
   )
   public final int mUnreadCount;


   private FacebookPushNotification() {
      this.mType = null;
      this.mTimestamp = 0L;
      this.mMessage = null;
      this.mUnreadCount = 0;
      this.mParams = null;
   }

   public FacebookPushNotification(String var1, long var2, String var4, int var5, Map<Object, Object> var6) {
      this.mType = var1;
      this.mTimestamp = var2;
      this.mMessage = var4;
      this.mUnreadCount = var5;
      this.mParams = var6;
   }

   private FacebookPushNotification.NotificationType mapType(String var1) {
      FacebookPushNotification.NotificationType var2;
      if(var1.equals("msg")) {
         var2 = FacebookPushNotification.NotificationType.MSG;
      } else if(var1.equals("chat")) {
         var2 = FacebookPushNotification.NotificationType.CHAT;
      } else if(var1.equals("wall")) {
         var2 = FacebookPushNotification.NotificationType.WALL;
      } else if(var1.equals("event_invite")) {
         var2 = FacebookPushNotification.NotificationType.EVENT;
      } else if(var1.equals("friend")) {
         var2 = FacebookPushNotification.NotificationType.FRIEND;
      } else {
         var2 = FacebookPushNotification.NotificationType.DEFAULT;
      }

      return var2;
   }

   public void showNotification(Context var1) {
      SharedPreferences var2 = PreferenceManager.getDefaultSharedPreferences(var1);
      if(var2.getBoolean("notif", (boolean)1)) {
         String var3 = this.mType;
         FacebookPushNotification.NotificationType var4 = this.mapType(var3);
         FacebookPushNotification.NotificationType var5 = FacebookPushNotification.NotificationType.CHAT;
         if(var4 == var5) {
            ChatNotificationsManager var6 = new ChatNotificationsManager(var1);
            long var7 = ((Long)this.mParams.get("from")).longValue();
            String var9 = this.mMessage;
            String var10 = (String)this.mParams.get("token");
            var6.displayNotification(var7, var9, var10);
         } else {
            String var11 = this.mMessage;
            int var12 = this.mUnreadCount;
            byte var13 = 0;
            int var14 = -1;
            byte var15 = -1;
            Intent var16 = null;
            int[] var17 = FacebookPushNotification.1.$SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType;
            String var18 = this.mType;
            int var19 = this.mapType(var18).ordinal();
            switch(var17[var19]) {
            case 1:
               if(var2.getBoolean("notif_messages", (boolean)0)) {
                  var14 = 2130837803;
                  var15 = 1;
                  var16 = IntentUriHandler.getIntentForUri(var1, "fb://messaging");
               }
               break;
            case 2:
               var14 = 2130837704;
               var15 = 5;
               AppSession var23 = AppSession.getActiveSession(var1, (boolean)0);
               if(var23 == null) {
                  return;
               }

               long var24 = var23.getSessionInfo().userId;
               var16 = ProfileTabHostActivity.intentForProfile(var1, var24);
               break;
            case 3:
               var14 = 2130837801;
               var15 = 2;
               var16 = new Intent(var1, UsersTabHostActivity.class);
               Intent var26 = var16.putExtra("com.facebook.katana.DefaultTab", "requests");
               break;
            case 4:
               var14 = 2130837800;
               var15 = 3;
               var16 = new Intent(var1, EventsActivity.class);
               break;
            default:
               var14 = 2130837704;
               var15 = 5;
               var16 = new Intent(var1, HomeActivity.class);
               String var20 = HomeActivity.EXTRA_SHOW_NOTIFICATIONS;
               var16.putExtra(var20, (boolean)1);
            }

            if(var16 != null) {
               Intent var22 = var16.addFlags(67108864);
               ServiceNotificationManager.realShowNotification(var1, var14, var11, var12, var15, var16, (boolean)var13);
            }
         }
      }
   }

   private static enum NotificationType {

      // $FF: synthetic field
      private static final FacebookPushNotification.NotificationType[] $VALUES;
      CHAT("CHAT", 2),
      DEFAULT("DEFAULT", 6),
      EVENT("EVENT", 4),
      FRIEND("FRIEND", 5),
      MSG("MSG", 1),
      UNKNOWN("UNKNOWN", 0),
      WALL("WALL", 3);


      static {
         FacebookPushNotification.NotificationType[] var0 = new FacebookPushNotification.NotificationType[7];
         FacebookPushNotification.NotificationType var1 = UNKNOWN;
         var0[0] = var1;
         FacebookPushNotification.NotificationType var2 = MSG;
         var0[1] = var2;
         FacebookPushNotification.NotificationType var3 = CHAT;
         var0[2] = var3;
         FacebookPushNotification.NotificationType var4 = WALL;
         var0[3] = var4;
         FacebookPushNotification.NotificationType var5 = EVENT;
         var0[4] = var5;
         FacebookPushNotification.NotificationType var6 = FRIEND;
         var0[5] = var6;
         FacebookPushNotification.NotificationType var7 = DEFAULT;
         var0[6] = var7;
         $VALUES = var0;
      }

      private NotificationType(String var1, int var2) {}
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType = new int[FacebookPushNotification.NotificationType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType;
            int var1 = FacebookPushNotification.NotificationType.MSG.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType;
            int var3 = FacebookPushNotification.NotificationType.WALL.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType;
            int var5 = FacebookPushNotification.NotificationType.FRIEND.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType;
            int var7 = FacebookPushNotification.NotificationType.EVENT.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }
}
