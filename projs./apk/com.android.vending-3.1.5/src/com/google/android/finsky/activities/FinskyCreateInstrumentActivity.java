package com.google.android.finsky.activities;

import android.os.Bundle;
import com.google.android.finsky.activities.CreateInstrumentActivity;
import com.google.android.finsky.activities.FakeNavigationManager;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.layout.CustomActionBarFactory;
import com.google.android.finsky.navigationmanager.NavigationManager;

public class FinskyCreateInstrumentActivity extends CreateInstrumentActivity {

   private static final String KEY_LAST_TITLE = "last_title";
   private CustomActionBar mActionBar;
   private NavigationManager mNavigationManager;


   public FinskyCreateInstrumentActivity() {
      FakeNavigationManager var1 = new FakeNavigationManager(this);
      this.mNavigationManager = var1;
   }

   private void setupActionBar(Bundle var1) {
      CustomActionBar var2 = CustomActionBarFactory.getInstance(this);
      this.mActionBar = var2;
      CustomActionBar var3 = this.mActionBar;
      NavigationManager var4 = this.mNavigationManager;
      var3.initialize(var4, this);
      int var5 = this.getIntent().getIntExtra("backend_id", 0);
      this.mActionBar.updateCurrentBackendId(var5);
      if(var1 != null) {
         if(var1.containsKey("last_title")) {
            String var6 = var1.getString("last_title");
            this.setTitle(var6);
         }
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setupActionBar(var1);
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      String var2 = this.mActionBar.getBreadcrumbText();
      var1.putString("last_title", var2);
   }

   protected void setTitle(String var1) {
      this.mActionBar.updateBreadcrumb(var1);
   }
}
