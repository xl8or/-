package com.google.android.finsky.activities;

import android.view.View;
import com.google.android.finsky.api.model.DfeList;

public interface SlidingPanelTab {

   String getTitle();

   View getView(int var1);

   void onDestroy();

   void setTabSelected(boolean var1);

   public interface OnListLoaded {

      void onListReady(DfeList var1);
   }
}
