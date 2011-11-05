package com.htc.android.mail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import com.android.internal.R.styleable;

public class SynchronizationListItem extends RelativeLayout {

   private CheckedTextView mText1;


   public SynchronizationListItem(Context var1) {
      this(var1, (AttributeSet)null, 0);
   }

   public SynchronizationListItem(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public SynchronizationListItem(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      int[] var4 = styleable.TwoLineListItem;
      var1.obtainStyledAttributes(var2, var4, var3, 0).recycle();
   }

   public CheckedTextView getText1() {
      return this.mText1;
   }

   protected void onFinishInflate() {
      super.onFinishInflate();
      CheckedTextView var1 = (CheckedTextView)this.findViewById(2131296461);
      this.mText1 = var1;
   }
}
