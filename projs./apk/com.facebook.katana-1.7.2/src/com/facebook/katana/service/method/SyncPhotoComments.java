package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.BatchRun;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.service.method.PhotosCanComment;
import com.facebook.katana.service.method.PhotosGetComments;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SyncPhotoComments extends ApiMethod {

   private final Map<Long, FacebookProfile> mAllProfiles;
   private boolean mCanComment;
   private List<FacebookPhotoComment> mComments;
   private final String mPhotoId;
   private final String mSessionKey;


   public SyncPhotoComments(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", (String)null, var6, var5);
      this.mSessionKey = var3;
      this.mPhotoId = var4;
      HashMap var11 = new HashMap();
      this.mAllProfiles = var11;
   }

   public boolean getCanComment() {
      return this.mCanComment;
   }

   public List<FacebookPhotoComment> getComments() {
      List var1;
      if(this.mComments == null) {
         var1 = Collections.emptyList();
      } else {
         var1 = this.mComments;
      }

      return var1;
   }

   public Map<Long, FacebookProfile> requestProfiles() {
      HashMap var1 = new HashMap();
      Iterator var2 = this.mComments.iterator();

      while(var2.hasNext()) {
         Long var3 = Long.valueOf(((FacebookPhotoComment)var2.next()).getFromProfileId());
         var1.put(var3, (Object)null);
      }

      if(var1.size() != 0) {
         StringBuffer var5 = new StringBuffer(256);
         StringBuffer var6 = var5.append("user_id").append(" IN(");
         boolean var7 = true;

         Long var8;
         for(var2 = var1.keySet().iterator(); var2.hasNext(); var5.append(var8)) {
            var8 = (Long)var2.next();
            if(var7) {
               var7 = false;
            } else {
               StringBuffer var10 = var5.append(",");
            }
         }

         StringBuffer var11 = var5.append(")");
         ContentResolver var12 = this.mContext.getContentResolver();
         Uri var13 = ConnectionsProvider.CONNECTIONS_CONTENT_URI;
         String[] var14 = SyncPhotoComments.ConnectionsQuery.PROJECTION;
         String var15 = var5.toString();
         Cursor var16 = var12.query(var13, var14, var15, (String[])null, (String)null);
         if(var16.moveToFirst()) {
            do {
               Long var17 = Long.valueOf(var16.getLong(0));
               var1.remove(var17);
               boolean var19 = false;
               int var20 = var16.getInt(3);
               int var21 = ConnectionsProvider.ConnectionType.USER.ordinal();
               if(var20 == var21) {
                  var19 = true;
               }

               FacebookProfile var22 = new FacebookProfile;
               long var23 = var17.longValue();
               String var25 = var16.getString(1);
               String var26 = var16.getString(2);
               byte var27;
               if(var19) {
                  var27 = 0;
               } else {
                  var27 = 1;
               }

               var22.<init>(var23, var25, var26, var27);
               this.mAllProfiles.put(var17, var22);
            } while(var16.moveToNext());
         }

         var16.close();
         if(var1.size() == 0) {
            var2 = this.mComments.iterator();

            while(var2.hasNext()) {
               FacebookPhotoComment var29 = (FacebookPhotoComment)var2.next();
               Map var30 = this.mAllProfiles;
               Long var31 = Long.valueOf(var29.getFromProfileId());
               FacebookProfile var32 = (FacebookProfile)var30.get(var31);
               var29.setFromProfile(var32);
            }
         }
      }

      return var1;
   }

   public void start() {
      ArrayList var1 = new ArrayList();
      Context var2 = this.mContext;
      Intent var3 = this.mReqIntent;
      String var4 = this.mSessionKey;
      String var5 = this.mPhotoId;
      PhotosGetComments var6 = new PhotosGetComments(var2, var3, var4, var5, (ApiMethodListener)null);
      var1.add(var6);
      Context var8 = this.mContext;
      Intent var9 = this.mReqIntent;
      String var10 = this.mSessionKey;
      String var11 = this.mPhotoId;
      PhotosCanComment var12 = new PhotosCanComment(var8, var9, var10, var11, (ApiMethodListener)null);
      var1.add(var12);
      Context var14 = this.mContext;
      Intent var15 = this.mReqIntent;
      String var16 = this.mSessionKey;
      SyncPhotoComments.BatchListener var17 = new SyncPhotoComments.BatchListener((SyncPhotoComments.1)null);
      (new BatchRun(var14, var15, var16, var1, var17)).start();
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class GetUsersApiMethodListener implements ApiMethodListener {

      private GetUsersApiMethodListener() {}

      // $FF: synthetic method
      GetUsersApiMethodListener(SyncPhotoComments.1 var2) {
         this();
      }

      public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         ApiMethodListener var5 = SyncPhotoComments.this.mListener;
         SyncPhotoComments var6 = SyncPhotoComments.this;
         var5.onOperationComplete(var6, var2, var3, var4);
      }

      public void onOperationProgress(ApiMethod var1, long var2, long var4) {}

      public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         if(var2 == 200) {
            Map var5 = ((FqlGetProfile)var1).getProfiles();
            SyncPhotoComments.this.mAllProfiles.putAll(var5);
            Iterator var6 = SyncPhotoComments.this.mComments.iterator();

            while(var6.hasNext()) {
               FacebookPhotoComment var7 = (FacebookPhotoComment)var6.next();
               Map var8 = SyncPhotoComments.this.mAllProfiles;
               Long var9 = Long.valueOf(var7.getFromProfileId());
               FacebookProfile var10 = (FacebookProfile)var8.get(var9);
               var7.setFromProfile(var10);
            }

         }
      }
   }

   private interface ConnectionsQuery {

      int INDEX_USER_CONNECTION_TYPE = 3;
      int INDEX_USER_DISPLAY_NAME = 1;
      int INDEX_USER_ID = 0;
      int INDEX_USER_IMAGE_URL = 2;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"user_id", "display_name", "user_image_url", "connection_type"};
         PROJECTION = var0;
      }
   }

   private class BatchListener implements ApiMethodListener {

      private Map<Long, FacebookProfile> mNeedProfiles;


      private BatchListener() {}

      // $FF: synthetic method
      BatchListener(SyncPhotoComments.1 var2) {
         this();
      }

      public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         if(this.mNeedProfiles != null && this.mNeedProfiles.size() > 0) {
            Context var5 = SyncPhotoComments.this.mContext;
            Intent var6 = SyncPhotoComments.this.getIntent();
            String var7 = SyncPhotoComments.this.mSessionKey;
            SyncPhotoComments var8 = SyncPhotoComments.this;
            SyncPhotoComments.GetUsersApiMethodListener var9 = var8.new GetUsersApiMethodListener((SyncPhotoComments.1)null);
            Map var10 = this.mNeedProfiles;
            (new FqlGetProfile(var5, var6, var7, var9, var10)).start();
         } else {
            ApiMethodListener var11 = SyncPhotoComments.this.mListener;
            SyncPhotoComments var12 = SyncPhotoComments.this;
            var11.onOperationComplete(var12, var2, var3, var4);
         }
      }

      public void onOperationProgress(ApiMethod var1, long var2, long var4) {}

      public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         if(var2 == 200) {
            BatchRun var5 = (BatchRun)var1;
            PhotosGetComments var6 = (PhotosGetComments)var5.getMethods().get(0);
            SyncPhotoComments var7 = SyncPhotoComments.this;
            List var8 = var6.getComments();
            var7.mComments = var8;
            Map var10 = SyncPhotoComments.this.requestProfiles();
            this.mNeedProfiles = var10;
            SyncPhotoComments var11 = SyncPhotoComments.this;
            boolean var12 = ((PhotosCanComment)var5.getMethods().get(1)).getCanComment();
            var11.mCanComment = var12;
         }
      }
   }
}
