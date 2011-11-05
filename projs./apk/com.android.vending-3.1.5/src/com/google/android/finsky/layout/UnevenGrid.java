package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import com.google.android.finsky.adapters.UnevenGridAdapter;
import com.google.android.finsky.adapters.UnevenGridItemType;
import com.google.android.finsky.layout.CellBasedLayout;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnevenGrid extends ViewGroup {

   private UnevenGridAdapter mAdapter;
   private int mCellHeight;
   private int mCellWidth;
   Map<Integer, UnevenGridItemType> mConvertViewTypeMap;
   private CellBasedLayout mGridData;
   private int mGutterSize;
   private boolean mHasTopGutter;
   private List<CellBasedLayout.Item> mItems;
   private final int mNumCellsWide;
   private UnevenGrid.UnevenGridAdapterObserver mObserver;
   private boolean mRebindNecessary;


   public UnevenGrid(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public UnevenGrid(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      ArrayList var4 = Lists.newArrayList();
      this.mItems = var4;
      HashMap var5 = Maps.newHashMap();
      this.mConvertViewTypeMap = var5;
      this.mRebindNecessary = (boolean)0;
      Resources var6 = var1.getResources();
      int var7 = var6.getInteger(2131492868);
      this.mNumCellsWide = var7;
      int var8 = this.mNumCellsWide;
      CellBasedLayout var9 = new CellBasedLayout(var8);
      this.mGridData = var9;
      int var10 = var6.getDimensionPixelSize(2131427396);
      this.mGutterSize = var10;
   }

   private int measureChildren() {
      int var1 = 0;
      int var2 = this.mGutterSize / 2;
      int var3 = 0;

      while(true) {
         int var4 = this.mItems.size();
         if(var3 >= var4) {
            int var21 = this.mCellHeight;
            int var22 = var1 * var21;
            if(!this.mHasTopGutter) {
               int var23 = this.mGutterSize;
               var22 -= var23;
            }

            return var22;
         }

         CellBasedLayout.Item var5 = (CellBasedLayout.Item)this.mItems.get(var3);
         int var6;
         if(this.mGridData.isOnLeftEdge(var5)) {
            var6 = 0;
         } else {
            var6 = var2;
         }

         int var7;
         if(this.mGridData.isOnRightEdge(var5)) {
            var7 = 0;
         } else {
            var7 = var2;
         }

         View var8 = this.getChildAt(var3);
         int var9 = this.mCellWidth;
         int var10 = var5.getCellWidth();
         int var11 = var9 * var10;
         int var12 = var6 + var7;
         int var13 = MeasureSpec.makeMeasureSpec(var11 - var12, 1073741824);
         int var14 = this.mCellHeight;
         int var15 = var5.getCellHeight();
         int var16 = var14 * var15;
         int var17 = this.mGutterSize;
         int var18 = MeasureSpec.makeMeasureSpec(var16 - var17, 1073741824);
         var8.measure(var13, var18);
         int var19 = this.mGridData.getTop(var5);
         int var20 = var5.getCellHeight();
         var1 = Math.max(var19 + var20, var1);
         ++var3;
      }
   }

   private void rebindChildren() {
      this.mItems.clear();
      int var1 = this.mNumCellsWide;
      CellBasedLayout var2 = new CellBasedLayout(var1);
      this.mGridData = var2;
      int var3 = 0;

      while(true) {
         int var4 = this.mAdapter.getCount();
         if(var3 >= var4) {
            break;
         }

         View var5;
         label31: {
            var5 = this.getChildAt(var3);
            Map var6 = this.mConvertViewTypeMap;
            Integer var7 = Integer.valueOf(var3);
            if(var6.containsKey(var7)) {
               Map var8 = this.mConvertViewTypeMap;
               Integer var9 = Integer.valueOf(var3);
               UnevenGridItemType var10 = (UnevenGridItemType)var8.get(var9);
               Integer var11 = Integer.valueOf(this.mAdapter.getItemViewType(var3));
               if(var10.equals(var11)) {
                  break label31;
               }
            }

            var5 = null;
         }

         View var12 = this.mAdapter.getView(var3, var5, this);
         UnevenGridAdapter.UnevenGridItem var13 = this.mAdapter.getItem(var3);
         CellBasedLayout var14 = this.mGridData;
         boolean var15 = this.mAdapter.isItemRequired(var3);
         if(!var14.addItem(var13, var15)) {
            break;
         }

         this.mItems.add(var13);
         if(var12 != var5) {
            if(var5 != null) {
               int var17 = this.mItems.size() + -1;
               this.removeViewAt(var17);
            }

            int var18 = this.mItems.size() + -1;
            this.addView(var12, var18);
         }

         ++var3;
      }

      int var19 = this.getChildCount();
      int var20 = this.mItems.size();
      if(var19 > var20) {
         int var21 = this.mItems.size();
         int var22 = this.getChildCount();
         int var23 = this.mItems.size();
         int var24 = var22 - var23;
         this.removeViews(var21, var24);
      }
   }

   private void removeAllItems() {
      int var1 = this.mNumCellsWide;
      CellBasedLayout var2 = new CellBasedLayout(var1);
      this.mGridData = var2;
      this.mItems.clear();
      this.removeAllViews();
   }

   protected void dispatchRestoreInstanceState(SparseArray<Parcelable> var1) {
      this.dispatchThawSelfOnly(var1);
   }

   protected void dispatchSaveInstanceState(SparseArray<Parcelable> var1) {
      this.dispatchFreezeSelfOnly(var1);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = var5 - var3;
      if(var4 - var2 > 0) {
         if(var6 > 0) {
            int var7 = this.mGutterSize / 2;
            int var8 = 0;

            while(true) {
               int var9 = this.mItems.size();
               if(var8 >= var9) {
                  return;
               }

               CellBasedLayout.Item var10 = (CellBasedLayout.Item)this.mItems.get(var8);
               View var11 = this.getChildAt(var8);
               int var12 = this.mGridData.getLeft(var10);
               int var13 = this.mCellWidth;
               int var14 = var12 * var13;
               int var15 = this.mGridData.getTop(var10);
               int var16 = this.mCellHeight;
               int var17 = var15 * var16;
               int var18 = var10.getCellWidth();
               int var19 = this.mCellWidth;
               int var20 = var18 * var19;
               int var21 = var14 + var20;
               int var22 = var10.getCellHeight();
               int var23 = this.mCellHeight;
               int var24 = var22 * var23;
               int var25 = var17 + var24;
               int var26;
               if(this.mGridData.isOnLeftEdge(var10)) {
                  var26 = 0;
               } else {
                  var26 = var7;
               }

               int var27;
               if(this.mGridData.isOnRightEdge(var10)) {
                  var27 = 0;
               } else {
                  var27 = var7;
               }

               if(!this.mHasTopGutter) {
                  int var28 = this.mGutterSize;
                  var17 -= var28;
                  int var29 = this.mGutterSize;
                  var25 -= var29;
               }

               int var30 = var14 + var26;
               int var31 = this.mGutterSize + var17;
               int var32 = var21 - var27;
               var11.layout(var30, var31, var32, var25);
               ++var8;
            }
         }
      }
   }

   protected void onMeasure(int var1, int var2) {
      if(this.mRebindNecessary) {
         this.rebindChildren();
         this.mRebindNecessary = (boolean)0;
      }

      if(this.getChildCount() == 0) {
         int var3 = MeasureSpec.getSize(var1);
         int var4 = MeasureSpec.getSize(var2);
         this.setMeasuredDimension(var3, var4);
      } else {
         int var5 = MeasureSpec.getSize(var1);
         int var6 = MeasureSpec.getSize(var2);
         this.setMeasuredDimension(var5, var6);
         int var7 = this.getMeasuredWidth();
         int var8 = this.mNumCellsWide;
         int var9 = var7 / var8;
         this.mCellWidth = var9;
         int var10 = this.mCellWidth;
         this.mCellHeight = var10;
         int var11 = MeasureSpec.getSize(var1);
         int var12 = this.measureChildren();
         this.setMeasuredDimension(var11, var12);
      }
   }

   public void setAdapter(UnevenGridAdapter var1) {
      if(this.mAdapter != null && this.mObserver != null) {
         UnevenGridAdapter var2 = this.mAdapter;
         UnevenGrid.UnevenGridAdapterObserver var3 = this.mObserver;
         var2.unregisterDataSetObserver(var3);
      }

      this.removeAllItems();
      this.mAdapter = var1;
      if(this.mAdapter != null) {
         if(this.mObserver == null) {
            UnevenGrid.UnevenGridAdapterObserver var4 = new UnevenGrid.UnevenGridAdapterObserver((UnevenGrid.1)null);
            this.mObserver = var4;
         }

         UnevenGridAdapter var5 = this.mAdapter;
         UnevenGrid.UnevenGridAdapterObserver var6 = this.mObserver;
         var5.registerDataSetObserver(var6);
         this.rebindChildren();
      }

      this.requestLayout();
   }

   public void setHasTopGutter(boolean var1) {
      this.mHasTopGutter = var1;
   }

   private class UnevenGridAdapterObserver extends DataSetObserver {

      private UnevenGridAdapterObserver() {}

      // $FF: synthetic method
      UnevenGridAdapterObserver(UnevenGrid.1 var2) {
         this();
      }

      public void onChanged() {
         boolean var1 = (boolean)(UnevenGrid.this.mRebindNecessary = (boolean)1);
         UnevenGrid.this.requestLayout();
      }

      public void onInvalidated() {
         this.onChanged();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
