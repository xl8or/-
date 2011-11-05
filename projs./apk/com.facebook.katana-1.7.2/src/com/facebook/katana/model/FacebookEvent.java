package com.facebook.katana.model;

import android.content.ContentValues;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookEvent extends JMCachingDictDestination {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   public static final long INVALID_ID = 255L;
   public static final String KEY_VENUE_CITY = "city";
   public static final String KEY_VENUE_COUNTRY = "country";
   public static final String KEY_VENUE_STATE = "state";
   public static final String KEY_VENUE_STREET = "street";
   static Map<Integer, FacebookEvent.RsvpStatus> intStatusMapper;
   static Map<FacebookEvent.RsvpStatus, String> statusStringMapper;
   static Map<String, FacebookEvent.RsvpStatus> stringStatusMapper;
   protected FacebookProfile mCreator;
   @JMAutogen.InferredType(
      jsonFieldName = "creator"
   )
   protected long mCreatorId;
   @JMAutogen.ExplicitType(
      jsonFieldName = "description",
      type = StringUtils.JMStrippedString.class
   )
   protected String mDescription;
   @JMAutogen.InferredType(
      jsonFieldName = "end_time"
   )
   protected long mEndTime;
   @JMAutogen.InferredType(
      jsonFieldName = "eid"
   )
   protected long mEventId = 65535L;
   @JMAutogen.ExplicitType(
      jsonFieldName = "event_subtype",
      type = StringUtils.JMStrippedString.class
   )
   protected String mEventSubtype;
   @JMAutogen.ExplicitType(
      jsonFieldName = "event_type",
      type = StringUtils.JMStrippedString.class
   )
   protected String mEventType;
   @JMAutogen.InferredType(
      jsonFieldName = "hide_guest_list"
   )
   protected boolean mHideGuestList;
   @JMAutogen.ExplicitType(
      jsonFieldName = "host",
      type = StringUtils.JMStrippedString.class
   )
   protected String mHost;
   @JMAutogen.InferredType(
      jsonFieldName = "pic_small"
   )
   protected String mImageUrl;
   @JMAutogen.ExplicitType(
      jsonFieldName = "location",
      type = StringUtils.JMStrippedString.class
   )
   protected String mLocation;
   @JMAutogen.InferredType(
      jsonFieldName = "pic"
   )
   protected String mMediumImageUrl;
   @JMAutogen.ExplicitType(
      jsonFieldName = "name",
      type = StringUtils.JMStrippedString.class
   )
   protected String mName;
   protected FacebookEvent.RsvpStatus mRsvpStatus;
   @JMAutogen.InferredType(
      jsonFieldName = "start_time"
   )
   protected long mStartTime;
   @JMAutogen.ExplicitType(
      jsonFieldName = "tagline",
      type = StringUtils.JMStrippedString.class
   )
   protected String mTagline;
   @JMAutogen.InferredType(
      jsonFieldName = "venue"
   )
   protected Map<String, Serializable> mVenue;


   static {
      byte var0;
      if(!FacebookEvent.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      intStatusMapper = new HashMap();
      stringStatusMapper = new HashMap();
      statusStringMapper = new HashMap();
      Map var1 = stringStatusMapper;
      FacebookEvent.RsvpStatusEnum var2 = FacebookEvent.RsvpStatusEnum.ATTENDING;
      FacebookEvent.RsvpStatus var3 = new FacebookEvent.RsvpStatus(var2);
      var1.put("attending", var3);
      Map var5 = stringStatusMapper;
      FacebookEvent.RsvpStatusEnum var6 = FacebookEvent.RsvpStatusEnum.UNSURE;
      FacebookEvent.RsvpStatus var7 = new FacebookEvent.RsvpStatus(var6);
      var5.put("unsure", var7);
      Map var9 = stringStatusMapper;
      FacebookEvent.RsvpStatusEnum var10 = FacebookEvent.RsvpStatusEnum.DECLINED;
      FacebookEvent.RsvpStatus var11 = new FacebookEvent.RsvpStatus(var10);
      var9.put("declined", var11);
      Map var13 = stringStatusMapper;
      FacebookEvent.RsvpStatusEnum var14 = FacebookEvent.RsvpStatusEnum.NOT_REPLIED;
      FacebookEvent.RsvpStatus var15 = new FacebookEvent.RsvpStatus(var14);
      var13.put("not_replied", var15);
      Iterator var17 = stringStatusMapper.entrySet().iterator();

      while(var17.hasNext()) {
         Entry var18 = (Entry)var17.next();
         Map var19 = intStatusMapper;
         int var20 = ((FacebookEvent.RsvpStatus)var18.getValue()).status.ordinal();
         Integer var21 = new Integer(var20);
         Object var22 = var18.getValue();
         var19.put(var21, var22);
         Map var24 = statusStringMapper;
         Object var25 = var18.getValue();
         Object var26 = var18.getKey();
         var24.put(var25, var26);
      }

   }

   private FacebookEvent() {}

   public static Map<String, Serializable> deserializeVenue(byte[] var0) throws IOException {
      ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
      ObjectInputStream var2 = new ObjectInputStream(var1);

      try {
         Map var5 = (Map)var2.readObject();
         return var5;
      } catch (ClassNotFoundException var4) {
         String var3 = var4.toString();
         throw new IOException(var3);
      }
   }

   public static FacebookEvent.RsvpStatus getRsvpStatus(int var0) {
      Map var1 = intStatusMapper;
      Integer var2 = new Integer(var0);
      return (FacebookEvent.RsvpStatus)var1.get(var2);
   }

   public static FacebookEvent.RsvpStatus getRsvpStatus(String var0) {
      return (FacebookEvent.RsvpStatus)stringStatusMapper.get(var0);
   }

   public static String getRsvpStatusString(FacebookEvent.RsvpStatus var0) {
      return (String)statusStringMapper.get(var0);
   }

   public static FacebookEvent parseFromJSON(JsonParser var0) throws JsonParseException, IOException, JMException {
      JMDict var1 = JMAutogen.generateJMParser(FacebookEvent.class);
      Object var2 = JMParser.parseJsonResponse(var0, (JMBase)var1);
      FacebookEvent var3;
      if(var2 instanceof FacebookEvent) {
         var3 = (FacebookEvent)var2;
      } else {
         var3 = null;
      }

      return var3;
   }

   public static void parseRsvpStatus(JsonParser var0, Map<Long, FacebookEvent.RsvpStatus> var1) throws JsonParseException, IOException {
      JsonToken var2 = var0.getCurrentToken();
      Long var3 = null;
      FacebookEvent.RsvpStatus var4 = null;
      if(!$assertionsDisabled) {
         JsonToken var5 = JsonToken.START_OBJECT;
         if(var2 != var5) {
            throw new AssertionError();
         }
      }

      JsonToken var6 = var0.nextToken();

      while(true) {
         JsonToken var7 = JsonToken.END_OBJECT;
         if(var6 == var7) {
            if(var3 == null) {
               return;
            }

            var1.put(var3, var4);
            return;
         }

         label58: {
            JsonToken var8 = JsonToken.VALUE_NUMBER_INT;
            if(var6 != var8) {
               JsonToken var9 = JsonToken.VALUE_STRING;
               if(var6 != var9) {
                  JsonToken var17 = JsonToken.START_ARRAY;
                  if(var6 != var17) {
                     JsonToken var18 = JsonToken.START_OBJECT;
                     if(var6 != var18) {
                        break label58;
                     }
                  }

                  var0.skipChildren();
                  break label58;
               }
            }

            String var10 = var0.getCurrentName();
            if(!var10.equals("eid") && !var10.equals("uid")) {
               if(var10.equals("rsvp_status")) {
                  if(!$assertionsDisabled) {
                     JsonToken var15 = JsonToken.VALUE_STRING;
                     if(var6 != var15) {
                        throw new AssertionError();
                     }
                  }

                  String var16 = var0.getText();
                  var4 = (FacebookEvent.RsvpStatus)stringStatusMapper.get(var16);
               }
            } else {
               JsonToken var11 = JsonToken.VALUE_NUMBER_INT;
               if(var6 == var11) {
                  long var12 = var0.getLongValue();
                  var3 = new Long(var12);
               } else {
                  String var14 = var0.getText();
                  var3 = new Long(var14);
               }
            }
         }

         var6 = var0.nextToken();
      }
   }

   public static byte[] serializeVenue(Map<String, Serializable> var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      (new ObjectOutputStream(var1)).writeObject(var0);
      return var1.toByteArray();
   }

   public FacebookProfile getCreator() {
      return this.mCreator;
   }

   public long getCreatorId() {
      return this.mCreatorId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public long getEndTime() {
      return this.mEndTime;
   }

   public long getEventId() {
      return this.mEventId;
   }

   public String getEventSubtype() {
      return this.mEventSubtype;
   }

   public String getEventType() {
      return this.mEventType;
   }

   public boolean getHideGuestList() {
      return this.mHideGuestList;
   }

   public String getHost() {
      return this.mHost;
   }

   public String getImageUrl() {
      return this.mImageUrl;
   }

   public String getLocation() {
      return this.mLocation;
   }

   public String getMediumImageUrl() {
      return this.mMediumImageUrl;
   }

   public String getName() {
      return this.mName;
   }

   public FacebookEvent.RsvpStatus getRsvpStatus() {
      return this.mRsvpStatus;
   }

   public long getStartTime() {
      return this.mStartTime;
   }

   public String getTagline() {
      return this.mTagline;
   }

   public Map<String, Serializable> getVenue() {
      return Collections.unmodifiableMap(this.mVenue);
   }

   public void setCreator(FacebookProfile var1) {
      this.mCreator = var1;
   }

   public void setRsvpStatus(FacebookEvent.RsvpStatus var1) {
      this.mRsvpStatus = var1;
   }

   public void writeContentValues(ContentValues var1) throws IOException {
      byte[] var2 = serializeVenue(this.mVenue);
      Long var3 = Long.valueOf(this.mEventId);
      var1.put("event_id", var3);
      String var4 = this.mName;
      var1.put("event_name", var4);
      String var5 = this.mTagline;
      var1.put("tagline", var5);
      String var6 = this.mImageUrl;
      var1.put("image_url", var6);
      String var7 = this.mMediumImageUrl;
      var1.put("medium_image_url", var7);
      String var8 = this.mHost;
      var1.put("host", var8);
      String var9 = this.mDescription;
      var1.put("description", var9);
      String var10 = this.mEventType;
      var1.put("event_type", var10);
      String var11 = this.mEventSubtype;
      var1.put("event_subtype", var11);
      Long var12 = Long.valueOf(this.mStartTime);
      var1.put("start_time", var12);
      Long var13 = Long.valueOf(this.mEndTime);
      var1.put("end_time", var13);
      if(this.mCreator != null) {
         Long var14 = Long.valueOf(this.mCreator.mId);
         var1.put("creator_id", var14);
         String var15 = this.mCreator.mDisplayName;
         var1.put("display_name", var15);
         String var16 = this.mCreator.mImageUrl;
         var1.put("creator_image_url", var16);
      }

      String var17 = this.mLocation;
      var1.put("location", var17);
      var1.put("venue", var2);
      String var18 = "hide_guest_list";
      byte var19;
      if(this.mHideGuestList) {
         var19 = 1;
      } else {
         var19 = 0;
      }

      Integer var20 = Integer.valueOf(var19);
      var1.put(var18, var20);
      Integer var21 = Integer.valueOf(this.mRsvpStatus.status.ordinal());
      var1.put("rsvp_status", var21);
   }

   public static class RsvpStatus {

      public final FacebookEvent.RsvpStatusEnum status;


      public RsvpStatus(FacebookEvent.RsvpStatusEnum var1) {
         this.status = var1;
      }
   }

   public static enum RsvpStatusEnum {

      // $FF: synthetic field
      private static final FacebookEvent.RsvpStatusEnum[] $VALUES;
      ATTENDING("ATTENDING", 0),
      DECLINED("DECLINED", 2),
      NOT_REPLIED("NOT_REPLIED", 3),
      UNSURE("UNSURE", 1);


      static {
         FacebookEvent.RsvpStatusEnum[] var0 = new FacebookEvent.RsvpStatusEnum[4];
         FacebookEvent.RsvpStatusEnum var1 = ATTENDING;
         var0[0] = var1;
         FacebookEvent.RsvpStatusEnum var2 = UNSURE;
         var0[1] = var2;
         FacebookEvent.RsvpStatusEnum var3 = DECLINED;
         var0[2] = var3;
         FacebookEvent.RsvpStatusEnum var4 = NOT_REPLIED;
         var0[3] = var4;
         $VALUES = var0;
      }

      private RsvpStatusEnum(String var1, int var2) {}
   }
}
