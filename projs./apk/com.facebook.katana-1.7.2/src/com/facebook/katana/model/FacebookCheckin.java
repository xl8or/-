package com.facebook.katana.model;

import com.facebook.katana.model.FacebookCheckinDetails;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.Comparator;

public class FacebookCheckin extends JMCachingDictDestination {

   public static final long INVALID_ID = 255L;
   public static final Comparator<FacebookCheckin> checkinsByTimeComparator = new FacebookCheckin.1();
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "actor",
      type = FacebookUser.class
   )
   protected FacebookUser mActor;
   @JMAutogen.InferredType(
      jsonFieldName = "actor_uid"
   )
   public final long mActorId;
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "checkin_details",
      type = FacebookCheckinDetails.class
   )
   protected FacebookCheckinDetails mCheckinDetails;
   @JMAutogen.InferredType(
      jsonFieldName = "checkin_id"
   )
   public final long mCheckinId;


   private FacebookCheckin() {
      this.mActorId = 65535L;
      this.mCheckinId = 65535L;
   }

   public FacebookCheckin(long var1, long var3) {
      this.mActorId = var1;
      this.mCheckinId = var3;
   }

   public FacebookUser getActor() {
      return this.mActor;
   }

   public FacebookCheckinDetails getDetails() {
      return this.mCheckinDetails;
   }

   public void setActor(FacebookUser var1) {
      this.mActor = var1;
   }

   public void setDetails(FacebookCheckinDetails var1) {
      this.mCheckinDetails = var1;
   }

   static class 1 implements Comparator<FacebookCheckin> {

      1() {}

      public int compare(FacebookCheckin var1, FacebookCheckin var2) {
         long var4 = var2.mCheckinDetails.mTimestamp;
         long var6 = var1.mCheckinDetails.mTimestamp;
         return (int)(var4 - var6);
      }
   }
}
