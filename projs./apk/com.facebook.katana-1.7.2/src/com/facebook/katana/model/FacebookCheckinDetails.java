package com.facebook.katana.model;

import com.facebook.katana.model.FacebookApp;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.types.JMLong;
import java.util.Collections;
import java.util.List;

@JMAutogen.IgnoreUnexpectedJsonFields
public class FacebookCheckinDetails extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "app_id"
   )
   public final long mAppId;
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "app_info",
      type = FacebookApp.class
   )
   protected FacebookApp mAppInfo;
   @JMAutogen.InferredType(
      jsonFieldName = "author_uid"
   )
   public final long mAuthorId;
   @JMAutogen.InferredType(
      jsonFieldName = "checkin_id"
   )
   public final long mCheckinId;
   @JMAutogen.InferredType(
      jsonFieldName = "page_id"
   )
   public final long mPageId;
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "place_info",
      type = FacebookPlace.class
   )
   protected FacebookPlace mPlaceInfo;
   @JMAutogen.ListType(
      jsonFieldName = "tagged_uids",
      listElementTypes = {JMLong.class}
   )
   public List<Long> mTaggedUids;
   @JMAutogen.InferredType(
      jsonFieldName = "timestamp"
   )
   public final long mTimestamp;


   private FacebookCheckinDetails() {
      this.mCheckinId = 65535L;
      this.mAuthorId = 65535L;
      this.mPageId = 65535L;
      this.mTimestamp = 0L;
      List var1 = Collections.EMPTY_LIST;
      this.mTaggedUids = var1;
      this.mAppId = 0L;
   }

   public FacebookCheckinDetails(long var1, long var3, long var5, long var7, List<Long> var9, long var10) {
      this.mCheckinId = var1;
      this.mAuthorId = var3;
      this.mPageId = var5;
      this.mTimestamp = var7;
      this.mTaggedUids = var9;
      this.mAppId = var10;
   }

   public FacebookApp getAppInfo() {
      return this.mAppInfo;
   }

   public FacebookPlace getPlaceInfo() {
      return this.mPlaceInfo;
   }

   public void setAppInfo(FacebookApp var1) {
      this.mAppInfo = var1;
   }

   public void setPlaceInfo(FacebookPlace var1) {
      this.mPlaceInfo = var1;
   }
}
