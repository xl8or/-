package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v4.widget.CursorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

public abstract class CursorAdapter extends BaseAdapter implements Filterable, CursorFilter.CursorFilterClient {

   @Deprecated
   public static final int FLAG_AUTO_REQUERY = 1;
   public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
   protected boolean mAutoRequery;
   protected CursorAdapter.ChangeObserver mChangeObserver;
   protected Context mContext;
   protected Cursor mCursor;
   protected CursorFilter mCursorFilter;
   protected DataSetObserver mDataSetObserver;
   protected boolean mDataValid;
   protected FilterQueryProvider mFilterQueryProvider;
   protected int mRowIDColumn;


   @Deprecated
   public CursorAdapter(Context var1, Cursor var2) {
      this.init(var1, var2, 1);
   }

   public CursorAdapter(Context var1, Cursor var2, int var3) {
      this.init(var1, var2, var3);
   }

   public CursorAdapter(Context var1, Cursor var2, boolean var3) {
      byte var4;
      if(var3) {
         var4 = 1;
      } else {
         var4 = 2;
      }

      this.init(var1, var2, var4);
   }

   public abstract void bindView(View var1, Context var2, Cursor var3);

   public void changeCursor(Cursor var1) {
      Cursor var2 = this.swapCursor(var1);
      if(var2 != null) {
         var2.close();
      }
   }

   public CharSequence convertToString(Cursor var1) {
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         var2 = var1.toString();
      }

