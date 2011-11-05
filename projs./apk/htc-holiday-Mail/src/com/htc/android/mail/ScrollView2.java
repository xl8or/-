package com.htc.android.mail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;

public class ScrollView2 extends ScrollView {

   final String TAG = "ScrollView2";
   private AutoCompleteTextView editTo = null;
   private AutoCompleteTextView edit_bcc = null;
   private AutoCompleteTextView edit_cc = null;
   private int mMode = 0;


   public ScrollView2(Context var1) {
      super(var1);
   }

   public ScrollView2(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public ScrollView2(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public int getMode() {
      return this.mMode;
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      byte var2;
      if(this.mMode == 1) {
         switch(var1.getAction()) {
         case 1:
            this.mMode = 0;
            break;
         default:
            var2 = 0;
            return (boolean)var2;
         }
      }

      var2 = super.onInterceptTouchEvent(var1);
      return (boolean)var2;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      return super.onTouchEvent(var1);
   }

   public void setMode(int var1) {
      this.mMode = var1;
   }
}
