package com.google.android.finsky.activities;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.navigationmanager.NavigationManager;

class FakeNavigationManager extends NavigationManager {

   private Activity mActivity;


   public FakeNavigationManager(Activity var1) {
      super((MainActivity)null);
      this.mActivity = var1;
   }

   public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener var1) {}

   public boolean canGoUp() {
      return true;
   }

   public boolean canSearch() {
      return false;
   }

   public boolean goBack() {
      this.mActivity.onBackPressed();
      return true;
   }

   public void goUp() {
      this.mActivity.onBackPressed();
   }

   public void init(MainActivity var1) {}

   public boolean isEmpty() {
      return false;
   }
}
