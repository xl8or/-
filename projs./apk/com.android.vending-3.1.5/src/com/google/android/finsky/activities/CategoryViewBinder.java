package com.google.android.finsky.activities;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.android.finsky.adapters.CategoryAdapter;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.fragments.ViewBinder;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public class CategoryViewBinder extends ViewBinder<DfeBrowse> {

   CategoryAdapter mAdapter;


   public CategoryViewBinder() {}

   public void bind(ViewGroup var1, int var2, String var3) {
      ListView var4 = (ListView)var1.findViewById(2131755069);
      Context var5 = this.mContext;
      DfeBrowse var6 = (DfeBrowse)this.mData;
      NavigationManager var7 = this.mNavManager;
      BitmapLoader var8 = this.mBitmapLoader;
      CategoryAdapter var11 = new CategoryAdapter(var5, var6, var7, var8, var2, var3);
      this.mAdapter = var11;
      CategoryAdapter var12 = this.mAdapter;
      var4.setAdapter(var12);
      var4.setItemsCanFocus((boolean)1);
   }

   public void onDestroy() {
      if(this.mAdapter != null) {
         this.mAdapter.onDestroy();
         this.mAdapter = null;
      }
   }
}
