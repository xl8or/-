package com.facebook.katana.binding;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.FacebookStreamContainer;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.features.places.PlacesNearby;
import com.facebook.katana.features.places.PlacesUtils;
import com.facebook.katana.model.FacebookCheckin;
import com.facebook.katana.model.FacebookCheckinDetails;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.FqlGetEvents;
import com.facebook.katana.service.method.FqlGetFriendCheckins;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.service.method.PlacesCheckin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ExtendedReq {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;


   static {
      byte var0;
      if(!ExtendedReq.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   ExtendedReq() {}

   static void onExtendedOperationComplete(AppSession var0, Context var1, Intent var2, int var3, String var4, Exception var5, ApiMethod var6) {
      String var8 = "extended_type";
      byte var9 = 0;
      int var10 = var2.getIntExtra(var8, var9);
      String var12 = "rid";
      String var13 = var2.getStringExtra(var12);
      Iterator var140;
      switch(var10) {
      case 120:
         FqlGetEvents var142 = (FqlGetEvents)var6;
         List var15 = null;
         short var17 = 200;
         if(var3 == var17) {
            var15 = var142.getEvents();
         }

         var140 = var0.mListeners.getListeners().iterator();

         while(var140.hasNext()) {
            AppSessionListener var18 = (AppSessionListener)var140.next();
            var18.onUserGetEventsComplete(var0, var13, var3, var4, var5, var15);
         }

         return;
      case 500:
         FqlGetFriendCheckins var144 = (FqlGetFriendCheckins)var6;
         List var23 = null;
         short var25 = 200;
         if(var3 == var25) {
            var23 = var144.getCheckins();
         }

         var140 = var0.mListeners.getListeners().iterator();

         while(var140.hasNext()) {
            AppSessionListener var26 = (AppSessionListener)var140.next();
            var26.onFriendCheckinsComplete(var0, var13, var3, var4, var5, var23);
         }

         return;
      case 501:
         FqlGetPlacesNearby var143 = (FqlGetPlacesNearby)var6;
         List var32 = null;
         List var33 = null;
         byte var34 = 0;
         short var36 = 200;
         if(var3 == var36) {
            var32 = var143.getPlaces();
            var33 = var143.getRegions();
            var34 = 1;
         }

         if(var143.callback != null) {
            Location var37 = var143.location;
            double var38 = var143.maxDistance;
            String var40 = var143.filter;
            int var41 = var143.resultLimit;
            PlacesNearby.PlacesNearbyArgType var42 = new PlacesNearby.PlacesNearbyArgType(var37, var38, var40, var41);
            NetworkRequestCallback var43 = var143.callback;
            var43.callback(var1, (boolean)var34, var42, (String)null, var143, (Object)null);
         }

         var140 = var0.mListeners.getListeners().iterator();

         while(var140.hasNext()) {
            AppSessionListener var46 = (AppSessionListener)var140.next();
            Location var47 = var143.location;
            var46.onGetPlacesNearbyComplete(var0, var13, var3, var4, var5, var32, var33, var47);
         }

         return;
      case 503:
         PlacesCheckin var14 = (PlacesCheckin)var6;
         FacebookPlace var141 = var14.mPlace;
         if(!$assertionsDisabled && var141.getPageInfo() == null) {
            throw new AssertionError();
         }

         long var54 = var14.getCheckinId();
         FacebookPost var56 = null;
         short var58 = 200;
         if(var3 == var58 && var54 != 65535L) {
            FacebookUser var59 = var0.getSessionInfo().getProfile();
            ArrayList var60 = new ArrayList;
            Set var61 = var14.mTaggedUids;
            var60.<init>(var61);
            long var64 = var59.mUserId;
            long var66 = var141.mPageId;
            long var68 = System.currentTimeMillis() / 1000L;
            FacebookCheckinDetails var70 = new FacebookCheckinDetails(var54, var64, var66, var68, var60, 350685531728L);
            var70.setPlaceInfo(var141);
            FacebookPost.Attachment var73 = new FacebookPost.Attachment;
            String var74 = var141.mName;
            var73.<init>(var74, var70);
            StringBuilder var78 = new StringBuilder();
            long var79 = var59.mUserId;
            StringBuilder var84 = var78.append(var79);
            String var85 = "_";
            StringBuilder var86 = var84.append(var85);
            String var89 = var86.append(var54).toString();
            long var90 = var59.mUserId;
            String var92 = var14.mMessage;
            Set var93 = var14.mTaggedUids;
            var56 = new FacebookPost(var89, 350685531728L, var90, 65535L, var92, var73, var93, (Set)null, (String)null);
            FacebookProfile var94 = new FacebookProfile(var59);
            var56.setProfile(var94);
            if(var0.mHomeStreamContainer != null) {
               FacebookStreamContainer var99 = var0.mHomeStreamContainer;
               var99.insertFirst(var56);
            }

            Map var101 = var0.mWallContainerMap;
            Long var102 = Long.valueOf(var59.mUserId);
            FacebookStreamContainer var105 = (FacebookStreamContainer)var101.get(var102);
            if(var105 != null) {
               var105.insertFirst(var56);
            }

            Map var108 = var0.mPlacesActivityContainerMap;
            Long var109 = Long.valueOf(var141.mPageId);
            FacebookStreamContainer var112 = (FacebookStreamContainer)var108.get(var109);
            if(var112 != null) {
               var112.insertFirst(var56);
            }

            FacebookCheckin var115 = new FacebookCheckin;
            long var116 = var59.mUserId;
            var115.<init>(var116, var54);
            var115.setDetails(var70);
            var115.setActor(var59);
            long var127 = System.currentTimeMillis();
            PlacesUtils.setLastCheckin(var1, var115, var127);
         }

         var140 = var0.mListeners.getListeners().iterator();

         while(var140.hasNext()) {
            AppSessionListener var133 = (AppSessionListener)var140.next();
            var133.onCheckinComplete(var0, var13, var3, var4, var5, var56);
         }

         return;
      default:
      }
   }
}
