package com.google.android.finsky.layout;

import android.app.Activity;
import android.view.Menu;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.navigationmanager.NavigationManager;

public class FakeActionBar implements CustomActionBar {

   private int mBackendId;
   private String mBreadcrumb;


   public FakeActionBar() {}

   public void configureMenu(Activity var1, Menu var2) {}

   public String getBreadcrumbText() {
      return this.mBreadcrumb;
   }

   public int getCurrentBackendId() {
      return this.mBackendId;
   }

   public void hide() {}

   public void initialize(NavigationManager var1, Activity var2) {}

   public void initializeNoNavigation(Activity var1) {}

   public void shareButtonClicked(Activity var1) {}

   public void updateBreadcrumb(String var1) {
      this.mBreadcrumb = var1;
   }

   public void updateCurrentBackendId(int var1) {
      this.mBackendId = var1;
   }

   public void updateSearchQuery(String var1) {}
}
