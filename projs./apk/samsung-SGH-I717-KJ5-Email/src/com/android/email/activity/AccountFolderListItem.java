package com.android.email.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.android.email.activity.AccountManager;
import com.android.email.activity.AccountManagerPremium;

public class AccountFolderListItem extends LinearLayout {

   private static final float FOLDER_PAD = 5.0F;
   public long mAccountId;
   private AccountManager.AccountManagerAdapter mAdapter;
   private AccountManagerPremium.AccountManagerAdapter mAdapterPremium;
   private boolean mCachedViewPositions;
   private boolean mDownEvent;
   private int mFolderLeft;
   private boolean mHasFolderButton;


   public AccountFolderListItem(Context var1) {
      super(var1);
   }

   public AccountFolderListItem(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public void bindViewInit(AccountManager.AccountManagerAdapter var1, boolean var2) {
      this.mAdapter = var1;
      this.mCachedViewPositions = (boolean)0;
      this.mHasFolderButton = var2;
   }

   public void bindViewInit(AccountManagerPremium.AccountManagerAdapter var1, boolean var2) {
      this.mAdapterPremium = var1;
      this.mCachedViewPositions = (boolean)0;
      this.mHasFolderButton = var2;
   }

   public boolean onTouchEvent(MotionEvent param1) {
      // $FF: Couldn't be decompiled
   }
}
