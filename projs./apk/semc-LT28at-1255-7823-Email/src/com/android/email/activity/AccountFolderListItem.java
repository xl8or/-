package com.android.email.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.android.email.activity.AccountFolderList;

public class AccountFolderListItem extends LinearLayout {

   private static final float FOLDER_PAD = 5.0F;
   public long mAccountId;
   private AccountFolderList.AccountsAdapter mAdapter;
   private boolean mCachedViewPositions;
   private boolean mDownEvent;
   private int mFolderLeft;
   private int mFolderRight;
   private boolean mHasFolderButton;


   public AccountFolderListItem(Context var1) {
      super(var1);
   }

   public AccountFolderListItem(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public void bindViewInit(AccountFolderList.AccountsAdapter var1, boolean var2) {
      this.mAdapter = var1;
      this.mCachedViewPositions = (boolean)0;
      this.mHasFolderButton = var2;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      AccountFolderList.AccountsAdapter var2 = this.mAdapter;
      long var3 = this.mAccountId;
      byte var5;
      if(var2.isOnDeletingAccountView(var3)) {
         var5 = 1;
      } else if(!this.mHasFolderButton) {
         var5 = super.onTouchEvent(var1);
      } else {
         byte var6 = 0;
         int var7 = (int)var1.getX();
         if(!this.mCachedViewPositions) {
            float var8 = this.getContext().getResources().getDisplayMetrics().density;
            int var9 = (int)((double)(5.0F * var8) + 0.5D);
            int var10 = this.findViewById(2131558409).getLeft() - var9;
            this.mFolderLeft = var10;
            int var11 = this.findViewById(2131558409).getRight() + var9;
            this.mFolderRight = var11;
            this.mCachedViewPositions = (boolean)1;
         }

         switch(var1.getAction()) {
         case 0:
            this.mDownEvent = (boolean)1;
            if(this.mFolderRight > var7) {
               int var12 = this.mFolderLeft;
               if(var7 > var12) {
                  var6 = 1;
               }
            }
            break;
         case 1:
            if(this.mDownEvent && this.mFolderRight > var7) {
               int var13 = this.mFolderLeft;
               if(var7 > var13) {
                  AccountFolderList.AccountsAdapter var14 = this.mAdapter;
                  Context var15 = this.getContext();
                  var14.onClickFolder(var15, this);
                  var6 = 1;
               }
            }
         case 2:
         default:
            break;
         case 3:
            this.mDownEvent = (boolean)0;
         }

         if(var6 != 0) {
            this.postInvalidate();
         } else {
            var6 = super.onTouchEvent(var1);
         }

         var5 = var6;
      }

      return (boolean)var5;
   }
}
