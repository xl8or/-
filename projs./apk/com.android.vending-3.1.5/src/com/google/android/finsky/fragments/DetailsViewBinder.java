package com.google.android.finsky.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.ErrorStrings;

public abstract class DetailsViewBinder implements Response.ErrorListener {

   protected static final int LOADING_SPINNER_DELAY = 350;
   protected Context mContext;
   protected DfeApi mDfeApi;
   protected Document mDoc;
   protected LayoutInflater mInflater;
   protected View mLayout;
   protected LayoutSwitcher mLayoutSwitcher;
   protected NavigationManager mNavigationManager;


   public DetailsViewBinder() {}

   private void setupHeader(int var1, int var2) {
      TextView var3 = (TextView)this.mLayout.findViewById(var1);
      if(var3 != null) {
         if(var2 >= 0) {
            String var4 = this.mContext.getString(var2).toUpperCase();
            var3.setText(var4);
         }

         Context var5 = this.mContext;
         int var6 = this.mDoc.getBackend();
         int var7 = CorpusMetadata.getBackendHintColor(var5, var6);
         var3.setTextColor(var7);
      }
   }

   public void bind(View var1, Document var2, int var3, int var4) {
      this.mLayout = var1;
      this.mDoc = var2;
      this.setupHeader(var3, var4);
   }

   public void init(Context var1, DfeApi var2, NavigationManager var3) {
      this.mContext = var1;
      this.mDfeApi = var2;
      this.mNavigationManager = var3;
      LayoutInflater var4 = LayoutInflater.from(this.mContext);
      this.mInflater = var4;
   }

   public void onDestroyView() {}

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      if(this.mLayoutSwitcher != null) {
         LayoutSwitcher var4 = this.mLayoutSwitcher;
         String var5 = ErrorStrings.get(this.mContext, var1, var2);
         var4.switchToErrorMode(var5);
      }
   }

   protected void setButtonClickListener(int var1, OnClickListener var2) {
      this.mLayout.findViewById(var1).setOnClickListener(var2);
   }

   protected void setButtonVisibility(int var1, int var2, int var3) {
      if(var2 != 8 && var2 != 4 && var2 != 0) {
         String var4 = "Invalid visibility value for a view " + var2;
         throw new IllegalArgumentException(var4);
      } else {
         Button var5 = (Button)this.mLayout.findViewById(var1);
         var5.setVisibility(var2);
         if(var2 == 0) {
            var5.setText(var3);
         }
      }
   }
}
