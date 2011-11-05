package com.google.android.finsky.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.android.finsky.activities.DetailsSummaryViewBinder;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.PackageInfoCache;

public class DetailsSummaryMoviesViewBinder extends DetailsSummaryViewBinder {

   private final PackageInfoCache mPackageInfoCache;


   public DetailsSummaryMoviesViewBinder(PackageInfoCache var1, DfeToc var2) {
      super(var2);
      this.mPackageInfoCache = var1;
   }

   protected void setupActionButtons(boolean var1) {
      super.setupActionButtons(var1);
      Button var2 = (Button)this.mLayout.findViewById(2131755144);
      var2.setVisibility(8);
      Document var3 = this.mDoc;
      PackageInfoCache var4 = this.mPackageInfoCache;
      if(var3.ownedByUser(var4)) {
         var2.setVisibility(0);
         DetailsSummaryMoviesViewBinder.1 var5 = new DetailsSummaryMoviesViewBinder.1();
         var2.setOnClickListener(var5);
         String var6 = var2.getText().toString().toUpperCase();
         var2.setText(var6);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         PackageManager var2 = DetailsSummaryMoviesViewBinder.this.mContext.getPackageManager();
         int var3 = DetailsSummaryMoviesViewBinder.this.mDoc.getBackend();
         if(!IntentUtils.isConsumptionAppInstalled(var2, var3)) {
            NavigationManager var4 = DetailsSummaryMoviesViewBinder.this.mNavigationManager;
            int var5 = DetailsSummaryMoviesViewBinder.this.mDoc.getBackend();
            var4.showAppNeededDialog(var5);
         } else {
            PackageManager var6 = DetailsSummaryMoviesViewBinder.this.mContext.getPackageManager();
            Document var7 = DetailsSummaryMoviesViewBinder.this.mDoc;
            String var8 = DetailsSummaryMoviesViewBinder.this.mAccountName;
            Intent var9 = IntentUtils.buildConsumptionAppManageItemIntent(var6, var7, var8);
            DetailsSummaryMoviesViewBinder.this.mContext.startActivity(var9);
         }
      }
   }
}
