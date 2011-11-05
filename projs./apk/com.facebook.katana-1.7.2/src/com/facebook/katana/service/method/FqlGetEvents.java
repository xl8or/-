package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetProfile;
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

public class FqlGetEvents extends FqlMultiQuery {

   private static final String TAG = "FqlGetEvents";
   protected List<FacebookEvent> mEvents;


   public FqlGetEvents(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
      LinkedHashMap var7 = buildQueries(var1, var2, var3, var5);
      super(var1, var2, var3, var7, var4);
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3) {
      LinkedHashMap var5 = new LinkedHashMap();
      FqlGetEvents.FqlGetEventInfo var11 = new FqlGetEvents.FqlGetEventInfo(var0, var1, var2, (ApiMethodListener)null, var3);
      var5.put("event_info", var11);
      FqlGetEvents.FqlGetEventRsvpStatus var18 = new FqlGetEvents.FqlGetEventRsvpStatus(var0, var1, var2, (ApiMethodListener)null, var3, "event_info");
      var5.put("rsvp_status", var18);
      FqlGetProfile var23 = new FqlGetProfile(var0, var1, var2, (ApiMethodListener)null, "id IN (SELECT creator FROM #event_info)");
      var5.put("creator_info", var23);
      return var5;
   }

   private void saveSearchResults() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public List<FacebookEvent> getEvents() {
      return this.mEvents;
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      String var4 = "event_info";
      FqlGetEvents.FqlGetEventInfo var5 = (FqlGetEvents.FqlGetEventInfo)this.getQueryByName(var4);
      String var7 = "rsvp_status";
      FqlGetEvents.FqlGetEventRsvpStatus var8 = (FqlGetEvents.FqlGetEventRsvpStatus)this.getQueryByName(var7);
      String var10 = "creator_info";
      FqlGetProfile var11 = (FqlGetProfile)this.getQueryByName(var10);
      List var12 = var5.getEvents();
      this.mEvents = var12;
      HashMap var13 = new HashMap();

      FacebookEvent var15;
      Object var27;
      for(Iterator var14 = this.mEvents.iterator(); var14.hasNext(); ((List)var27).add(var15)) {
         var15 = (FacebookEvent)var14.next();
         long var16 = var15.getEventId();
         FacebookEvent.RsvpStatus var18 = var8.getEventRsvpStatus(var16);
         var15.setRsvpStatus(var18);
         Long var21 = new Long;
         long var22 = var15.getCreatorId();
         var21.<init>(var22);
         var27 = (List)var13.get(var21);
         if(var27 == null) {
            var27 = new ArrayList();
            var13.put(var21, var27);
         }
      }

      Iterator var30 = var11.getProfiles().entrySet().iterator();

      while(var30.hasNext()) {
         Entry var31 = (Entry)var30.next();
         Long var32 = (Long)var31.getKey();
         List var33 = (List)var13.get(var32);
         if(var33 != null) {
            Iterator var34 = var33.iterator();

            while(var34.hasNext()) {
               FacebookEvent var35 = (FacebookEvent)var34.next();
               FacebookProfile var36 = (FacebookProfile)var31.getValue();
               var35.setCreator(var36);
            }
         }
      }

      this.saveSearchResults();
   }

   static class FqlGetEventRsvpStatus extends FqlQuery {

      // $FF: synthetic field
      static final boolean $assertionsDisabled = false;
      private static final String TAG = "FqlGetEventRsvpStatus";
      private final Map<Long, FacebookEvent.RsvpStatus> mRsvpStatus;


      static {
         byte var0;
         if(!FqlGetEvents.class.desiredAssertionStatus()) {
            var0 = 1;
         } else {
            var0 = 0;
         }

         $assertionsDisabled = (boolean)var0;
      }

      public FqlGetEventRsvpStatus(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, String var7) {
         String var8 = buildQuery(var5, var7);
         super(var1, var2, var3, var8, var4);
         HashMap var14 = new HashMap();
         this.mRsvpStatus = var14;
      }

      protected static String buildQuery(long var0, String var2) {
         StringBuilder var3 = new StringBuilder("SELECT eid, rsvp_status FROM event_member WHERE uid=");
         String var4 = String.valueOf(var0);
         var3.append(var4);
         StringBuilder var6 = var3.append(" AND eid IN (SELECT eid FROM #");
         var3.append(var2);
         StringBuilder var8 = var3.append(")");
         return var3.toString();
      }

      public FacebookEvent.RsvpStatus getEventRsvpStatus(long var1) {
         Map var3 = this.mRsvpStatus;
         Long var4 = new Long(var1);
         return (FacebookEvent.RsvpStatus)var3.get(var4);
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

   static class FqlGetEventInfo extends FqlQuery {

      private static final String TAG = "FqlGetEventInfo";
      private final List<FacebookEvent> mEvents;


      public FqlGetEventInfo(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
         String var7 = buildQuery(var5);
         super(var1, var2, var3, var7, var4);
         ArrayList var13 = new ArrayList();
         this.mEvents = var13;
      }

      protected static String buildQuery(long var0) {
         Time var2 = new Time();
         var2.setToNow();
         long var3 = var2.toMillis((boolean)0) / 1000L;
         StringBuilder var5 = new StringBuilder("SELECT eid, name, tagline, pic_small, pic, host, description, event_type, event_subtype, start_time, end_time, creator, location, venue, hide_guest_list FROM event WHERE end_time > ");
         String var6 = String.valueOf(var3);
         var5.append(var6);
         StringBuilder var8 = var5.append(" AND eid IN (SELECT eid FROM event_member where uid=");
         String var9 = String.valueOf(var0);
         var5.append(var9);
         StringBuilder var11 = var5.append(") ORDER BY start_time");
         return var5.toString();
      }

      public List<FacebookEvent> getEvents() {
         return Collections.unmodifiableList(this.mEvents);
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
         JsonToken var2 = var1.getCurrentToken();
         JsonToken var3 = JsonToken.START_ARRAY;
         if(var2 == var3) {
            while(true) {
               JsonToken var4 = JsonToken.END_ARRAY;
               if(var2 == var4) {
                  return;
               }

               JsonToken var5 = JsonToken.START_OBJECT;
               if(var2 == var5) {
                  FacebookEvent var6 = FacebookEvent.parseFromJSON(var1);
                  this.mEvents.add(var6);
               }

               var2 = var1.nextToken();
            }
         }
      }
   }
}
