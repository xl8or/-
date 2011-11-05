package com.android.settings.flipfont;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.settings.flipfont.TypefaceFinder;
import java.util.List;
import java.util.Vector;

public class FontListAdapter extends BaseAdapter {

   public static final String APPLE_MINT = "Apple mint";
   public static final String CHOCO = "Choco";
   public static final String CHOCO_COOKY = "Choco cooky";
   public static final String COOL = "Cool";
   public static final String COOL_JAZZ = "Cool jazz";
   static final String FONT_DIRECTORY = "fonts/";
   static final String FONT_DROID_SANS = "DroidSans.ttf";
   static final String FONT_EXTENSION = ".ttf";
   static final String FONT_PACKAGE = "com.monotype.android.font.";
   public static final String ROSE = "Rose";
   public static final String ROSE_MARY = "Rosemary";
   private static final String TAG = "FontListAdapter";
   public static final String TINKER_BELL = "Tinker bell";
   Context context = null;
   private android.graphics.Typeface droidSansFont;
   public AssetManager mFontAssetManager = null;
   public Vector mFontNames;
   public Vector mFontPackageNames;
   LayoutInflater mInflater = null;
   public List<ApplicationInfo> mInstalledApplications;
   public PackageManager mPackageManager = null;
   public Vector mTypefaceFiles;
   public TypefaceFinder mTypefaceFinder;
   private Vector mTypefaces;


   FontListAdapter(Context var1) {
      TypefaceFinder var2 = new TypefaceFinder();
      this.mTypefaceFinder = var2;
      Vector var3 = new Vector();
      this.mFontPackageNames = var3;
      Vector var4 = new Vector();
      this.mFontNames = var4;
      Vector var5 = new Vector();
      this.mTypefaceFiles = var5;
      Vector var6 = new Vector();
      this.mTypefaces = var6;
      this.droidSansFont = null;
      this.context = var1;
      this.mTypefaceFinder.context = var1;
      LayoutInflater var7 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var7;
      PackageManager var8 = var1.getPackageManager();
      this.mPackageManager = var8;

      try {
         List var9 = this.mPackageManager.getInstalledApplications(128);
         this.mInstalledApplications = var9;
         int var10 = 0;

         while(true) {
            int var11 = this.mInstalledApplications.size();
            if(var10 >= var11) {
               TypefaceFinder var17 = this.mTypefaceFinder;
               PackageManager var18 = this.mPackageManager;
               Vector var19 = this.mFontNames;
               Vector var20 = this.mTypefaceFiles;
               Vector var21 = this.mFontPackageNames;
               var17.getSansEntries(var18, var19, var20, var21);
               break;
            }

            String var12 = ((ApplicationInfo)this.mInstalledApplications.get(var10)).packageName;
            if(var12.startsWith("com.monotype.android.font.")) {
               AssetManager var13 = this.mPackageManager.getResourcesForApplication(var12).getAssets();
               this.mFontAssetManager = var13;
               TypefaceFinder var14 = this.mTypefaceFinder;
               AssetManager var15 = this.mFontAssetManager;
               var14.findTypefaces(var15, var12);
            }

            ++var10;
         }
      } catch (Exception var26) {
         String var24 = "font package not found, just use default font, " + var26;
         int var25 = Log.v("FontListAdapter", var24);
      }

      android.graphics.Typeface var22 = android.graphics.Typeface.createFromAsset(var1.getAssets(), "fonts/DroidSans.ttf");
      this.droidSansFont = var22;
   }

   private android.graphics.Typeface getFont(String var1, String var2) {
      int var3 = var1.lastIndexOf(47);
      int var4 = var1.lastIndexOf(46);
      if(var4 < 0) {
         var4 = var1.length();
      }

      int var5 = var3 + 1;
      String var6 = var1.substring(var5, var4);
      String var7 = var6.replaceAll(" ", "");

      try {
         AssetManager var8 = this.mPackageManager.getResourcesForApplication(var2).getAssets();
         this.mFontAssetManager = var8;
      } catch (Exception var14) {
         String var12 = "font package not found, just use default font, " + var14;
         int var13 = Log.v("FontListAdapter", var12);
      }

      AssetManager var9 = this.mFontAssetManager;
      String var10 = "fonts/" + var6 + ".ttf";
      return android.graphics.Typeface.createFromAsset(var9, var10);
   }

   private void setFont(int var1, TextView var2) {
      android.graphics.Typeface var3 = (android.graphics.Typeface)this.mTypefaces.elementAt(var1);
      if(var3 != null) {
         var2.setTypeface(var3);
      }
   }

   public int getCount() {
      return this.mFontNames.size();
   }

   public String getFontName(int var1) {
      String var2 = (String)this.mFontNames.elementAt(var1);
      if(var2.equals("Cool")) {
         var2 = (String)this.context.getResources().getText(2131232052);
      } else if(var2.equals("Rose")) {
         var2 = (String)this.context.getResources().getText(2131232053);
      } else if(var2.equals("Choco")) {
         var2 = (String)this.context.getResources().getText(2131232054);
      } else if(var2.equals("Rosemary")) {
         var2 = (String)this.context.getResources().getText(2131232053);
      } else if(var2.equals("Choco cooky")) {
         var2 = (String)this.context.getResources().getText(2131232054);
      } else if(var2.equals("Cool jazz")) {
         var2 = (String)this.context.getResources().getText(2131232052);
      } else if(var2.equals("Apple mint")) {
         var2 = (String)this.context.getResources().getText(2131232055);
      } else if(var2.equals("Tinker bell")) {
         var2 = (String)this.context.getResources().getText(2131232056);
      }

      return var2;
   }

   public Object getItem(int var1) {
      return this.mTypefaceFiles.elementAt(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      TextView var4;
      if(var2 == null) {
         var4 = (TextView)this.mInflater.inflate(17367055, (ViewGroup)null);
      } else {
         var4 = (TextView)var2;
      }

      String var5 = this.getFontName(var1);
      var4.setText(var5);
      this.setFont(var1, var4);
      var4.setTextColor(-16777216);
      var4.setTextSize(20.0F);
      WindowManager var6 = (WindowManager)this.context.getSystemService("window");
      DisplayMetrics var7 = new DisplayMetrics();
      var6.getDefaultDisplay().getMetrics(var7);
      float var8 = (float)var7.densityDpi / 160.0F;
      int var9 = (int)(65.0F * var8);
      var4.setHeight(var9);
      return var4;
   }

   public void loadTypefaces() {
      Vector var1 = this.mTypefaces;
      android.graphics.Typeface var2 = this.droidSansFont;
      var1.add(var2);
      int var4 = 1;

      while(true) {
         int var5 = this.mTypefaceFiles.size();
         if(var4 >= var5) {
            return;
         }

         String var6 = this.mTypefaceFiles.elementAt(var4).toString();
         String var7 = this.mFontPackageNames.elementAt(var4).toString();
         android.graphics.Typeface var8 = this.getFont(var6, var7);
         this.mTypefaces.add(var8);
         ++var4;
      }
   }
}
