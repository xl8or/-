package com.google.android.finsky.fragments;

import android.os.Bundle;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.fragments.PageFragment;

public abstract class UrlBasedPageFragment extends PageFragment {

   protected static final String KEY_URL = "finsky.UrlBasedPageFragment.url";
   protected String mUrl;


   public UrlBasedPageFragment() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      String var2 = this.getArguments().getString("finsky.UrlBasedPageFragment.url");
      this.mUrl = var2;
   }

   protected void setArguments(DfeToc var1, String var2) {
      super.setArguments(var1);
      this.setArgument("finsky.UrlBasedPageFragment.url", var2);
   }
}
