package com.facebook.katana;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RequestsAdapter extends BaseAdapter {

   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private Context mContext;
   private ArrayList<RequestsAdapter.FriendRequest> mFriendRequests;
   private Map<Long, RequestsAdapter.FriendRequest> mFriendRequestsById;
   boolean mSyncRequired;


   public RequestsAdapter(Context var1, AppSession var2, Map<Long, FacebookUser> var3) {
      HashMap var4 = new HashMap();
      this.mFriendRequestsById = var4;
      RequestsAdapter.1 var5 = new RequestsAdapter.1();
      this.mAppSessionListener = var5;
      this.mContext = var1;
      this.mAppSession = var2;
      this.mSyncRequired = (boolean)0;
      AppSession var6 = this.mAppSession;
      AppSessionListener var7 = this.mAppSessionListener;
      var6.addListener(var7);
      this.setupRequestors(var3);
   }

   public boolean areAllItemsEnabled() {
      return true;
   }

   public int getCount() {
      return this.mFriendRequests.size();
   }

   public Object getItem(int var1) {
      return ((RequestsAdapter.FriendRequest)this.mFriendRequests.get(var1)).mRequestor;
   }

   public long getItemId(int var1) {
      return ((RequestsAdapter.FriendRequest)this.mFriendRequests.get(var1)).mRequestor.mUserId;
   }

   public int getItemViewType(int var1) {
      return 0;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      RequestsAdapter.FriendRequest var4 = (RequestsAdapter.FriendRequest)this.mFriendRequests.get(var1);
      if(var4.mView == null) {
         View var5 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903151, (ViewGroup)null);
         var4.mView = var5;
      }

      var4.updateView();
      return var4.mView;
   }

   public int getViewTypeCount() {
      return 1;
   }

   public boolean hasStableIds() {
      return true;
   }

   public boolean isEmpty() {
      return this.mFriendRequests.isEmpty();
   }

   public boolean isEnabled(int var1) {
      boolean var2;
      if(this.mFriendRequests.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void onDestroy() {
      AppSession var1 = this.mAppSession;
      AppSessionListener var2 = this.mAppSessionListener;
      var1.removeListener(var2);
   }

   public void setupRequestors(Map<Long, FacebookUser> var1) {
      int var2 = var1.size();
      ArrayList var3 = new ArrayList(var2);
      this.mFriendRequests = var3;
      Iterator var4 = var1.values().iterator();

      while(var4.hasNext()) {
         FacebookUser var5 = (FacebookUser)var4.next();
         RequestsAdapter.FriendRequest var6 = new RequestsAdapter.FriendRequest(var5);
         this.mFriendRequests.add(var6);
         Map var8 = this.mFriendRequestsById;
         Long var9 = Long.valueOf(var5.mUserId);
         var8.put(var9, var6);
      }

      AppSession var11 = this.mAppSession;
      Context var12 = this.mContext;
      long var13 = this.mAppSession.getSessionInfo().userId;
      var11.getFriendRequestsMutualFriends(var12, var13);
      ProfileImagesCache var16 = this.mAppSession.getUserImagesCache();

      RequestsAdapter.FriendRequest var21;
      Bitmap var26;
      for(Iterator var17 = var1.values().iterator(); var17.hasNext(); var21.mProfilePicture = var26) {
         FacebookUser var18 = (FacebookUser)var17.next();
         Map var19 = this.mFriendRequestsById;
         Long var20 = Long.valueOf(var18.mUserId);
         var21 = (RequestsAdapter.FriendRequest)var19.get(var20);
         Context var22 = this.mContext;
         long var23 = var18.mUserId;
         String var25 = var18.mImageUrl;
         var26 = var16.get(var22, var23, var25);
      }

   }

   private class OnClick implements OnClickListener {

      boolean mConfirm;
      RequestsAdapter.FriendRequest mFriendRequest;


      public OnClick(RequestsAdapter.FriendRequest var2, boolean var3) {
         this.mFriendRequest = var2;
         this.mConfirm = var3;
      }

      public void onClick(View var1) {
         if(this.mConfirm) {
            RequestsAdapter.this.mSyncRequired = (boolean)1;
         }

         RequestsAdapter.FriendRequest var2 = this.mFriendRequest;
         RequestsAdapter.RequestState var3;
         if(this.mConfirm) {
            var3 = RequestsAdapter.RequestState.RESPONSE_CONFIRMING;
         } else {
            var3 = RequestsAdapter.RequestState.RESPONSE_IGNORING;
         }

         var2.setState(var3);
         AppSession var4 = RequestsAdapter.this.mAppSession;
         Context var5 = RequestsAdapter.this.mContext;
         long var6 = this.mFriendRequest.mRequestor.mUserId;
         boolean var8 = this.mConfirm;
         var4.respondToFriendRequest(var5, var6, var8);
      }
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState = new int[RequestsAdapter.RequestState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState;
            int var1 = RequestsAdapter.RequestState.RESPONSE_CONFIRMING.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState;
            int var3 = RequestsAdapter.RequestState.RESPONSE_IGNORING.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState;
            int var5 = RequestsAdapter.RequestState.RESPONSE_CONFIRMED.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$facebook$katana$RequestsAdapter$RequestState;
            int var7 = RequestsAdapter.RequestState.RESPONSE_IGNORED.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }

   class 1 extends AppSessionListener {

      1() {}

      public void onFriendRequestRespondComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, boolean var8) {
         Map var9 = RequestsAdapter.this.mFriendRequestsById;
         Long var10 = Long.valueOf(var6);
         RequestsAdapter.FriendRequest var11 = (RequestsAdapter.FriendRequest)var9.get(var10);
         if(var8 && var11 != null) {
            RequestsAdapter.RequestState var12 = var11.getState();
            RequestsAdapter.RequestState var13 = RequestsAdapter.RequestState.RESPONSE_CONFIRMING;
            RequestsAdapter.RequestState var14;
            if(var12 == var13) {
               var14 = RequestsAdapter.RequestState.RESPONSE_CONFIRMED;
            } else {
               var14 = RequestsAdapter.RequestState.RESPONSE_IGNORED;
            }

            var11.setState(var14);
         } else {
            Toaster.toast(RequestsAdapter.this.mContext, 2131362182);
            if(var11 != null) {
               RequestsAdapter.RequestState var15 = RequestsAdapter.RequestState.NOT_RESPONDED;
               var11.setState(var15);
            }
         }
      }

      public void onFriendRequestsMutualFriendsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Map<Long, List<Long>> var6) {
         if(var6 != null) {
            Iterator var7 = var6.keySet().iterator();

            while(var7.hasNext()) {
               Long var8 = (Long)var7.next();
               RequestsAdapter.FriendRequest var9 = (RequestsAdapter.FriendRequest)RequestsAdapter.this.mFriendRequestsById.get(var8);
               if(var9 != null) {
                  int var10 = ((List)var6.get(var8)).size();
                  var9.setMutualFriendCount(var10);
               }
            }

            Iterator var11 = RequestsAdapter.this.mFriendRequests.iterator();

            while(var11.hasNext()) {
               RequestsAdapter.FriendRequest var12 = (RequestsAdapter.FriendRequest)var11.next();
               if(var12.mMutualFriends == -1) {
                  var12.setMutualFriendCount(0);
               }
            }

         }
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var6 != null) {
            Map var8 = RequestsAdapter.this.mFriendRequestsById;
            Long var9 = Long.valueOf(var6.id);
            RequestsAdapter.FriendRequest var10 = (RequestsAdapter.FriendRequest)var8.get(var9);
            if(var10 != null) {
               Bitmap var11 = var6.getBitmap();
               var10.setProfilePicture(var11);
            }
         }
      }
   }

   private static enum RequestState {

      // $FF: synthetic field
      private static final RequestsAdapter.RequestState[] $VALUES;
      NOT_RESPONDED("NOT_RESPONDED", 0),
      RESPONSE_CONFIRMED("RESPONSE_CONFIRMED", 3),
      RESPONSE_CONFIRMING("RESPONSE_CONFIRMING", 1),
      RESPONSE_IGNORED("RESPONSE_IGNORED", 4),
      RESPONSE_IGNORING("RESPONSE_IGNORING", 2);


      static {
         RequestsAdapter.RequestState[] var0 = new RequestsAdapter.RequestState[5];
         RequestsAdapter.RequestState var1 = NOT_RESPONDED;
         var0[0] = var1;
         RequestsAdapter.RequestState var2 = RESPONSE_CONFIRMING;
         var0[1] = var2;
         RequestsAdapter.RequestState var3 = RESPONSE_IGNORING;
         var0[2] = var3;
         RequestsAdapter.RequestState var4 = RESPONSE_CONFIRMED;
         var0[3] = var4;
         RequestsAdapter.RequestState var5 = RESPONSE_IGNORED;
         var0[4] = var5;
         $VALUES = var0;
      }

      private RequestState(String var1, int var2) {}
   }

   private class FriendRequest {

      int mMutualFriends;
      Bitmap mProfilePicture;
      FacebookUser mRequestor;
      RequestsAdapter.RequestState mState;
      View mView;


      FriendRequest(FacebookUser var2) {
         RequestsAdapter.RequestState var3 = RequestsAdapter.RequestState.NOT_RESPONDED;
         this.mState = var3;
         this.mMutualFriends = -1;
         this.mRequestor = var2;
      }

      RequestsAdapter.RequestState getState() {
         return this.mState;
      }

      void setMutualFriendCount(int var1) {
         this.mMutualFriends = var1;
         this.updateView();
      }

      void setProfilePicture(Bitmap var1) {
         this.mProfilePicture = var1;
         this.updateView();
      }

      void setState(RequestsAdapter.RequestState var1) {
         this.mState = var1;
         this.updateView();
      }

      void updateView() {
         // $FF: Couldn't be decompiled
      }

      class 1 implements OnClickListener {

         1() {}

         public void onClick(View var1) {
            Context var2 = RequestsAdapter.this.mContext;
            long var3 = FriendRequest.this.mRequestor.mUserId;
            ApplicationUtils.OpenUserProfile(var2, var3, (FacebookProfile)null);
         }
      }
   }
}
