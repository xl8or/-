package com.sonyericsson.email.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sonyericsson.email.utils.customization.AccountData;
import com.sonyericsson.email.utils.customization.Customization;
import com.sonyericsson.email.utils.customization.CustomizationFactory;

public class BrandedAccountsListAdapter extends BaseAdapter {

   private Bitmap[] mBitmaps;
   private Context mContext;
   private String[] mDomains;
   private int mEmailProvidersCount = 0;
   private LayoutInflater mInflater;
   private String[] mLabels;


   public BrandedAccountsListAdapter(Context var1) {
      this.mContext = var1;
      LayoutInflater var2 = LayoutInflater.from(var1);
      this.mInflater = var2;
      this.loadAccountsSettings();
   }

   private void loadAccountsSettings() {
      CustomizationFactory var1 = CustomizationFactory.getInstance();
      Context var2 = this.mContext;
      Customization var3 = var1.getCustomization(var2);
      Context var4 = this.mContext;
      AccountData[] var5 = var3.getBrandedAccountsData(var4);
      Context var6 = this.mContext;
      AccountData var7 = var3.getPreconfAccountData(var6);
      int var8;
      if(var5 != null) {
         var8 = var5.length;
      } else {
         var8 = 0;
      }

      byte var9;
      if(var7 != null) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      int var10 = var8 + var9;
      this.mEmailProvidersCount = var10;
      AccountData[] var11 = new AccountData[this.mEmailProvidersCount];
      if(var5 != null) {
         int var12 = 0;

         while(true) {
            int var13 = var5.length;
            if(var12 >= var13) {
               break;
            }

            int var14;
            if(var9 > 0) {
               var14 = var12 + 1;
            } else {
               var14 = var12;
            }

            AccountData var15 = var5[var12];
            var11[var14] = var15;
            ++var12;
         }
      }

      if(var9 > 0) {
         Context var16 = this.mContext;
         AccountData var17 = var3.getPreconfAccountData(var16);
         var11[0] = var17;
      }

      String[] var18 = new String[this.mEmailProvidersCount];
      this.mDomains = var18;
      Uri[] var19 = new Uri[this.mEmailProvidersCount];
      String[] var20 = new String[this.mEmailProvidersCount];
      this.mLabels = var20;
      int var21 = 0;

      while(true) {
         int var22 = this.mEmailProvidersCount;
         if(var21 >= var22) {
            Bitmap[] var28 = new Bitmap[this.mEmailProvidersCount];
            this.mBitmaps = var28;
            int var29 = 0;

            while(true) {
               int var30 = this.mEmailProvidersCount;
               if(var29 >= var30) {
                  return;
               }

               if(var19[var29] != false) {
                  Bitmap[] var31 = this.mBitmaps;
                  Uri var32 = var19[var29];
                  Bitmap var33 = this.loadBitmap(var32);
                  var31[var29] = var33;
               } else {
                  this.mBitmaps[var29] = false;
               }

               ++var29;
            }
         }

         String[] var23 = this.mDomains;
         String var24 = var11[var21].getDomain();
         var23[var21] = var24;
         Uri var25 = var11[var21].getBrandedIconUri();
         var19[var21] = var25;
         String[] var26 = this.mLabels;
         String var27 = var11[var21].getBrandedLabel();
         var26[var21] = var27;
         ++var21;
      }
   }

   private Bitmap loadBitmap(Uri param1) {
      // $FF: Couldn't be decompiled
   }

   public Bitmap getBitmap(int var1) {
      int var2 = this.mEmailProvidersCount;
      Bitmap var3;
      if(var1 >= var2) {
         var3 = null;
      } else if(this.mBitmaps == null) {
         var3 = null;
      } else {
         var3 = this.mBitmaps[var1];
      }

      return var3;
   }

   public int getCount() {
      return this.mEmailProvidersCount + 2;
   }

   public String getDomain(int var1) {
      int var2 = this.mEmailProvidersCount;
      String var3;
      if(var1 >= var2) {
         var3 = null;
      } else if(this.mDomains == null) {
         var3 = null;
      } else {
         StringBuilder var4 = (new StringBuilder()).append("@");
         String var5 = this.mDomains[var1];
         var3 = var4.append(var5).toString();
      }

      return var3;
   }

   public Object getItem(int var1) {
      return null;
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      View var4;
      if(var2 != null) {
         var4 = var2;
      } else {
         var4 = this.mInflater.inflate(2130903053, var3, (boolean)0);
      }

      TextView var5 = (TextView)var4.findViewById(2131558456);
      ImageView var6 = (ImageView)var4.findViewById(2131558455);
      var5.setVisibility(8);
      var6.setVisibility(8);
      int var7 = this.mEmailProvidersCount + 1;
      if(var1 == var7) {
         var5.setText(2131165186);
         var5.setVisibility(0);
      } else {
         int var8 = this.mEmailProvidersCount;
         if(var1 == var8) {
            Drawable var9 = this.mContext.getResources().getDrawable(2130837585);
            var6.setImageDrawable(var9);
            var6.setVisibility(0);
            var5.setText(2131165339);
            var5.setVisibility(0);
         } else if(this.mBitmaps[var1] != false) {
            Bitmap var10 = this.mBitmaps[var1];
            var6.setImageBitmap(var10);
            var6.setVisibility(0);
         } else {
            var5.setVisibility(0);
            String var11 = this.mLabels[var1];
            var5.setText(var11);
         }
      }

      return var4;
   }
}
