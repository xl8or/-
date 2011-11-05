package custom.android;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewDebug.CapturedViewProperty;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Adapter;

public abstract class AdapterView<T extends Object & Adapter> extends ViewGroup {

   public static final int INVALID_POSITION = 255;
   public static final long INVALID_ROW_ID = Long.MIN_VALUE;
   public static final int ITEM_VIEW_TYPE_HEADER_OR_FOOTER = 254;
   public static final int ITEM_VIEW_TYPE_IGNORE = 255;
   static final int SYNC_FIRST_POSITION = 1;
   static final int SYNC_MAX_DURATION_MILLIS = 100;
   static final int SYNC_SELECTED_POSITION;
   boolean mBlockLayoutRequests = 0;
   boolean mDataChanged;
   private boolean mDesiredFocusableInTouchModeState;
   private boolean mDesiredFocusableState;
   private View mEmptyView;
   int mFirstPosition = 0;
   boolean mInLayout = 0;
   int mItemCount;
   private int mLayoutHeight;
   boolean mNeedSync = 0;
   int mNextSelectedPosition = -1;
   long mNextSelectedRowId = Long.MIN_VALUE;
   int mOldItemCount;
   int mOldSelectedPosition = -1;
   long mOldSelectedRowId = Long.MIN_VALUE;
   AdapterView.OnItemClickListener mOnItemClickListener;
   AdapterView.OnItemLongClickListener mOnItemLongClickListener;
   AdapterView.OnItemSelectedListener mOnItemSelectedListener;
   int mSelectedPosition = -1;
   long mSelectedRowId = Long.MIN_VALUE;
   private AdapterView.SelectionNotifier mSelectionNotifier;
   int mSpecificTop;
   long mSyncHeight;
   int mSyncMode;
   int mSyncPosition;
   long mSyncRowId = Long.MIN_VALUE;


   public AdapterView(Context var1) {
      super(var1);
   }

   public AdapterView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public AdapterView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   private void fireOnSelected() {
      if(this.mOnItemSelectedListener != null) {
         int var1 = this.getSelectedItemPosition();
         if(var1 >= 0) {
            View var2 = this.getSelectedView();
            AdapterView.OnItemSelectedListener var3 = this.mOnItemSelectedListener;
            long var4 = this.getAdapter().getItemId(var1);
            var3.onItemSelected(this, var2, var1, var4);
         } else {
            this.mOnItemSelectedListener.onNothingSelected(this);
         }
      }
   }

   private void updateEmptyStatus(boolean var1) {
      if(this.isInFilterMode()) {
         var1 = false;
      }

      if(var1) {
         if(this.mEmptyView != null) {
            this.mEmptyView.setVisibility(0);
            this.setVisibility(8);
         } else {
            this.setVisibility(0);
         }

         if(this.mDataChanged) {
            int var2 = this.getLeft();
            int var3 = this.getTop();
            int var4 = this.getRight();
            int var5 = this.getBottom();
            this.onLayout((boolean)0, var2, var3, var4, var5);
         }
      } else {
         if(this.mEmptyView != null) {
            this.mEmptyView.setVisibility(8);
         }

         this.setVisibility(0);
      }
   }

   public void addView(View var1) {
      throw new UnsupportedOperationException("addView(View) is not supported in AdapterView");
   }

   public void addView(View var1, int var2) {
      throw new UnsupportedOperationException("addView(View, int) is not supported in AdapterView");
   }

   public void addView(View var1, int var2, LayoutParams var3) {
      throw new UnsupportedOperationException("addView(View, int, LayoutParams) is not supported in AdapterView");
   }

   public void addView(View var1, LayoutParams var2) {
      throw new UnsupportedOperationException("addView(View, LayoutParams) is not supported in AdapterView");
   }

