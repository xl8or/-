package com.facebook.katana.ui;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class SectionedListAdapter {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private List<DataSetObserver> mDataSetObservers;
   protected BaseAdapter mInternalAdapter;
   protected List<SectionedListAdapter.SectionCache> mSectionCache;
   protected boolean mSectionCacheValid;


   static {
      byte var0;
      if(!SectionedListAdapter.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public SectionedListAdapter() {}

   public int[] decodeFlatPosition(int var1) {
      this.ensureSectionCacheValid();
      int[] var2 = new int[2];
      int var3 = SectionedListAdapter.SectionCache.findSectionByFlatIndex(this.mSectionCache, var1);
      var2[0] = var3;
      List var4 = this.mSectionCache;
      int var5 = var2[0];
      int var6 = ((SectionedListAdapter.SectionCache)var4.get(var5)).mStartOffset;
      int var7 = var1 - var6 - 1;
      var2[1] = var7;
      return var2;
   }

   protected void ensureSectionCacheValid() {
      if(!this.mSectionCacheValid) {
         this.rebuildSectionCache();
      }
   }

   public abstract Object getChild(int var1, int var2);

   public abstract View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5);

   public abstract int getChildViewType(int var1, int var2);

   public abstract int getChildrenCount(int var1);

   public int getCount() {
      this.ensureSectionCacheValid();
      int var1;
      if(this.mSectionCache.size() == 0) {
         var1 = 0;
      } else {
         List var2 = this.mSectionCache;
         int var3 = this.mSectionCache.size() - 1;
         SectionedListAdapter.SectionCache var4 = (SectionedListAdapter.SectionCache)var2.get(var3);
         int var5 = var4.mStartOffset;
         int var6 = var4.mNumItems;
         var1 = var5 + var6 + 1;
      }

      return var1;
   }

   public Object getItem(int var1) {
      int[] var2 = this.decodeFlatPosition(var1);
      if(!$assertionsDisabled && var2.length != 2) {
         throw new AssertionError();
      } else {
         Object var3;
         if(var2[1] == -1) {
            var3 = null;
         } else {
            int var4 = var2[0];
            int var5 = var2[1];
            var3 = this.getChild(var4, var5);
         }

         return var3;
      }
   }

   int getItemViewType(int var1) {
      int[] var2 = this.decodeFlatPosition(var1);
      if(!$assertionsDisabled && var2.length != 2) {
         throw new AssertionError();
      } else {
         int var4;
         if(var2[1] == -1) {
            int var3 = var2[0];
            var4 = this.getSectionHeaderViewType(var3);
         } else {
            int var5 = var2[0];
            int var6 = var2[1];
            var4 = this.getChildViewType(var5, var6);
         }

         return var4;
      }
   }

   public int getPositionForSection(int var1) {
      this.ensureSectionCacheValid();
      return ((SectionedListAdapter.SectionCache)this.mSectionCache.get(var1)).mStartOffset;
   }

   public abstract Object getSection(int var1);

   public abstract int getSectionCount();

   public abstract View getSectionHeaderView(int var1, View var2, ViewGroup var3);

   public abstract int getSectionHeaderViewType(int var1);

   View getView(int var1, View var2, ViewGroup var3) {
      int[] var4 = this.decodeFlatPosition(var1);
      if(!$assertionsDisabled && var4.length != 2) {
         throw new AssertionError();
      } else {
         View var6;
         if(var4[1] == -1) {
            int var5 = var4[0];
            var6 = this.getSectionHeaderView(var5, var2, var3);
         } else {
            List var7 = this.mSectionCache;
            int var8 = var4[0];
            int var9 = ((SectionedListAdapter.SectionCache)var7.get(var8)).mNumItems - 1;
            int var10 = var4[1];
            byte var11;
            if(var9 == var10) {
               var11 = 1;
            } else {
               var11 = 0;
            }

            int var12 = var4[0];
            int var13 = var4[1];
            var6 = this.getChildView(var12, var13, (boolean)var11, var2, var3);
         }

         return var6;
      }
   }

   public abstract int getViewTypeCount();

   public abstract boolean isEmpty();

   public boolean isEnabled(int var1) {
      int[] var2 = this.decodeFlatPosition(var1);
      if(!$assertionsDisabled && var2.length != 2) {
         throw new AssertionError();
      } else {
         byte var3;
         if(var2[1] == -1) {
            var3 = 0;
         } else {
            int var4 = var2[0];
            int var5 = var2[1];
            var3 = this.isEnabled(var4, var5);
         }

         return (boolean)var3;
      }
   }

   public abstract boolean isEnabled(int var1, int var2);

   public boolean isPositionSectionHeader(int var1) {
      int[] var2 = this.decodeFlatPosition(var1);
      if(!$assertionsDisabled && var2.length != 2) {
         throw new AssertionError();
      } else {
         boolean var3;
         if(var2[1] == -1) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   public void notifyDataSetChanged() {
      this.mSectionCacheValid = (boolean)0;
      if(this.mInternalAdapter != null) {
         this.mInternalAdapter.notifyDataSetChanged();
      }

      if(this.mDataSetObservers != null) {
         int var1 = 0;

         while(true) {
            int var2 = this.mDataSetObservers.size();
            if(var1 >= var2) {
               return;
            }

            ((DataSetObserver)this.mDataSetObservers.get(var1)).onChanged();
            ++var1;
         }
      }
   }

   protected void rebuildSectionCache() {
      if(!$assertionsDisabled && this.mSectionCacheValid) {
         throw new AssertionError();
      } else {
         if(this.mSectionCache == null) {
            ArrayList var1 = new ArrayList();
            this.mSectionCache = var1;
         }

         int var2 = this.mSectionCache.size();

         for(int var3 = this.getSectionCount(); var2 < var3; ++var2) {
            List var4 = this.mSectionCache;
            SectionedListAdapter.SectionCache var5 = new SectionedListAdapter.SectionCache(var2);
            var4.add(var5);
         }

         if(!$assertionsDisabled) {
            int var7 = this.mSectionCache.size();
            int var8 = this.getSectionCount();
            if(var7 < var8) {
               throw new AssertionError();
            }
         }

         int var9 = this.mSectionCache.size();

         for(int var10 = this.getSectionCount(); var9 > var10; var9 += -1) {
            List var11 = this.mSectionCache;
            int var12 = var9 - 1;
            var11.remove(var12);
         }

         if(!$assertionsDisabled) {
            int var14 = this.mSectionCache.size();
            int var15 = this.getSectionCount();
            if(var14 != var15) {
               throw new AssertionError();
            }
         }

         int var16 = 0;
         int var17 = 0;

         while(true) {
            int var18 = this.getSectionCount();
            if(var16 >= var18) {
               this.mSectionCacheValid = (boolean)1;
               return;
            }

            int var19 = this.getChildrenCount(var16);
            SectionedListAdapter.SectionCache var20 = (SectionedListAdapter.SectionCache)this.mSectionCache.get(var16);
            var20.mStartOffset = var17;
            var20.mNumItems = var19;
            int var21 = var19 + 1;
            var17 += var21;
            ++var16;
         }
      }
   }

   public void registerDataSetObserver(DataSetObserver var1) {
      if(this.mDataSetObservers == null) {
         ArrayList var2 = new ArrayList();
         this.mDataSetObservers = var2;
      }

      this.mDataSetObservers.add(var1);
   }

   void setInternalListAdapter(BaseAdapter var1) {
      this.mInternalAdapter = var1;
   }

   public void unregisterDataSetObserver(DataSetObserver var1) {
      if(this.mDataSetObservers != null) {
         this.mDataSetObservers.remove(var1);
      }
   }

   static class SectionCache {

      static Comparator<SectionedListAdapter.SectionCache> cmp = new SectionedListAdapter.SectionCache.1();
      int mNumItems;
      final int mSectionPosition;
      int mStartOffset;


      SectionCache(int var1) {
         this.mSectionPosition = var1;
      }

      static int findSectionByFlatIndex(List<SectionedListAdapter.SectionCache> var0, int var1) {
         SectionedListAdapter.SectionCache var2 = new SectionedListAdapter.SectionCache(0);
         var2.mStartOffset = var1;
         Comparator var3 = cmp;
         return Collections.binarySearch(var0, var2, var3);
      }

      static class 1 implements Comparator<SectionedListAdapter.SectionCache> {

         1() {}

         public int compare(SectionedListAdapter.SectionCache var1, SectionedListAdapter.SectionCache var2) {
            int var3 = var1.mStartOffset;
            int var4 = var1.mNumItems;
            int var5 = var3 + var4;
            int var6 = var2.mStartOffset;
            byte var7;
            if(var5 < var6) {
               var7 = -1;
            } else {
               int var8 = var1.mStartOffset;
               int var9 = var2.mStartOffset;
               int var10 = var2.mNumItems;
               int var11 = var9 + var10;
               if(var8 > var11) {
                  var7 = 1;
               } else {
                  var7 = 0;
               }
            }

            return var7;
         }
      }
   }
}
