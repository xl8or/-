package com.google.android.finsky.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.activities.CategoryViewBinder;
import com.google.android.finsky.activities.SlidingPanelTab;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public class CategoryTab implements SlidingPanelTab {

   private final CategoryViewBinder mCategoryBinder;
   private ViewGroup mCategoryView;
   private final Context mContext;
   private final String mCurrentBrowseUrl;
   private final LayoutInflater mLayoutInflater;


   public CategoryTab(Context var1, LayoutInflater var2, NavigationManager var3, BitmapLoader var4, DfeBrowse var5, String var6) {
      if(var5 != null && var5.isReady()) {
         this.mContext = var1;
         this.mLayoutInflater = var2;
         this.mCurrentBrowseUrl = var6;
         CategoryViewBinder var7 = new CategoryViewBinder();
         this.mCategoryBinder = var7;
         this.mCategoryBinder.init(var1, var3, var4);
         this.mCategoryBinder.setData(var5);
      } else {
         throw new IllegalStateException("Tried to create category tab with invalid data!");
      }
   }

   public String getTitle() {
      return this.mContext.getString(2131231044);
   }

   public View getView(int var1) {
      if(this.mCategoryView == null) {
         ViewGroup var2 = (ViewGroup)this.mLayoutInflater.inflate(2130968712, (ViewGroup)null);
         this.mCategoryView = var2;
         this.mCategoryView.findViewById(2131755312).setVisibility(8);
         this.mCategoryView.findViewById(2131755069).setVisibility(0);
         CategoryViewBinder var3 = this.mCategoryBinder;
         ViewGroup var4 = this.mCategoryView;
         String var5 = this.mCurrentBrowseUrl;
         var3.bind(var4, var1, var5);
      }

      return this.mCategoryView;
   }

   public void onDestroy() {
      this.mCategoryBinder.onDestroy();
      this.mCategoryView = null;
   }

   public void setTabSelected(boolean var1) {}
}
