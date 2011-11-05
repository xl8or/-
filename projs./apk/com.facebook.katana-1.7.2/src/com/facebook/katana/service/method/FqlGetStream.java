package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookCheckinDetails;
import com.facebook.katana.model.FacebookDeal;
import com.facebook.katana.model.FacebookDealHistory;
import com.facebook.katana.model.FacebookDealStatus;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.service.method.FqlGetDealHistory;
import com.facebook.katana.service.method.FqlGetDealStatus;
import com.facebook.katana.service.method.FqlGetDeals;
import com.facebook.katana.service.method.FqlGetPages;
import com.facebook.katana.service.method.FqlGetPlaces;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetStream extends FqlMultiQuery {

   private static final String TAG = "FqlGetStream";
   private static String checkinPagesWhereClause = "page_id IN (SELECT page_id FROM checkin WHERE post_id IN (SELECT post_id FROM #posts))";
   private static FacebookStreamType mType;
   private static String placesWhereClause = "page_id IN (SELECT page_id FROM #checkin_pages)";
   protected static String profileWhereClause = "id IN (SELECT actor_id FROM #posts) OR id IN (SELECT target_id FROM #posts) OR id IN (SELECT comments.comment_list.fromid FROM #posts) OR id in (SELECT tagged_ids FROM #posts)";
   private List<FacebookPost> mPosts;


   public FqlGetStream(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, long var7, long[] var9, long var10, String var12, int var13, FacebookStreamType var14) {
      LinkedHashMap var30 = buildQueries(var1, var2, var3, var9, var5, var7, var10, var12, (String[])null, var13, var14);
      super(var1, var2, var3, var30, var4);
   }

   public FqlGetStream(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, String[] var7) {
      int var8 = var7.length;
      FacebookStreamType var9 = mType;
      LinkedHashMap var16 = buildQueries(var1, var2, var3, (long[])null, 0L, 65535L, var5, (String)null, var7, var8, var9);
      super(var1, var2, var3, var16, var4);
   }

   protected FqlGetStream(Context var1, Intent var2, String var3, ApiMethodListener var4, LinkedHashMap<String, FqlQuery> var5) {
      super(var1, var2, var3, var5, var4);
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long[] var3, long var4, long var6, long var8, String var10, String[] var11, int var12, FacebookStreamType var13) {
      mType = var13;
      LinkedHashMap var14 = new LinkedHashMap();
      FacebookStreamType var15 = mType;
      FqlGetStream.FqlGetPosts var30 = new FqlGetStream.FqlGetPosts(var0, var1, var2, (ApiMethodListener)null, var4, var6, var3, var8, var10, var11, var12, var15);
      String var32 = "posts";
      var14.put(var32, var30);
      String var35 = profileWhereClause;
      FqlGetProfile var39 = new FqlGetProfile(var0, var1, var2, (ApiMethodListener)null, var35);
      String var41 = "profiles";
      var14.put(var41, var39);
      String var44 = checkinPagesWhereClause;
      FqlGetPages var48 = new FqlGetPages(var0, var1, var2, (ApiMethodListener)null, var44, FacebookPage.class);
      String var50 = "checkin_pages";
      var14.put(var50, var48);
      String var53 = placesWhereClause;
      FqlGetPlaces var57 = new FqlGetPlaces(var0, var1, var2, (ApiMethodListener)null, var53);
      String var59 = "places";
      var14.put(var59, var57);
      FqlGetDeals var65 = new FqlGetDeals(var0, var1, var2, (ApiMethodListener)null, "creator_id IN (SELECT page_id FROM #places)");
      String var67 = "deals";
      var14.put(var67, var65);
      FqlGetDealStatus var73 = new FqlGetDealStatus(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      String var75 = "deal_status";
      var14.put(var75, var73);
      FqlGetDealHistory var81 = new FqlGetDealHistory(var0, var1, var2, (ApiMethodListener)null, "promotion_id IN (SELECT promotion_id FROM #deals)");
      String var83 = "deal_history";
      var14.put(var83, var81);
      return var14;
   }

   public List<FacebookPost> getPosts() {
      List var1 = this.mPosts;
      return new ArrayList(var1);
   }

   protected FacebookProfile getProfile(long var1, Map<Long, FacebookProfile> var3) {
      FacebookProfile var4;
      if(var1 < 0L) {
         var4 = null;
      } else {
         Long var5 = Long.valueOf(var1);
         FacebookProfile var6 = (FacebookProfile)var3.get(var5);
         if(var6 != null) {
            var4 = var6;
         } else {
            String var7 = this.mContext.getString(2131361895);
            var4 = new FacebookProfile(var1, var7, (String)null, 0);
         }
      }

      return var4;
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      String var4 = "posts";
      List var5 = ((FqlGetStream.FqlGetPosts)this.getQueryByName(var4)).mPosts;
      String var7 = "profiles";
      Map var8 = ((FqlGetProfile)this.getQueryByName(var7)).getProfiles();
      boolean var9 = false;
      Map var10 = null;
      String var12 = "checkin_pages";
      FqlGetPages var13 = (FqlGetPages)this.getQueryByName(var12);
      String var15 = "places";
      FqlGetPlaces var16 = (FqlGetPlaces)this.getQueryByName(var15);
      if(var13 != null && var16 != null) {
         Map var17 = var13.getPages();
         var10 = var16.getPlaces();
         String var19 = "deals";
         Map var20 = ((FqlGetDeals)this.getQueryByName(var19)).getDeals();
         String var22 = "deal_status";
         Map var23 = ((FqlGetDealStatus)this.getQueryByName(var22)).getDealStatuses();
         String var25 = "deal_history";
         Map var26 = ((FqlGetDealHistory)this.getQueryByName(var25)).getDealHistories();

         FacebookPlace var30;
         FacebookDeal var38;
         for(Iterator var27 = var10.entrySet().iterator(); var27.hasNext(); var30.setDealInfo(var38)) {
            Entry var28 = (Entry)var27.next();
            Long var29 = (Long)var28.getKey();
            var30 = (FacebookPlace)var28.getValue();
            FacebookPage var33 = (FacebookPage)var17.get(var29);
            var30.setPageInfo(var33);
            var38 = (FacebookDeal)var20.get(var29);
            if(var38 != null) {
               Long var39 = Long.valueOf(var38.mDealId);
               FacebookDealStatus var42 = (FacebookDealStatus)var23.get(var39);
               Long var43 = Long.valueOf(var38.mDealId);
               FacebookDealHistory var46 = (FacebookDealHistory)var26.get(var43);
               var38.mDealHistory = var46;
               var38.mDealStatus = var42;
            }
         }

         var9 = true;
      }

      Iterator var49 = var5.iterator();

      while(var49.hasNext()) {
         FacebookPost var50 = (FacebookPost)var49.next();
         long var51 = var50.actorId;
         FacebookProfile var57 = this.getProfile(var51, var8);
         var50.setProfile(var57);
         long var60 = var50.targetId;
         FacebookProfile var66 = this.getProfile(var60, var8);
         var50.setTargetProfile(var66);
         var50.buildTaggedProfiles(var8);
         Iterator var71 = var50.getComments().getComments().iterator();

         while(var71.hasNext()) {
            FacebookPost.Comment var72 = (FacebookPost.Comment)var71.next();
            long var73 = var72.fromId;
            FacebookProfile var79 = this.getProfile(var73, var8);
            var72.setProfile(var79);
         }

         if(var9) {
            FacebookPost.Attachment var82 = var50.getAttachment();
            if(var82 != null && var82.mCheckinDetails != null) {
               long var83 = var82.mCheckinDetails.mPageId;
               FacebookCheckinDetails var85 = var82.mCheckinDetails;
               Long var86 = Long.valueOf(var83);
               FacebookPlace var89 = (FacebookPlace)var10.get(var86);
               var85.setPlaceInfo(var89);
            }
         }
      }

      this.mPosts = var5;
   }

   static class FqlGetPosts extends FqlGeneratedQuery {

      private static final String TAG = "FqlGetPosts";
      private static FacebookStreamType mType;
      private List<FacebookPost> mPosts;


      protected FqlGetPosts(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, long var7, String var9, int var10) {
         String var11 = buildWhereClause(var5, var7, var9, var10);
         super(var1, var2, var3, var4, "stream", var11, FacebookPost.class);
      }

      public FqlGetPosts(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, long var7, long[] var9, long var10, String var12, String[] var13, int var14, FacebookStreamType var15) {
         String var24 = buildWhereClause(var9, var10, var12, var13, var15);
         this(var1, var2, var3, var4, var5, var7, var24, var14);
      }

      private static String buildWhereClause(long var0, long var2, String var4, int var5) {
         StringBuilder var6 = new StringBuilder();
         boolean var7 = false;
         if(var0 > 0L) {
            StringBuilder var8 = var6.append(" updated_time >= ").append(var0);
            var7 = true;
         }

         if(var2 > 0L) {
            if(var7) {
               StringBuilder var9 = var6.append(" AND ");
            }

            StringBuilder var10 = var6.append(" updated_time <= ").append(var2);
            var7 = true;
         }

         if(var4 != false) {
            if(var7) {
               StringBuilder var11 = var6.append(" AND ");
            }

            var6.append(var4);
         }

         if(var5 > 0) {
            StringBuilder var13 = var6.append(" LIMIT ").append(var5);
         }

         return var6.toString();
      }

      private static String buildWhereClause(long[] var0, long var1, String var3, String[] var4, FacebookStreamType var5) {
         mType = var5;
         StringBuilder var6 = new StringBuilder();
         boolean var7 = false;
         boolean var31;
         if(var0 != null) {
            StringBuilder var8 = var6.append("source_id IN (");
            boolean var9 = true;
            long[] var10 = var0;
            int var11 = var0.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               long var13 = var10[var12];
               if(var9) {
                  var9 = false;
               } else {
                  StringBuilder var16 = var6.append(",");
               }

               var6.append(var13);
            }

            FacebookStreamType var17 = mType;
            FacebookStreamType var18 = FacebookStreamType.PAGE_WALL_STREAM;
            if(var17 == var18) {
               StringBuilder var19 = var6.append(")");
               StringBuilder var20 = var6.append("AND actor_id IN (");
               var7 = true;
               long[] var21 = var0;
               int var22 = var0.length;

               for(int var23 = 0; var23 < var22; ++var23) {
                  long var43 = var21[var23];
                  if(var7) {
                     var7 = false;
                  } else {
                     StringBuilder var25 = var6.append(",");
                  }

                  var6.append(var43);
               }
            }

            StringBuilder var30 = var6.append(")");
            var31 = true;
         } else {
            var31 = false;
         }

         if(var3 != null) {
            if(var31) {
               StringBuilder var32 = var6.append(" AND ");
            }

            StringBuilder var33 = var6.append(" filter_key=");
            StringUtils.appendEscapedFQLString(var6, var3);
            var31 = true;
         }

         FacebookStreamType var35 = mType;
         FacebookStreamType var36 = FacebookStreamType.PAGE_WALL_STREAM;
         if(var35 != var36 && var4 != null) {
            if(var31) {
               StringBuilder var37 = var6.append(" AND ");
            }

            StringBuilder var38 = var6.append(" post_id IN (");
            StringUtils.StringProcessor var39 = StringUtils.FQLEscaper;
            Object[] var40 = (Object[])var4;
            StringUtils.join(var6, ", ", var39, var40);
            StringBuilder var41 = var6.append(")");
            var31 = true;
         }

         if(!var31) {
            StringBuilder var42 = var6.append("source_id IN (SELECT target_id FROM connection WHERE source_id=").append(var1).append(" AND is_following=1)");
         }

         return var6.toString();
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
         List var2 = JMParser.parseObjectListJson(var1, FacebookPost.class);
         this.mPosts = var2;
      }
   }
}
