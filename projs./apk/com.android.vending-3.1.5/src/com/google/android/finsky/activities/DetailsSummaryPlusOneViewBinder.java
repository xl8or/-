package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.PlusOne;
import java.text.DecimalFormat;

public class DetailsSummaryPlusOneViewBinder implements Response.Listener<PlusOne.PlusOneResponse>, Response.ErrorListener {

   private static final DecimalFormat sAllIntegerDigitFormatter = new DecimalFormat("#");
   private static final DecimalFormat sSingleFractionDigitFormatter = new DecimalFormat("@#");
   private Context mContext;
   private String mCookie;
   private DfeApi mDfeApi;
   private Document mDoc;
   private boolean mIsWaitingServerResponse;
   private DeviceDoc.PlusOneData mLastPlusOne;
   private View mLayout;
   private String mUrl;
   private DetailsSummaryPlusOneViewBinder.PlusOneButtonState mUserState;


   public DetailsSummaryPlusOneViewBinder() {}

   private void bindPlusOneButton() {
      DetailsSummaryPlusOneViewBinder.PlusOneButtonState var1;
      if(this.mLastPlusOne.getSetByUser()) {
         var1 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.On;
      } else {
         var1 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.Off;
      }

      this.mUserState = var1;
      this.syncButtonState();
      View var2 = this.mLayout.findViewById(2131755183);
      DetailsSummaryPlusOneViewBinder.1 var3 = new DetailsSummaryPlusOneViewBinder.1();
      var2.setOnClickListener(var3);
   }

   private void bindPlusOneLegend() {
      TextView var1 = (TextView)this.mLayout.findViewById(2131755186);
      DetailsSummaryPlusOneViewBinder.PlusOneButtonState var2 = this.mUserState;
      DetailsSummaryPlusOneViewBinder.PlusOneButtonState var3 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.Error;
      if(var2 == var3) {
         var1.setText(2131231145);
      } else {
         DetailsSummaryPlusOneViewBinder.PlusOneButtonState var4 = this.mUserState;
         DetailsSummaryPlusOneViewBinder.PlusOneButtonState var5 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.On;
         boolean var6;
         if(var4 == var5) {
            var6 = true;
         } else {
            var6 = false;
         }

         long var7 = this.mLastPlusOne.getTotal();
         if(var7 == 0L && !var6) {
            var1.setText(2131231146);
         } else if(var7 == 1L && var6) {
            var1.setText(2131231147);
         } else {
            int var9 = this.mLastPlusOne.getCirclesPeopleCount();
            if(var9 == 0) {
               String var10 = formatPlusOneCount(this.mContext, var7, 2131231153);
               Resources var11 = this.mContext.getResources();
               int var12 = (int)var7;
               Object[] var13 = new Object[]{var10};
               String var14 = var11.getQuantityString(2131558402, var12, var13);
               var1.setText(var14);
            } else {
               String var15 = ((DeviceDoc.PlusPerson)this.mLastPlusOne.getCirclesPeopleList().get(0)).getDisplayName();
               if(var9 == 1 && !var6) {
                  Resources var16 = this.mContext.getResources();
                  Object[] var17 = new Object[]{var15};
                  String var18 = var16.getString(2131231148, var17);
                  var1.setText(var18);
               } else if(var9 == 1 && var6) {
                  Resources var19 = this.mContext.getResources();
                  Object[] var20 = new Object[]{var15};
                  String var21 = var19.getString(2131231149, var20);
                  var1.setText(var21);
               } else {
                  int var22 = var9 + -1;
                  Context var23 = this.mContext;
                  long var24 = (long)var22;
                  String var26 = formatPlusOneCount(var23, var24, 2131231153);
                  Resources var27 = this.mContext.getResources();
                  Object[] var28 = new Object[]{var15, var26};
                  String var29 = var27.getQuantityString(2131558403, var22, var28);
                  var1.setText(var29);
               }
            }
         }
      }
   }

   public static String formatPlusOneCount(Context var0, long var1, int var3) {
      String var7;
      if(var1 < 1000L) {
         Resources var4 = var0.getResources();
         Object[] var5 = new Object[2];
         Long var6 = Long.valueOf(var1);
         var5[0] = var6;
         var5[1] = "";
         var7 = var4.getString(var3, var5);
      } else {
         float var8 = (float)var1;
         int var9 = 2131231151;
         float var10 = var8 / 1000.0F;
         if(var10 > 1000.0F) {
            var9 = 2131231152;
            var10 /= 1000.0F;
         }

         String var14;
         if(var10 < 10.0F) {
            DecimalFormat var11 = sSingleFractionDigitFormatter;
            double var12 = (double)var10;
            var14 = var11.format(var12);
         } else {
            DecimalFormat var18 = sAllIntegerDigitFormatter;
            double var19 = (double)var10;
            var14 = var18.format(var19);
         }

         Resources var15 = var0.getResources();
         Object[] var16 = new Object[]{var14, null};
         String var17 = var0.getString(var9);
         var16[1] = var17;
         var7 = var15.getString(var3, var16);
      }

      return var7;
   }

   private String getAnalyticsString() {
      StringBuilder var1 = (new StringBuilder()).append("plusOne?doc=");
      String var2 = this.mDoc.getDocId();
      return var1.append(var2).toString();
   }

