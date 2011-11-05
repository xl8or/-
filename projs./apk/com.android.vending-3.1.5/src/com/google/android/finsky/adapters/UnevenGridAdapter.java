package com.google.android.finsky.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.android.finsky.adapters.UnevenGridItemType;
import com.google.android.finsky.layout.CellBasedLayout;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.List;

public class UnevenGridAdapter extends BaseAdapter {

   protected Context mContext;
   private boolean mEnableImages;
   private int mFillerLimit;
   protected List<UnevenGridAdapter.UnevenGridItem> mItems;
   protected LayoutInflater mLayoutInflater;
   private boolean mShowCorpusStrip;


   public UnevenGridAdapter(Context var1) {
      ArrayList var2 = Lists.newArrayList();
      this.mItems = var2;
      this.mEnableImages = (boolean)0;
      this.mShowCorpusStrip = (boolean)0;
      LayoutInflater var3 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mLayoutInflater = var3;
      this.mContext = var1;
   }

   public int getCount() {
      return this.mItems.size();
   }

   public UnevenGridAdapter.UnevenGridItem getItem(int var1) {
      return (UnevenGridAdapter.UnevenGridItem)this.mItems.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getItemViewType(int var1) {
      return ((UnevenGridAdapter.UnevenGridItem)this.mItems.get(var1)).getGridItemType().ordinal();
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      UnevenGridAdapter.UnevenGridItem var4 = (UnevenGridAdapter.UnevenGridItem)this.mItems.get(var1);
      ViewGroup var5;
      if(var4 == null) {
         var5 = null;
      } else {
         View var8;
         if(var2 == null) {
            LayoutInflater var6 = this.mLayoutInflater;
            int var7 = var4.getLayoutId();
            var8 = var6.inflate(var7, var3, (boolean)0);
         } else {
            var8 = var2;
         }

         var5 = (ViewGroup)var8;
         boolean var9 = this.mEnableImages;
         boolean var10 = this.mShowCorpusStrip;
         var4.bind(var5, var9, var10);
      }

      return var5;
   }

   public int getViewTypeCount() {
      return UnevenGridItemType.values().length;
   }

   public boolean isItemRequired(int var1) {
      int var2 = this.mFillerLimit;
      boolean var3;
      if(var1 < var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void onDestroy() {
      this.mItems.clear();
   }

   public void setData(List<UnevenGridAdapter.UnevenGridItem> var1, List<UnevenGridAdapter.UnevenGridItem> var2) {
      ArrayList var3 = Lists.newArrayList();
      this.mItems = var3;
      this.mItems.addAll(var1);
      int var5 = var1.size();
      this.mFillerLimit = var5;
      this.mItems.addAll(var2);
      this.notifyDataSetChanged();
   }

   public void setImagesEnabled(boolean var1) {
      boolean var2 = this.mEnableImages;
      if(var1 != var2) {
         this.mEnableImages = var1;
         this.notifyDataSetChanged();
      }
   }

   public void setShowCorpusStrip(boolean var1) {
      this.mShowCorpusStrip = var1;
   }

   public interface UnevenGridItem extends CellBasedLayout.Item {

      void bind(ViewGroup var1, boolean var2, boolean var3);

      UnevenGridItemType getGridItemType();

      int getLayoutId();
   }
}
