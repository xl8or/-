package com.android.email.activity;

import android.content.Context;

public interface ProgressListener {

   void hideProgress(Context var1);

   void showProgress(Context var1, String var2, String var3, long var4, long var6, boolean var8);

   void updateProgress(Context var1, String var2, String var3, long var4, long var6, boolean var8);
}
