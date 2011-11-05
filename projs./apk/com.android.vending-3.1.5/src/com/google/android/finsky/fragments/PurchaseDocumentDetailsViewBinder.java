package com.google.android.finsky.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.AppSecurityPermissions;
import com.google.android.finsky.layout.CustomRadioGroup;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PurchaseDocumentDetailsViewBinder {

   private static final int IAB_DESCRIPTION_TAB_ID = 2;
   private static final String KEY_DEFAULT_TAB = "default_tab";
   private static final int MOVIES_RENTAL_TERMS_ID = 3;
   private static final int PERMISSIONS_TAB_ID = 0;
   private static final int TERMS_CONDITIONS_TAB_ID = 1;
   private AppSecurityPermissions mAppSecurityPermissions;
   private CustomRadioGroup mButtonStrip;
   private Context mContext;
   private int mDefaultTab = -1;
   private View mLayout;
   private PackageManager mPackageManager;
   private Bundle mSavedState;
   private FrameLayout mTabContent;


   public PurchaseDocumentDetailsViewBinder() {}

   private void initTab(LayoutInflater var1, int var2, String var3, View var4, int var5) {
      CustomRadioGroup var6 = this.mButtonStrip;
      RadioButton var7 = (RadioButton)var1.inflate(2130968607, var6, (boolean)0);
      var7.setId(var2);
      String var8 = var3.toUpperCase();
      var7.setText(var8);
      int var9 = CorpusMetadata.getBackendForegroundColor(this.mContext, var5);
      var7.setTextColor(var9);
      this.mButtonStrip.addView(var7);
      byte var10;
      if(this.mContext.getResources().getBoolean(2131296257)) {
         var10 = 4;
      } else {
         var10 = 8;
      }

      var4.setVisibility(var10);
      this.mTabContent.addView(var4);
      PurchaseDocumentDetailsViewBinder.1 var11 = new PurchaseDocumentDetailsViewBinder.1(var4, var10, var7);
      var7.setOnCheckedChangeListener(var11);
   }

   private void requestContentLayout() {
      Looper var1 = Looper.getMainLooper();
      Handler var2 = new Handler(var1);
      PurchaseDocumentDetailsViewBinder.2 var3 = new PurchaseDocumentDetailsViewBinder.2();
      var2.post(var3);
   }

   public void bind(FragmentManager var1, ViewGroup var2, Document var3, int var4, List<String> var5, boolean var6) {
      LayoutInflater var7 = LayoutInflater.from(this.mContext);
      int var8 = var3.getBackend();
      this.mLayout = var2;
      CustomRadioGroup var10 = (CustomRadioGroup)var2.findViewById(2131755285);
      this.mButtonStrip = var10;
      FrameLayout var11 = (FrameLayout)var2.findViewById(2131755286);
      this.mTabContent = var11;
      ((HorizontalScrollView)var2.findViewById(2131755284)).setHorizontalScrollBarEnabled((boolean)0);
      int var12 = this.mDefaultTab;
      if(this.mButtonStrip.getChildCount() > 0) {
         var12 = this.mButtonStrip.getCheckedRadioButtonId();
      }

      this.mButtonStrip.clearCheck();
      this.mButtonStrip.removeAllViews();
      CustomRadioGroup var13 = this.mButtonStrip;
      int var14 = var3.getBackend();
      var13.setBackendId(var14);
      this.mTabContent.removeAllViews();
      List var15 = var3.getAppPermissionsList();
      if(var8 == 3 && var6) {
         CharSequence var16 = var3.getDescription();
         TextView var17 = (TextView)var7.inflate(2130968693, (ViewGroup)null);
         var17.setText(var16);
         String var19 = FinskyApp.get().getString(2131231014);
         this.initTab(var7, 2, var19, var17, var8);
         if(var12 < 0) {
            var12 = 2;
         }
      } else {
         Iterator var31;
         if(var15 != null) {
            ArrayList var30 = Lists.newArrayList();
            var31 = var15.iterator();

            while(var31.hasNext()) {
               String var32 = (String)var31.next();

               try {
                  PackageManager var33 = this.mPackageManager;
                  PermissionInfo var35 = var33.getPermissionInfo(var32, 0);
                  boolean var38 = var30.add(var35);
               } catch (NameNotFoundException var74) {
                  ;
               }
            }

            AppSecurityPermissions var40 = (AppSecurityPermissions)var7.inflate(2130968679, (ViewGroup)null);
            this.mAppSecurityPermissions = var40;
            AppSecurityPermissions var41 = this.mAppSecurityPermissions;
            String var42 = var3.getAppDetails().getPackageName();
            Bundle var43 = this.mSavedState;
            var41.bindInfo(var1, var42, var30, var43);
            String var46 = FinskyApp.get().getString(2131231012);
            AppSecurityPermissions var47 = this.mAppSecurityPermissions;
            this.initTab(var7, 0, var46, var47, var8);
            if(var12 < 0) {
               var12 = 0;
            }
         } else if(var8 == 4) {
            ViewGroup var51 = (ViewGroup)var7.inflate(2130968691, (ViewGroup)null);
            var31 = var3.getMovieRentalTerms().iterator();
            if(var31.hasNext()) {
               DeviceDoc.VideoRentalTerm var52 = (DeviceDoc.VideoRentalTerm)var31.next();
               int var53 = var52.getOfferType();
               if(var53 == var4) {
                  String var55 = var52.getRentalHeader();
                  var31 = var52.getTermList().iterator();

                  while(var31.hasNext()) {
                     DeviceDoc.VideoRentalTerm.Term var56 = (DeviceDoc.VideoRentalTerm.Term)var31.next();
                     ViewGroup var57 = (ViewGroup)var7.inflate(2130968656, var51, (boolean)0);
                     TextView var58 = (TextView)var57.findViewById(2131755140);
                     Spanned var59 = Html.fromHtml(var56.getHeader());
                     var58.setText(var59);
                     MovementMethod var60 = LinkMovementMethod.getInstance();
                     var58.setMovementMethod(var60);
                     TextView var61 = (TextView)var57.findViewById(2131755211);
                     Spanned var62 = Html.fromHtml(var56.getBody());
                     var61.setText(var62);
                     MovementMethod var63 = LinkMovementMethod.getInstance();
                     var61.setMovementMethod(var63);
                     var51.addView(var57);
                  }

                  this.initTab(var7, 3, var55, var51, var8);
                  if(var12 < 0) {
                     var12 = 3;
                  }
               }
            }
         }
      }

      if(var5 != null && var5.size() > 0) {
         ViewGroup var20 = (ViewGroup)var7.inflate(2130968691, (ViewGroup)null);
         Iterator var21 = var5.iterator();

         while(var21.hasNext()) {
            String var22 = (String)var21.next();
            ViewGroup var24 = (ViewGroup)var7.inflate(2130968656, var20, (boolean)0);
            TextView var25 = (TextView)var24.findViewById(2131755140);
            Spanned var26 = Html.fromHtml(var22);
            var25.setText(var26);
            MovementMethod var27 = LinkMovementMethod.getInstance();
            var25.setMovementMethod(var27);
            ((TextView)var24.findViewById(2131755211)).setVisibility(8);
            var20.addView(var24);
         }

         String var68 = FinskyApp.get().getString(2131231013);
         this.initTab(var7, 1, var68, var20, var8);
         if(var12 < 0) {
            var12 = 1;
         }
      }

      if(this.mButtonStrip.getChildCount() == 0) {
         this.mButtonStrip.setVisibility(8);
      } else {
         this.mButtonStrip.setVisibility(0);
         CustomRadioGroup var72 = this.mButtonStrip;
         var72.check(var12);
      }
   }

   public void init(Context var1, PackageManager var2, Bundle var3) {
      this.mContext = var1;
      this.mPackageManager = var2;
      this.mSavedState = var3;
      if(var3 != null) {
         if(var3.containsKey("default_tab")) {
            int var4 = var3.getInt("default_tab", -1);
            this.mDefaultTab = var4;
         }
      }
   }

   public void onDestroyView() {}

   public void saveState(Bundle var1) {
      if(this.mButtonStrip != null) {
         int var2 = this.mButtonStrip.getCheckedRadioButtonId();
         var1.putInt("default_tab", var2);
      }

      if(this.mAppSecurityPermissions != null) {
         this.mAppSecurityPermissions.saveInstanceState(var1);
      }
   }

   class 1 implements OnCheckedChangeListener {

      // $FF: synthetic field
      final View val$content;
      // $FF: synthetic field
      final RadioButton val$radioButton;
      // $FF: synthetic field
      final int val$visibility;


      1(View var2, int var3, RadioButton var4) {
         this.val$content = var2;
         this.val$visibility = var3;
         this.val$radioButton = var4;
      }

      public void onCheckedChanged(CompoundButton var1, boolean var2) {
         View var3 = this.val$content;
         int var4;
         if(var2) {
            var4 = 0;
         } else {
            var4 = this.val$visibility;
         }

         var3.setVisibility(var4);
         if(var2) {
            ViewGroup var5 = (ViewGroup)PurchaseDocumentDetailsViewBinder.this.mButtonStrip.getParent();
            int var6 = this.val$radioButton.getLeft();
            var5.scrollTo(var6, 0);
         }

         PurchaseDocumentDetailsViewBinder.this.requestContentLayout();
      }
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         PurchaseDocumentDetailsViewBinder.this.mLayout.requestLayout();
         PurchaseDocumentDetailsViewBinder.this.mButtonStrip.invalidate();
      }
   }
}
