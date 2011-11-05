package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

   private ObservableScrollView.ScrollListener mOnScrollListener;
   private int mTop;


   public ObservableScrollView(Context var1) {
      super(var1);
   }

   public ObservableScrollView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public ObservableScrollView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public int getViewportTop() {
      return this.mTop;
   }

   protected void onScrollChanged(int var1, int var2, int var3, int var4) {
      super.onScrollChanged(var1, var2, var3, var4);
      this.mTop = var2;
      if(this.mOnScrollListener != null) {
         this.mOnScrollListener.onScroll(var1, var2);
      }
   }

   public void setOnScrollListener(ObservableScrollView.ScrollListener var1) {
      this.mOnScrollListener = var1;
   }

   public interface ScrollListener {

      void onScroll(int var1, int var2);
   }
}