   private void handleClick() {
      Analytics var1 = FinskyApp.get().getAnalytics();
      String var2 = this.mUrl;
      String var3 = this.mCookie;
      String var4 = this.getAnalyticsString();
      var1.logPageView(var2, var3, var4);
      byte var5;
      if(!this.mLastPlusOne.getSetByUser()) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      if(this.mLastPlusOne != null) {
         if(this.mLastPlusOne.getSetByUser()) {
            DeviceDoc.PlusOneData var6 = this.mLastPlusOne;
            long var7 = this.mLastPlusOne.getTotal() - 1L;
            var6.setTotal(var7);
            DeviceDoc.PlusOneData var10 = this.mLastPlusOne.setSetByUser((boolean)0);
         } else {
            DeviceDoc.PlusOneData var15 = this.mLastPlusOne;
            long var16 = this.mLastPlusOne.getTotal() + 1L;
            var15.setTotal(var16);
            DeviceDoc.PlusOneData var19 = this.mLastPlusOne.setSetByUser((boolean)1);
         }
      }

      this.setLoading((boolean)1);
      DetailsSummaryPlusOneViewBinder.PlusOneButtonState var11;
      if(this.mLastPlusOne.getSetByUser()) {
         var11 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.On;
      } else {
         var11 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.Off;
      }

      this.mUserState = var11;
      this.syncButtonState();
      DfeApi var12 = this.mDfeApi;
      String var13 = this.mDoc.getDocId();
      var12.setPlusOne(var13, (boolean)var5, this, this);
   }

   private void setLoading(boolean var1) {
      this.mIsWaitingServerResponse = var1;
      View var2 = this.mLayout.findViewById(2131755185);
      byte var3;
      if(this.mIsWaitingServerResponse) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      var2.setVisibility(var3);
   }

   private void syncButtonState() {
      ImageView var1 = (ImageView)this.mLayout.findViewById(2131755184);
      int[] var2 = DetailsSummaryPlusOneViewBinder.2.$SwitchMap$com$google$android$finsky$activities$DetailsSummaryPlusOneViewBinder$PlusOneButtonState;
      int var3 = this.mUserState.ordinal();
      int var4;
      switch(var2[var3]) {
      case 1:
         if(this.mIsWaitingServerResponse) {
            var4 = 2130837599;
         } else {
            var4 = 2130837598;
         }

         var1.setImageResource(var4);
         return;
      case 2:
         if(this.mIsWaitingServerResponse) {
            var4 = 2130837597;
         } else {
            var4 = 2130837596;
         }

         var1.setImageResource(var4);
         return;
      case 3:
         var1.setImageResource(2130837595);
         return;
      default:
      }
   }

   private void syncContentDescription() {
      View var1 = this.mLayout.findViewById(2131755183);
      CharSequence var2 = ((TextView)this.mLayout.findViewById(2131755186)).getText();
      var1.setContentDescription(var2);
   }

   public void bind(View var1, Document var2) {
      this.mLayout = var1;
      this.mDoc = var2;
      if(!this.mDoc.hasPlusOneData()) {
         this.mLayout.setVisibility(8);
      } else {
         this.mLayout.setVisibility(0);
         DeviceDoc.PlusOneData var3 = this.mDoc.getPlusOneData();
         this.mLastPlusOne = var3;
         this.rebindViews();
      }
   }

   public void init(Context var1, DfeApi var2, String var3, String var4) {
      this.mContext = var1;
      this.mDfeApi = var2;
      this.mUrl = var3;
      this.mCookie = var4;
   }

   public void onDestroyView() {
      this.mLayout = null;
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      if(this.mLayout != null) {
         this.setLoading((boolean)0);
         DetailsSummaryPlusOneViewBinder.PlusOneButtonState var4 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.Error;
         this.mUserState = var4;
         this.syncButtonState();
         this.bindPlusOneLegend();
         this.syncContentDescription();
      }

      DfeApi var5 = this.mDfeApi;
      String var6 = this.mUrl;
      var5.invalidateDetailsCache(var6, (boolean)1);
   }

   public void onResponse(PlusOne.PlusOneResponse var1) {
      if(this.mLayout != null) {
         this.setLoading((boolean)0);
         this.rebindViews();
      }

      DfeApi var2 = this.mDfeApi;
      String var3 = this.mUrl;
      var2.invalidateDetailsCache(var3, (boolean)1);
   }

   public void rebindViews() {
      if(this.mLastPlusOne != null) {
         this.bindPlusOneButton();
         this.bindPlusOneLegend();
         this.syncContentDescription();
      }
   }

   private static enum PlusOneButtonState {

      // $FF: synthetic field
      private static final DetailsSummaryPlusOneViewBinder.PlusOneButtonState[] $VALUES;
      Error("Error", 2),
      Off("Off", 1),
      On("On", 0);


      static {
         DetailsSummaryPlusOneViewBinder.PlusOneButtonState[] var0 = new DetailsSummaryPlusOneViewBinder.PlusOneButtonState[3];
         DetailsSummaryPlusOneViewBinder.PlusOneButtonState var1 = On;
         var0[0] = var1;
         DetailsSummaryPlusOneViewBinder.PlusOneButtonState var2 = Off;
         var0[1] = var2;
         DetailsSummaryPlusOneViewBinder.PlusOneButtonState var3 = Error;
         var0[2] = var3;
         $VALUES = var0;
      }

      private PlusOneButtonState(String var1, int var2) {}
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         DetailsSummaryPlusOneViewBinder.this.handleClick();
      }
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$activities$DetailsSummaryPlusOneViewBinder$PlusOneButtonState = new int[DetailsSummaryPlusOneViewBinder.PlusOneButtonState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$activities$DetailsSummaryPlusOneViewBinder$PlusOneButtonState;
            int var1 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.On.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$activities$DetailsSummaryPlusOneViewBinder$PlusOneButtonState;
            int var3 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.Off.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$activities$DetailsSummaryPlusOneViewBinder$PlusOneButtonState;
            int var5 = DetailsSummaryPlusOneViewBinder.PlusOneButtonState.Error.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }
}
