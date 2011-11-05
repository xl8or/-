package com.google.android.finsky.api.model;

import android.text.TextUtils;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.android.finsky.api.PaginatedDfeRequest;
import com.google.android.finsky.api.model.DfeModel;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PaginatedList<T extends Object, D extends Object> extends DfeModel implements PaginatedDfeRequest.PaginatedListener<T> {

   private static final int DEFAULT_WINDOW_DISTANCE = 12;
   private static final int ITEMS_UNTIL_END_COUNT = 4;
   protected final boolean mAutoLoadNextPage;
   private int mCurrentOffset;
   private Request<?> mCurrentRequest;
   private final List<D> mItems;
   private int mLastPositionRequested;
   protected T mLastResponse;
   private boolean mMoreAvailable;
   protected List<PaginatedList.UrlOffsetPair> mUrlOffsetList;
   private int mWindowDistance;


   protected PaginatedList(String var1) {
      this(var1, (boolean)1);
   }

   protected PaginatedList(String var1, boolean var2) {
      this.mWindowDistance = 12;
      ArrayList var3 = Lists.newArrayList();
      this.mItems = var3;
      ArrayList var4 = Lists.newArrayList();
      this.mUrlOffsetList = var4;
      List var5 = this.mUrlOffsetList;
      PaginatedList.UrlOffsetPair var6 = new PaginatedList.UrlOffsetPair(0, var1);
      var5.add(var6);
      this.mMoreAvailable = (boolean)1;
      this.mAutoLoadNextPage = var2;
   }

   protected PaginatedList(List<PaginatedList.UrlOffsetPair> var1, int var2, boolean var3) {
      this((String)null, var3);
      this.mUrlOffsetList = var1;

      for(int var4 = 0; var4 < var2; ++var4) {
         boolean var5 = this.mItems.add((Object)null);
      }

   }

   private void requestMoreItemsIfNoRequestExists(PaginatedList.UrlOffsetPair var1) {
      if(this.mCurrentRequest != null) {
         String var2 = this.mCurrentRequest.getUrl();
         String var3 = var1.url;
         if(var2.endsWith(var3)) {
            return;
         }

         this.mCurrentRequest.cancel();
      }

      int var4 = var1.offset;
      this.mCurrentOffset = var4;
      String var5 = var1.url;
      Request var6 = this.makeRequest(var5);
      this.mCurrentRequest = var6;
   }

   public void clearTransientState() {
      this.mCurrentRequest = null;
   }

   public void flushUnusedPages() {
      if(this.mLastPositionRequested >= 0) {
         int var1 = 0;

         while(true) {
            int var2 = this.mItems.size();
            if(var1 >= var2) {
               return;
            }

            label20: {
               int var3 = this.mLastPositionRequested;
               int var4 = this.mWindowDistance;
               int var5 = var3 - var4;
               if(var1 > var5) {
                  int var6 = this.mLastPositionRequested;
                  int var7 = this.mWindowDistance;
                  int var8 = var6 + var7;
                  if(var1 < var8) {
                     break label20;
                  }
               }

               this.mItems.set(var1, (Object)null);
            }

            ++var1;
         }
      }
   }

   protected boolean forceLoadMoreItems() {
      return false;
   }

   public int getCount() {
      return this.mItems.size();
   }

   public final D getItem(int var1) {
      this.mLastPositionRequested = var1;
      Object var2 = null;
      if(var1 < 0) {
         String var3 = "Can\'t return an item with a negative index: " + var1;
         throw new IllegalArgumentException(var3);
      } else {
         int var4 = this.getCount();
         if(var1 < var4) {
            var2 = this.mItems.get(var1);
            if(this.mAutoLoadNextPage && this.mMoreAvailable) {
               int var5 = this.getCount() + -4;
               if(var1 >= var5) {
                  List var6 = this.mUrlOffsetList;
                  int var7 = this.mUrlOffsetList.size() + -1;
                  PaginatedList.UrlOffsetPair var8 = (PaginatedList.UrlOffsetPair)var6.get(var7);
                  this.requestMoreItemsIfNoRequestExists(var8);
               }
            }

            if(var2 == null) {
               Object var9 = null;
               Iterator var10 = this.mUrlOffsetList.iterator();

               while(var10.hasNext()) {
                  PaginatedList.UrlOffsetPair var11 = (PaginatedList.UrlOffsetPair)var10.next();
                  if(var11.offset > var1) {
                     break;
                  }
               }

               this.requestMoreItemsIfNoRequestExists((PaginatedList.UrlOffsetPair)var9);
            }
         }

         return var2;
      }
   }

   protected abstract List<D> getItemsFromResponse(T var1);

   protected abstract String getNextPageUrl(T var1);

   public final boolean hasItem(int var1) {
      int var2 = this.getCount();
      boolean var3;
      if(var1 < var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isInTransit() {
      boolean var1;
      if(this.mCurrentRequest != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isMoreAvailable() {
      return this.mMoreAvailable;
   }

   public boolean isReady() {
      boolean var1;
      if(this.mLastResponse == null && this.mItems.size() <= 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   protected abstract Request<?> makeRequest(String var1);

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      this.clearTransientState();
      super.onErrorResponse(var1, var2, var3);
   }

   public void onResponse(T var1) {
      this.clearErrors();
      this.mLastResponse = var1;
      int var2 = this.mItems.size();
      List var3 = this.getItemsFromResponse(var1);
      int var4 = this.mCurrentOffset;
      int var5 = this.mItems.size();
      if(var4 >= var5) {
         this.mItems.addAll(var3);
      } else {
         int var18 = 0;

         while(true) {
            int var19 = var3.size();
            if(var18 >= var19) {
               break;
            }

            int var20 = this.mCurrentOffset + var18;
            int var21 = this.mItems.size();
            if(var20 < var21) {
               List var22 = this.mItems;
               int var23 = this.mCurrentOffset + var18;
               Object var24 = var3.get(var18);
               var22.set(var23, var24);
            } else {
               List var26 = this.mItems;
               Object var27 = var3.get(var18);
               var26.add(var27);
            }

            ++var18;
         }
      }

      String var7 = this.getNextPageUrl(var1);
      boolean var8 = false;
      if(!TextUtils.isEmpty(var7) && this.mCurrentOffset == var2) {
         List var9 = this.mUrlOffsetList;
         int var10 = this.mItems.size();
         PaginatedList.UrlOffsetPair var11 = new PaginatedList.UrlOffsetPair(var10, var7);
         var9.add(var11);
      }

      int var13 = this.mItems.size();
      List var14 = this.mUrlOffsetList;
      int var15 = this.mUrlOffsetList.size() + -1;
      int var16 = ((PaginatedList.UrlOffsetPair)var14.get(var15)).offset;
      if(var13 == var16 && var3.size() > 0) {
         var8 = true;
      }

      byte var17;
      if((var8 || this.forceLoadMoreItems()) && this.mAutoLoadNextPage) {
         var17 = 1;
      } else {
         var17 = 0;
      }

      this.mMoreAvailable = (boolean)var17;
      this.clearTransientState();
      this.notifyDataSetChanged();
   }

   public void resetItems() {
      this.mMoreAvailable = (boolean)1;
      this.mItems.clear();
   }

   public void retryLoadItems() {
      if(this.inErrorState()) {
         this.clearTransientState();
         PaginatedList.UrlOffsetPair var1 = null;
         if(this.mCurrentOffset != -1) {
            Iterator var2 = this.mUrlOffsetList.iterator();

            while(var2.hasNext()) {
               PaginatedList.UrlOffsetPair var3 = (PaginatedList.UrlOffsetPair)var2.next();
               int var4 = this.mCurrentOffset;
               int var5 = var3.offset;
               if(var4 == var5) {
                  var1 = var3;
                  break;
               }
            }
         }

         if(var1 == null) {
            List var6 = this.mUrlOffsetList;
            int var7 = this.mUrlOffsetList.size() + -1;
            var1 = (PaginatedList.UrlOffsetPair)var6.get(var7);
         }

         this.requestMoreItemsIfNoRequestExists(var1);
      }
   }

   public void setWindowDistance(int var1) {
      this.mWindowDistance = var1;
   }

   public void startLoadItems() {
      if(this.mMoreAvailable) {
         if(this.getCount() == 0) {
            this.clearErrors();
            PaginatedList.UrlOffsetPair var1 = (PaginatedList.UrlOffsetPair)this.mUrlOffsetList.get(0);
            this.requestMoreItemsIfNoRequestExists(var1);
         }
      }
   }

   protected static class UrlOffsetPair {

      public final int offset;
      public final String url;


      public UrlOffsetPair(int var1, String var2) {
         this.offset = var1;
         this.url = var2;
      }
   }
}
