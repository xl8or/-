package com.facebook.katana.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.FqlGetPlaceById;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Toaster;

public final class ApplicationUtils {

   public ApplicationUtils() {}

   public static void OpenPageProfile(Context var0, long var1, FacebookProfile var3) {
      String var4 = "fb://page/" + var1;
      Intent var5 = IntentUriHandler.getIntentForUri(var0, var4);
      if(var3 != null) {
         String var6 = var3.mDisplayName;
         var5.putExtra("extra_user_display_name", var6);
         String var8 = var3.mImageUrl;
         var5.putExtra("extra_image_url", var8);
      }

      var0.startActivity(var5);
   }

   public static void OpenPlaceProfile(Context var0, long var1) {
      String var3 = var0.getString(2131361827);
      ProgressDialog var4 = ProgressDialog.show(var0, "", var3, (boolean)1);
      ApplicationUtils.1 var5 = new ApplicationUtils.1(var4, var0);
      FqlGetPlaceById.loadPlaceById(var0, var1, var5);
   }

   public static boolean OpenPlaceProfile(Context var0, FacebookPlace var1) {
      return OpenPlaceProfile(var0, var1, (Integer)null);
   }

   public static boolean OpenPlaceProfile(Context var0, FacebookPlace var1, Integer var2) {
      boolean var4;
      if(var1 != null) {
         FacebookPage var3 = var1.getPageInfo();
         if(var3 != null) {
            Object[] var5 = new Object[1];
            Long var6 = Long.valueOf(var3.mPageId);
            var5[0] = var6;
            String var7 = String.format("fb://place/fw?pid=%d", var5);
            Intent var8 = IntentUriHandler.getIntentForUri(var0, var7);
            var8.putExtra("extra_place", var1);
            if(var2 != null) {
               int var10 = var2.intValue();
               var8.setFlags(var10);
            }

            var0.startActivity(var8);
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public static void OpenProfile(Context var0, int var1, long var2, FacebookProfile var4) {
      switch(var1) {
      case 0:
         OpenUserProfile(var0, var2, var4);
         return;
      case 1:
         OpenPageProfile(var0, var2, var4);
         return;
      case 2:
         OpenPlaceProfile(var0, var2);
         return;
      default:
      }
   }

   public static void OpenSearch(Context var0) {
      boolean var1 = IntentUriHandler.handleUri(var0, "fb://friends");
   }

   public static void OpenUserProfile(Context var0, long var1, FacebookProfile var3) {
      String var4 = "fb://profile/" + var1;
      Intent var5 = IntentUriHandler.getIntentForUri(var0, var4);
      if(var3 != null) {
         String var6 = var3.mDisplayName;
         var5.putExtra("extra_user_display_name", var6);
         String var8 = var3.mImageUrl;
         var5.putExtra("extra_image_url", var8);
      }

      var0.startActivity(var5);
   }

   public static void startDefaultActivity(Context var0, Intent var1) {
      Intent var2 = IntentUriHandler.getIntentForUri(var0, "fb://root");
      Intent var3 = var2.addFlags(65536);
      var0.startActivity(var2);
      Intent var4 = var1;
      if(var1 == null) {
         var4 = IntentUriHandler.getIntentForUri(var0, "fb://feed");
      }

      var0.startActivity(var4);
   }

   static class 1 implements AppSessionListener.GetObjectListener<FacebookPlace> {

      // $FF: synthetic field
      final Context val$ctx;
      // $FF: synthetic field
      final ProgressDialog val$dlg;


      1(ProgressDialog var1, Context var2) {
         this.val$dlg = var1;
         this.val$ctx = var2;
      }

      public void onLoadError(Exception var1) {
         Object[] var2 = new Object[1];
         String var3 = var1.getMessage();
         var2[0] = var3;
         String var4 = String.format("Exception when loading place: %s", var2);
         Log.e("FacebookPlaces", var4);
         this.val$dlg.dismiss();
         Toaster.toast(this.val$ctx, 2131362228);
      }

      public void onObjectLoaded(FacebookPlace var1) {
         this.val$dlg.dismiss();
         ApplicationUtils.OpenPlaceProfile(this.val$ctx, var1);
      }
   }
}