   protected boolean canAnimate() {
      boolean var1;
      if(super.canAnimate() && this.mItemCount > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void checkFocus() {
      Adapter var1 = this.getAdapter();
      boolean var2;
      if(var1 != null && var1.getCount() != 0) {
         var2 = false;
      } else {
         var2 = true;
      }

      boolean var3;
      if(var2 && !this.isInFilterMode()) {
         var3 = false;
      } else {
         var3 = true;
      }

      byte var4;
      if(var3 && this.mDesiredFocusableInTouchModeState) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      super.setFocusableInTouchMode((boolean)var4);
      byte var5;
      if(var3 && this.mDesiredFocusableState) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      super.setFocusable((boolean)var5);
      if(this.mEmptyView != null) {
         byte var6;
         if(var1 != null && !var1.isEmpty()) {
            var6 = 0;
         } else {
            var6 = 1;
         }

         this.updateEmptyStatus((boolean)var6);
      }
   }

   void checkSelectionChanged() {
      int var1 = this.mSelectedPosition;
      int var2 = this.mOldSelectedPosition;
      if(var1 == var2) {
         long var3 = this.mSelectedRowId;
         long var5 = this.mOldSelectedRowId;
         if(var3 == var5) {
            return;
         }
      }

      this.selectionChanged();
      int var7 = this.mSelectedPosition;
      this.mOldSelectedPosition = var7;
      long var8 = this.mSelectedRowId;
      this.mOldSelectedRowId = var8;
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      boolean var2 = false;
      if(var1.getEventType() == 8) {
         var1.setEventType(4);
      }

      View var3 = this.getSelectedView();
      if(var3 != null) {
         var2 = var3.dispatchPopulateAccessibilityEvent(var1);
      }

      if(!var2) {
         if(var3 != null) {
            boolean var4 = var3.isEnabled();
            var1.setEnabled(var4);
         }

         int var5 = this.getCount();
         var1.setItemCount(var5);
         int var6 = this.getSelectedItemPosition();
         var1.setCurrentItemIndex(var6);
      }

      return var2;
   }

   protected void dispatchRestoreInstanceState(SparseArray<Parcelable> var1) {
      this.dispatchThawSelfOnly(var1);
   }

   protected void dispatchSaveInstanceState(SparseArray<Parcelable> var1) {
      this.dispatchFreezeSelfOnly(var1);
   }

   int findSyncPosition() {
      // $FF: Couldn't be decompiled
   }

   public abstract T getAdapter();

   @CapturedViewProperty
   public int getCount() {
      return this.mItemCount;
   }

   public View getEmptyView() {
      return this.mEmptyView;
   }

   public int getFirstVisiblePosition() {
      return this.mFirstPosition;
   }

   public Object getItemAtPosition(int var1) {
      Adapter var2 = this.getAdapter();
      Object var3;
      if(var2 != null && var1 >= 0) {
         var3 = var2.getItem(var1);
      } else {
         var3 = null;
      }

      return var3;
   }

   public long getItemIdAtPosition(int var1) {
      Adapter var2 = this.getAdapter();
      long var3;
      if(var2 != null && var1 >= 0) {
         var3 = var2.getItemId(var1);
      } else {
         var3 = Long.MIN_VALUE;
      }

      return var3;
   }

   public int getLastVisiblePosition() {
      int var1 = this.mFirstPosition;
      int var2 = this.getChildCount();
      return var1 + var2 - 1;
   }

   public final AdapterView.OnItemClickListener getOnItemClickListener() {
      return this.mOnItemClickListener;
   }

   public final AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
      return this.mOnItemLongClickListener;
   }

   public final AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
      return this.mOnItemSelectedListener;
   }

   public int getPositionForView(View var1) {
      View var2 = var1;

      int var6;
      while(true) {
         View var3;
         boolean var4;
         try {
            var3 = (View)var2.getParent();
            var4 = var3.equals(this);
         } catch (ClassCastException var9) {
            var6 = -1;
            break;
         }

         if(var4) {
            int var7 = this.getChildCount();

            for(int var8 = 0; var8 < var7; ++var8) {
               if(this.getChildAt(var8).equals(var2)) {
                  var6 = this.mFirstPosition + var8;
                  return var6;
               }
            }

            var6 = -1;
            break;
         }

         var2 = var3;
      }

      return var6;
   }

