package com.facebook.katana.util;

import android.content.Context;
import android.os.Build;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.provider.KeyValueManager;

public final class GrowthUtils {

   private static final String FIND_FRIENDS_CONSENT_APPROVED = "findFriendsConsentApproved";
   private static final String FRIENDS_NAG_FIELD = "findFriendsDialogShown";
   private static final String LEGAL_BAR_SHOWN = "findFriendsLegalBarShown";
   private static final String PHONE_NAG_FIELD = "phoneNumberDialogShown";


   public GrowthUtils() {}

   private static String getUserKey(Context var0, String var1) {
      String var2 = Long.toString(AppSession.getActiveSession(var0, (boolean)0).getSessionInfo().userId);
      StringBuilder var3 = new StringBuilder(var2);
      StringBuilder var4 = var3.append(":");
      var3.append(var1);
      return var3.toString();
   }

   public static boolean kddiImporterEnabled(Context var0) {
      String var1 = Build.BRAND;
      boolean var4;
      if(var1 != null && var1.equalsIgnoreCase("KDDI")) {
         Boolean var2 = Boolean.TRUE;
         Boolean var3 = Gatekeeper.get(var0, "android_ci_kddi_intro_enabled");
         if(var2.equals(var3)) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public static void setFindFriendsConsentApproved(Context var0) {
      String var1 = getUserKey(var0, "findFriendsConsentApproved");
      Boolean var2 = new Boolean((boolean)1);
      KeyValueManager.setValue(var0, var1, var2);
   }

   public static void setLegalBarShown(Context var0) {
      String var1 = getUserKey(var0, "findFriendsLegalBarShown");
      Boolean var2 = new Boolean((boolean)1);
      KeyValueManager.setValue(var0, var1, var2);
   }

   public static boolean shouldShowLegalBar(Context var0) {
      Boolean var1 = Gatekeeper.get(var0, "android_ci_legal_bar");
      boolean var2;
      if(var1 != null && !var1.booleanValue()) {
         String var3 = getUserKey(var0, "findFriendsLegalBarShown");
         if(!KeyValueManager.getBooleanValue(var0, var3)) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = true;
      }

      return var2;
   }

   public static boolean shouldShowLegalScreen(Context var0) {
      boolean var1;
      if(kddiImporterEnabled(var0)) {
         var1 = true;
      } else {
         Boolean var2 = Gatekeeper.get(var0, "android_ci_legal_screen");
         if(var2 != null && !var2.booleanValue()) {
            var1 = false;
         } else {
            String var3 = getUserKey(var0, "findFriendsConsentApproved");
            if(!KeyValueManager.getBooleanValue(var0, var3)) {
               var1 = true;
            } else {
               var1 = false;
            }
         }
      }

      return var1;
   }

   public static boolean showFindFriendsDialog(Context var0) {
      Boolean var1 = Gatekeeper.get(var0, "android_ci_alert_enabled");
      boolean var2;
      if(var1 != null && var1.booleanValue()) {
         String var3 = getUserKey(var0, "findFriendsDialogShown");
         if(!KeyValueManager.getBooleanValue(var0, var3)) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean showPhoneNumberDialog(Context var0) {
      String var1 = getUserKey(var0, "phoneNumberDialogShown");
      boolean var2;
      if(!KeyValueManager.getBooleanValue(var0, var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static void stopFindFriendsDialog(Context var0) {
      String var1 = getUserKey(var0, "findFriendsDialogShown");
      Boolean var2 = Boolean.valueOf((boolean)1);
      KeyValueManager.setValue(var0, var1, var2);
   }

   public static void stopPhoneNumberDialog(Context var0) {
      String var1 = getUserKey(var0, "phoneNumberDialogShown");
      Boolean var2 = Boolean.valueOf((boolean)1);
      KeyValueManager.setValue(var0, var1, var2);
   }
}
