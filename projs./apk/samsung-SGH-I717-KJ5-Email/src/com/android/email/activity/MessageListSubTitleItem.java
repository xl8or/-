package com.android.email.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.android.email.activity.MessageList;

public class MessageListSubTitleItem extends LinearLayout {

   private final int LONG_PRESS_TIMEOUT = 500;
   private final int SHOW_LISTBY_DIALOG = 1001;
   private boolean isItemLongPressed;
   private float itemBottom = 0.0F;
   private float itemTop = 0.0F;
   private MessageList.MessageListAdapter mAdapter;
   private boolean mCachedViewPositions;
   private boolean mDownEvent;
   private boolean mIsLongClickEvent;
   private boolean mSpread = 1;
   private float yPosition = 0.0F;


   public MessageListSubTitleItem(Context var1) {
      super(var1);
   }

   public MessageListSubTitleItem(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public void bindViewInit(MessageList.MessageListAdapter var1) {
      this.mAdapter = var1;
      this.mCachedViewPositions = (boolean)0;
   }

   public boolean getSpread() {
      return this.mSpread;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      byte var2 = 0;
      LinearLayout var3 = (LinearLayout)this.findViewById(2131362421);
      if(!this.mCachedViewPositions) {
         this.mCachedViewPositions = (boolean)1;
      }

      switch(var1.getAction()) {
      case 0:
         var3.setBackgroundResource(2130837819);
         if(this.mAdapter != null) {
            this.mAdapter.updateSubTitleItemBackground(this);
         }

         var2 = 1;
         break;
      case 1:
         this.playSoundEffect(0);
         var3.setBackgroundResource(2130837817);
         byte var4;
         if(!this.mSpread) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         this.mSpread = (boolean)var4;
         if(this.mAdapter != null) {
            this.mAdapter.updateArrow(this);
         }

         var2 = 1;
         break;
      case 2:
         this.isItemLongPressed = (boolean)0;
         break;
      case 3:
         var3.setBackgroundResource(2130837817);
         this.mDownEvent = (boolean)0;
      }

      if(var2 != 0) {
         this.postInvalidate();
      } else {
         var2 = super.onTouchEvent(var1);
      }

      return (boolean)var2;
   }

   public void setSpread(boolean var1) {
      this.mSpread = var1;
   }
}
