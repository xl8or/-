package com.android.email.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.android.email.activity.MessageList;

public class MessageListItem extends RelativeLayout {

   private static final float CHECKMARK_PAD = 10.0F;
   private static final float STAR_PAD = 10.0F;
   public long mAccountId;
   private MessageList.MessageListAdapter mAdapter;
   private boolean mAllowBatch;
   private boolean mCachedViewPositions;
   private int mCheckLeft;
   private int mCheckRight;
   private boolean mDownEvent;
   public boolean mFavorite;
   private boolean mIsCheckClicked;
   public long mMailboxId;
   public long mMessageId;
   public boolean mRead;
   public boolean mSelected;
   private int mStarLeft;
   private int mStarRight;


   public MessageListItem(Context var1) {
      super(var1);
   }

   public MessageListItem(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public MessageListItem(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public void bindViewInit(MessageList.MessageListAdapter var1, boolean var2) {
      this.mAdapter = var1;
      this.mAllowBatch = var2;
      this.mCachedViewPositions = (boolean)0;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      byte var2;
      if(this.getParent() == null) {
         var2 = super.onTouchEvent(var1);
      } else {
         byte var3 = 0;
         int var4 = (int)var1.getX();
         if(!this.mCachedViewPositions) {
            float var5 = this.getContext().getResources().getDisplayMetrics().density;
            int var6 = (int)((double)(10.0F * var5) + 0.5D);
            int var7 = (int)((double)(10.0F * var5) + 0.5D);
            byte var12;
            if(this.shouldMirror()) {
               int var8 = this.findViewById(2131558493).getLeft() - var7;
               this.mStarLeft = var8;
               int var9 = this.findViewById(2131558492).getLeft() + var6;
               this.mCheckLeft = var9;
               int var10 = this.findViewById(2131558493).getRight() - var7;
               this.mStarRight = var10;
               int var11 = this.mCheckLeft;
               if(var4 > var11) {
                  var12 = 1;
               } else {
                  var12 = 0;
               }

               this.mIsCheckClicked = (boolean)var12;
            } else {
               int var13 = this.findViewById(2131558492).getRight() + var6;
               this.mCheckRight = var13;
               int var14 = this.findViewById(2131558493).getLeft() - var7;
               this.mStarLeft = var14;
               int var15 = this.mCheckRight;
               if(var4 < var15) {
                  var12 = 1;
               } else {
                  var12 = 0;
               }

               this.mIsCheckClicked = (boolean)var12;
            }

            this.mCachedViewPositions = (boolean)1;
         }

         switch(var1.getAction()) {
         case 0:
            this.mDownEvent = (boolean)1;
            if(this.mAllowBatch && this.mIsCheckClicked) {
               var3 = 1;
            }
            break;
         case 1:
            if(this.mDownEvent && this.mAllowBatch && this.mIsCheckClicked) {
               byte var16;
               if(!this.mSelected) {
                  var16 = 1;
               } else {
                  var16 = 0;
               }

               this.mSelected = (boolean)var16;
               MessageList.MessageListAdapter var17 = this.mAdapter;
               boolean var18 = this.mSelected;
               var17.updateSelected(this, var18);
               var3 = 1;
            }
         case 2:
         default:
            break;
         case 3:
            this.mDownEvent = (boolean)0;
         }

         if(var3 != 0) {
            this.postInvalidate();
         } else {
            var3 = super.onTouchEvent(var1);
         }

         var2 = var3;
      }

      return (boolean)var2;
   }
}
