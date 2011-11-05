package com.google.android.finsky.layout;

import android.app.Activity;
import android.view.Menu;
import com.google.android.finsky.navigationmanager.NavigationManager;

public interface CustomActionBar {

   void configureMenu(Activity var1, Menu var2);

   String getBreadcrumbText();

   int getCurrentBackendId();

   void hide();

   void initialize(NavigationManager var1, Activity var2);

   void initializeNoNavigation(Activity var1);

   void shareButtonClicked(Activity var1);

   void updateBreadcrumb(String var1);

   void updateCurrentBackendId(int var1);

   void updateSearchQuery(String var1);
}
