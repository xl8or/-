package com.google.android.finsky.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.widget.RemoteViews;
import com.android.vending.MarketWidgetProvider;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.ThumbnailUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class FinskyWidgetProvider extends AppWidgetProvider {

   private static String sAccountName = null;
   private static FinskyWidgetProvider.WidgetModel sWidgetInfo = new FinskyWidgetProvider.WidgetModel((FinskyWidgetProvider.1)null);


   public FinskyWidgetProvider() {}

   private void clearList(Context var1, AppWidgetManager var2, int[] var3) {
      sWidgetInfo.reset();
      this.rebindWidgets(var1, var2, var3);
   }

   private static PendingIntent getDocumentViewIntent(Context var0, String var1) {
      Intent var2 = new Intent("android.intent.action.VIEW");
      String var3 = var0.getPackageName();
      var2.setPackage(var3);
      Builder var5 = new Builder();
      Builder var6 = var5.scheme("http");
      Builder var7 = var5.authority("market.android.com");
      Builder var8 = var5.appendEncodedPath("details");
      var5.appendQueryParameter("id", var1);
      Uri var10 = var5.build();
      var2.setData(var10);
      return PendingIntent.getActivity(var0, 0, var2, 134217728);
   }

   private static PendingIntent getLaunchMarketIntent(Context var0) {
      Intent var1 = new Intent(var0, MainActivity.class);
      return PendingIntent.getActivity(var0, 0, var1, 134217728);
   }

   private static boolean isSameAccount(DfeApi var0) {
      String var1 = var0.getCurrentAccountName();
      boolean var3;
      if(sAccountName != null && var1 != null) {
         String var2 = sAccountName;
         if(var1.equals(var2)) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   private RemoteViews makePromoItemView(Context var1, FinskyWidgetProvider.WidgetModel.PromotionalItem var2) {
      String var3 = var1.getPackageName();
      RemoteViews var4 = new RemoteViews(var3, 2130968718);
      String var5 = var2.title;
      var4.setTextViewText(2131755326, var5);
      String var6 = var2.developer;
      var4.setTextViewText(2131755327, var6);
      Bitmap var7 = var2.image;
      var4.setImageViewBitmap(2131755324, var7);
      String var8 = var2.docId;
      PendingIntent var9 = getDocumentViewIntent(var1, var8);
      var4.setOnClickPendingIntent(2131755323, var9);
      return var4;
   }

   private void rebindWidgets(Context var1, AppWidgetManager var2, int[] var3) {
      int[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = var4[var6];
         String var8 = var1.getPackageName();
         RemoteViews var9 = new RemoteViews(var8, 2130968719);
         if(sWidgetInfo.getItems().size() == 0) {
            this.showEmptyState(var1, var9);
         } else {
            var9.removeAllViews(2131755329);
            var9.setViewVisibility(2131755328, 8);
            var9.setViewVisibility(2131755329, 0);
            Iterator var10 = sWidgetInfo.getItems().iterator();

            while(var10.hasNext()) {
               FinskyWidgetProvider.WidgetModel.PromotionalItem var11 = (FinskyWidgetProvider.WidgetModel.PromotionalItem)var10.next();
               RemoteViews var12 = this.makePromoItemView(var1, var11);
               var9.addView(2131755329, var12);
            }
         }

         var2.updateAppWidget(var7, var9);
      }

   }

   private void refreshList(Context var1, AppWidgetManager var2, int[] var3) {
      DfeApi var4 = FinskyApp.get().getDfeApi();
      FinskyWidgetProvider.WidgetModel var5 = sWidgetInfo;
      FinskyWidgetProvider.1 var6 = new FinskyWidgetProvider.1(var1, var2, var3);
      var5.refresh(var1, var4, var6);
   }

   private void showEmptyState(Context var1, RemoteViews var2) {
      var2.removeAllViews(2131755329);
      var2.setViewVisibility(2131755328, 0);
      var2.setViewVisibility(2131755329, 8);
      PendingIntent var3 = getLaunchMarketIntent(var1);
      var2.setOnClickPendingIntent(2131755328, var3);
   }

   private void showError(Context var1, AppWidgetManager var2, int[] var3) {
      int[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = var4[var6];
         String var8 = var1.getPackageName();
         RemoteViews var9 = new RemoteViews(var8, 2130968719);
         this.showEmptyState(var1, var9);
         var2.updateAppWidget(var7, var9);
      }

   }

   public void onReceive(Context var1, Intent var2) {
      super.onReceive(var1, var2);
      String var3 = var2.getAction();
      AppWidgetManager var4 = AppWidgetManager.getInstance(var1);
      ComponentName var5 = new ComponentName(var1, MarketWidgetProvider.class);
      int[] var6 = var4.getAppWidgetIds(var5);
      DfeApi var7 = FinskyApp.get().getDfeApi();
      if(var6.length == 0) {
         sAccountName = null;
         this.clearList(var1, var4, var6);
      } else if(var7 == null) {
         sAccountName = null;
         this.clearList(var1, var4, var6);
         boolean var8 = AuthenticatedActivity.setupAccountFromPreferences(var1);
      } else if("com.google.android.finsky.action.DFE_API_CONTEXT_CHANGED".equals(var3) && !isSameAccount(var7) || "android.appwidget.action.APPWIDGET_UPDATE".equals(var3)) {
         sAccountName = var7.getCurrentAccountName();
         this.clearList(var1, var4, var6);
         if(sAccountName != null) {
            this.refreshList(var1, var4, var6);
         }
      }
   }

   class 1 implements FinskyWidgetProvider.WidgetModel.RefreshListener {

      // $FF: synthetic field
      final int[] val$appWidgetIds;
      // $FF: synthetic field
      final AppWidgetManager val$appWidgetManager;
      // $FF: synthetic field
      final Context val$context;


      1(Context var2, AppWidgetManager var3, int[] var4) {
         this.val$context = var2;
         this.val$appWidgetManager = var3;
         this.val$appWidgetIds = var4;
      }

      public void onData() {
         FinskyWidgetProvider var1 = FinskyWidgetProvider.this;
         Context var2 = this.val$context;
         AppWidgetManager var3 = this.val$appWidgetManager;
         int[] var4 = this.val$appWidgetIds;
         var1.rebindWidgets(var2, var3, var4);
      }

      public void onError(String var1) {
         Object[] var2 = new Object[]{var1};
         FinskyLog.e("Failed to load list for widget! %s", var2);
         FinskyWidgetProvider var3 = FinskyWidgetProvider.this;
         Context var4 = this.val$context;
         AppWidgetManager var5 = this.val$appWidgetManager;
         int[] var6 = this.val$appWidgetIds;
         var3.showError(var4, var5, var6);
      }
   }

   private static class WidgetModel implements OnDataChangedListener, Response.ErrorListener {

      private static final int MAX_ITEMS = 10;
      private final List<FinskyWidgetProvider.WidgetModel.PromotionalItem> mItems;
      private DfeList mList;
      private FinskyWidgetProvider.WidgetModel.RefreshListener mListener;
      private int mLoadedImagesSoFar;
      private int mMaxHeight;
      private int mMaxWidth;
      private int mSize;
      private boolean mUpdatePending;


      private WidgetModel() {
         ArrayList var1 = Lists.newArrayList();
         this.mItems = var1;
         this.mLoadedImagesSoFar = 0;
      }

      // $FF: synthetic method
      WidgetModel(FinskyWidgetProvider.1 var1) {
         this();
      }

      private void bitmapLoaded(Document var1, BitmapLoader.BitmapContainer var2) {
         int var3 = this.mLoadedImagesSoFar + 1;
         this.mLoadedImagesSoFar = var3;
         if(var2.getBitmap() != null) {
            FinskyWidgetProvider.WidgetModel.PromotionalItem var4 = new FinskyWidgetProvider.WidgetModel.PromotionalItem((FinskyWidgetProvider.1)null);
            String var5 = var1.getCreator();
            var4.developer = var5;
            String var6 = var1.getTitle();
            var4.title = var6;
            Bitmap var7 = var2.getBitmap();
            var4.image = var7;
            String var8 = var1.getDocId();
            var4.docId = var8;
            this.mItems.add(var4);
         }

         int var10 = this.mLoadedImagesSoFar;
         int var11 = this.mSize;
         if(var10 == var11) {
            this.mListener.onData();
         }
      }

      public Collection<FinskyWidgetProvider.WidgetModel.PromotionalItem> getItems() {
         return this.mItems;
      }

      public void onDataChanged() {
         this.mItems.clear();
         this.mUpdatePending = (boolean)0;
         this.mLoadedImagesSoFar = 0;
         int var1 = Math.min(this.mList.getCount(), 10);
         this.mSize = var1;
         int var2 = 0;

         while(true) {
            int var3 = this.mSize;
            if(var2 >= var3) {
               return;
            }

            Document var4 = (Document)this.mList.getItem(var2);
            int var5 = this.mMaxWidth;
            String var6 = ThumbnailUtils.getPromoBitmapUrlFromDocument(var4, var5);
            BitmapLoader var7 = FinskyApp.get().getBitmapLoader();
            FinskyWidgetProvider.WidgetModel.1 var8 = new FinskyWidgetProvider.WidgetModel.1(var4);
            int var9 = this.mMaxWidth;
            int var10 = this.mMaxHeight;
            BitmapLoader.BitmapContainer var11 = var7.get(var6, (Bitmap)null, var8, var9, var10);
            if(var11.getBitmap() != null) {
               this.bitmapLoaded(var4, var11);
            }

            ++var2;
         }
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         this.mUpdatePending = (boolean)0;
         this.mListener.onError(var2);
      }

      public void refresh(Context var1, DfeApi var2, FinskyWidgetProvider.WidgetModel.RefreshListener var3) {
         if(!this.mUpdatePending) {
            this.mUpdatePending = (boolean)1;
            this.mListener = var3;
            if(this.mList != null) {
               this.mList.removeDataChangedListener(this);
               this.mList.removeErrorListener(this);
            }

            int var4 = var1.getResources().getDimensionPixelSize(2131427397);
            this.mMaxWidth = var4;
            int var5 = var1.getResources().getDimensionPixelSize(2131427398);
            this.mMaxHeight = var5;
            String var6 = (String)G.widgetUrl.get();
            DfeList var7 = new DfeList(var2, var6, (boolean)0);
            this.mList = var7;
            this.mList.addErrorListener(this);
            this.mList.addDataChangedListener(this);
            this.mList.startLoadItems();
         }
      }

      public void reset() {
         this.mUpdatePending = (boolean)0;
         this.mItems.clear();
      }

      private class PromotionalItem {

         String developer;
         String docId;
         Bitmap image;
         String title;


         private PromotionalItem() {}

         // $FF: synthetic method
         PromotionalItem(FinskyWidgetProvider.1 var2) {
            this();
         }
      }

      public interface RefreshListener {

         void onData();

         void onError(String var1);
      }

      class 1 implements BitmapLoader.BitmapLoadedHandler {

         // $FF: synthetic field
         final Document val$document;


         1(Document var2) {
            this.val$document = var2;
         }

         public void onResponse(BitmapLoader.BitmapContainer var1) {
            FinskyWidgetProvider.WidgetModel var2 = WidgetModel.this;
            Document var3 = this.val$document;
            var2.bitmapLoaded(var3, var1);
         }
      }
   }
}
