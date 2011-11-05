package com.google.android.finsky.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.navigationmanager.NavigationManager;

public abstract class PaginatedListAdapter extends BaseAdapter implements OnDataChangedListener {

   protected final Context mContext;
   private PaginatedListAdapter.FooterMode mFooterMode;
   private final LayoutInflater mLayoutInflater;
   protected final NavigationManager mNavigationManager;
   protected OnClickListener mRetryClickListener;


   public PaginatedListAdapter(Context var1, NavigationManager var2, boolean var3, boolean var4) {
      PaginatedListAdapter.1 var5 = new PaginatedListAdapter.1();
      this.mRetryClickListener = var5;
      this.mContext = var1;
      this.mNavigationManager = var2;
      LayoutInflater var6 = LayoutInflater.from(var1);
      this.mLayoutInflater = var6;
      if(var3) {
         PaginatedListAdapter.FooterMode var7 = PaginatedListAdapter.FooterMode.ERROR;
         this.mFooterMode = var7;
      } else if(var4) {
         PaginatedListAdapter.FooterMode var8 = PaginatedListAdapter.FooterMode.LOADING;
         this.mFooterMode = var8;
      } else {
         PaginatedListAdapter.FooterMode var9 = PaginatedListAdapter.FooterMode.NONE;
         this.mFooterMode = var9;
      }
   }

   private void setFooterMode(PaginatedListAdapter.FooterMode var1) {
      this.mFooterMode = var1;
      this.notifyDataSetChanged();
   }

   protected View getErrorFooterView(View var1, ViewGroup var2) {
      if(var1 == null) {
         var1 = this.inflate(2130968648, var2, (boolean)0);
         Button var3 = (Button)var1.findViewById(2131755192);
         OnClickListener var4 = this.mRetryClickListener;
         var3.setOnClickListener(var4);
      }

      TextView var5 = (TextView)var1.findViewById(2131755191);
      String var6 = this.getLastRequestError();
      var5.setText(var6);
      return var1;
   }

   protected PaginatedListAdapter.FooterMode getFooterMode() {
      return this.mFooterMode;
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   protected abstract String getLastRequestError();

   protected View getLoadingFooterView(View var1, ViewGroup var2) {
      if(var1 == null) {
         var1 = this.inflate(2130968664, var2, (boolean)0);
      }

      return var1;
   }

   protected View inflate(int var1, ViewGroup var2, boolean var3) {
      return this.mLayoutInflater.inflate(var1, var2, var3);
   }

   public boolean isEnabled(int var1) {
      return false;
   }

   protected abstract boolean isMoreDataAvailable();

   public void onDataChanged() {
      if(this.isMoreDataAvailable()) {
         PaginatedListAdapter.FooterMode var1 = PaginatedListAdapter.FooterMode.LOADING;
         this.setFooterMode(var1);
      } else {
         PaginatedListAdapter.FooterMode var2 = PaginatedListAdapter.FooterMode.NONE;
         this.setFooterMode(var2);
      }
   }

   protected abstract void retryLoadingItems();

   public void triggerFooterErrorMode() {
      PaginatedListAdapter.FooterMode var1 = PaginatedListAdapter.FooterMode.ERROR;
      this.setFooterMode(var1);
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         PaginatedListAdapter.FooterMode var2 = PaginatedListAdapter.this.mFooterMode;
         PaginatedListAdapter.FooterMode var3 = PaginatedListAdapter.FooterMode.ERROR;
         if(var2 == var3) {
            PaginatedListAdapter.this.retryLoadingItems();
         }

         PaginatedListAdapter var4 = PaginatedListAdapter.this;
         PaginatedListAdapter.FooterMode var5 = PaginatedListAdapter.FooterMode.LOADING;
         var4.setFooterMode(var5);
      }
   }

   protected static enum FooterMode {

      // $FF: synthetic field
      private static final PaginatedListAdapter.FooterMode[] $VALUES;
      ERROR("ERROR", 2),
      LOADING("LOADING", 1),
      NONE("NONE", 0);


      static {
         PaginatedListAdapter.FooterMode[] var0 = new PaginatedListAdapter.FooterMode[3];
         PaginatedListAdapter.FooterMode var1 = NONE;
         var0[0] = var1;
         PaginatedListAdapter.FooterMode var2 = LOADING;
         var0[1] = var2;
         PaginatedListAdapter.FooterMode var3 = ERROR;
         var0[2] = var3;
         $VALUES = var0;
      }

      private FooterMode(String var1, int var2) {}
   }
}
