package com.facebook.orca.common.ui.widgets.refreshablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import com.facebook.orca.common.ui.widgets.refreshablelistview.RefreshableListViewContainer;

public class RefreshableListView extends ListView {

   private static final String TAG = "PullToRefreshListView";
   private OnScrollListener onScrollListener;


   public RefreshableListView(Context var1) {
      super(var1);
      this.init(var1);
   }

   public RefreshableListView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var1);
   }

   public RefreshableListView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var1);
   }

   private void init(Context var1) {
      RefreshableListView.1 var2 = new RefreshableListView.1();
      super.setOnScrollListener(var2);
   }

   private void onScroll(int var1, int var2, int var3) {
      if(this.onScrollListener != null) {
         this.onScrollListener.onScroll(this, var1, var2, var3);
      }
   }

   private void onScrollStateChanged(int var1) {
      ((RefreshableListViewContainer)this.getParent()).onScrollStateChanged(var1);
      if(this.onScrollListener != null) {
         this.onScrollListener.onScrollStateChanged(this, var1);
      }
   }

   public void setOnScrollListener(OnScrollListener var1) {
      this.onScrollListener = var1;
   }

   class 1 implements OnScrollListener {

      1() {}

      public void onScroll(AbsListView var1, int var2, int var3, int var4) {
         RefreshableListView.this.onScroll(var2, var3, var4);
      }

      public void onScrollStateChanged(AbsListView var1, int var2) {
         RefreshableListView.this.onScrollStateChanged(var2);
      }
   }
}