      return var2;
   }

   public int getCount() {
      int var1;
      if(this.mDataValid && this.mCursor != null) {
         var1 = this.mCursor.getCount();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public Cursor getCursor() {
      return this.mCursor;
   }

   public View getDropDownView(int var1, View var2, ViewGroup var3) {
      View var7;
      if(this.mDataValid) {
         this.mCursor.moveToPosition(var1);
         if(var2 == null) {
            Context var5 = this.mContext;
            Cursor var6 = this.mCursor;
            var7 = this.newDropDownView(var5, var6, var3);
         } else {
            var7 = var2;
         }

         Context var8 = this.mContext;
         Cursor var9 = this.mCursor;
         this.bindView(var7, var8, var9);
      } else {
         var7 = null;
      }

      return var7;
   }

   public Filter getFilter() {
      if(this.mCursorFilter == null) {
         CursorFilter var1 = new CursorFilter(this);
         this.mCursorFilter = var1;
      }

      return this.mCursorFilter;
   }

   public FilterQueryProvider getFilterQueryProvider() {
      return this.mFilterQueryProvider;
   }

   public Object getItem(int var1) {
      Cursor var3;
      if(this.mDataValid && this.mCursor != null) {
         this.mCursor.moveToPosition(var1);
         var3 = this.mCursor;
      } else {
         var3 = null;
      }

      return var3;
   }

   public long getItemId(int var1) {
      long var2 = 0L;
      if(this.mDataValid && this.mCursor != null && this.mCursor.moveToPosition(var1)) {
         Cursor var4 = this.mCursor;
         int var5 = this.mRowIDColumn;
         var2 = var4.getLong(var5);
      }

      return var2;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      if(!this.mDataValid) {
         throw new IllegalStateException("this should only be called when the cursor is valid");
      } else if(!this.mCursor.moveToPosition(var1)) {
         String var4 = "couldn\'t move cursor to position " + var1;
         throw new IllegalStateException(var4);
      } else {
         View var7;
         if(var2 == null) {
            Context var5 = this.mContext;
            Cursor var6 = this.mCursor;
            var7 = this.newView(var5, var6, var3);
         } else {
            var7 = var2;
         }

         Context var8 = this.mContext;
         Cursor var9 = this.mCursor;
         this.bindView(var7, var8, var9);
         return var7;
      }
   }

   public boolean hasStableIds() {
      return true;
   }

   void init(Context var1, Cursor var2, int var3) {
      byte var4 = 1;
      if((var3 & 1) == var4) {
         var3 |= 2;
         this.mAutoRequery = (boolean)1;
      } else {
         this.mAutoRequery = (boolean)0;
      }

      if(var2 == null) {
         var4 = 0;
      }

      this.mCursor = var2;
      this.mDataValid = (boolean)var4;
      this.mContext = var1;
      int var5;
      if(var4 != 0) {
         var5 = var2.getColumnIndexOrThrow("_id");
      } else {
         var5 = -1;
      }

      this.mRowIDColumn = var5;
      if((var3 & 2) == 2) {
         CursorAdapter.ChangeObserver var6 = new CursorAdapter.ChangeObserver();
         this.mChangeObserver = var6;
         CursorAdapter.MyDataSetObserver var7 = new CursorAdapter.MyDataSetObserver((CursorAdapter.1)null);
         this.mDataSetObserver = var7;
      } else {
         this.mChangeObserver = null;
         this.mDataSetObserver = null;
      }

      if(var4 != 0) {
         if(this.mChangeObserver != null) {
            CursorAdapter.ChangeObserver var8 = this.mChangeObserver;
            var2.registerContentObserver(var8);
         }

         if(this.mDataSetObserver != null) {
            DataSetObserver var9 = this.mDataSetObserver;
            var2.registerDataSetObserver(var9);
         }
      }
   }

   @Deprecated
   protected void init(Context var1, Cursor var2, boolean var3) {
      byte var4;
      if(var3) {
         var4 = 1;
      } else {
         var4 = 2;
      }

      this.init(var1, var2, var4);
   }

   public View newDropDownView(Context var1, Cursor var2, ViewGroup var3) {
      return this.newView(var1, var2, var3);
   }

   public abstract View newView(Context var1, Cursor var2, ViewGroup var3);

   protected void onContentChanged() {
      if(this.mAutoRequery) {
         if(this.mCursor != null) {
            if(!this.mCursor.isClosed()) {
               boolean var1 = this.mCursor.requery();
               this.mDataValid = var1;
            }
         }
      }
   }

   public Cursor runQueryOnBackgroundThread(CharSequence var1) {
      Cursor var2;
      if(this.mFilterQueryProvider != null) {
         var2 = this.mFilterQueryProvider.runQuery(var1);
      } else {
         var2 = this.mCursor;
      }

      return var2;
   }

   public void setFilterQueryProvider(FilterQueryProvider var1) {
      this.mFilterQueryProvider = var1;
   }

   public Cursor swapCursor(Cursor var1) {
      Cursor var2 = this.mCursor;
      Cursor var3;
      if(var1 == var2) {
         var3 = null;
      } else {
         var3 = this.mCursor;
         if(var3 != null) {
            if(this.mChangeObserver != null) {
               CursorAdapter.ChangeObserver var4 = this.mChangeObserver;
               var3.unregisterContentObserver(var4);
            }

            if(this.mDataSetObserver != null) {
               DataSetObserver var5 = this.mDataSetObserver;
               var3.unregisterDataSetObserver(var5);
            }
         }

         this.mCursor = var1;
         if(var1 != null) {
            if(this.mChangeObserver != null) {
               CursorAdapter.ChangeObserver var6 = this.mChangeObserver;
               var1.registerContentObserver(var6);
            }

            if(this.mDataSetObserver != null) {
               DataSetObserver var7 = this.mDataSetObserver;
               var1.registerDataSetObserver(var7);
            }

            int var8 = var1.getColumnIndexOrThrow("_id");
            this.mRowIDColumn = var8;
            this.mDataValid = (boolean)1;
            this.notifyDataSetChanged();
         } else {
            this.mRowIDColumn = -1;
            this.mDataValid = (boolean)0;
            this.notifyDataSetInvalidated();
         }
      }

      return var3;
   }

   private class ChangeObserver extends ContentObserver {

      public ChangeObserver() {
         Handler var2 = new Handler();
         super(var2);
      }

      public boolean deliverSelfNotifications() {
         return true;
      }

      public void onChange(boolean var1) {
         CursorAdapter.this.onContentChanged();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class MyDataSetObserver extends DataSetObserver {

      private MyDataSetObserver() {}

      // $FF: synthetic method
      MyDataSetObserver(CursorAdapter.1 var2) {
         this();
      }

      public void onChanged() {
         CursorAdapter.this.mDataValid = (boolean)1;
         CursorAdapter.this.notifyDataSetChanged();
      }

      public void onInvalidated() {
         CursorAdapter.this.mDataValid = (boolean)0;
         CursorAdapter.this.notifyDataSetInvalidated();
      }
   }
}
