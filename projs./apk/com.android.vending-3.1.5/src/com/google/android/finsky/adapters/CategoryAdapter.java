package com.google.android.finsky.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.remoting.protos.Browse;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.CorpusMetadata;

public class CategoryAdapter extends BaseAdapter implements OnDataChangedListener {

   private final int mBackendId;
   private final BitmapLoader mBitmapLoader;
   private final DfeBrowse mBrowseData;
   private final String mCurrentBrowseUrl;
   private final LayoutInflater mLayoutInflater;
   private final NavigationManager mNavigationManager;
   private final int mTextColor;


   public CategoryAdapter(Context var1, DfeBrowse var2, NavigationManager var3, BitmapLoader var4, int var5, String var6) {
      LayoutInflater var7 = LayoutInflater.from(var1);
      this.mLayoutInflater = var7;
      this.mBrowseData = var2;
      this.mNavigationManager = var3;
      this.mBitmapLoader = var4;
      this.mBackendId = var5;
      this.mCurrentBrowseUrl = var6;
      int var8 = CorpusMetadata.getBackendHintColor(var1, var5);
      this.mTextColor = var8;
      this.mBrowseData.addDataChangedListener(this);
   }

   private void loadImage(CategoryAdapter.ViewHolder var1, String var2) {
      BitmapLoader.BitmapContainer var3 = var1.container;
      if(TextUtils.isEmpty(var2)) {
         if(var3 != null) {
            var3.cancelRequest();
            var1.container = null;
         }

         var1.image.setImageBitmap((Bitmap)null);
      } else {
         if(var3 != null && var3.getRequestUrl() != null) {
            if(var3.getRequestUrl().equals(var2)) {
               return;
            }

            var3.cancelRequest();
         }

         BitmapLoader var4 = this.mBitmapLoader;
         ImageView var5 = var1.image;
         CategoryAdapter.2 var6 = new CategoryAdapter.2(var1);
         int var7 = var1.image.getWidth();
         int var8 = var1.image.getHeight();
         BitmapLoader.BitmapContainer var10 = var4.getOrBindImmediately(var2, var5, var6, var7, var8);
         var1.container = var10;
      }
   }

   public int getCount() {
      int var1;
      if(!this.mBrowseData.hasCategories()) {
         var1 = 0;
      } else {
         var1 = this.mBrowseData.getCategoryList().size();
      }

      return var1;
   }

   public Object getItem(int var1) {
      return this.mBrowseData.getCategoryList().get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      Browse.BrowseLink var4 = (Browse.BrowseLink)this.getItem(var1);
      if(var2 == null) {
         var2 = this.mLayoutInflater.inflate(2130968600, var3, (boolean)0);
      }

      CategoryAdapter.ViewHolder var5 = (CategoryAdapter.ViewHolder)var2.getTag();
      if(var5 == null) {
         var5 = new CategoryAdapter.ViewHolder((CategoryAdapter.1)null);
         TextView var6 = (TextView)var2.findViewById(2131755101);
         var5.title = var6;
         ImageView var7 = (ImageView)var2.findViewById(2131755100);
         var5.image = var7;
         var2.setTag(var5);
      }

      TextView var8 = var5.title;
      int var9 = this.mTextColor;
      var8.setTextColor(var9);
      TextView var10 = var5.title;
      String var11 = var4.getName().toUpperCase();
      var10.setText(var11);
      String var12 = var4.getIconUrl();
      this.loadImage(var5, var12);
      CategoryAdapter.1 var13 = new CategoryAdapter.1(var4);
      var2.setOnClickListener(var13);
      TextView var14 = var5.title;
      String var15 = var4.getName();
      var14.setContentDescription(var15);
      return var2;
   }

   public void onDataChanged() {
      if(!this.mBrowseData.inErrorState()) {
         this.notifyDataSetChanged();
      }
   }

   public void onDestroy() {
      if(this.mBrowseData != null) {
         this.mBrowseData.removeDataChangedListener(this);
      }
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final Browse.BrowseLink val$category;


      1(Browse.BrowseLink var2) {
         this.val$category = var2;
      }

      public void onClick(View var1) {
         NavigationManager var2 = CategoryAdapter.this.mNavigationManager;
         String var3 = this.val$category.getDataUrl();
         String var4 = this.val$category.getName();
         int var5 = CategoryAdapter.this.mBackendId;
         String var6 = CategoryAdapter.this.mCurrentBrowseUrl;
         var2.goBrowse(var3, var4, var5, var6);
      }
   }

   class 2 implements BitmapLoader.BitmapLoadedHandler {

      // $FF: synthetic field
      final CategoryAdapter.ViewHolder val$holder;


      2(CategoryAdapter.ViewHolder var2) {
         this.val$holder = var2;
      }

      public void onResponse(BitmapLoader.BitmapContainer var1) {
         if(var1.getBitmap() != null) {
            ImageView var2 = this.val$holder.image;
            Bitmap var3 = var1.getBitmap();
            var2.setImageBitmap(var3);
         }
      }
   }

   private static class ViewHolder {

      BitmapLoader.BitmapContainer container;
      ImageView image;
      TextView title;


      private ViewHolder() {}

      // $FF: synthetic method
      ViewHolder(CategoryAdapter.1 var1) {
         this();
      }
   }
}
