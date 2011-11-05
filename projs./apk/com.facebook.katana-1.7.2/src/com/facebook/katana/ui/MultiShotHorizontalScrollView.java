package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class MultiShotHorizontalScrollView extends HorizontalScrollView {

   private MultiShotHorizontalScrollView.ScrollViewListener mScrollListener;


   public MultiShotHorizontalScrollView(Context var1) {
      super(var1);
   }

   public MultiShotHorizontalScrollView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public MultiShotHorizontalScrollView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void onScrollChanged(int var1, int var2, int var3, int var4) {
      super.onScrollChanged(var1, var2, var3, var4);
      if(this.mScrollListener != null) {
         this.mScrollListener.onScrollChanged(var1, var2, var3, var4);
      }
   }

   public void setScrollViewListener(MultiShotHorizontalScrollView.ScrollViewListener var1) {
      this.mScrollListener = var1;
   }

   public interface ScrollViewListener {

      void onScrollChanged(int var1, int var2, int var3, int var4);
   }
}