   public Object getSelectedItem() {
      Adapter var1 = this.getAdapter();
      int var2 = this.getSelectedItemPosition();
      Object var3;
      if(var1 != null && var1.getCount() > 0 && var2 >= 0) {
         var3 = var1.getItem(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   @CapturedViewProperty
   public long getSelectedItemId() {
      return this.mNextSelectedRowId;
   }

   @CapturedViewProperty
   public int getSelectedItemPosition() {
      return this.mNextSelectedPosition;
   }

   public abstract View getSelectedView();

   void handleDataChanged() {
      int var1 = this.mItemCount;
      boolean var2 = false;
      if(var1 > 0) {
         int var3;
         if(this.mNeedSync) {
            this.mNeedSync = (boolean)0;
            var3 = this.findSyncPosition();
            if(var3 >= 0 && this.lookForSelectablePosition(var3, (boolean)1) == var3) {
               this.setNextSelectedPositionInt(var3);
               var2 = true;
            }
         }

         if(!var2) {
            var3 = this.getSelectedItemPosition();
            if(var3 >= var1) {
               int var4 = var1 - 1;
            }

            if(var3 < 0) {
               ;
            }

            int var5 = this.lookForSelectablePosition(var3, (boolean)1);
            if(var5 < 0) {
               this.lookForSelectablePosition(var3, (boolean)0);
            }

            if(var5 >= 0) {
               this.setNextSelectedPositionInt(var5);
               this.checkSelectionChanged();
               var2 = true;
            }
         }
      }

      if(!var2) {
         this.mSelectedPosition = -1;
         this.mSelectedRowId = Long.MIN_VALUE;
         this.mNextSelectedPosition = -1;
         this.mNextSelectedRowId = Long.MIN_VALUE;
         this.mNeedSync = (boolean)0;
         this.checkSelectionChanged();
      }
   }

   boolean isInFilterMode() {
      return false;
   }

   int lookForSelectablePosition(int var1, boolean var2) {
      return var1;
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      AdapterView.SelectionNotifier var1 = this.mSelectionNotifier;
      this.removeCallbacks(var1);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = this.getHeight();
      this.mLayoutHeight = var6;
   }

   public boolean performItemClick(View var1, int var2, long var3) {
      boolean var11;
      if(this.mOnItemClickListener != null) {
         this.playSoundEffect(0);
         AdapterView.OnItemClickListener var5 = this.mOnItemClickListener;
         var5.onItemClick(this, var1, var2, var3);
         var11 = true;
      } else {
         var11 = false;
      }

      return var11;
   }

   void rememberSyncState() {
      if(this.getChildCount() > 0) {
         this.mNeedSync = (boolean)1;
         long var1 = (long)this.mLayoutHeight;
         this.mSyncHeight = var1;
         View var6;
         if(this.mSelectedPosition >= 0) {
            int var3 = this.mSelectedPosition;
            int var4 = this.mFirstPosition;
            int var5 = var3 - var4;
            var6 = this.getChildAt(var5);
            long var7 = this.mNextSelectedRowId;
            this.mSyncRowId = var7;
            int var9 = this.mNextSelectedPosition;
            this.mSyncPosition = var9;
            if(var6 != null) {
               int var10 = var6.getTop();
               this.mSpecificTop = var10;
            }

            this.mSyncMode = 0;
         } else {
            label25: {
               var6 = this.getChildAt(0);
               Adapter var11 = this.getAdapter();
               if(this.mFirstPosition >= 0) {
                  int var12 = this.mFirstPosition;
                  int var13 = var11.getCount();
                  if(var12 < var13) {
                     int var14 = this.mFirstPosition;
                     long var15 = var11.getItemId(var14);
                     this.mSyncRowId = var15;
                     break label25;
                  }
               }

               this.mSyncRowId = 65535L;
            }

            int var17 = this.mFirstPosition;
            this.mSyncPosition = var17;
            if(var6 != null) {
               int var18 = var6.getTop();
               this.mSpecificTop = var18;
            }

            this.mSyncMode = 1;
         }
      }
   }

   public void removeAllViews() {
      throw new UnsupportedOperationException("removeAllViews() is not supported in AdapterView");
   }

   public void removeView(View var1) {
      throw new UnsupportedOperationException("removeView(View) is not supported in AdapterView");
   }

   public void removeViewAt(int var1) {
      throw new UnsupportedOperationException("removeViewAt(int) is not supported in AdapterView");
   }

   void selectionChanged() {
      if(this.mOnItemSelectedListener != null) {
         if(!this.mInLayout && !this.mBlockLayoutRequests) {
            this.fireOnSelected();
         } else {
            if(this.mSelectionNotifier == null) {
               AdapterView.SelectionNotifier var1 = new AdapterView.SelectionNotifier((AdapterView.1)null);
               this.mSelectionNotifier = var1;
            }

            AdapterView.SelectionNotifier var2 = this.mSelectionNotifier;
            this.post(var2);
         }
      }

      if(this.mSelectedPosition != -1) {
         if(this.isShown()) {
            if(!this.isInTouchMode()) {
               this.sendAccessibilityEvent(4);
            }
         }
      }
   }

   public abstract void setAdapter(T var1);

   public void setEmptyView(View var1) {
      this.mEmptyView = var1;
      Adapter var2 = this.getAdapter();
      byte var3;
      if(var2 != null && !var2.isEmpty()) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      this.updateEmptyStatus((boolean)var3);
   }

   public void setFocusable(boolean var1) {
      Adapter var2 = this.getAdapter();
      boolean var3;
      if(var2 != null && var2.getCount() != 0) {
         var3 = false;
      } else {
         var3 = true;
      }

      this.mDesiredFocusableState = var1;
      if(!var1) {
         this.mDesiredFocusableInTouchModeState = (boolean)0;
      }

      byte var4;
      if(var1 && (!var3 || this.isInFilterMode())) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      super.setFocusable((boolean)var4);
   }

   public void setFocusableInTouchMode(boolean var1) {
      Adapter var2 = this.getAdapter();
      boolean var3;
      if(var2 != null && var2.getCount() != 0) {
         var3 = false;
      } else {
         var3 = true;
      }

      this.mDesiredFocusableInTouchModeState = var1;
      if(var1) {
         this.mDesiredFocusableState = (boolean)1;
      }

      byte var4;
      if(var1 && (!var3 || this.isInFilterMode())) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      super.setFocusableInTouchMode((boolean)var4);
   }

   void setNextSelectedPositionInt(int var1) {
      this.mNextSelectedPosition = var1;
      long var2 = this.getItemIdAtPosition(var1);
      this.mNextSelectedRowId = var2;
      if(this.mNeedSync) {
         if(this.mSyncMode == 0) {
            if(var1 >= 0) {
               this.mSyncPosition = var1;
               long var4 = this.mNextSelectedRowId;
               this.mSyncRowId = var4;
            }
         }
      }
   }

   public void setOnClickListener(OnClickListener var1) {
      throw new RuntimeException("Don\'t call setOnClickListener for an AdapterView. You probably want setOnItemClickListener instead");
   }

   public void setOnItemClickListener(AdapterView.OnItemClickListener var1) {
      this.mOnItemClickListener = var1;
   }

   public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener var1) {
      if(!this.isLongClickable()) {
         this.setLongClickable((boolean)1);
      }

      this.mOnItemLongClickListener = var1;
   }

   public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener var1) {
      this.mOnItemSelectedListener = var1;
   }

   void setSelectedPositionInt(int var1) {
      this.mSelectedPosition = var1;
      long var2 = this.getItemIdAtPosition(var1);
      this.mSelectedRowId = var2;
   }

   public abstract void setSelection(int var1);

   public static class AdapterContextMenuInfo implements ContextMenuInfo {

      public long id;
      public int position;
      public View targetView;


      public AdapterContextMenuInfo(View var1, int var2, long var3) {
         this.targetView = var1;
         this.position = var2;
         this.id = var3;
      }
   }

   private class SelectionNotifier implements Runnable {

      private SelectionNotifier() {}

      // $FF: synthetic method
      SelectionNotifier(AdapterView.1 var2) {
         this();
      }

      public void run() {
         if(AdapterView.this.mDataChanged) {
            if(AdapterView.this.getAdapter() != null) {
               boolean var1 = AdapterView.this.post(this);
            }
         } else {
            AdapterView.this.fireOnSelected();
         }
      }
   }

   public interface OnItemSelectedListener {

      void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4);

      void onNothingSelected(AdapterView<?> var1);
   }

