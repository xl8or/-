package com.google.android.finsky.api.model;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.utils.Sets;
import java.util.HashSet;

public abstract class DfeModel implements Response.ErrorListener {

   private Response.ErrorCode mErrorCode;
   private HashSet<Response.ErrorListener> mErrorListeners;
   private String mErrorMessage;
   private HashSet<OnDataChangedListener> mListeners;


   public DfeModel() {
      HashSet var1 = Sets.newHashSet();
      this.mListeners = var1;
      HashSet var2 = Sets.newHashSet();
      this.mErrorListeners = var2;
   }

   public final void addDataChangedListener(OnDataChangedListener var1) {
      this.mListeners.add(var1);
   }

   public final void addErrorListener(Response.ErrorListener var1) {
      this.mErrorListeners.add(var1);
   }

   protected void clearErrors() {
      this.mErrorCode = null;
      this.mErrorMessage = null;
   }

   public Response.ErrorCode getErrorCode() {
      return this.mErrorCode;
   }

   public String getErrorMessage() {
      return this.mErrorMessage;
   }

   public boolean inErrorState() {
      boolean var1;
      if(this.mErrorCode == null && this.mErrorMessage == null) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public abstract boolean isReady();

   protected void notifyDataSetChanged() {
      HashSet var1 = this.mListeners;
      OnDataChangedListener[] var2 = new OnDataChangedListener[0];
      OnDataChangedListener[] var3 = (OnDataChangedListener[])var1.toArray(var2);
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            return;
         }

         var3[var4].onDataChanged();
         ++var4;
      }
   }

   protected void notifyErrorOccured(Response.ErrorCode var1, String var2, NetworkError var3) {
      HashSet var4 = this.mErrorListeners;
      Response.ErrorListener[] var5 = new Response.ErrorListener[0];
      Response.ErrorListener[] var6 = (Response.ErrorListener[])var4.toArray(var5);
      int var7 = 0;

      while(true) {
         int var8 = var6.length;
         if(var7 >= var8) {
            return;
         }

         var6[var7].onErrorResponse(var1, var2, var3);
         ++var7;
      }
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      this.mErrorCode = var1;
      this.mErrorMessage = var2;
      this.notifyErrorOccured(var1, var2, var3);
   }

   public final void removeDataChangedListener(OnDataChangedListener var1) {
      this.mListeners.remove(var1);
   }

   public final void removeErrorListener(Response.ErrorListener var1) {
      this.mErrorListeners.remove(var1);
   }

   public final void unregisterAll() {
      this.mListeners.clear();
      this.mErrorListeners.clear();
   }
}
