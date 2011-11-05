package com.facebook.katana.activity;

import android.view.View;

public interface FacebookActivity {

   boolean facebookOnBackPressed();

   long getActivityId();

   boolean isOnTop();

   void setTransitioningToBackground(boolean var1);

   void titleBarClickHandler(View var1);

   void titleBarPrimaryActionClickHandler(View var1);

   void titleBarSearchClickHandler(View var1);
}
