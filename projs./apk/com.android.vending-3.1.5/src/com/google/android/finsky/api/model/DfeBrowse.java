package com.google.android.finsky.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeModel;
import com.google.android.finsky.remoting.protos.Browse;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.List;

public class DfeBrowse extends DfeModel implements Response.Listener<Browse.BrowseResponse>, Parcelable {

   public static Creator<DfeBrowse> CREATOR = new DfeBrowse.1();
   private Browse.BrowseResponse mBrowseResponse;
   private final DfeApi mDfeApi;


   public DfeBrowse(DfeApi var1, Browse.BrowseResponse var2) {
      this.mDfeApi = var1;
      this.mBrowseResponse = var2;
   }

   public DfeBrowse(DfeApi var1, String var2) {
      this.mDfeApi = var1;
      var1.getBrowseLayout(var2, this, this);
   }

   public DfeList buildContentList() {
      DfeList var1;
      if(this.isReady() && !TextUtils.isEmpty(this.mBrowseResponse.getContentsUrl())) {
         DfeApi var2 = this.mDfeApi;
         String var3 = this.mBrowseResponse.getContentsUrl();
         var1 = new DfeList(var2, var3, (boolean)1);
      } else {
         var1 = null;
      }

      return var1;
   }

   public DfeList buildPromoList() {
      DfeList var1;
      if(!this.hasPromotionalItems()) {
         var1 = null;
      } else {
         DfeApi var2 = this.mDfeApi;
         String var3 = this.mBrowseResponse.getPromoUrl();
         var1 = new DfeList(var2, var3, (boolean)0);
      }

      return var1;
   }

   public int describeContents() {
      return 0;
   }

   public List<Browse.BrowseLink> getBreadcrumbList() {
      List var1;
      if(this.mBrowseResponse != null) {
         var1 = this.mBrowseResponse.getBreadcrumbList();
      } else {
         var1 = null;
      }

      return var1;
   }

   public List<Browse.BrowseLink> getCategoryList() {
      List var1;
      if(this.mBrowseResponse != null) {
         var1 = this.mBrowseResponse.getCategoryList();
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean hasCategories() {
      boolean var1;
      if(this.mBrowseResponse != null && this.mBrowseResponse.getCategoryList() != null && !this.mBrowseResponse.getCategoryList().isEmpty()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasPromotionalItems() {
      boolean var1;
      if(this.mBrowseResponse != null && !TextUtils.isEmpty(this.mBrowseResponse.getPromoUrl())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isReady() {
      boolean var1;
      if(this.mBrowseResponse != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onResponse(Browse.BrowseResponse var1) {
      this.clearErrors();
      this.mBrowseResponse = var1;
      this.notifyDataSetChanged();
   }

   public void writeToParcel(Parcel var1, int var2) {
      ParcelableProto var3 = ParcelableProto.forProto(this.mBrowseResponse);
      var1.writeParcelable(var3, 0);
   }

   static class 1 implements Creator<DfeBrowse> {

      1() {}

      public DfeBrowse createFromParcel(Parcel var1) {
         ClassLoader var2 = ParcelableProto.class.getClassLoader();
         Browse.BrowseResponse var3 = (Browse.BrowseResponse)ParcelableProto.getProtoFromParcel(var1, var2);
         DfeApi var4 = FinskyApp.get().getDfeApi();
         return new DfeBrowse(var4, var3);
      }

      public DfeBrowse[] newArray(int var1) {
         return new DfeBrowse[var1];
      }
   }
}