   public interface OnItemClickListener {

      void onItemClick(AdapterView<?> var1, View var2, int var3, long var4);
   }

   // $FF: synthetic class
   static class 1 {
   }

   class AdapterDataSetObserver extends DataSetObserver {

      private Parcelable mInstanceState = null;


      AdapterDataSetObserver() {}

      public void clearSavedState() {
         this.mInstanceState = null;
      }

      public void onChanged() {
         AdapterView.this.mDataChanged = (boolean)1;
         AdapterView var1 = AdapterView.this;
         int var2 = AdapterView.this.mItemCount;
         var1.mOldItemCount = var2;
         AdapterView var3 = AdapterView.this;
         int var4 = AdapterView.this.getAdapter().getCount();
         var3.mItemCount = var4;
         if(AdapterView.this.getAdapter().hasStableIds() && this.mInstanceState != null && AdapterView.this.mOldItemCount == 0 && AdapterView.this.mItemCount > 0) {
            AdapterView var5 = AdapterView.this;
            Parcelable var6 = this.mInstanceState;
            var5.onRestoreInstanceState(var6);
            this.mInstanceState = null;
         } else {
            AdapterView.this.rememberSyncState();
         }

         AdapterView.this.checkFocus();
         AdapterView.this.requestLayout();
      }

      public void onInvalidated() {
         AdapterView.this.mDataChanged = (boolean)1;
         if(AdapterView.this.getAdapter().hasStableIds()) {
            Parcelable var1 = AdapterView.this.onSaveInstanceState();
            this.mInstanceState = var1;
         }

         AdapterView var2 = AdapterView.this;
         int var3 = AdapterView.this.mItemCount;
         var2.mOldItemCount = var3;
         AdapterView.this.mItemCount = 0;
         AdapterView.this.mSelectedPosition = -1;
         AdapterView.this.mSelectedRowId = Long.MIN_VALUE;
         AdapterView.this.mNextSelectedPosition = -1;
         AdapterView.this.mNextSelectedRowId = Long.MIN_VALUE;
         AdapterView.this.mNeedSync = (boolean)0;
         AdapterView.this.checkFocus();
         AdapterView.this.requestLayout();
      }
   }

   public interface OnItemLongClickListener {

      boolean onItemLongClick(AdapterView<?> var1, View var2, int var3, long var4);
   }
}
