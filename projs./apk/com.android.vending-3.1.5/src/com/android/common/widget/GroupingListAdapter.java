package com.android.common.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class GroupingListAdapter extends BaseAdapter {

   private static final long EXPANDED_GROUP_MASK = Long.MIN_VALUE;
   private static final int GROUP_METADATA_ARRAY_INCREMENT = 128;
   private static final int GROUP_METADATA_ARRAY_INITIAL_SIZE = 16;
   private static final long GROUP_OFFSET_MASK = 4294967295L;
   private static final long GROUP_SIZE_MASK = 9223372032559808512L;
   public static final int ITEM_TYPE_GROUP_HEADER = 1;
   public static final int ITEM_TYPE_IN_GROUP = 2;
   public static final int ITEM_TYPE_STANDALONE;
   protected ContentObserver mChangeObserver;
   private Context mContext;
   private int mCount;
   private Cursor mCursor;
   protected DataSetObserver mDataSetObserver;
   private int mGroupCount;
   private long[] mGroupMetadata;
   private int mLastCachedCursorPosition;
   private int mLastCachedGroup;
   private int mLastCachedListPosition;
   private SparseIntArray mPositionCache;
   private GroupingListAdapter.PositionMetadata mPositionMetadata;
   private int mRowIdColumnIndex;


   public GroupingListAdapter(Context var1) {
      SparseIntArray var2 = new SparseIntArray();
      this.mPositionCache = var2;
      GroupingListAdapter.PositionMetadata var3 = new GroupingListAdapter.PositionMetadata();
      this.mPositionMetadata = var3;
      Handler var4 = new Handler();
      GroupingListAdapter.1 var5 = new GroupingListAdapter.1(var4);
      this.mChangeObserver = var5;
      GroupingListAdapter.2 var6 = new GroupingListAdapter.2();
      this.mDataSetObserver = var6;
      this.mContext = var1;
      this.resetCache();
   }

   private void findGroups() {
      this.mGroupCount = 0;
      Object var1 = null;
      this.mGroupMetadata = (long[])var1;
      if(this.mCursor != null) {
         Cursor var2 = this.mCursor;
         this.addGroups(var2);
      }
   }

   private int idealByteArraySize(int var1) {
      for(int var2 = 4; var2 < 32; ++var2) {
         int var3 = (1 << var2) + -12;
         if(var1 <= var3) {
            var1 = (1 << var2) + -12;
            break;
         }
      }

      return var1;
   }

   private int idealLongArraySize(int var1) {
      int var2 = var1 * 8;
      return this.idealByteArraySize(var2) / 8;
   }

   private void resetCache() {
      this.mCount = -1;
      this.mLastCachedListPosition = -1;
      this.mLastCachedCursorPosition = -1;
      this.mLastCachedGroup = -1;
      int var1 = this.mPositionMetadata.listPosition = -1;
      this.mPositionCache.clear();
   }

   protected void addGroup(int var1, int var2, boolean var3) {
      int var4 = this.mGroupCount;
      int var5 = this.mGroupMetadata.length;
      if(var4 >= var5) {
         int var6 = this.mGroupMetadata.length + 128;
         Object var7 = this.idealLongArraySize(var6);
         long[] var8 = this.mGroupMetadata;
         int var9 = this.mGroupCount;
         System.arraycopy(var8, 0, var7, 0, var9);
         this.mGroupMetadata = (long[])var7;
      }

      long var10 = (long)var2 << 32;
      long var12 = (long)var1;
      long var14 = var10 | var12;
      if(var3) {
         var14 |= Long.MIN_VALUE;
      }

      long[] var16 = this.mGroupMetadata;
      int var17 = this.mGroupCount;
      int var18 = var17 + 1;
      this.mGroupCount = var18;
      var16[var17] = var14;
   }

   protected abstract void addGroups(Cursor var1);

   protected abstract void bindChildView(View var1, Context var2, Cursor var3);

   protected abstract void bindGroupView(View var1, Context var2, Cursor var3, int var4, boolean var5);

   protected abstract void bindStandAloneView(View var1, Context var2, Cursor var3);

   public void changeCursor(Cursor var1) {
      Cursor var2 = this.mCursor;
      if(var1 != var2) {
         if(this.mCursor != null) {
            Cursor var3 = this.mCursor;
            ContentObserver var4 = this.mChangeObserver;
            var3.unregisterContentObserver(var4);
            Cursor var5 = this.mCursor;
            DataSetObserver var6 = this.mDataSetObserver;
            var5.unregisterDataSetObserver(var6);
            this.mCursor.close();
         }

         this.mCursor = var1;
         this.resetCache();
         this.findGroups();
         if(var1 != null) {
            ContentObserver var7 = this.mChangeObserver;
            var1.registerContentObserver(var7);
            DataSetObserver var8 = this.mDataSetObserver;
            var1.registerDataSetObserver(var8);
            int var9 = var1.getColumnIndexOrThrow("_id");
            this.mRowIdColumnIndex = var9;
            this.notifyDataSetChanged();
         } else {
            this.notifyDataSetInvalidated();
         }
      }
   }

   public int getCount() {
      int var1 = 0;
      if(this.mCursor != null) {
         if(this.mCount != -1) {
            var1 = this.mCount;
         } else {
            int var2 = 0;
            int var3 = 0;
            int var4 = 0;

            while(true) {
               int var5 = this.mGroupCount;
               if(var4 >= var5) {
                  int var14 = this.mCursor.getCount() + var3 - var2;
                  this.mCount = var14;
                  var1 = this.mCount;
                  break;
               }

               long var6 = this.mGroupMetadata[var4];
               int var8 = (int)(4294967295L & var6);
               boolean var9;
               if((Long.MIN_VALUE & var6) != 0L) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               int var10 = (int)((9223372032559808512L & var6) >> 32);
               int var11 = var8 - var2;
               int var12 = var3 + var11;
               if(var9) {
                  int var13 = var10 + 1;
                  var3 = var12 + var13;
               } else {
                  var3 = var12 + 1;
               }

               var2 = var8 + var10;
               ++var4;
            }
         }
      }

      return var1;
   }

   public Cursor getCursor() {
      return this.mCursor;
   }

   public int getGroupSize(int var1) {
      GroupingListAdapter.PositionMetadata var2 = this.mPositionMetadata;
      this.obtainPositionMetadata(var2, var1);
      return this.mPositionMetadata.childCount;
   }

   public Object getItem(int var1) {
      Cursor var2 = null;
      if(this.mCursor != null) {
         GroupingListAdapter.PositionMetadata var3 = this.mPositionMetadata;
         this.obtainPositionMetadata(var3, var1);
         Cursor var4 = this.mCursor;
         int var5 = this.mPositionMetadata.cursorPosition;
         if(var4.moveToPosition(var5)) {
            var2 = this.mCursor;
         }
      }

      return var2;
   }

   public long getItemId(int var1) {
      long var4;
      if(this.getItem(var1) != null) {
         Cursor var2 = this.mCursor;
         int var3 = this.mRowIdColumnIndex;
         var4 = var2.getLong(var3);
      } else {
         var4 = 65535L;
      }

      return var4;
   }

   public int getItemViewType(int var1) {
      GroupingListAdapter.PositionMetadata var2 = this.mPositionMetadata;
      this.obtainPositionMetadata(var2, var1);
      return this.mPositionMetadata.itemType;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      GroupingListAdapter.PositionMetadata var4 = this.mPositionMetadata;
      this.obtainPositionMetadata(var4, var1);
      View var5 = var2;
      if(var2 == null) {
         switch(this.mPositionMetadata.itemType) {
         case 0:
            Context var9 = this.mContext;
            var5 = this.newStandAloneView(var9, var3);
            break;
         case 1:
            Context var10 = this.mContext;
            var5 = this.newGroupView(var10, var3);
            break;
         case 2:
            Context var11 = this.mContext;
            var5 = this.newChildView(var11, var3);
         }
      }

      Cursor var6 = this.mCursor;
      int var7 = this.mPositionMetadata.cursorPosition;
      var6.moveToPosition(var7);
      switch(this.mPositionMetadata.itemType) {
      case 0:
         Context var12 = this.mContext;
         Cursor var13 = this.mCursor;
         this.bindStandAloneView(var5, var12, var13);
         break;
      case 1:
         Context var14 = this.mContext;
         Cursor var15 = this.mCursor;
         int var16 = this.mPositionMetadata.childCount;
         boolean var17 = this.mPositionMetadata.isExpanded;
         this.bindGroupView(var5, var14, var15, var16, var17);
         break;
      case 2:
         Context var18 = this.mContext;
         Cursor var19 = this.mCursor;
         this.bindChildView(var5, var18, var19);
      }

      return var5;
   }

   public int getViewTypeCount() {
      return 3;
   }

   public boolean isGroupHeader(int var1) {
      byte var2 = 1;
      GroupingListAdapter.PositionMetadata var3 = this.mPositionMetadata;
      this.obtainPositionMetadata(var3, var1);
      if(this.mPositionMetadata.itemType != var2) {
         var2 = 0;
      }

      return (boolean)var2;
   }

   protected abstract View newChildView(Context var1, ViewGroup var2);

   protected abstract View newGroupView(Context var1, ViewGroup var2);

   protected abstract View newStandAloneView(Context var1, ViewGroup var2);

   public void obtainPositionMetadata(GroupingListAdapter.PositionMetadata var1, int var2) {
      int var3 = var1.listPosition;
      if(var3 != var2) {
         int var5 = 0;
         int var6 = 0;
         int var7 = 0;
         if(this.mLastCachedListPosition != -1) {
            int var8 = this.mLastCachedListPosition;
            if(var2 <= var8) {
               SparseIntArray var9 = this.mPositionCache;
               int var11 = var9.indexOfKey(var2);
               if(var11 < 0) {
                  var11 = ~var11 + -1;
                  int var12 = this.mPositionCache.size();
                  if(var11 >= var12) {
                     var11 += -1;
                  }
               }

               if(var11 >= 0) {
                  this.mPositionCache.keyAt(var11);
                  var7 = this.mPositionCache.valueAt(var11);
                  long var14 = this.mGroupMetadata[var7];
                  int var16 = (int)(4294967295L & var14);
               }
            } else {
               var7 = this.mLastCachedGroup;
               int var26 = this.mLastCachedListPosition;
               int var27 = this.mLastCachedCursorPosition;
            }
         }

         int var17 = var7;

         while(true) {
            int var18 = this.mGroupCount;
            if(var17 >= var18) {
               var1.itemType = 0;
               int var37 = var2 - var5 + var6;
               var1.cursorPosition = var37;
               return;
            }

            long var19 = this.mGroupMetadata[var17];
            int var21 = (int)(4294967295L & var19);
            int var22 = var21 - var6;
            var5 += var22;
            var6 = var21;
            int var23 = this.mLastCachedGroup;
            if(var17 > var23) {
               this.mPositionCache.append(var5, var17);
               this.mLastCachedListPosition = var5;
               this.mLastCachedCursorPosition = var21;
               this.mLastCachedGroup = var17;
            }

            if(var2 < var5) {
               var1.itemType = 0;
               int var24 = var5 - var2;
               int var25 = var21 - var24;
               var1.cursorPosition = var25;
               return;
            }

            byte var28;
            if((Long.MIN_VALUE & var19) != 0L) {
               var28 = 1;
            } else {
               var28 = 0;
            }

            int var29 = (int)((9223372032559808512L & var19) >> 32);
            if(var2 == var5) {
               var1.itemType = 1;
               var1.groupPosition = var17;
               var1.isExpanded = (boolean)var28;
               var1.childCount = var29;
               var1.cursorPosition = var21;
               return;
            }

            int var10000;
            if(var28 != 0) {
               int var31 = var5 + var29 + 1;
               if(var2 < var31) {
                  var1.itemType = 2;
                  int var32 = var2 - var5 + var21 + -1;
                  var1.cursorPosition = var32;
                  return;
               }

               int var33 = var29 + 1;
               var10000 = var5 + var33;
            } else {
               int var36 = var5 + 1;
            }

            var10000 = var21 + var29;
            ++var17;
         }
      }
   }

   protected void onContentChanged() {}

   public void toggleGroup(int var1) {
      GroupingListAdapter.PositionMetadata var2 = this.mPositionMetadata;
      this.obtainPositionMetadata(var2, var1);
      if(this.mPositionMetadata.itemType != 1) {
         String var3 = "Not a group at position " + var1;
         throw new IllegalArgumentException(var3);
      } else {
         if(this.mPositionMetadata.isExpanded) {
            long[] var4 = this.mGroupMetadata;
            int var5 = this.mPositionMetadata.groupPosition;
            long var6 = var4[var5] & Long.MAX_VALUE;
            var4[var5] = var6;
         } else {
            long[] var8 = this.mGroupMetadata;
            int var9 = this.mPositionMetadata.groupPosition;
            long var10 = var8[var9] | Long.MIN_VALUE;
            var8[var9] = var10;
         }

         this.resetCache();
         this.notifyDataSetChanged();
      }
   }

   class 2 extends DataSetObserver {

      2() {}

      public void onChanged() {
         GroupingListAdapter.this.notifyDataSetChanged();
      }

      public void onInvalidated() {
         GroupingListAdapter.this.notifyDataSetInvalidated();
      }
   }

   class 1 extends ContentObserver {

      1(Handler var2) {
         super(var2);
      }

      public boolean deliverSelfNotifications() {
         return true;
      }

      public void onChange(boolean var1) {
         GroupingListAdapter.this.onContentChanged();
      }
   }

   protected static class PositionMetadata {

      int childCount;
      int cursorPosition;
      private int groupPosition;
      boolean isExpanded;
      int itemType;
      private int listPosition = -1;


      protected PositionMetadata() {}
   }
}
