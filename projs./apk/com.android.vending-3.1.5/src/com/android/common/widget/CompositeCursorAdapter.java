package com.android.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CompositeCursorAdapter extends BaseAdapter {

   private static final int INITIAL_CAPACITY = 2;
   private boolean mCacheValid;
   private final Context mContext;
   private int mCount;
   private boolean mNotificationNeeded;
   private boolean mNotificationsEnabled;
   private CompositeCursorAdapter.Partition[] mPartitions;
   private int mSize;


   public CompositeCursorAdapter(Context var1) {
      this(var1, 2);
   }

   public CompositeCursorAdapter(Context var1, int var2) {
      this.mSize = 0;
      this.mCount = 0;
      this.mCacheValid = (boolean)1;
      this.mNotificationsEnabled = (boolean)1;
      this.mContext = var1;
      CompositeCursorAdapter.Partition[] var3 = new CompositeCursorAdapter.Partition[2];
      this.mPartitions = var3;
   }

   public void addPartition(CompositeCursorAdapter.Partition var1) {
      int var2 = this.mSize;
      int var3 = this.mPartitions.length;
      if(var2 >= var3) {
         CompositeCursorAdapter.Partition[] var4 = new CompositeCursorAdapter.Partition[this.mSize + 2];
         CompositeCursorAdapter.Partition[] var5 = this.mPartitions;
         int var6 = this.mSize;
         System.arraycopy(var5, 0, var4, 0, var6);
         this.mPartitions = var4;
      }

      CompositeCursorAdapter.Partition[] var7 = this.mPartitions;
      int var8 = this.mSize;
      int var9 = var8 + 1;
      this.mSize = var9;
      var7[var8] = var1;
      this.invalidate();
      this.notifyDataSetChanged();
   }

   public void addPartition(boolean var1, boolean var2) {
      CompositeCursorAdapter.Partition var3 = new CompositeCursorAdapter.Partition(var1, var2);
      this.addPartition(var3);
   }

   public boolean areAllItemsEnabled() {
      int var1 = 0;

      boolean var3;
      while(true) {
         int var2 = this.mSize;
         if(var1 >= var2) {
            var3 = true;
            break;
         }

         if(this.mPartitions[var1].hasHeader) {
            var3 = false;
            break;
         }

         ++var1;
      }

      return var3;
   }

   protected void bindHeaderView(View var1, int var2, Cursor var3) {}

   protected abstract void bindView(View var1, int var2, Cursor var3, int var4);

   public void changeCursor(int var1, Cursor var2) {
      Cursor var3 = this.mPartitions[var1].cursor;
      if(var3 != var2) {
         if(var3 != null && !var3.isClosed()) {
            var3.close();
         }

         this.mPartitions[var1].cursor = var2;
         if(var2 != null) {
            CompositeCursorAdapter.Partition var4 = this.mPartitions[var1];
            int var5 = var2.getColumnIndex("_id");
            var4.idColumnIndex = var5;
         }

         this.invalidate();
         this.notifyDataSetChanged();
      }
   }

   public void clearPartitions() {
      int var1 = 0;

      while(true) {
         int var2 = this.mSize;
         if(var1 >= var2) {
            this.invalidate();
            this.notifyDataSetChanged();
            return;
         }

         this.mPartitions[var1].cursor = null;
         ++var1;
      }
   }

   public void close() {
      int var1 = 0;

      while(true) {
         int var2 = this.mSize;
         if(var1 >= var2) {
            this.mSize = 0;
            this.invalidate();
            this.notifyDataSetChanged();
            return;
         }

         Cursor var3 = this.mPartitions[var1].cursor;
         if(var3 != null && !var3.isClosed()) {
            var3.close();
            this.mPartitions[var1].cursor = null;
         }

         ++var1;
      }
   }

   protected void ensureCacheValid() {
      if(!this.mCacheValid) {
         this.mCount = 0;
         int var1 = 0;

         while(true) {
            int var2 = this.mSize;
            if(var1 >= var2) {
               this.mCacheValid = (boolean)1;
               return;
            }

            Cursor var3 = this.mPartitions[var1].cursor;
            int var4;
            if(var3 != null) {
               var4 = var3.getCount();
            } else {
               var4 = 0;
            }

            if(this.mPartitions[var1].hasHeader && (var4 != 0 || this.mPartitions[var1].showIfEmpty)) {
               ++var4;
            }

            this.mPartitions[var1].count = var4;
            int var5 = this.mCount + var4;
            this.mCount = var5;
            ++var1;
         }
      }
   }

   public Context getContext() {
      return this.mContext;
   }

   public int getCount() {
      this.ensureCacheValid();
      return this.mCount;
   }

   public Cursor getCursor(int var1) {
      return this.mPartitions[var1].cursor;
   }

   protected View getHeaderView(int var1, Cursor var2, View var3, ViewGroup var4) {
      View var5;
      if(var3 != null) {
         var5 = var3;
      } else {
         Context var6 = this.mContext;
         var5 = this.newHeaderView(var6, var1, var2, var4);
      }

      this.bindHeaderView(var5, var1, var2);
      return var5;
   }

   public Object getItem(int var1) {
      Cursor var2 = null;
      this.ensureCacheValid();
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = this.mSize;
         if(var4 >= var5) {
            break;
         }

         int var6 = this.mPartitions[var4].count;
         int var7 = var3 + var6;
         if(var1 >= var3 && var1 < var7) {
            int var8 = var1 - var3;
            if(this.mPartitions[var4].hasHeader) {
               var8 += -1;
            }

            if(var8 != -1) {
               var2 = this.mPartitions[var4].cursor;
               var2.moveToPosition(var8);
            }
            break;
         }

         var3 = var7;
         ++var4;
      }

      return var2;
   }

   public long getItemId(int var1) {
      long var2 = 0L;
      this.ensureCacheValid();
      int var4 = 0;
      int var5 = 0;

      while(true) {
         int var6 = this.mSize;
         if(var5 >= var6) {
            break;
         }

         int var7 = this.mPartitions[var5].count;
         int var8 = var4 + var7;
         if(var1 >= var4 && var1 < var8) {
            int var9 = var1 - var4;
            if(this.mPartitions[var5].hasHeader) {
               var9 += -1;
            }

            if(var9 != -1 && this.mPartitions[var5].idColumnIndex != -1) {
               Cursor var10 = this.mPartitions[var5].cursor;
               if(var10 != null && !var10.isClosed() && var10.moveToPosition(var9)) {
                  int var11 = this.mPartitions[var5].idColumnIndex;
                  var2 = var10.getLong(var11);
               }
            }
            break;
         }

         var4 = var8;
         ++var5;
      }

      return var2;
   }

   public int getItemViewType(int var1) {
      this.ensureCacheValid();
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = this.mSize;
         if(var3 >= var4) {
            throw new ArrayIndexOutOfBoundsException(var1);
         }

         int var5 = this.mPartitions[var3].count;
         int var6 = var2 + var5;
         if(var1 >= var2 && var1 < var6) {
            int var7 = var1 - var2;
            int var8;
            if(this.mPartitions[var3].hasHeader && var7 == 0) {
               var8 = -1;
            } else {
               var8 = this.getItemViewType(var3, var1);
            }

            return var8;
         }

         var2 = var6;
         ++var3;
      }
   }

   protected int getItemViewType(int var1, int var2) {
      return 1;
   }

   public int getItemViewTypeCount() {
      return 1;
   }

   public int getOffsetInPartition(int var1) {
      this.ensureCacheValid();
      int var2 = 0;
      int var3 = 0;

      int var7;
      while(true) {
         int var4 = this.mSize;
         if(var3 >= var4) {
            var7 = -1;
            break;
         }

         int var5 = this.mPartitions[var3].count;
         int var6 = var2 + var5;
         if(var1 >= var2 && var1 < var6) {
            var7 = var1 - var2;
            if(this.mPartitions[var3].hasHeader) {
               var7 += -1;
            }
            break;
         }

         var2 = var6;
         ++var3;
      }

      return var7;
   }

   public CompositeCursorAdapter.Partition getPartition(int var1) {
      int var2 = this.mSize;
      if(var1 >= var2) {
         throw new ArrayIndexOutOfBoundsException(var1);
      } else {
         return this.mPartitions[var1];
      }
   }

   public int getPartitionCount() {
      return this.mSize;
   }

   public int getPartitionForPosition(int var1) {
      this.ensureCacheValid();
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = this.mSize;
         if(var3 >= var4) {
            var3 = -1;
            break;
         }

         int var5 = this.mPartitions[var3].count;
         int var6 = var2 + var5;
         if(var1 >= var2 && var1 < var6) {
            break;
         }

         var2 = var6;
         ++var3;
      }

      return var3;
   }

   public int getPositionForPartition(int var1) {
      this.ensureCacheValid();
      int var2 = 0;

      for(int var3 = 0; var3 < var1; ++var3) {
         int var4 = this.mPartitions[var3].count;
         var2 += var4;
      }

      return var2;
   }

   protected View getView(int var1, Cursor var2, int var3, View var4, ViewGroup var5) {
      View var6;
      if(var4 != null) {
         var6 = var4;
      } else {
         Context var7 = this.mContext;
         var6 = this.newView(var7, var1, var2, var3, var5);
      }

      this.bindView(var6, var1, var2, var3);
      return var6;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      this.ensureCacheValid();
      int var4 = 0;
      int var5 = 0;

      while(true) {
         int var6 = this.mSize;
         if(var5 >= var6) {
            throw new ArrayIndexOutOfBoundsException(var1);
         }

         int var7 = this.mPartitions[var5].count;
         int var8 = var4 + var7;
         if(var1 >= var4 && var1 < var8) {
            int var9 = var1 - var4;
            if(this.mPartitions[var5].hasHeader) {
               var9 += -1;
            }

            View var11;
            if(var9 == -1) {
               Cursor var10 = this.mPartitions[var5].cursor;
               var11 = this.getHeaderView(var5, var10, var2, var3);
            } else {
               if(!this.mPartitions[var5].cursor.moveToPosition(var9)) {
                  String var13 = "Couldn\'t move cursor to position " + var9;
                  throw new IllegalStateException(var13);
               }

               Cursor var14 = this.mPartitions[var5].cursor;
               var11 = this.getView(var5, var14, var9, var2, var3);
            }

            if(var11 == null) {
               String var12 = "View should not be null, partition: " + var5 + " position: " + var9;
               throw new NullPointerException(var12);
            }

            return var11;
         }

         var4 = var8;
         ++var5;
      }
   }

   public int getViewTypeCount() {
      return this.getItemViewTypeCount() + 1;
   }

   public boolean hasHeader(int var1) {
      return this.mPartitions[var1].hasHeader;
   }

   protected void invalidate() {
      this.mCacheValid = (boolean)0;
   }

   public boolean isEnabled(int var1) {
      boolean var2 = false;
      this.ensureCacheValid();
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = this.mSize;
         if(var4 >= var5) {
            break;
         }

         int var6 = this.mPartitions[var4].count;
         int var7 = var3 + var6;
         if(var1 >= var3 && var1 < var7) {
            int var8 = var1 - var3;
            if(!this.mPartitions[var4].hasHeader || var8 != 0) {
               var2 = this.isEnabled(var4, var8);
            }
            break;
         }

         var3 = var7;
         ++var4;
      }

      return var2;
   }

   protected boolean isEnabled(int var1, int var2) {
      return true;
   }

   public boolean isPartitionEmpty(int var1) {
      Cursor var2 = this.mPartitions[var1].cursor;
      boolean var3;
      if(var2 != null && var2.getCount() != 0) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   protected View newHeaderView(Context var1, int var2, Cursor var3, ViewGroup var4) {
      return null;
   }

   protected abstract View newView(Context var1, int var2, Cursor var3, int var4, ViewGroup var5);

   public void notifyDataSetChanged() {
      if(this.mNotificationsEnabled) {
         this.mNotificationNeeded = (boolean)0;
         super.notifyDataSetChanged();
      } else {
         this.mNotificationNeeded = (boolean)1;
      }
   }

   public void removePartition(int var1) {
      Cursor var2 = this.mPartitions[var1].cursor;
      if(var2 != null && !var2.isClosed()) {
         var2.close();
      }

      CompositeCursorAdapter.Partition[] var3 = this.mPartitions;
      int var4 = var1 + 1;
      CompositeCursorAdapter.Partition[] var5 = this.mPartitions;
      int var6 = this.mSize - var1 + -1;
      System.arraycopy(var3, var4, var5, var1, var6);
      int var7 = this.mSize + -1;
      this.mSize = var7;
      this.invalidate();
      this.notifyDataSetChanged();
   }

   public void setHasHeader(int var1, boolean var2) {
      this.mPartitions[var1].hasHeader = var2;
      this.invalidate();
   }

   public void setNotificationsEnabled(boolean var1) {
      this.mNotificationsEnabled = var1;
      if(var1) {
         if(this.mNotificationNeeded) {
            this.notifyDataSetChanged();
         }
      }
   }

   public void setShowIfEmpty(int var1, boolean var2) {
      this.mPartitions[var1].showIfEmpty = var2;
      this.invalidate();
   }

   public static class Partition {

      int count;
      Cursor cursor;
      boolean hasHeader;
      int idColumnIndex;
      boolean showIfEmpty;


      public Partition(boolean var1, boolean var2) {
         this.showIfEmpty = var1;
         this.hasHeader = var2;
      }

      public boolean getHasHeader() {
         return this.hasHeader;
      }

      public boolean getShowIfEmpty() {
         return this.showIfEmpty;
      }
   }
}
