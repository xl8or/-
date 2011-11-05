package com.google.android.finsky.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;

public abstract class PageFragment extends Fragment implements Response.ErrorListener, LayoutSwitcher.RetryButtonListener, OnDataChangedListener {

   private static final String KEY_TOC = "finsky.PageFragment.toc";
   private static final int LOADING_SPINNER_DELAY = 350;
   protected BitmapLoader mBitmapLoader;
   protected Context mContext;
   protected ViewGroup mDataView;
   protected DfeApi mDfeApi;
   private DfeToc mDfeToc;
   private LayoutSwitcher mLayoutSwitcher;
   protected NavigationManager mNavigationManager;
   private long mPageCreationTime;
   protected PageFragmentHost mPageFragmentHost;


   protected PageFragment() {
      Bundle var1 = new Bundle();
      this.setArguments(var1);
   }

   protected abstract int getLayoutRes();

   public DfeToc getToc() {
      return this.mDfeToc;
   }

   protected abstract boolean isDataReady();

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      PageFragmentHost var2 = (PageFragmentHost)this.getActivity();
      PageFragmentHost var3 = this.mPageFragmentHost;
      if(var2 != var3) {
         PageFragmentHost var4 = (PageFragmentHost)this.getActivity();
         this.mPageFragmentHost = var4;
         FragmentActivity var5 = this.getActivity();
         this.mContext = var5;
         NavigationManager var6 = this.mPageFragmentHost.getNavigationManager();
         this.mNavigationManager = var6;
         DfeApi var7 = this.mPageFragmentHost.getDfeApi();
         this.mDfeApi = var7;
         BitmapLoader var8 = this.mPageFragmentHost.getBitmapLoader();
         this.mBitmapLoader = var8;
         this.onInitViewBinders();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      long var2 = System.currentTimeMillis();
      this.mPageCreationTime = var2;
      DfeToc var4 = (DfeToc)this.getArguments().getParcelable("finsky.PageFragment.toc");
      this.mDfeToc = var4;
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      int var4 = this.getLayoutRes();
      View var5 = var1.inflate(var4, var2, (boolean)0);
      ViewGroup var6 = (ViewGroup)var5.findViewById(2131755111);
      this.mDataView = var6;
      LayoutSwitcher var8 = new LayoutSwitcher(var5, 2131755111, 2131755258, 2131755150, this, 2);
      this.mLayoutSwitcher = var8;
      return var5;
   }

   public void onDataChanged() {
      if(this.isAdded()) {
         this.switchToData();
         this.rebindViews();
      }
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      String var4 = ErrorStrings.get(this.mContext, var1, var2);
      this.switchToError(var4);
   }

   protected abstract void onInitViewBinders();

   public void onRetry() {
      this.refresh();
   }

   public void rebindActionBar() {}

   protected void rebindViews() {
      Object[] var1 = new Object[2];
      String var2 = this.getClass().getSimpleName();
      var1[0] = var2;
      long var3 = System.currentTimeMillis();
      long var5 = this.mPageCreationTime;
      Long var7 = Long.valueOf(var3 - var5);
      var1[1] = var7;
      FinskyLog.d("Page [class=%s] loaded in [%s ms]", var1);
   }

   public void refresh() {
      this.requestData();
   }

   protected abstract void requestData();

   protected void setArgument(String var1, int var2) {
      this.getArguments().putInt(var1, var2);
   }

   protected void setArgument(String var1, Parcelable var2) {
      this.getArguments().putParcelable(var1, var2);
   }

   protected void setArgument(String var1, String var2) {
      this.getArguments().putString(var1, var2);
   }

   protected void setArgument(String var1, boolean var2) {
      this.getArguments().putBoolean(var1, var2);
   }

   protected void setArguments(DfeToc var1) {
      this.setArgument("finsky.PageFragment.toc", (Parcelable)var1);
   }

   protected void switchToBlank() {
      this.mLayoutSwitcher.switchToBlankMode();
   }

   protected void switchToData() {
      this.mLayoutSwitcher.switchToDataMode();
   }

   protected void switchToError(String var1) {
      this.mLayoutSwitcher.switchToErrorMode(var1);
   }

   protected void switchToLoading() {
      this.mLayoutSwitcher.switchToLoadingDelayed(350);
   }
}
