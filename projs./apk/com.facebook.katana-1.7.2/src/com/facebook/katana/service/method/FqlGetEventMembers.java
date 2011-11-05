package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetUsersProfile;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FqlGetEventMembers extends FqlMultiQuery {

   private static final String TAG = "FqlGetEventMembers";
   protected long mEventId;
   protected Map<FacebookEvent.RsvpStatus, List<FacebookUser>> mMembersByRsvpStatus;


   public FqlGetEventMembers(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
      LinkedHashMap var7 = buildQueries(var1, var2, var3, var5);
      super(var1, var2, var3, var7, var4);
      this.mEventId = var5;
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3) {
      LinkedHashMap var5 = new LinkedHashMap();
      FqlGetEventMembers.FqlGetRsvpStatusByEvent var11 = new FqlGetEventMembers.FqlGetRsvpStatusByEvent(var0, var1, var2, (ApiMethodListener)null, var3);
      var5.put("rsvp_status", var11);
      FqlGetUsersProfile var16 = new FqlGetUsersProfile(var0, var1, var2, (ApiMethodListener)null, "uid IN (SELECT uid FROM #rsvp_status)", FacebookUser.class);
      var5.put("user_info", var16);
      return var5;
   }

   public long getEventId() {
      return this.mEventId;
   }

   public Map<FacebookEvent.RsvpStatus, List<FacebookUser>> getEventMembersByStatus() {
      return Collections.unmodifiableMap(this.mMembersByRsvpStatus);
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      FqlGetEventMembers.FqlGetRsvpStatusByEvent var3 = (FqlGetEventMembers.FqlGetRsvpStatusByEvent)this.getQueryByName("rsvp_status");
      FqlGetUsersProfile var4 = (FqlGetUsersProfile)this.getQueryByName("user_info");
      HashMap var5 = new HashMap();
      this.mMembersByRsvpStatus = var5;
      Map var6 = var3.getEventRsvpStatus();
      Map var7 = var4.getUsers();
      Iterator var8 = var6.entrySet().iterator();

      while(var8.hasNext()) {
         Entry var9 = (Entry)var8.next();
         Long var10 = (Long)var9.getKey();
         FacebookEvent.RsvpStatus var11 = (FacebookEvent.RsvpStatus)var9.getValue();
         if(!this.mMembersByRsvpStatus.containsKey(var11)) {
            Map var12 = this.mMembersByRsvpStatus;
            ArrayList var13 = new ArrayList();
            var12.put(var11, var13);
         }

         List var15 = (List)this.mMembersByRsvpStatus.get(var11);
         Object var16 = var7.get(var10);
         var15.add(var16);
      }

   }

   static class FqlGetRsvpStatusByEvent extends FqlQuery {

      // $FF: synthetic field
      static final boolean $assertionsDisabled = false;
      private static final String TAG = "FqlGetRsvpStatusByEvent";
      private final Map<Long, FacebookEvent.RsvpStatus> mRsvpStatus;


      static {
         byte var0;
         if(!FqlGetEventMembers.class.desiredAssertionStatus()) {
            var0 = 1;
         } else {
            var0 = 0;
         }

         $assertionsDisabled = (boolean)var0;
      }

      public FqlGetRsvpStatusByEvent(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
         String var7 = buildQuery(var5);
         super(var1, var2, var3, var7, var4);
         HashMap var13 = new HashMap();
         this.mRsvpStatus = var13;
      }

      protected static String buildQuery(long var0) {
         StringBuilder var2 = new StringBuilder("SELECT uid, rsvp_status FROM event_member WHERE eid=");
         String var3 = String.valueOf(var0);
         var2.append(var3);
         return var2.toString();
      }

      public Map<Long, FacebookEvent.RsvpStatus> getEventRsvpStatus() {
         return Collections.unmodifiableMap(this.mRsvpStatus);
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
         JsonToken var2 = var1.getCurrentToken();
         if(!$assertionsDisabled) {
            JsonToken var3 = JsonToken.START_ARRAY;
            if(var2 != var3) {
               throw new AssertionError();
            }
         }

         JsonToken var4 = var1.nextToken();

         while(true) {
            JsonToken var5 = JsonToken.END_ARRAY;
            if(var4 == var5) {
               return;
            }

            JsonToken var6 = JsonToken.START_OBJECT;
            if(var4 == var6) {
               Map var7 = this.mRsvpStatus;
               FacebookEvent.parseRsvpStatus(var1, var7);
            } else {
               JsonToken var8 = JsonToken.START_ARRAY;
               if(var4 == var8) {
                  var1.skipChildren();
               }
            }

            var4 = var1.nextToken();
         }
      }
   }
}
