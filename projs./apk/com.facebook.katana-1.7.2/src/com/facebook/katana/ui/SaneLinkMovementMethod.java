package com.facebook.katana.ui;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class SaneLinkMovementMethod extends LinkMovementMethod {

   private static SaneLinkMovementMethod sInstance;


   public SaneLinkMovementMethod() {}

   public static MovementMethod getInstance() {
      if(sInstance == null) {
         sInstance = new SaneLinkMovementMethod();
      }

      return sInstance;
   }

   public boolean onTouchEvent(TextView var1, Spannable var2, MotionEvent var3) {
      int var4 = var3.getAction();
      boolean var20;
      if(var4 == 1 || var4 == 0) {
         int var5 = (int)var3.getX();
         int var6 = (int)var3.getY();
         int var7 = var1.getTotalPaddingLeft();
         int var8 = var5 - var7;
         int var9 = var1.getTotalPaddingTop();
         int var10 = var6 - var9;
         int var11 = var1.getScrollX();
         int var12 = var8 + var11;
         int var13 = var1.getScrollY();
         int var14 = var10 + var13;
         Layout var15 = var1.getLayout();
         int var16 = var15.getLineForVertical(var14);
         float var17 = (float)var12;
         int var18 = var15.getOffsetForHorizontal(var16, var17);
         ClickableSpan[] var19 = (ClickableSpan[])var2.getSpans(var18, var18, ClickableSpan.class);
         if(var19.length != 0) {
            if(var4 == 1) {
               var19[0].onClick(var1);
            }

            var20 = true;
            return var20;
         }
      }

      var20 = false;
      return var20;
   }
}
