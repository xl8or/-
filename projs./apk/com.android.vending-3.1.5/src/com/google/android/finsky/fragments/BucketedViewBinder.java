package com.google.android.finsky.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.android.finsky.adapters.BucketAdapter;
import com.google.android.finsky.api.model.BucketedList;
import com.google.android.finsky.fragments.ViewBinder;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;
import java.util.List;

public class BucketedViewBinder extends ViewBinder<BucketedList<?>> {

   private final int mCellLayoutId;
   private final int mSuggestionBarLayoutId;


   public BucketedViewBinder(int var1, int var2) {
      this.mCellLayoutId = var1;
      this.mSuggestionBarLayoutId = var2;
   }

   public void bind(ViewGroup var1, int var2, int var3, String var4, String var5, String var6) {
      ListView var7 = (ListView)var1.findViewById(2131755069);
      View var8 = var1.findViewById(2131755255);
      var7.setEmptyView(var8);
      Context var9 = this.mContext;
      NavigationManager var10 = this.mNavManager;
      BitmapLoader var11 = this.mBitmapLoader;
      List var12 = ((BucketedList)this.mData).getBucketList();
      int var13 = this.mCellLayoutId;
      int var14 = this.mSuggestionBarLayoutId;
      BucketAdapter var20 = new BucketAdapter(var9, var10, var11, var12, var2, var3, var13, var14, var4, var5, var6);
      var7.setAdapter(var20);
   }

   public void onDestroyView() {
      this.mData = null;
   }
}
