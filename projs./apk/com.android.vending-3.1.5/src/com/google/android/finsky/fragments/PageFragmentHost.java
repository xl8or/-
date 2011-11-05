package com.google.android.finsky.fragments;

import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public interface PageFragmentHost {

   BitmapLoader getBitmapLoader();

   DfeApi getDfeApi();

   NavigationManager getNavigationManager();

   void goBack();

   void showErrorDialog(String var1, String var2, boolean var3);

   void updateBreadcrumb(String var1);

   void updateCurrentBackendId(int var1);
}
